import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Level {

    public BufferedImage background;
    public BufferedImage bitmap;           // a.k.a. effectLayer

    public Point2D.Float posTim;
    public Point2D.Float posJack;
    public Point2D.Float posCamera;
    public ArrayList<Point2D.Float> hookablePoints = new ArrayList<Point2D.Float>();


    // public Cutscene[] startCutscenes;
    // public Cutscene[] endCutscenes;

    // constructor
    public Level(int levelNumber) {

        // load background from image file
        // this.background = ImageLoader.loadImage(imageLayerPath);
        this.background = ImageLoader.loadImage("/VisualAssets/Background/levelBackground_" + levelNumber + ".png");

        // load bitmap from image file
        // this.bitmap = ImageLoader.loadImage(bitmapPath);
        this.bitmap = ImageLoader.loadImage("/VisualAssets/Bitmap/levelBitmap_" + levelNumber + ".png");

        // load other data from text file
        // this.miscDataPath = ...

        // store cutscenes
        // this.startCutscenes = startCutscenes;
        // this.endCutscenes = endCutscenes;

        // --- TEST VALUES ---
        this.posTim = new Point2D.Float(100, 800);
        this.posJack = new Point2D.Float(200, 540);
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

    public void setStartPosXTim(int x){

    }
    public void setStartPosYTim(int x){

    }

    public void setStartPosXJack(int x){

    }
    public void setStartPosYJack(int x){

    }
}
