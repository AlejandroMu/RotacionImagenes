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

/**
 * ClientRotacion
 */
@Service(Runnable.class)
public class ClientRotacion implements Runnable {

    private IBroker broker;
    @Property(name = "pathI")
    private String pathImage;
    @Property(name = "grados")
    private double grados;

    
    @Reference(name = "broker",required=true)
    public void setBroker(IBroker broker){
        this.broker=broker;
    }

    public void run(){
        try {
            String route=broker.getOperation();
            File imagen=new File(pathImage);
            IMatrixOperations tmp=(IMatrixOperations)Naming.lookup(route);
            int[] inic={0,0};
            int[] fin={16330,8588};
            Point[] corners=claculateCorners();
            int[] c={-corners[0].x,-corners[0].yMa};
            int[] size=getSize(image);
            tmp.rotar(inic,fin,c,grados,pathImage);

         
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private Point[] claculateCorners(int row,int colum,double gr) {
		double cos=Math.cos(gr);
		double sen=Math.sin(gr);
		double xMi=min(0,min(row*sen,min(colum*cos,colum*cos+row*sen)));
		double xMa=max(0,max(row*sen,max(colum*cos,colum*cos+row*sen)));
		double yMi=min(0,min(row*cos,min(-colum*Sen,-colum*Sen+row*cos)));
		double yMa=max(0,max(row*cos,max(-colum*Sen,-colum*Sen+row*cos)));
		Point[] ret={new Point((int)Math.floor(xMi),(int)Math.floor(yMi)),new Point((int)Math.floor(xMa),(int)Math.floor(yMa))};
		return ret;
	}
	public double min(double x,double y){
		return x>y?y:x;
	}
	public double max(double x,double y){
		return x<y?y:x;
	}
    
	public int getSize(File ruta){
		ImageInputStream input = ImageIO.createImageInputStream(ruta);
		ImageReader reader = ImageIO.getImageReaders(input).next();
		int[] size={reader.getHeight(0),reader.getWidth(0)};
		return size;
	}

      
}