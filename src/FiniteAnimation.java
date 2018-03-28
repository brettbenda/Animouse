import java.awt.image.BufferedImage;

public class FiniteAnimation implements Animation {
    private long previousTime;
    private int frameRate;
    private BufferedImage frames[];
    private int index;

    public FiniteAnimation(BufferedImage frames[], int frameRate) {
        this.frames = frames;
        this.index = 0;
        this.frameRate = frameRate;
        this.previousTime = System.currentTimeMillis();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (this.index < frames.length - 1 && currentTime - previousTime > frameRate) {
            this.index++;
            previousTime = currentTime;
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