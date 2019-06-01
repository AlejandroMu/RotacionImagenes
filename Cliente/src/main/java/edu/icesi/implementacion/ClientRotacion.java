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
            System.out.println("recive --> ");
            System.out.println(pathImage);
            System.out.println(grados);
            System.out.println(broker);
            String route=broker.getOperation();
            System.out.println("recive --> "+route); 
            File imagen=new File(pathImage);
            IMatrixOperations tmp=(IMatrixOperations)Naming.lookup(route);
            rotar(imagen,tmp);

            double[][] m1={{2,1},{2,1}};
            double[][] m2={{1},{3}};
            double[][] res=tmp.matrixMultiplication(m1,m2);
            for (double[] var : res) {
                for (double var1 : var) {
                    System.out.println(var1);
                }
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void rotar(File newFile,IMatrixOperations conexion){
        BufferedImage image=ImageIo.read(newFile);
        int h=image.getHeight();
        int w=image.getWidht();
        int[] inic={0,0};
        int[] fin={w-1,h-1};
        int[] cen={w/2,h/2};
        List<int[][]> ret=conexion.rotar(inic,fin,cen,45);
        int[][] corners={{0,0},{0,0}};
        for (int[][] var : ret) {
            if(var[0][0]<corners[0][0]){
                corners[0][0]=var[0][0];
            }

            if(var[0][0]>corners[1][0]){
                corners[1][0]=var[0][0];
            }

            if(var[0][1]<corners[0][1]){
                corners[0][1]=var[0][1];
            }

            if(var[0][1]>corners[1][1]){
                corners[1][1]=var[0][1];
            }
        }
        int nh=corners[1][1]-corners[0][1];
        int nw=corners[1][0]-corners[0][0];
        BufferedImage rotada=new BufferedImage(nw,nh,BufferedImage.TYPE_INT_RGB);
        for (int[][] var : ret) {
            rotada.setRGB(var[0][1],var[0][0],image.getRGB(var[1][1],var[1][0]));
        }
        ImageIo.write(rotada,"jpg",new File("/"));


    }

      
}