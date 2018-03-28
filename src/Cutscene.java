import java.awt.image.BufferedImage;

public class Cutscene {
    BufferedImage[] frames;     // a.k.a. scenes
    // Sounds
    BufferedImage currentFrame;     // a.k.a. currentScene
    int index;

    // constructor
    public Cutscene(int scene, int numOfFrames) {
        frames = new BufferedImage[numOfFrames];
        for (int i = 0; i < numOfFrames; ++i) {
            frames[i] = ImageLoader.loadImage("/VisualAssets/Cutscenes/scene_" + scene + "/frame_" + i + ".jpg");
        }
        index = 0;
    }

    public BufferedImage getCurrentFrame() {
        return this.frames[index];
    }

    protected void advance(){
        ++index;
    }

    protected boolean ended(){
        if (index == frames.length)
            return true;
        else
            return false;
    }
}