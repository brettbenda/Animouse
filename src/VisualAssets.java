import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class VisualAssets {

    final int numOfLevels = 9;

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
        // add for loops for Tim's animations

    }

    private void loadJackArt(){
        //      JACK = ImageLoader.loadImage("/VisualAssets/Jack/Jack.png");
        // add for loops for Tim's animations
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

