package edu.icesi.implementacion;

import java.io.*;
import java.rmi.Naming;
import java.util.*;
import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Service;
import edu.icesi.interfaces.*;



/**
 * Hello world!
 *
 */
@Service(Runnable.class)
public class ClientMultiplicar implements Runnable {
	// @Reference(required = true, name = "multiplicar")
	private IMatrixOperations servicio;
	@Property(name = "path")
	private String pathOrig;
	@Property(name = "keyM1")
	private String keyM1;
	@Property(name = "keyResult")
	private String keyRes;
	@Property(name = "keyM2")
	private String keyM2;

	@Property(name="conexion")
	public void setConexion(String conexion){
		try {
			servicio = (IMatrixOperations) Naming.lookup(conexion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void run() {
		Properties properties = new Properties();
		try {
			File fl=new File(pathOrig);
			properties.load(new FileReader(fl));
			String matrix1 = properties.getProperty(keyM1);
			String matrix2 = properties.getProperty(keyM2);
			String result = properties.getProperty(keyRes);
			double[][] mat1 = readMatrix(matrix1);
			double[][] mat2 = readMatrix(matrix2);
			double[][] resul = servicio.matrixMultiplication(mat1, mat2);
			writte(resul, result);
			System.out.println("Termino con éxito");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private void writte(double[][] resul, String result) throws IOException {
		FileWriter a = new FileWriter(new File(result));
		BufferedWriter bw = new BufferedWriter(a);
		bw.write(resul.length + " " + resul[0].length + "\n");
		for (double[] ds : resul) {

			for (double d : ds) {
				bw.write(d + " ");
			}
			bw.newLine();
		}
		bw.close();

	}

	private double[][] readMatrix(String matrix1) throws IOException {
		FileReader a = new FileReader(new File(matrix1));
		BufferedReader br = new BufferedReader(a);
		String tam[] = br.readLine().split(" ");
		int r = Integer.parseInt(tam[0]);
		int c = Integer.parseInt(tam[1]);
		double[][] ret = new double[r][c];
		for (int i = 0; i < ret.length; i++) {
			String row[] = br.readLine().split(" ");
			for (int j = 0; j < ret[0].length; j++) {
				ret[i][j] = Double.parseDouble(row[j]);
			}
		}
		br.close();
		return ret;
	}
}
