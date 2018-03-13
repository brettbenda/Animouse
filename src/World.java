import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class World {
    private Display display;

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

        // create camera
        this.camera = new Camera(display.getWidth()/display.getHeight(), display.getWidth(), display.getHeight());
    }

    void handleKeyInput(KeyEvent event){

    }

    void handleMouseInput(MouseEvent event){

    }

    void tick(){

    }

    void render(){

    }

    boolean intersects(){

        return true;
    }

    void loadNextLevel(){

    }

}
