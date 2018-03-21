import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Level {
    private VisualAssets visualAssets;

    public BufferedImage imageLayer;
    public BufferedImage bitmap;           // a.k.a. effectLayer

    public Point2D.Float posTim;
    public Point2D.Float posJack;
    public Point2D.Float posCamera;
    public ArrayList<Point2D.Float> hookablePoints = new ArrayList<Point2D.Float>();


    // public Cutscene[] startCutscenes;
    // public Cutscene[] endCutscenes;

    // constructor
    public Level(int levelNumber, VisualAssets visualAssets) {

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
        this.posTim = new Point2D.Float(960, 540);
        this.posJack = new Point2D.Float(960, 540);
        this.posCamera = new Point2D.Float(960, 540);
        // --- TEST VALUES ---
    }

    //used to associate the point with the region
    public void addHookPoint(Point2D.Float newPoint){
        hookablePoints.add(newPoint);
    }

    //used to get the point belonging to a region
    public Point2D.Float getHookablePoint(int index){
        return hookablePoints.get(index);
    }
}
