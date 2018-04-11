import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Jack extends Player {

    private final float maxXVelocity = 15;
    private final float grappleSpeed = 40;
    public int movementState;
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
        this.currentImage = ImageLoader.loadImage("/VisualAssets/Jack/jack.png");
        this.crosshair = ImageLoader.loadImage("/VisualAssets/Jack/crosshair.png");
        this.width = currentImage.getWidth();
        this.height = currentImage.getHeight() - 5;

        System.out.println(width + "" + height);

        this.state = CharacterState.IDLE;
        this.dir = Direction.RIGHT;

        BufferedImage TIM_JUMPING_ANIM[];
        BufferedImage TIM_WALKING_ANIM[];
        BufferedImage TIM_IDLE_ANIM[];

        // Jumping Animation
        TIM_JUMPING_ANIM = new BufferedImage[5];
        for (int i = 0; i < TIM_JUMPING_ANIM.length; ++i) {
            // TIM_JUMPING_ANIM[i] = ImageLoader.loadImage("/test_images/tim_animation_" + i + ".png");
            TIM_JUMPING_ANIM[i] = ImageLoader.loadImage("/VisualAssets/Jack/jack.png");
        }
        // Walking Animation
        TIM_WALKING_ANIM = new BufferedImage[5];
        for (int i = 0; i < TIM_WALKING_ANIM.length; ++i) {
            //TIM_WALKING_ANIM[i] = ImageLoader.loadImage("/test_images/tim_animation_" + i + ".png");
            TIM_WALKING_ANIM[i] = ImageLoader.loadImage("/VisualAssets/Jack/jack.png");
        }
        // Idle Animation
        TIM_IDLE_ANIM = new BufferedImage[5];
        for (int i = 0; i < TIM_IDLE_ANIM.length; ++i) {
            //TIM_IDLE_ANIM[i] = ImageLoader.loadImage("/test_images/tim_animation_" + i + ".png");
            TIM_IDLE_ANIM[i] = ImageLoader.loadImage("/VisualAssets/Jack/jack.png");
        }

        this.walkingAnim = new LoopingAnimation(TIM_WALKING_ANIM, 250);
        this.jumpingAnim = new FiniteAnimation(TIM_JUMPING_ANIM, 250);
        this.idleAnim = new LoopingAnimation(TIM_IDLE_ANIM, 250);
        this.currentAnim = idleAnim;
        // --- TEST VALUES ---
    }

    public void reset(){
        this.velocity = new Point2D.Float(0, 0);
        this.movementState = NORMAL;
        this.hookPosition = new Point2D.Float();

        // --- TEST VALUES ---
        this.width = currentImage.getWidth();
        this.height = currentImage.getHeight() - 5;

        this.state = CharacterState.IDLE;
        this.dir = Direction.RIGHT;
        this.currentAnim = idleAnim;
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
        //System.out.println(this.position.y);
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

    public float getNextX() {
        return this.position.x + this.velocity.x;
    }

    public float getNextY() {
        return this.position.y + this.velocity.y;
    }

    public void setState(CharacterState state){
        if (this.state != state) {
            if (state == CharacterState.WALKING) {
                currentAnim = walkingAnim;
                this.width = currentAnim.getFrame().getWidth();
                this.height = currentAnim.getFrame().getHeight() - 5;
                currentAnim.reset();
            } else if (state == CharacterState.IDLE) {
                currentAnim = idleAnim;
                this.width = currentAnim.getFrame().getWidth();
                this.height = currentAnim.getFrame().getHeight() - 5;
                currentAnim.reset();
            } else if (state == CharacterState.JUMPING) {
                currentAnim = jumpingAnim;
                this.width = currentAnim.getFrame().getWidth();
                this.height = currentAnim.getFrame().getHeight() - 5;
                currentAnim.reset();
            }
        }
        this.state = state;
    }
}
