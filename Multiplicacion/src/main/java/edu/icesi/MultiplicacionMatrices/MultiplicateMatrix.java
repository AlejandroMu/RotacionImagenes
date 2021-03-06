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
public class MultiplicateMatrix extends UnicastRemoteObject implements IMatrixOperations, Runnable {

	@Property(name = "service")
	private String servi;
	private IMultiplicationVectors mVectors;
	private IBroker broker;
	@Property(name = "nfs")
	private String pathBase;
	@Property(name = "dest")
	private String destino;
	@Reference(name="manager")
	private IImageManager manager;

	public MultiplicateMatrix() throws RemoteException {
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

	public void run() {
		System.out.println("Connecting ...");
		broker.attach(servi);
		System.out.println("Connected");
	}

	// MxN v NxK
	public double[][] matrixMultiplication(double[][] m1, double[][] m2) throws RemoteException {
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
	public boolean rotar(int[] inic, int[] fin, int[] deltas, double angle, String name, int[] tam)
			throws RemoteException {
		try {
			BufferedImage nueva = new BufferedImage(tam[0], tam[1], BufferedImage.TYPE_INT_RGB);
			BufferedImage old = manager.loadImage(pathBase+"/"+name, new Rectangle(inic[0],inic[1],fin[0]-inic[0],fin[1]-inic[1]));
			double[][] matRotacion = matRotacion(angle);
			for (int i = 0; i < fin[1]-inic[1]; i++) {
				for (int j = 0; j < fin[0]-inic[0]; j++) {
					Point rotado = processIndex(i, j, matRotacion);
					if (rotado.y + deltas[1] >= 0 && rotado.y + deltas[1] < nueva.getWidth()
							&& rotado.x + deltas[0] >= 0 && rotado.x + deltas[0] < nueva.getHeight()) {
						int rgb = old.getRGB(j, i);
						nueva.setRGB(rotado.y + deltas[1], rotado.x + deltas[0], rgb);
					}

				}
			}
			ImageIO.write(nueva, "jpg", new File(pathBase + "/" + destino +inic[0]+inic[1]+"-"+fin[0]+fin[1]+"-"+angle +".jpg"));
			return true;

		} catch (Exception e) {
			System.out.println("exc " + e.toString());

			return false;
		}
	}

	public double[][] matRotacion(double grados) {
		double gr = Math.toRadians(grados);
		double cos = Math.cos(gr);
		double sen = Math.sin(gr);
		return new double[][] { { cos, sen }, { -sen, cos } };
	}

	public Point processIndex(int i, int j, double[][] matRotacion) throws RemoteException {
		double[][] vP = new double[][] { { i }, { j } };
		double[][] rs = matrixMultiplication(matRotacion, vP);
		int iN = (int) Math.round(rs[0][0]);
		int jn = (int) Math.round(rs[1][0]);
		return new Point(iN, jn);
	}

}
