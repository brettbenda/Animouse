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
        this.levels[0] = new Level("/test_images/test_effect_layer03.png", "/test_images/test_effect_layer03.png", "path", new Cutscene[1], new Cutscene[1]);
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
                int xx = (int) gameState.tim.position.x;
                int yy = (int) gameState.tim.position.y + gameState.tim.height;

                if (getCollisionStatus(xx, yy, gameState.tim.width, 15)) {
                    gameState.tim.velocity.y = -35;
                }
                break;
            case KeyEvent.VK_DOWN:
                gameState.tim.velocity.y = 7;
                break;
            case KeyEvent.VK_LEFT:
                System.out.println(gameState.tim.velocity.y);
                gameState.tim.velocity.x = -7;
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println(gameState.tim.velocity.y);
                gameState.tim.velocity.x = 7;
                break;
        }
    }

    public void handleKeyRelease(KeyEvent event) {
        int keyCode = event.getKeyCode();
        switch( keyCode ) {
//            case KeyEvent.VK_UP:
//                gameState.tim.resetYVelocity();
//                break;
            case KeyEvent.VK_DOWN:
                gameState.tim.resetYVelocity();
                break;
            case KeyEvent.VK_LEFT:
                gameState.tim.resetXVelocity();
                break;
            case KeyEvent.VK_RIGHT :
                gameState.tim.resetXVelocity();
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
        int xx = (int) gameState.tim.getNextPosition().x;
        int yy = (int) gameState.tim.getNextPosition().y;

        if (!getRightCollisionStatus() && !getLeftCollisionStatus() && !getTopCollisionStatus() && !getBottomCollisionStatus()) {
            gameState.tim.updatePosition();
            gameState.tim.incrementYVelocity(1);
        } else if (getTopCollisionStatus() || getBottomCollisionStatus()){
            gameState.tim.resetYVelocity();
        }
    }

    // in progress
    public boolean getCollisionStatus(int posX, int posY, int width, int height) {
        int colorA = (gameState.getBitmap().getRGB(posX, posY) >>> 16) & 0x000000FF;
        int colorB = (gameState.getBitmap().getRGB(posX + width, posY) >>> 16) & 0x000000FF;
        int colorC = (gameState.getBitmap().getRGB(posX, posY + height) >>> 16) & 0x000000FF;
        int colorD = (gameState.getBitmap().getRGB(posX + width, posY + height) >>> 16) & 0x000000FF;

        if (colorA == 10 || colorB == 10 || colorC == 10 || colorD == 10) {
            return true;
        }
        return false;
    }

    public boolean getRightCollisionStatus() {
        int posX = (int) gameState.tim.getNextPosition().x + gameState.tim.width;
        int posY = (int) gameState.tim.getNextPosition().y;
        int height = gameState.tim.height;
        int color;

        for (int j = posY; j < posY + height; ++j) {
            color = (gameState.getBitmap().getRGB(posX, j) >>> 16) & 0x000000FF;
            if (color == 10) {
                return true;
            }
        }
        return false;
    }

    public boolean getLeftCollisionStatus() {
        int posX = (int) gameState.tim.getNextPosition().x;
        int posY = (int) gameState.tim.getNextPosition().y;
        int height = gameState.tim.height;
        int color;

        for (int j = posY; j < posY + height; ++j) {
            color = (gameState.getBitmap().getRGB(posX, j) >>> 16) & 0x000000FF;
            if (color == 10) {
                return true;
            }
        }
        return false;
    }

    public boolean getTopCollisionStatus() {
        int posX = (int) gameState.tim.getNextPosition().x;
        int posY = (int) gameState.tim.getNextPosition().y;
        int width = gameState.tim.width;
        int color;

        for (int i = posX; i < posX + width; ++i) {
            color = (gameState.getBitmap().getRGB(i, posY) >>> 16) & 0x000000FF;
            if (color == 10) {
                return true;
            }
        }
        return false;
    }

    public boolean getBottomCollisionStatus() {
        int posX = (int) gameState.tim.getNextPosition().x;
        int posY = (int) gameState.tim.getNextPosition().y + gameState.tim.height;
        int height = gameState.tim.height;
        int color;

        for (int i = posX; i < posX + height; ++i) {
            color = (gameState.getBitmap().getRGB(i, posY) >>> 16) & 0x000000FF;
            if (color == 10) {
                return true;
            }
        }
        return false;
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
