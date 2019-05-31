package edu.icesi.MultiplicacionVectores;

import edu.icesi.interfaces.IMultiplicationVectors;

public class MultiplicationVectors implements IMultiplicationVectors {

	public double multipication(double[] v1, double[] v2) {// throws RemoteException {
		double rt = 0;
		for (int i = 0; i < v2.length; i++) {
			double d = v2[i];
			rt += d * v1[i];

		}
		return rt;
	}

}
