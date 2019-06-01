package edu.icesi.MultiplicacionMatrices;


import java.util.ArrayList;
import java.util.List;
import java.lang.Runnable;
import org.osoa.sca.annotations.*;
import java.rmi.*;
import java.rmi.server.*;
import edu.icesi.interfaces.*;

@Service(Runnable.class)
public class MultiplicateMatrix extends UnicastRemoteObject implements IMatrixOperations,Runnable {

	@Property(name = "service")
	private String servi;
	private IMultiplicationVectors mVectors;
	private IBroker broker;

	public MultiplicateMatrix()throws RemoteException{
		super();
	}

	@Reference(name = "broker", required = true)
	public void setBroker(IBroker pat) {
		broker = pat;
		
	}

	@Reference(name = "mVectors", required = true)
	public void setMVectors(IMultiplicationVectors v) {
		mVectors = v;
	}

	public void run(){
		System.out.println("Connecting ...");
		broker.attach(servi);
		System.out.println("Connected");
	}

	// MxN v NxK
	public double[][] matrixMultiplication(double[][] m1, double[][] m2)throws RemoteException {
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
	public List<int[][]> rotar(int[] inic,int[] fin,int[] c, double angle)throws RemoteException {
		double grR = Math.toRadians(angle);
		double sin = Math.sin(grR);
		double cos = Math.cos(grR);
		double tan = Math.tan(grR / 2);
		double dx = c[0] - c[0] * cos - c[1] * sin;
		double dy = c[1] + c[0] * cos - c[1] * sin;
		double[][] mat1 = { { 1, 0 }, { sin, 1 } };
		double[][] mat2 = { { 1, -tan }, { 0, 1 } };
		List<int[][]> points = new ArrayList<int[][]>();
		for (int ini = inic[0]; ini <= fin[0]; ini++) {
			for (int j = inic[1]; j <= inic[1]; j++) {
				double[][] vP = new double[][] { { ini }, { j } };
				double[][] rs = matrixMultiplication(mat2, vP);
				double[][] rs1 = matrixMultiplication(mat1, rs);
				double[][] rs2 = matrixMultiplication(mat2, rs1);
				int iN = (int) (Math.round(rs2[0][0]) + dx);
				int jn = (int) (Math.round(rs2[1][0]) + dy);
				int[] newPoint = { iN, jn };
				int[] oldPoint = { ini, j };
				int[][] tmp = { newPoint, oldPoint };
				points.add(tmp);
			}
		}
		broker.removeProces(servi);

		return points;
	}
}
