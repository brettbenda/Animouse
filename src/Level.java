import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Level {
    public BufferedImage imageLayer;
    public BufferedImage bitmap;           // a.k.a. effectLayer

    public Point2D.Float[] hookablePoints;
    public Point2D.Float posTim;
    public Point2D.Float posJack;
    public Point2D.Float posCamera;

    public Cutscene[] startCutscenes;
    public Cutscene[] endCutscenes;

    // constructor
    public Level(String imageLayerPath, String bitmapPath, String miscDataPath, Cutscene[] startCutscenes, Cutscene[] endCutscenes) {
        // load imageLayer from image file
        this.imageLayer = ImageLoader.loadImage(imageLayerPath);

        // load bitmap from image file
        this.bitmap = ImageLoader.loadImage(bitmapPath);

        // load other data from text file
        // this.miscDataPath = ...

        // store cutscenes
        this.startCutscenes = startCutscenes;
        this.endCutscenes = endCutscenes;

        // --- TEST VALUES ---
        this.hookablePoints = new Point2D.Float[1];
        this.posTim = new Point2D.Float(0, 0);
        this.posJack = new Point2D.Float(0, 0);
        this.posCamera = new Point2D.Float(0, 0);
        // --- TEST VALUES ---
    }
}
