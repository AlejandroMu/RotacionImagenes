package edu.icesi.interfaces;

import java.util.List;
import org.osoa.sca.annotations.Service;
import java.rmi.*;


 @Service
public interface IMatrixOperations extends Remote{
	public double[][] matrixMultiplication(double[][] m1,double[][] m2)throws RemoteException;
	public boolean rotar(int[] inic,int[] fin,int[] c, double angle, String base,int[] tam)throws RemoteException;
}
