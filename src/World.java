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
                if (intersects(new Point2D.Float(gameState.tim.getPosition().x, gameState.tim.getPosition().y + gameState.tim.collider.getHeight()), new BufferedImage(gameState.tim.collider.getWidth(), 15, BufferedImage.TYPE_INT_ARGB))) {
                    gameState.tim.velocity.y = -35;
                }
                break;
            case KeyEvent.VK_DOWN:
                gameState.tim.velocity.y = 7;
                break;
            case KeyEvent.VK_LEFT:
                gameState.tim.velocity.x = -7;
                break;
            case KeyEvent.VK_RIGHT :
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
        if (!intersects(gameState.tim.getNextPosition(), gameState.tim.getCollider())) {
            gameState.tim.updatePosition();
            gameState.tim.incrementYVelocity(1);
            System.out.println(gameState.tim.velocity.y);
        }else if(intersects(gameState.tim.getNextPosition(), gameState.tim.getCollider())){
            gameState.tim.resetYVelocity();
        }
    }

    // in progress
    public boolean intersects(Point2D.Float pos, BufferedImage collider){
        int col, r;
        for (int i = (int) pos.x; i < pos.x + collider.getWidth(); ++i) {
            for (int j = (int) pos.y; j < pos.y + collider.getHeight(); ++j) {
                col = gameState.getBitmap().getRGB(i, j);
                r = (col >>> 16) & 0x000000FF;

                if (r == 10) {
                    return true;
                }
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
