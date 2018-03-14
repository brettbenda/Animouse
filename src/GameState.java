import java.awt.image.BufferedImage;

public class GameState {
    // current level data
    private Level level;

    // currently selected player: T = 0, J = 1
    private int selectedPlayer;

    // Tim and Jack
    private Tim tim;
    private Jack jack;

    // constructor
    public GameState(Level level) {
        this.level = level;
        this.selectedPlayer = 0;
        this.tim = new Tim();
        this.jack = new Jack();
    }

    public void loadLevel(Level level) {
        this.level = level;
    }

    public BufferedImage getBackground() {
        return level.imageLayer;
    }

    public BufferedImage getBitmap() {
        return level.bitmap;
    }
}
