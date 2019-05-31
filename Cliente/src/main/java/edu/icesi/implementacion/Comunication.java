package edu.icesi.implementacion;

import java.awt.Point;
import java.rmi.Naming;
import java.util.List;
import edu.icesi.interfaces.*;


/**
 * Comunication
 */
public class Comunication extends Thread {

    private int[][] data;
    private double angle;
    private String urlB;
    private ImageManager manager;

    public Comunication(int[][] data, double a, String b,ImageManager ma) {
        this.data = data;
        angle = a;
        urlB = b;
        manager=ma;
    }

    @Override
    public void run() {
        try {
            String url=urlB;
            System. out. println(url);
            // IBroker broker = (IBroker) Naming.lookup(url);
            // String urlS = broker.getOperation();
            IMatrixOperations oper = (IMatrixOperations) Naming.lookup("rmi://localhost:1235/multiplicar");
            List<int[][]> ret=oper.rotar(data, angle);
            manager.merge(ret,new Point(),new Point());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}