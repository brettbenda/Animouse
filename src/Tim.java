
import sun.awt.image.BufferedImageDevice;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.text.Bidi;

public class Tim extends Player {
    public boolean isClimbing;
    private Animation climbAnim;
    private Animation carryAnim;

    public boolean isCarrying;

    public Tim(float x, float y) {
        this.position = new Point2D.Float(x, y);
        this.velocity = new Point2D.Float(0, 0);
        this.isClimbing = false;
        this.isCarrying = false;

        // --- TEST VALUES ---
        this.currentImage = ImageLoader.loadImage("/VisualAssets/Tim/tim.png");
        this.width = currentImage.getWidth()/2;
        this.height = currentImage.getHeight() - 5;

        this.state = CharacterState.IDLE;
        this.dir = Direction.RIGHT;

        BufferedImage TIM_JUMPING_ANIM[];
        BufferedImage TIM_WALKING_ANIM[];
        BufferedImage TIM_IDLE_ANIM[];
        BufferedImage TIM_CLIMB_ANIM[];
        BufferedImage TIM_CARRY_ANIM[];

        // Jumping Animation
        TIM_JUMPING_ANIM = new BufferedImage[8];
        for (int i = 0; i < TIM_JUMPING_ANIM.length; ++i) {
            TIM_JUMPING_ANIM[i] = ImageLoader.loadImage("/VisualAssets/Tim/tim_walk_" + i + ".png");
        }
        // Walking Animation
        TIM_WALKING_ANIM = new BufferedImage[8];
        for (int i = 0; i < TIM_WALKING_ANIM.length; ++i) {
            TIM_WALKING_ANIM[i] = ImageLoader.loadImage("/VisualAssets/Tim/tim_walk_" + i + ".png");
        }
        // Idle Animation
        TIM_IDLE_ANIM = new BufferedImage[1];
        for (int i = 0; i < TIM_IDLE_ANIM.length; ++i) {
            TIM_IDLE_ANIM[i] = ImageLoader.loadImage("/VisualAssets/Tim/tim_walk_0.png");
        }
        // CLIMB Animation
        TIM_CLIMB_ANIM = new BufferedImage[1];
        for (int i = 0; i < TIM_CLIMB_ANIM.length; ++i) {
            TIM_CLIMB_ANIM[i] = ImageLoader.loadImage("/VisualAssets/Tim/tim_climbing.png");
        }
        // Idle Animation
        TIM_CARRY_ANIM = new BufferedImage[1];
        for (int i = 0; i < TIM_CARRY_ANIM.length; ++i) {
            TIM_CARRY_ANIM[i] = ImageLoader.loadImage("/VisualAssets/Tim/tim_carrying.png");
        }


        this.walkingAnim = new LoopingAnimation(TIM_WALKING_ANIM, 250);
        this.jumpingAnim = new FiniteAnimation(TIM_JUMPING_ANIM, 250);
        this.idleAnim = new LoopingAnimation(TIM_IDLE_ANIM, 250);
        this.climbAnim = new LoopingAnimation(TIM_CLIMB_ANIM, 250);
        this.carryAnim = new LoopingAnimation(TIM_CARRY_ANIM, 250);
        this.currentAnim = idleAnim;
        // --- TEST VALUES ---
    }

    public void reset(){
        this.velocity = new Point2D.Float(0, 0);
        this.isClimbing = false;
        this.isCarrying = false;

        this.width = currentImage.getWidth()/2;
        this.height = currentImage.getHeight() - 5;

        this.state = CharacterState.IDLE;
        this.dir = Direction.RIGHT;
        this.currentAnim = idleAnim;
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

    public void setFalling() {
        this.state = CharacterState.JUMPING;
    }

    public void setCarrying() {
        this.isCarrying = true;
        this.state = CharacterState.CLIMBING;
        currentAnim = carryAnim;
        this.width = currentAnim.getFrame().getWidth();
        this.height = currentAnim.getFrame().getHeight() - 5;
        currentAnim.reset();
    }

    public void setState(CharacterState state){
        if (!isCarrying) {
            if (this.state != state) {
                if (state == CharacterState.WALKING) {
                    currentAnim = walkingAnim;
                    this.width = currentAnim.getFrame().getWidth();
                    this.height = currentAnim.getFrame().getHeight() - 5;
                    currentAnim.reset();
                    currentImage = currentAnim.getFrame();
                } else if (state == CharacterState.IDLE) {
                    currentAnim = idleAnim;
                    this.width = currentAnim.getFrame().getWidth();
                    this.height = currentAnim.getFrame().getHeight() - 5;
                    currentAnim.reset();
                    currentImage = currentAnim.getFrame();
                } else if (state == CharacterState.JUMPING) {
                    currentAnim = jumpingAnim;
                    this.width = currentAnim.getFrame().getWidth();
                    this.height = currentAnim.getFrame().getHeight() - 5;
                    currentAnim.reset();
                    currentImage = currentAnim.getFrame();
                } else if (state == CharacterState.CARRYING) {
                    currentAnim = carryAnim;
                    this.width = currentAnim.getFrame().getWidth();
                    this.height = currentAnim.getFrame().getHeight() - 5;
                    currentAnim.reset();
                    currentImage = currentAnim.getFrame();
                } else if (state == CharacterState.CLIMBING) {
                    currentAnim = climbAnim;
                    this.width = currentAnim.getFrame().getWidth();
                    this.height = currentAnim.getFrame().getHeight() - 5;
                    currentAnim.reset();
                    currentImage = currentAnim.getFrame();
                }
            }
            this.state = state;
        }
    }
}
