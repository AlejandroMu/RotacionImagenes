package edu.icesi.interfaces;

import java.util.List;

import org.osoa.sca.annotations.Service;


@Service
public interface IMatrixOperations{
	public double[][] matrixMultiplication(double[][] m1,double[][] m2);
	public List<long[][]> rotar(long[][] points, double angle);
}
