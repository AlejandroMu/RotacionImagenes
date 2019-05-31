package edu.icesi.implementacion;

import java.rmi.*;
import java.util.List;

// import org.osoa.sca.annotations.Service;


// @Service
public interface IMatrixOperations extends Remote {
	public double[][] matrixMultiplication(double[][] m1,double[][] m2) throws RemoteException;
	public List<long[][]> rotar(long[][] points, double angle)throws RemoteException;
}
