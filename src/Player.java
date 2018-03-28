import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Player {
    protected int width;
    protected int height;

    protected Point2D.Float position;
    protected Point2D.Float velocity;

    protected BufferedImage currentImage;

    protected CharacterState state;

    protected Animation currentAnim;
    protected Animation walkingAnim;
    protected Animation idleAnim;
    protected Animation jumpingAnim;

    public Point2D.Float getPosition() {
        return this.position;
    }

    public void setPosition(Point2D.Float pos) {
        this.position = pos;
    }

    public void tick() {
        // ...
    }

    public abstract Point2D.Float getNextPosition();

    public abstract void updatePosition();

    public abstract float getNextX();

    public abstract float getNextY();

    public abstract void incrementYVelocity(float value);

    public abstract void resetYVelocity();

    public abstract void resetXVelocity();

    public BufferedImage getSprite() {
        return this.currentImage;
    }

    public void setState(CharacterState state){
        if (this.state != state) {
            if (state == CharacterState.WALKING) {
                currentAnim = walkingAnim;
                currentAnim.reset();
            } else if (state == CharacterState.IDLE) {
                currentAnim = idleAnim;
                currentAnim.reset();
            } else if (state == CharacterState.JUMPING) {
                currentAnim = jumpingAnim;
                currentAnim.reset();
            }
        }
        this.state = state;
    }

    public CharacterState getState(){
        return this.state;
    }

    public void updateImage() {
        this.currentAnim.update();
        this.currentImage = currentAnim.getFrame();
    }
}
