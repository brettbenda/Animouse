import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Player {
    public enum Direction {
        RIGHT, LEFT
    }

    protected int width;
    protected int height;

    protected Direction dir;

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
        if (this.dir == Direction.RIGHT) {
            return this.currentImage;
        } else {
            BufferedImage image = new BufferedImage(this.currentImage.getWidth(), this.currentImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < image.getWidth(); ++i) {
                for (int j = 0; j < image.getHeight(); ++j) {
                    image.setRGB(i, j, this.currentImage.getRGB(image.getWidth() - i - 1, j));
                }
            }
            return image;
        }
    }

    public abstract void setState(CharacterState state);

    public CharacterState getState(){
        return this.state;
    }

    public void updateImage() {
        this.currentAnim.update();
        this.currentImage = currentAnim.getFrame();
    }
}
