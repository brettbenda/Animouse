import java.awt.image.BufferedImage;

public class VisualAssets {

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
    }

    private void loadTimArt(){
        TIM = ImageLoader.loadImage("/VisualAssets/Tim/Tim.png");

    }

}

