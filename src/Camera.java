import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

public class Camera {
    private Point2D.Float position;
    private int fovWidth;
    private int fovHeight;
    private float aspectRatio;

    Camera(float aspectRatio, int displayWidth, int displayHeight){
        this.fovWidth = displayWidth;
        this.fovHeight = displayHeight;
        this.aspectRatio = aspectRatio;
        this.position = new Point2D.Float(0,0);
    }

    void handleKeyInput(KeyEvent event){

    }

}
