import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Player {
    protected BufferedImage collider;

    protected Point2D.Float position;
    protected Point2D.Float velocity;

    protected BufferedImage currentImage;

    // private Animation currentAnim;

    public Point2D.Float getPosition() {
        return this.position;
    }

    public void setPosition(Point2D.Float pos) {
        this.position = pos;
    }

    public BufferedImage getCollider() {
        return this.collider;
    }

    public void tick() {
        // ...
    }

    public abstract Point2D.Float getNextPosition();

    public BufferedImage getSprite() {
        return this.currentImage;
    }

}
