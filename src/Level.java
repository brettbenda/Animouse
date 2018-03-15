import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Level {
    private VisualAssets visualAssets;

    public BufferedImage imageLayer;
    public BufferedImage bitmap;           // a.k.a. effectLayer

    public Point2D.Float[] hookablePoints;
    public Point2D.Float posTim;
    public Point2D.Float posJack;
    public Point2D.Float posCamera;

    // public Cutscene[] startCutscenes;
    // public Cutscene[] endCutscenes;

    // constructor
    public Level(int levelNumber) {

        // load visualAssets
        this.visualAssets = new VisualAssets();

        // load imageLayer from image file
        // this.imageLayer = ImageLoader.loadImage(imageLayerPath);
        this.imageLayer = visualAssets.getBackground(levelNumber);

        // load bitmap from image file
        // this.bitmap = ImageLoader.loadImage(bitmapPath);
        this.bitmap = visualAssets.getBitmap(levelNumber);

        // load other data from text file
        // this.miscDataPath = ...

        // store cutscenes
        // this.startCutscenes = startCutscenes;
        // this.endCutscenes = endCutscenes;

        // --- TEST VALUES ---
        this.hookablePoints = new Point2D.Float[1];
        this.posTim = new Point2D.Float(960, 540);
        this.posJack = new Point2D.Float(960, 540);
        this.posCamera = new Point2D.Float(960, 540);
        // --- TEST VALUES ---
    }
}
