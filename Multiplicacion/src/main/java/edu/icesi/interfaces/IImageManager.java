package edu.icesi.interfaces;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import org.osoa.sca.annotations.Service;

@Service
public interface IImageManager {

	public int loadImage(String ruta, int [][] points);
	
	public void writeImage(String ruta,HashMap<Point,Integer> img,Rectangle rec);

	public int[] getSize(String ruta);

	public BufferedImage loadImage(String route, Rectangle zone);

	

}
