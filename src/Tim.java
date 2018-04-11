
import sun.awt.image.BufferedImageDevice;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Tim extends Player {
    public boolean isClimbing;

    public Tim(float x, float y) {
        this.position = new Point2D.Float(x, y);
        this.velocity = new Point2D.Float(0, 0);
        this.isClimbing = false;

        // --- TEST VALUES ---
        this.currentImage = ImageLoader.loadImage("/VisualAssets/Tim/tim.png");
        this.width = currentImage.getWidth();
        this.height = currentImage.getHeight() - 5;

        this.state = CharacterState.IDLE;
        this.dir = Direction.RIGHT;

        BufferedImage TIM_JUMPING_ANIM[];
        BufferedImage TIM_WALKING_ANIM[];
        BufferedImage TIM_IDLE_ANIM[];

        // Jumping Animation
        TIM_JUMPING_ANIM = new BufferedImage[5];
        for (int i = 0; i < TIM_JUMPING_ANIM.length; ++i) {
            TIM_JUMPING_ANIM[i] = ImageLoader.loadImage("/VisualAssets/Tim/tim.png");
        }
        // Walking Animation
        TIM_WALKING_ANIM = new BufferedImage[5];
        for (int i = 0; i < TIM_WALKING_ANIM.length; ++i) {
            TIM_WALKING_ANIM[i] = ImageLoader.loadImage("/VisualAssets/Tim/tim.png");
        }
        // Idle Animation
        TIM_IDLE_ANIM = new BufferedImage[5];
        for (int i = 0; i < TIM_IDLE_ANIM.length; ++i) {
            TIM_IDLE_ANIM[i] = ImageLoader.loadImage("/VisualAssets/Tim/tim.png");
        }

        this.walkingAnim = new LoopingAnimation(TIM_WALKING_ANIM, 250);
        this.jumpingAnim = new FiniteAnimation(TIM_JUMPING_ANIM, 250);
        this.idleAnim = new LoopingAnimation(TIM_IDLE_ANIM, 250);
        this.currentAnim = idleAnim;
        // --- TEST VALUES ---
    }

    public Point2D.Float getNextPosition() {
        return new Point2D.Float(position.x + velocity.x, position.y + velocity.y);
    }

    public void updatePosition(){
        this.position = new Point2D.Float(this.position.x + this.velocity.x, this.position.y + this.velocity.y);
        //System.out.println("🎈"+velocity.y);
    }

    public float getNextX() {
        return this.position.x + this.velocity.x;
    }

    public float getNextY() {
        return this.position.y + this.velocity.y;
    }

    public void incrementYVelocity(float value){
        this.velocity = new Point2D.Float(this.velocity.x, this.velocity.y + value);
    }

    public void resetYVelocity(){
        this.velocity = new Point2D.Float(this.velocity.x , 0);
    }

    public void resetXVelocity(){
        this.velocity = new Point2D.Float(0 , this.velocity.y);
    }

    public void setClimbing(boolean value){
        this.isClimbing = value;
    }

    public boolean isClimbing(){
        return this.isClimbing;
    }
}
