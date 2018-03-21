import java.awt.geom.Point2D;
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
        this.tim = new Tim(level.posTim.x, level.posTim.y);
        this.jack = new Jack(level.posJack.x,level.posJack.y);


        // --- TEST VALUES ---
    }

    public void loadLevel(Level level) {
        this.level = level;
        this.tim.position = level.posTim;
        this.jack.position = level.posJack;

    }

    public BufferedImage getBackground() {
        return level.background;
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

    public Player inactivePlayer(){
        if (this.selectedPlayer == 1)
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
