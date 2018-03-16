import java.awt.image.BufferedImage;

public class GameState {
    // current level data
    private Level level;

    // currently selected player: T = 0, J = 1
    private int selectedPlayer;


    // Tim and Jack
    public Tim tim;
    public Jack jack;

    // constructor
    public GameState(Level level) {
        this.level = level;

        // --- TEST VALUES ---
        this.selectedPlayer = 0; //0 for Tim, 1 for Jack
        this.tim = new Tim(960, 540);
        this.jack = new Jack(400, 540);
        // --- TEST VALUES ---
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

    public Player currentPlayer(){
        if (this.selectedPlayer == 0)
            return this.tim;
        else
            return this.jack;
    }

    public void switchPlayer(){
        if (selectedPlayer == 0)
            selectedPlayer = 1;
        else
            selectedPlayer = 0;
    }
}
