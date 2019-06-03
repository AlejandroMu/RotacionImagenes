package edu.icesi.implementacion;

import java.awt.Point;
import java.rmi.Naming;
import java.util.List;
import edu.icesi.interfaces.*;


/**
 * Comunication
 */
public class Comunication extends Thread {

    private int[] inic;
    private int[] fin;
    private int[] deltas;
    private double angle;
    private String name;
    private int[] tam;
    private IMatrixOperations operador;

    public Comunication(int[] inic,int[] fin,int[] deltas,double grados,String name,int[] tam,IMatrixOperations oper) {
       this.inic=inic;
        this.fin=fin;
        this.deltas=deltas;
        this.angle=grados;
        this.name=name;
        this.tam=tam;
        operador=oper;
    }

    @Override
    public void run() {
        try {
            operador.rotar(inic,fin,deltas,angle,name,tam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}