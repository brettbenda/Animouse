import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Tim extends Player {

    public Tim(float x, float y) {
        this.position = new Point2D.Float(x, y);
        this.velocity = new Point2D.Float(0, 0);

        // --- TEST VALUES ---
        this.collider = ImageLoader.loadImage("/test_images/tim_test.png");
        this.currentImage = ImageLoader.loadImage("/test_images/tim_test.png");
        // --- TEST VALUES ---
    }

    public Point2D.Float getNextPosition() {
        return new Point2D.Float(position.x + velocity.x, position.y + velocity.y);
    }

    public void updatePosition(){
        this.position = new Point2D.Float(this.position.x + this.velocity.x, this.position.y + this.velocity.y);
    }

    public void incrementYVelocity(float value){
        this.velocity = new Point2D.Float(this.velocity.x , this.velocity.y + value);
    }

    public void resetYVelocity(){
        this.velocity = new Point2D.Float(this.velocity.x , 0);
    }
}
