import java.awt.geom.Point2D;

public class Jack extends Player {

    private final float maxXVelocity = 15;
    private final float grappleSpeed = 40;
    private int movementState;
    private Point2D.Float hookPosition;

    private final int NORMAL = 0;
    private final int GRAPPLING = 1;
    private final int HOOKED = 2;

    public Jack(float x, float y) {
        this.position = new Point2D.Float(x, y);
        this.velocity = new Point2D.Float(0, 0);
        this.movementState = NORMAL;
        this.hookPosition = new Point2D.Float();

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
        if (isGrappling()){
            System.out.println("isGrappling");
            if ((this.velocity.x < 0 && position.x + velocity.x <= hookPosition.x)
                    || (this.velocity.x > 0 && position.x + velocity.x >= hookPosition.x)
                    || (this.velocity.y < 0 && position.y + velocity.y <= hookPosition.y)
                    || (this.velocity.y > 0 && position.y + velocity.y >= hookPosition.y)) {
                this.position.setLocation(this.hookPosition.x, this.hookPosition.y);
                movementState = HOOKED;
                this.velocity.setLocation(0, 0);
            }
            else
                this.position.setLocation(this.position.x + this.velocity.x, this.position.y + this.velocity.y);
        } else
            this.position.setLocation(this.position.x + this.velocity.x, this.position.y + this.velocity.y);
        System.out.println(this.position.y);
    }

    public void setPosition(Point2D.Float pos){
        this.position.setLocation(pos.x, pos.y);
    }

    public void incrementYVelocity(float value){
        if (this.velocity.y + value > 10) {
            this.velocity.y = 10;
        } else {
        //    this.velocity = new Point2D.Float(this.velocity.x, this.velocity.y + value);
            this.velocity.setLocation(this.velocity.x, this.velocity.y + value);
        }

    }

    public void incrementXVelocity(float value){
        if (this.velocity.x + value > maxXVelocity) {
            this.velocity.x = maxXVelocity;
        } else {
    //        this.velocity = new Point2D.Float(this.velocity.x+value, this.velocity.y );
            this.velocity.setLocation(this.velocity.x + value, this.velocity.y);
        }
    }

    public void decrementXVelocity(float value){
        if (this.velocity.x - value < -maxXVelocity) {
            this.velocity.x = -maxXVelocity;
        } else {
   //         this.velocity = new Point2D.Float(this.velocity.x-value, this.velocity.y );
            this.velocity.setLocation(this.velocity.x - value, this.velocity.y);
        }
    }

    public void resetYVelocity(){
        this.velocity = new Point2D.Float(this.velocity.x , 0);
    }

    public void resetXVelocity(){
        this.velocity = new Point2D.Float(0 , this.velocity.y);
    }

    public boolean isNormal() {
        return movementState == NORMAL;
    }

    public boolean isGrappling(){
        return movementState == GRAPPLING;
    }

    public boolean isHooked() {
        return movementState == HOOKED;
    }

    public void setMovementState(int ms) {
        this.movementState = ms;
    }


    public void grappleTo(Point2D.Float pos){
        this.movementState = GRAPPLING;
        hookPosition.setLocation(pos.x, pos.y);
        float dist = (float) Math.sqrt((pos.x - this.position.x)*(pos.x - this.position.x) + (pos.y - this.position.y)*(pos.y - this.position.y));
        this.velocity.x = grappleSpeed * (hookPosition.x - this.position.x) / dist;
        this.velocity.y = grappleSpeed * (hookPosition.y - this.position.y) / dist;
        System.out.println("GRAPPLE TO " + pos.x + ", " + pos.y + " FROM " + this.position.x + ", " + this.position.y);
        System.out.println("velocity x " + this.velocity.x);
        System.out.println("velocity y " + this.velocity.y);

    }
}
