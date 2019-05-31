package edu.icesi.implementacion;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * ImageManager
 */
public class ImageManager {

    private BufferedImage image;
    private BufferedImage image2;
    private File rout;

    public ImageManager(String route,double gr) {
        try {
            rout=new File(route);
            image = ImageIO.read(rout);
            int h=image.getHeight();
            int w=image.getWidth();
            Point size=calculeateSize(h,w,gr);
            image2=new BufferedImage(size.x,size.y ,BufferedImage.TYPE_INT_RGB);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Point calculeateSize(int h, int w, double d) {
        return new Point(h,w);
    }

    public BufferedImage loadImage(int x, int y, int w, int h) {
        return image;
    }
    public Point getSize(){
        return new Point(image.getWidth(),image.getHeight());
    }
    public void writeImage(int x,int y,BufferedImage im){
        try {
            ImageIO.write(im, "jpg", new File(rout.getParent()+"func"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized void merge(List<long[][]> t,Point i,Point j){
        for (long[][] var : t) {
            int rgb=image.getRGB((int)var[1][1], (int)var[1][0]);
            image2.setRGB((int)var[0][1], (int)var[0][0], rgb);
        }
        image2.flush();
    }
}