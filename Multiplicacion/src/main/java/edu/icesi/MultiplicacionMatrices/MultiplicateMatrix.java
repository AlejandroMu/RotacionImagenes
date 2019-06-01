package edu.icesi.MultiplicacionMatrices;


import java.util.ArrayList;
import java.util.List;
import java.lang.Runnable;
import org.osoa.sca.annotations.*;
import java.rmi.*;
import java.rmi.server.*;
import edu.icesi.interfaces.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.*;

@Service(Runnable.class)
public class MultiplicateMatrix extends UnicastRemoteObject implements IMatrixOperations,Runnable {

	@Property(name = "service")
	private String servi;
	private IMultiplicationVectors mVectors;
	private IBroker broker;
	@Property(name="nfs")
	private String pathBase;

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
	public boolean rotar(int[] inic,int[] fin,int[] c, double angle,String name)throws RemoteException {
		double grR = Math.toRadians(angle);
		double sin = Math.sin(grR);
		double cos = Math.cos(grR);
		double tan = Math.tan(grR / 2);
		double dx = c[0] - c[0] * cos - c[1] * sin;
		double[][] mat1 = { { 1, 0 }, { sin, 1 } };
		double[][] mat2 = { { 1, -tan }, { 0, 1 } };
		List<int[][]> points = new ArrayList<int[][]>();
		Point[] corners = new Point[] { new Point(0, 0), new Point(0, 0) };
		for (int ini = inic[0]; ini <= fin[0]; ini++) {
			for (int j = inic[1]; j <= inic[1]; j++) {
				double[][] vP = new double[][] { { ini }, { j } };
				double[][] rs = matrixMultiplication(mat2, vP);
				double[][] rs1 = matrixMultiplication(mat1, rs);
				double[][] rs2 = matrixMultiplication(mat2, rs1);
				int iN = (int) (Math.round(rs2[0][0]));
				int jn = (int) (Math.round(rs2[1][0]));
				int[] newPoint = { iN, jn };
				int[] oldPoint = { ini, j };
				int[][] tmp = { newPoint, oldPoint };
				claculateCorners(corners, newPoint);
				points.add(tmp);
			}
		}
		write(inic,fin,points,name,corners);
		broker.removeProces(servi);

		return true;
	}
	private void claculateCorners(Point[] corners, int[] newPoint) {
        if (newPoint[0] < corners[0].x) {
            corners[0].setLocation(newPoint[0], corners[0].y);
        }
        if (newPoint[0] > corners[1].x) {
            corners[1].setLocation(newPoint[0], corners[1].y);
        }
        if (newPoint[1] < corners[0].y) {
            corners[0].setLocation(corners[0].x, newPoint[1]);
        }
        if (newPoint[1] > corners[1].y) {
            corners[1].setLocation(corners[1].x, newPoint[1]);
        }
	}
	public void write(int[] inic,int[] fin,List<int[][]> points,String name,Point[] corners){
		try{
		int m = corners[1].y - corners[0].y;
        int n = corners[1].x - corners[0].x;
        int dx = -1 * corners[0].x;
		int dy = -1 * corners[0].y;
		File input=new File(pathBase+"/"+name);
		System.out.println(pathBase+"/"+name);
		System.out.println("bufer nuevo");
		BufferedImage nueva=new BufferedImage(n+1,m+1,BufferedImage.TYPE_INT_RGB);
		System.out.println("bufer antiguo");
		BufferedImage origin=ImageIO.read(input);
        for(int[][] tmp:points){
			int rgb=origin.getRGB(tmp[1][1],tmp[1][0]);
			nueva.setRGB(tmp[0][1],tmp[0][0],rgb);
		}
		System.out.println("writing");
		ImageIO.write(nueva,"jpg",new File(pathBase+"/"+"rotado.jpg"));
	}catch(Exception e){
		System.out.println(e.getMessage());
	}
	}
}
