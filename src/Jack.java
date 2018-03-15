import java.awt.geom.Point2D;

public class Jack extends Player {

    final float acceleration = 1.3f;

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

    public void incrementXVelocity(){
        if (this.velocity.x * acceleration > 13){
            this.velocity.x = 13;
        } else{
            this.velocity = new Point2D.Float(this.velocity.x * acceleration, this.velocity.y);
        }
    }

    public void decrementXVelocity(){
        if (this.velocity.x * (1 - acceleration) < -13){
            this.velocity.x = -13;
        } else{
            this.velocity = new Point2D.Float(this.velocity.x * (1 - acceleration), this.velocity.y);
        }
    }

    public void resetYVelocity(){
        this.velocity = new Point2D.Float(this.velocity.x , 0);
    }

    public void resetXVelocity(){
        this.velocity = new Point2D.Float(0 , this.velocity.y);
    }
}
