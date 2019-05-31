package edu.icesi.implementacion;

import java.awt.Point;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.osoa.sca.annotations.*;

/**
 * ClientRotacion
 */
@Service(Runnable.class)
public class ClientRotacion implements Runnable {

    @Property(name = "broker")
    private String broker;
    @Property(name = "pathI")
    private String pathImage;
    @Property(name = "grados")
    private double grados;
    private ImageManager manager;

    @Override
    public void run() {
        manager = new ImageManager(pathImage, grados);
        Point size = manager.getSize();
        Comunication h = new Comunication(new long[][] { { 0, 0 }, { size.x, size.y } }, grados, broker, manager);
        h.start();
    }

    public void processImage() throws Exception {
        // File src=new File(pathImage);
        // BufferedImage image=ImageIO.read(src);
        // int w=image.getWidth();
        // int h=image.getHeight();
        // double[][] ima=new double[h][w];
        // for (int i = 0; i < h; i++) {
        // for (int j = 0; j < w; j++) {
        // ima[i][j]=image.getRGB(j, i);
        // }
        // }
        // double[][] res=rotacion.rotar(ima, grados);
        // BufferedImage imageR=new BufferedImage(res[0].length,
        // res.length,BufferedImage.TYPE_INT_RGB);
        // for (int i = 0; i < res.length; i++) {
        // for (int j = 0; j < res[i].length; j++) {
        // imageR.setRGB(j, i, (int)res[i][j]);
        // }
        // }

        // ImageIO.write(imageR, "jpg", new
        // File(src.getParent()+"/"+src.getName()+grados+".jpg"));
    }

    public static void main(String[] args) throws Exception{
        ClientRotacion r=new ClientRotacion();
        r.broker="rmi://localhost:1234/redireccion";
        r.pathImage="./src/main/resources/test.jpg";
        r.grados=45;
        
        r.run();
        
    }
}