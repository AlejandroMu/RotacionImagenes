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
            String route=broker.getOperation();
            System.out.println(pathImage+"/"+name);
            File imagen=new File(pathImage+"/"+name);
            IMatrixOperations tmp=(IMatrixOperations)Naming.lookup(route);
            int[] inic={0,0};
            int[] size=getSize(imagen);
            System.out.println("size -> "+size[0]+" "+size[1]);
            Point[] corners=claculateCorners(size[1],size[0],grados);
            System.out.println("corners ->"+corners[0]+" "+corners[1]);
            int[] c={-corners[0].x,-corners[0].y};
            int[] tam={corners[1].x-corners[0].x,corners[1].y-corners[0].y};
            System.out.println("tam -> "+tam[0]+" "+tam[1]);
            tmp.rotar(inic,size,c,grados,name,tam);
         
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private Point[] claculateCorners(int row,int colum,double grados) {
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