import java.awt.image.BufferedImage;

public interface Animation {
    public void update();
    public BufferedImage getFrame();
    public void reset();
}