import java.awt.geom.Point2D;

public class Jack extends Player {

    private final float maxXVelocity = 15;
    private float nextVelocity = 0.5f;

    public Jack(float x, float y) {
        this.position = new Point2D.Float(x, y);
        this.velocity = new Point2D.Float(0, 0);

        // --- TEST VALUES ---
        this.currentImage = ImageLoader.loadImage("/test_images/tim_test.png");
        this.width = currentImage.getWidth();
        this.height = currentImage.getHeight();
        // --- TEST VALUES ---
    }

    public Point2D.Float getNextPosition() {
        return new Point2D.Float(position.x + velocity.x, position.y + velocity.y);
    }

    public void updatePosition(){
        this.position = new Point2D.Float(this.position.x + this.velocity.x, this.position.y + this.velocity.y);
    }

    public void incrementYVelocity(float value){
        if (this.velocity.y + value > 10) {
            this.velocity.y = 10;
        } else {
            this.velocity = new Point2D.Float(this.velocity.x, this.velocity.y + value);
        }
    }

    public void incrementXVelocity(float value){
        if (this.velocity.x + value > 10) {
            this.velocity.x = 10;
        } else {
            this.velocity = new Point2D.Float(this.velocity.x+value, this.velocity.y );
        }
    }

    public void decrementXVelocity(float value){
        if (this.velocity.x - value < -10) {
            this.velocity.x = -10;
        } else {
            this.velocity = new Point2D.Float(this.velocity.x-value, this.velocity.y );
        }
    }

    public void resetYVelocity(){
        this.velocity = new Point2D.Float(this.velocity.x , 0);
    }

    public void resetXVelocity(){
        this.velocity = new Point2D.Float(0 , this.velocity.y);
        nextVelocity = 0.0f;
    }
}
