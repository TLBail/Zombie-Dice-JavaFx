package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Tools {

    public static void drawRotatedImage(GraphicsContext gc, Image image,
                                        double angle, double tlpx, double tlpy) {
        gc.save();/*from  w ww .  ja  v  a2  s.c o  m*/
        rotate(gc, angle, tlpx + image.getWidth() / 2,
                tlpy + image.getHeight() / 2);
        gc.drawImage(image, tlpx, tlpy);
        gc.restore();
    }

    public static void drawRotatedImage(GraphicsContext gc, Image image,
                                        double angle, double tlpx, double tlpy, double width,
                                        double height) {
        gc.save();
        rotate(gc, angle, tlpx + width / 2, tlpy + height / 2);
        gc.drawImage(image, tlpx, tlpy, width, height);
        gc.restore();
    }

    private static void rotate(GraphicsContext gc, double angle, double px,
                               double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(),
                r.getTx(), r.getTy());
    }


    public static boolean colid(Rectangle r1, Rectangle r2){
        if(r1.getX() + r1.getWidth() < r2.getX() || r1.getY() + r1.getHeight() < r2.getY() || r1.getX() > r2.getX() + r2.getWidth() || r1.getY() > r2.getY() + r2.getHeight())
            return false;
        return true;

    }


}
