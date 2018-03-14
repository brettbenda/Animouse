import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class World {
    private Display display;

    private GameState gameState;

    private Level[] levels;
    private int levelIndex;

    private Camera camera;

    // constructor
    public World(Display display) {
        // store reference to display
        this.display = display;

        // load all the levels
        this.levels = new Level[1];
        this.levels[0] = new Level("path1", "path2", "path3", new Cutscene[1], new Cutscene[1]);
        this.levelIndex = 0;

        // initialize game state
        this.gameState = new GameState(levels[levelIndex]);

        // create camera
        this.camera = new Camera(display.getWidth()/display.getHeight(), display.getWidth(), display.getHeight());
    }

    public void handleKeyInput(KeyEvent event){

    }

    public void handleMouseInput(MouseEvent event){

    }

    public void tick(){

    }

    public boolean intersects(){
        return true;
    }

    public void loadNextLevel(){
        // play end cutscenes
        // ...

        // go to next level
        levelIndex++;
        gameState.loadLevel(levels[levelIndex]);

        // play start cutscenes
        // ...
    }

    public BufferedImage getBackground() {
        return gameState.getBackground();
    }

}
