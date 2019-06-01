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
	@Property(name="dest")
	private String destino;

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
		try {
			System.out.println("inic -> "+inic[0]+" "+inic[1]);
			System.out.println("fin -> "+fin[0]+" "+fin[1]);
			System.out.println("deltas -> "+deltas[0]+" "+deltas[1]);
			System.out.println("rotar");
			System.out.println("Bufer n --> "+pathBase+"/"+destino);
			File nu=new File(pathBase+"/"+destino);
			BufferedImage nueva=new BufferedImage(1500,1500,BufferedImage.TYPE_INT_RGB);
			System.out.println("Bufer old-> "+pathBase+"/"+name);
			BufferedImage old=ImageIO.read(new File(pathBase+"/"+name));
			double gr=Math.toRadians(angle);
			double cos = Math.cos(gr);
			double sen = Math.sin(gr);
			double[][] matRotacion = {{cos,sen},{-sen,cos}};
			for (int i = inic[1]; i < fin[1]; i++) {
				for (int j = inic[0]; j < fin[0]; j++) {
				   Point rotado=processIndex(i,j,matRotacion);
				   int rgb=old.getRGB(j,i);
				   if(rotado.x<0){System.out.println("x < "+ rotado.x);}
				   if(rotado.x+deltas[1]>=1500){System.out.println("x > "+ rotado.x);}
				   if(rotado.y<0){System.out.println("y < "+ rotado.y);}
				   if(rotado.y+deltas[0]>=1500){System.out.println("y > "+ rotado.y);}
				   
				   nueva.setRGB(rotado.y+deltas[0],rotado.x+deltas[1],rgb);
				}
			}
			System.out.println("return");
			ImageIO.write(nueva,"jpg",nu);
		return true;
			
		} catch (Exception e) {
			System.out.println("exc "+e.getMessage());
			return false;
		}
	}
	public Point processIndex(int i,int j,double[][] matRotacion)throws RemoteException{
		double[][] vP = new double[][] { { i }, { j } };
		double[][] rs = matrixMultiplication(matRotacion, vP);
		int iN= (int)Math.round(rs[0][0]);
		int jn= (int)Math.round(rs[1][0]);
		return new Point(iN, jn);		
	}
	

}
