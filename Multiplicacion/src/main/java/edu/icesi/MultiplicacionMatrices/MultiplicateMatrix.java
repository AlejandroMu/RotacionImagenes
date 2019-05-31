package edu.icesi.MultiplicacionMatrices;


import java.util.ArrayList;
import java.util.List;

import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;

import edu.icesi.interfaces.*;

public class MultiplicateMatrix implements IMatrixOperations {

	@Property(name = "service")
	private String servi;
	private IMultiplicationVectors mVectors;
	private IBroker broker;

	@Reference(name = "broker", required = true)
	public void setBroker(IBroker pat) {
		broker = pat;
		System.out.println("Connecting ...");
		broker.attach(servi);
		System.out.println("Connected");
	}

	@Reference(name = "mVectors", required = true)
	public void setMVectors(IMultiplicationVectors v) {
		mVectors = v;
	}

	// MxN v NxK
	public double[][] matrixMultiplication(double[][] m1, double[][] m2) {
		double[][] m2T = trasnponse(m2);
		double[][] ret = new double[m1.length][m2[0].length];
		for (int i = 0; i < ret.length; i++) {
			for (int j = 0; j < ret[0].length; j++) {
				ret[i][j] = mVectors.multipication(m1[i], m2T[j]);
			}
		}
		return ret;
	}

	private double[][] trasnponse(double[][] m2) {
		double[][] ret = new double[m2[0].length][m2.length];
		for (int i = 0; i < ret.length; i++) {
			for (int j = 0; j < ret[0].length; j++) {
				ret[i][j] = m2[j][i];

			}
		}
		return ret;
	}

	@Override
	public List<long[][]> rotar(long[][] point, double angle) {
		long piX = point[0][0];
		long piY = point[0][1];
		long pjX = point[1][0];
		long pjY = point[1][1];
		long pcX = point[2][0];
		long pcY = point[2][1];
		double grR = Math.toRadians(angle);
		double sin = Math.sin(grR);
		double cos = Math.cos(grR);
		double tan = Math.tan(grR / 2);
		double dx = pcX - pcX * cos - pcY * sin;
		double dy = pcY + pcX * cos - pcY * sin;
		double[][] mat1 = { { 1, 0 }, { sin, 1 } };
		double[][] mat2 = { { 1, -tan }, { 0, 1 } };
		List<long[][]> points = new ArrayList<long[][]>();
		for (long ini = piY; ini < pjY; ini++) {
			for (long j = piX; j < pjX; j++) {
				double[][] vP = new double[][] { { ini }, { j } };
				double[][] rs = matrixMultiplication(mat2, vP);
				double[][] rs1 = matrixMultiplication(mat1, rs);
				double[][] rs2 = matrixMultiplication(mat2, rs1);
				long iN = (long) (Math.round(rs2[0][0]) + dx);
				long jn = (long) (Math.round(rs2[1][0]) + dy);
				long[] newPoint = { iN, jn };
				long[] oldPoint = { ini, j };
				long[][] tmp = { newPoint, oldPoint };
				points.add(tmp);
			}
		}
		broker.removeProces(servi);

		return points;
	}
}
