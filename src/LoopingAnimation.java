import java.awt.image.BufferedImage;

public class LoopingAnimation implements Animation {
    private long previousTime;
    private int frameRate;
    private BufferedImage frames[];
    private int index;

    public LoopingAnimation(BufferedImage frames[], int frameRate) {
        this.frames = frames;
        this.index = 0;
        this.frameRate = frameRate;
        this.previousTime = System.currentTimeMillis();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (this.index < this.frames.length - 1 && currentTime - this.previousTime > frameRate) {
            this.index++;
            previousTime = currentTime;
        } else if (currentTime - this.previousTime > frameRate) {
            reset();
        }
    }

    public BufferedImage getFrame() {
        return this.frames[index];
    }

    public void reset() {
        this.index = 0;
        this.previousTime = System.currentTimeMillis();
    }
}