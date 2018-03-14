import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
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
        this.levels[0] = new Level("/test_images/test_effect_layer02.png", "/test_images/test_effect_layer02.png", "path", new Cutscene[1], new Cutscene[1]);
        this.levelIndex = 0;

        // initialize game state
        this.gameState = new GameState(levels[levelIndex]);

        // create camera
        // this.camera = new Camera(display.getWidth()/display.getHeight(), display.getWidth(), display.getHeight());
    }

    public void handleKeyPress(KeyEvent event){
        int keyCode = event.getKeyCode();
        switch( keyCode ) {
            case KeyEvent.VK_UP:
                gameState.tim.velocity.y = -5;
                break;
            case KeyEvent.VK_DOWN:
                gameState.tim.velocity.y = 5;
                break;
            case KeyEvent.VK_LEFT:
                gameState.tim.velocity.x = -5;
                break;
            case KeyEvent.VK_RIGHT :
                gameState.tim.velocity.x = 5;
                break;
        }
    }

    public void handleKeyRelease(KeyEvent event){
        int keyCode = event.getKeyCode();
        switch( keyCode ) {
            case KeyEvent.VK_UP:
                gameState.tim.velocity.y = 0;
                break;
            case KeyEvent.VK_DOWN:
                gameState.tim.velocity.y = 0;
                break;
            case KeyEvent.VK_LEFT:
                gameState.tim.velocity.x = 0;
                break;
            case KeyEvent.VK_RIGHT :
                gameState.tim.velocity.x = 0;
                break;
        }
    }

    public void handleMouseInput(MouseEvent event){
        // get mouse position
        int xx = event.getX();
        int yy = event.getY();

        // get bitmap
        BufferedImage bitmap = gameState.getBitmap();

        // get pixel color
        int color = bitmap.getRGB(xx, yy);
        int r = (color >>> 16) & 0x000000FF;
        int g = (color >>> 8) & 0x000000FF;
        int b = color & 0x000000FF;

        System.out.println(r + " " + b + " " + g);

        // check the region at the mouse position
        if ( (r == 10) && (g == 0) && (b == 0) ) {
            System.out.println("inside region");
        }
    }

    public void tick(){
        gameState.tim.position = new Point2D.Float(gameState.tim.position.x + gameState.tim.velocity.x, gameState.tim.position.y + gameState.tim.velocity.y);
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

    public BufferedImage getTimImage() {
        return gameState.tim.getSprite();
    }

    public float getTimX() {
        return gameState.tim.getPosition().x;
    }

    public float getTimY() {
        return gameState.tim.getPosition().y;
    }
}
