package edu.icesi.implementacion;

import java.awt.Point;

import org.osoa.sca.annotations.*;
import edu.icesi.interfaces.*;
import java.rmi.*;
import edu.icesi.interfaces.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.util.*;
import java.awt.*;
import javax.imageio.stream.ImageInputStream;


/**
 * ClientRotacion
 */
@Service(Runnable.class)
public class ClientRotacion implements Runnable {

    private IBroker broker;
    @Property(name = "pathI")
    private String pathImage;
    @Property(name = "name")
    private String name;
    @Property(name = "grados")
    private double grados;

    
    @Reference(name = "broker",required=true)
    public void setBroker(IBroker broker){
        this.broker=broker;
    }

    public void run(){
        try {
            File imagen=new File(pathImage+"/"+name);
            int[] size=getSize(imagen);
            int cutx=(int)Math.ceil(size[0]/17000.0);
            int cuty=(int)Math.ceil(size[1]/9000.0);
            int[] inic={0,0};
            
            int plusx=(int)Math.ceil((double)size[0]/cutx);
            int plusy=(int)Math.ceil((double)size[1]/cuty);
           
           
            
            int x0=0;
            int y0=0;
            int x1=plusx;
            int y1=plusy;
            while(true){
                Point[] corners=claculateCorners((y1-y0),(x1-x0),grados);
                int[] deltas = {-corners[0].x,-corners[0].y};
                int[] tam={corners[1].x-corners[0].x+1,corners[1].y-corners[0].y+1};
               
                
                rotar(new int[]{x0,y0},new int[]{x1,y1},deltas,grados,name,tam);
                if(x1==size[0]){
                    if(y1==size[1]){
                        break;
                    }
                    y0+=plusy;
                    x0=0;
                    y1+=plusy;
                    x1=plusx;
                    if(y1>size[1]){
                        y1=size[1];
                    }
                }else{
                    x0+=plusx;
                    x1+=plusx;
                    if(x1>size[0]){
                        x1=size[0];
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
   
    public void rotar(int[] inic,int[] fin,int[] deltas,double grados,String name,int[] tam){
       try {
        String route=broker.getOperation();
        IMatrixOperations tmp=(IMatrixOperations)Naming.lookup(route);
        Comunication com=new Comunication(inic, fin, deltas, grados, name, tam, tmp);
        com.start();

       } catch (Exception e) {
        System.out.println(e.getMessage());
    }
    }

    private Point[] claculateCorners(int row,int colum ,double grados) {
        double gr=Math.toRadians(grados);
		double cos=Math.cos(gr);
        double sen=Math.sin(gr);
     
		double xMi=min(0,min(row*cos,min(colum*sen,row*cos+colum*sen)));
		double xMa=max(0,max(row*cos,max(colum*sen,row*cos+colum*sen)));
		double yMi=min(0,min(colum*cos,min(-row*sen,-row*sen+colum*cos)));
		double yMa=max(0,max(colum*cos,max(-row*sen,-row*sen+colum*cos)));
		Point[] ret={new Point((int)Math.ceil(xMi),(int)Math.ceil(yMi)),new Point((int)Math.floor(xMa),(int)Math.floor(yMa))};
		return ret;
	}
	public double min(double x,double y){
		return x>y?y:x;
	}
	public double max(double x,double y){
		return x<y?y:x;
	}
    
	public int[] getSize(File ruta){
        try {
            ImageInputStream input = ImageIO.createImageInputStream(ruta);
            ImageReader reader = ImageIO.getImageReaders(input).next();
            reader.setInput(input);
            int[] size={reader.getWidth(0),reader.getHeight(0)};
            return size;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new int[]{1000 ,700};
        }
	}

      
}