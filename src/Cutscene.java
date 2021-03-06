import java.awt.image.BufferedImage;

public class Cutscene {
    BufferedImage currentFrame;
    int index;
    int numOfFrames;
    int scene;
    boolean ended;

    // constructor
    public Cutscene(int scene, int numOfFrames) {

        SoundEffect.init();
        this.ended = false;
        this.scene = scene;
        this.numOfFrames = numOfFrames;
        this.index = 0;
        this.currentFrame = ImageLoader.loadImage("/VisualAssets/Cutscenes/scene_" + scene + "/frame_" + index + ".png");
    }

    public BufferedImage getCurrentFrame() {
        return this.currentFrame;
    }

    public void advance() {
        ++index;
        if(scene == 8 && index == 28){
            SoundEffect.BGMUSIC7.playLoop();
        }
        if (index < numOfFrames)
            this.currentFrame = ImageLoader.loadImage("/VisualAssets/Cutscenes/scene_" + scene + "/frame_" + index + ".png");
        else
            ended = true;
    }
}