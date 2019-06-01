package edu.icesi.ManagerImage;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import edu.icesi.interfaces.*;

public class ManagerImage implements IImageManager{


	@Override
	public int loadImage(String ruta, int[][] points) {
	
		try {
			
			ImageInputStream input = ImageIO.createImageInputStream(new File(ruta));
			Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
			if (!readers.hasNext()) {
				System.out.println("Error images");
			}else {
				ImageReader reader = null;
				reader = readers.next();
				reader.setInput(input);		
				long width = (long) (reader.getWidth(0)/6000.0);
				long heigt = (long) (reader.getHeight(0)/6000.0);	
				int x=points[0][0],y=points[0][1];
				ImageReadParam param1 = reader.getDefaultReadParam();	
				System.out.println("width--->"+width);
				System.out.println("heigth--->"+heigt);	
				for (int i = 0; i <= width; i++) {
					for (int j = 0; j <= heigt; j++) {					
						param1.setSourceRegion(new Rectangle(x,y,6000,6000));
						param1.setDestinationType(reader.getRawImageType(0));
						BufferedImage nasaImage1 = reader.read(0,param1);
						File output = new File(ruta+j+i+".tif");
						ImageIO.write(nasaImage1, "tif", output);
						System.out.println("-----------------> Termine");
						nasaImage1.flush();
						nasaImage1 = null;
						System.gc();
						if (y<points[1][1]) {
							
							y+=6000;
							if (y>points[1][1]) {
								y=points[1][1];
							}
						}
					}
					y=0;
					if (x<points[1][0]) {
						
						x+=6000;
						if (x>points[1][0]) {
							x=points[1][0];
						}
					}
					x+=6000;
				}
			}
		}catch (Exception e) {
		
		} 
			
		
		return 0;
	}

	@Override
	public void writeImage(String ruta) {
		// TODO Auto-generated method stub
		
	}

	public int[] getSize(String ruta){
		try {
			
			ImageInputStream input = ImageIO.createImageInputStream(new File(ruta));
			ImageReader reader = ImageIO.getImageReaders(input).next();
			int[] size={reader.getHeight(0),reader.getWidth(0)};
			return size;
		} catch (Exception e) {
			return null;
		}
	}

	
	
	public static void main(String[] srg) {
		ManagerImage m = new ManagerImage();
		int [][] pints = { {0,0},{5900,5900}};
		m.loadImage("data/huge.tif", pints);
	}
}
