import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Level {

    // constructor
    BufferedImage imageLayer;
    BufferedImage bitmap;           // a.k.a. effectLayer

    Point2D.Float[] hookablePoints;
    Point2D.Float posTim;

    public Level() {
        // ...
    }
}
