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

        SoundEffect.init();

        // --- TEST VALUES ---
    }

    public void loadLevel(Level level) {
        this.level = level;
        this.tim.reset();
        this.jack.reset();
        this.tim.position = level.posTim;
        this.jack.position = level.posJack;
        this.tim.resetYVelocity();
        this.jack.resetXVelocity();
        if (level.playTim) {
            selectedPlayer = 0;
        } else {
            selectedPlayer = 1;
        }

        if (level.id == 7) {
            tim.setCarrying();
        }

        if (level.id == 0) {
            SoundEffect.BGMUSIC0.playLoop();
        } else if (level.id == 1) {
            SoundEffect.BGMUSIC1.playLoop();
        } else if (level.id == 2) {
            SoundEffect.BGMUSIC2.playLoop();
        } else if (level.id == 3) {
            SoundEffect.BGMUSIC3.playLoop();
        } else if (level.id == 4) {
            SoundEffect.BGMUSIC4.playLoop();
        } else if (level.id == 5) {
            SoundEffect.BGMUSIC5.playLoop();
        } else if (level.id == 6) {
            SoundEffect.BGMUSIC6.playLoop();
        } else {
            SoundEffect.BGMUSIC7.playLoop();
        }

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
