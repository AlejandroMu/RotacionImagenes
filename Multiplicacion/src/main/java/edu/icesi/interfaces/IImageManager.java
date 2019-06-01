package edu.icesi.interfaces;

public interface IImageManager {

	public int loadImage(String ruta, int [][] points);
	
	public void writeImage(String ruta);

	public int[] getSize(String ruta);

	

}
