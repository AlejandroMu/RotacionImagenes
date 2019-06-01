package edu.icesi.implementacion;

import java.awt.Point;

import org.osoa.sca.annotations.*;
import edu.icesi.interfaces.*;
import java.rmi.*;
import edu.icesi.interfaces.*;


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
            IMatrixOperations tmp=(IMatrixOperations)Naming.lookup(route);

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
   
    
}