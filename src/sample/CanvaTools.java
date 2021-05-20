package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public class CanvaTools {

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



}
