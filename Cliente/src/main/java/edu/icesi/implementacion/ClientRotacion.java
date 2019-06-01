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
            String route=broker.getOperation();
            File imagen=new File(pathImage);
            IMatrixOperations tmp=(IMatrixOperations)Naming.lookup(route);
            int[] inic={0,0};
            int[] fin={2500,2500};
            int[] c={2500,2500};
            tmp.rotar(inic,fin,c,grados,pathImage);

         
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    

      
}