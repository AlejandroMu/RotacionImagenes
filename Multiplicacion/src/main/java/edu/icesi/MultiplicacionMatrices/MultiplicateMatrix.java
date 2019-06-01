package edu.icesi.MultiplicacionMatrices;


import java.util.*;
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
	public boolean rotar(int[] inic,int[] fin,int[] deltas, double angle,String name)throws RemoteException {
		double gr=Math.toRadians(angle);
        double cos = Math.cos(gr);
        double sen = Math.sin(gr);
		double[][] matRotacion = {{cos,sen},{-sen,cos}};
        HashMap<Point, Point> points = new HashMap<Point,Point>();
        for (int i = inic[1]; i < fin[1]; i++) {
            for (int j = inic[0]; j < fin[0]; j++) {
			   Point rotado=processIndex(i,j,matRotacion);
            }
        }

	return transfomMatrix(points, corners,name,inic,fin);
	}
	public Point processIndex(int i,int j,double[][] matRotacion){
		double[][] vP = new double[][] { { i }, { j } };
		double[][] rs = matrixMultiplication(matRotacion, vP);
		int iN= (int)Math.round(rs[0][0]);
		int jn= (int)Math.round(rs[1][0]);
		return new Point(iN, jn);		
	}
	
	private boolean transfomMatrix(HashMap<Point, Point> points, Point[] cornes,String name,int[] inic,int[] fin) {
		try{
        int m = cornes[1].y - cornes[0].y;
        int n = cornes[1].x - cornes[0].x;
        int dx = -1 * cornes[0].x;
		int dy = -1 * cornes[0].y;
		BufferedImage nueva=new BufferedImage(n+1,m+1,BufferedImage.TYPE_INT_RGB);
		BufferedImage old=ImageIO.read(new File(pathBase+"/"+name));
		System.out.println("h "+old.getHeight());
		System.out.println("w "+old.getWidth());
        Iterator<Point> keys = points.keySet().iterator();
        while (keys.hasNext()) {
			Point tmp = keys.next();
			Point tmp1=points.get(tmp);
			int rgb=old.getRGB(tmp1.y,tmp1.x);
            nueva.setRGB(tmp.y+dy,tmp.x+dx,rgb);
		}
		ImageIO.write(nueva,"jpg",new File(pathBase+"/rotadaN"));
		return true;
	}catch(Exception e){
		System.out.println(e.getMessage());
		return false;

	}
	}
}
