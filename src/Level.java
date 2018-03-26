import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
        loadData(levelNumber);

        // store cutscenes
        // this.startCutscenes = startCutscenes;
        // this.endCutscenes = endCutscenes;

        // --- TEST VALUES ---
     //   this.posTim = new Point2D.Float(100, 800);
       // this.posJack = new Point2D.Float(200, 540);
     //   this.posCamera = new Point2D.Float(960, 540);
        // --- TEST VALUES ---
    }

    private void loadData(int levelNumber) {
        try {
            float x = 0;
            float y = 0;
            int i = 0;

            Scanner sc = new Scanner(new BufferedReader(new FileReader("res/levelData/levelData_" + levelNumber + ".txt")));

            while (sc.hasNextFloat()) {
                if (i % 2 == 0)
                    x = sc.nextFloat();
                else
                    y = sc.nextFloat();

                if (i == 1)
                    this.posTim = new Point2D.Float(x, y);
                else if (i == 3)
                    this.posJack = new Point2D.Float(x, y);
                else if (i % 2 == 1)
                    hookablePoints.add(new Point2D.Float(x, y));

                ++i;
            }
            sc.close();
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
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
