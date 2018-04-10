import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class VisualAssets {

    final int numOfLevels = 8;

    BufferedImage TIM;
    BufferedImage[] TIM_JUMPING_ANIM;
    BufferedImage[] TIM_WALKING_ANIM;
    BufferedImage[] TIM_IDLE_ANIM;

    BufferedImage JACK;
    BufferedImage[] JACK_JUMPING_ANIM;
    BufferedImage[] JACK_WALKING_ANIM;
    BufferedImage[] JACK_IDLE_ANIM;

    BufferedImage[] LEVELX_BACKGROUND;      // a.k.a. LEVELX_ART_LAYER
    BufferedImage[] LEVELX_BITMAP;          // a.k.a. LEVELX_ZONE_LAYER
    BufferedImage[][] LEVELX_CUTSCENES;

    BufferedImage[][] INTERMISSION_CUTSCENES;

    public VisualAssets(){
        // load all assets
        loadTimArt();
        loadJackArt();
        loadLevelBackgrounds();
        loadLevelBitmaps();
        // loadLevelCutscenes();
        // loadIntermissionCutscenes();
    }

    private void loadTimArt(){
        TIM = ImageLoader.loadImage("/VisualAssets/Tim/Tim.png");

        // Jumping Animation
        TIM_JUMPING_ANIM = new BufferedImage[5];
        for (int i = 0; i < TIM_JUMPING_ANIM.length; ++i) {
            TIM_JUMPING_ANIM[i] = ImageLoader.loadImage("/test_images/tim_animation_" + i + ".png");
        }
        // Walking Animation
        TIM_WALKING_ANIM = new BufferedImage[5];
        for (int i = 0; i < TIM_WALKING_ANIM.length; ++i) {
            TIM_WALKING_ANIM[i] = ImageLoader.loadImage("/test_images/tim_animation_" + i + ".png");
        }
        // Idle Animation
        TIM_IDLE_ANIM = new BufferedImage[5];
        for (int i = 0; i < TIM_IDLE_ANIM.length; ++i) {
            TIM_IDLE_ANIM[i] = ImageLoader.loadImage("/test_images/tim_animation_" + i + ".png");
        }
    }

    private void loadJackArt(){
        //      JACK = ImageLoader.loadImage("/VisualAssets/Jack/Jack.png");

        // Jumping Animation
        JACK_JUMPING_ANIM = new BufferedImage[5];
        for (int i = 0; i < JACK_JUMPING_ANIM.length; ++i) {
            JACK_JUMPING_ANIM[i] = ImageLoader.loadImage("/test_images/tim_animation_" + i + ".png");
        }
        // Walking Animation
        JACK_WALKING_ANIM = new BufferedImage[5];
        for (int i = 0; i < JACK_WALKING_ANIM.length; ++i) {
            JACK_WALKING_ANIM[i] = ImageLoader.loadImage("/test_images/tim_animation_" + i + ".png");
        }
        // Idle Animation
        JACK_IDLE_ANIM = new BufferedImage[5];
        for (int i = 0; i < JACK_IDLE_ANIM.length; ++i) {
            JACK_IDLE_ANIM[i] = ImageLoader.loadImage("/test_images/tim_animation_" + i + ".png");
        }
    }

    private void loadLevelBackgrounds(){
        LEVELX_BACKGROUND = new BufferedImage[numOfLevels];
        for (int i = 0; i < numOfLevels; ++i){
            LEVELX_BACKGROUND[i] = ImageLoader.loadImage("/VisualAssets/Background/levelBackground_" + i + ".png");
        }
    }

    private void loadLevelBitmaps(){
        LEVELX_BITMAP = new BufferedImage[numOfLevels];
        for (int i = 0; i < numOfLevels; ++i){
            LEVELX_BITMAP[i] = ImageLoader.loadImage("/VisualAssets/Bitmap/levelBitmap_" + i + ".png");
        }
    }

    public BufferedImage getTim(){
        return this.TIM;
    }
    public BufferedImage getJack(){
        return this.JACK;
    }
    public BufferedImage getBackground(int level){
        return this.LEVELX_BACKGROUND[level];
    }
    public BufferedImage getBitmap(int level){
        return this.LEVELX_BITMAP[level];
    }
}

