import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class World {
    private Display display;
    private GameState gameState;
    private Level currentLevel;
    private ArrayList<Level> levels = new ArrayList<Level>();

    private int plotPoint;
    private Camera camera;

    // constructor
    public World(Display display) {
        // store reference to display
        this.display = display;
        this.plotPoint = 2;

        //preload levels
        for(int i = 0; i < 9; i++){
            levels.add(new Level(i));
        }
        // initialize game state
        this.gameState = new GameState(levels.get(0));

      //  levels.get(2).addHookPoint(new Point2D.Float(1250,1628));
       // levels.get(2).addHookPoint(new Point2D.Float(1400,1124));
    }

    public void handleKeyPress(KeyEvent event){
        Player currentPlayer = gameState.currentPlayer();

        int keyCode = event.getKeyCode();
        switch( keyCode ) {
            case KeyEvent.VK_UP:
                if (currentPlayer == gameState.tim) {
                    if (gameState.tim.isClimbing()) {
                        gameState.tim.velocity.y = -5;
                    } else {
                        int xx = (int) gameState.tim.position.x;
                        int yy = (int) gameState.tim.position.y + gameState.tim.height;

                        if (!getCollisionStatus(xx, yy, gameState.tim.width, 15) && areTimAndJackIntersecting() && gameState.tim.velocity.y>0) {
                            gameState.tim.velocity.y = -70;
                        }else if (getCollisionStatus(xx, yy, gameState.tim.width, 15)) {
                            gameState.tim.velocity.y = -35;
                        }
                    }
                } else {
                    if (gameState.jack.isHooked()) {
                        gameState.jack.velocity = new Point2D.Float(0, -30);
                        gameState.jack.setMovementState(0);
                    }
                }
                break;
            case KeyEvent.VK_DOWN:
                if (currentPlayer == gameState.tim && gameState.tim.isClimbing()) {
                    gameState.tim.velocity.y = 5;
                } else {
                    if (gameState.jack.isHooked()) {
                        gameState.jack.velocity = new Point2D.Float(0, 30);
                        gameState.jack.setMovementState(0);
                    }
                }
                break;
            case KeyEvent.VK_LEFT:
                if (currentPlayer == gameState.tim) {
                    if (gameState.tim.isClimbing()) {
                        gameState.tim.velocity.x = -5;
                    } else {
                        System.out.println("Tim's y velocity " + gameState.tim.velocity.y);
                        gameState.tim.velocity.x = -7;
                    }
                } else {
                    if (gameState.jack.isHooked()) {
                        gameState.jack.velocity = new Point2D.Float(-30, 0);
                        gameState.jack.setMovementState(0);
                    } else if (!gameState.jack.isHooked() && !gameState.jack.isGrappling()) {
                        System.out.println("left");
                        gameState.jack.decrementXVelocity(0.5f);
                    }
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (currentPlayer == gameState.tim) {
                    if (gameState.tim.isClimbing()) {
                        gameState.tim.velocity.x = 5;
                    } else {
                        System.out.println("Tim's y velocity " + gameState.tim.velocity.y);
                        gameState.tim.velocity.x = 7;
                    }
                } else {
                    if (gameState.jack.isHooked()) {
                        gameState.jack.velocity = new Point2D.Float(30, 0);
                        gameState.jack.setMovementState(0);
                    } else if (!gameState.jack.isHooked() && !gameState.jack.isGrappling()) {
                        System.out.println("right");
                        gameState.jack.incrementXVelocity(0.5f);
                    }
                }
                break;
            case KeyEvent.VK_E:
                gameState.switchPlayer();
                System.out.println("swap");
                break;
            case KeyEvent.VK_C:
                if (gameState.currentPlayer() == gameState.tim && getRegion(gameState.tim) == 40 && !gameState.tim.isClimbing()) {
                    gameState.tim.setClimbing(true);
                    gameState.tim.resetXVelocity();
                    gameState.tim.resetYVelocity();
                } else if (gameState.currentPlayer() == gameState.tim && gameState.tim.isClimbing()) {
                    gameState.tim.setClimbing(false);
                }
                break;
            case KeyEvent.VK_Q:
                if (gameState.currentPlayer() == gameState.jack && getRegion(gameState.jack) == 50) {
                    int regionIndex = getHookIndex(gameState.jack);
                   // gameState.jack.setPosition(currentLevel.getHookablePoint(regionIndex));
                    gameState.jack.grappleTo(currentLevel.getHookablePoint(regionIndex));
                    System.out.println("Grapple to region " + regionIndex);
                    System.out.println("Grapple to location " + currentLevel.getHookablePoint(regionIndex).x + ", " + currentLevel.getHookablePoint(regionIndex).y);
                }
                break;
            case 49: // 1
                System.out.println("Loading Level 1...");
                gameState.loadLevel(levels.get(0));
                break;
            case 50: // 2
                System.out.println("Loading Level 2...");
                gameState.loadLevel(levels.get(1));
                break;
            case 51: // 3
                System.out.println("Loading Level 3...");
                gameState.loadLevel(levels.get(2));
                currentLevel = levels.get(2);
                break;
            case 52: // 4
                System.out.println("Loading Level 4...");
                gameState.loadLevel(levels.get(3));
                break;
            case 53: // 5
                System.out.println("Loading Level 5...");
                gameState.loadLevel(levels.get(4));
                break;
            case 54: // 6
                System.out.println("Loading Level 6...");
                gameState.loadLevel(levels.get(5));
                break;
            case 55: // 7
                System.out.println("Loading Level 7...");
                gameState.loadLevel(levels.get(6));
                break;
            case 56: // 8
                System.out.println("Loading Level 8...");
                gameState.loadLevel(levels.get(7));
                break;
            case 57: // 9
                System.out.println("Loading Level 9...");
                gameState.loadLevel(levels.get(8));
                break;
        }
    }

    public void handleKeyRelease(KeyEvent event) {
        Player currentPlayer = gameState.currentPlayer();

        int keyCode = event.getKeyCode();
        switch( keyCode ) {
            case KeyEvent.VK_UP:
                if (gameState.tim.isClimbing())
                    currentPlayer.resetYVelocity();
                break;
            case KeyEvent.VK_DOWN:
                currentPlayer.resetYVelocity();
                break;
            case KeyEvent.VK_LEFT:
                currentPlayer.resetXVelocity();
                break;
            case KeyEvent.VK_RIGHT :
                currentPlayer.resetXVelocity();
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
        Player currentPlayer = gameState.currentPlayer();
        Player inactivePlayer = gameState.inactivePlayer();

        //update Tim
        if (!rightCollision(gameState.tim) && !leftCollision(gameState.tim) && !topCollision(gameState.tim) && !bottomCollision(gameState.tim)) {
            gameState.tim.updatePosition();

            if (!gameState.tim.isClimbing())
                gameState.tim.incrementYVelocity(1);
        }
        else if (topCollision(gameState.tim) || bottomCollision(gameState.tim)){
            gameState.tim.resetYVelocity();
        }

        // Handle Death scenario for Tim
        if(gameState.tim.velocity.y > 25 && rightCollision(gameState.tim) && !(areTimAndJackIntersecting())){
            gameState.loadLevel(new Level(3));

        }

        //update Jack
        if (gameState.jack.isGrappling()){
            gameState.jack.updatePosition();
        }
        else if (gameState.jack.isHooked()) {
            // do nothing
        }
        else if (!rightCollision(gameState.jack) && !leftCollision(gameState.jack) && !topCollision(gameState.jack) && !bottomCollision(gameState.jack)) {
            gameState.jack.updatePosition();
            gameState.jack.incrementYVelocity(1);
        } else if (topCollision(gameState.jack) || bottomCollision(gameState.jack)){
            gameState.jack.resetYVelocity();
        }

        // handle leaving climbable region
        if (gameState.currentPlayer() == gameState.tim && getRegion(gameState.tim) != 40)
            gameState.tim.setClimbing(false);

        if(getRegion(gameState.tim)==30 && getRegion(gameState.jack)==30){
            System.out.println("End of level!111!!!!1!!!1!11");
            plotPoint++;
            gameState.loadLevel(levels.get(plotPoint));
        }

        //update inactive player
        inactivePlayer.resetXVelocity();
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

    public boolean rightCollision(Player player) {
        int posX = (int) player.getNextPosition().x + player.width;
        int posY = (int) player.getNextPosition().y;
        int height = player.height;
        int color;

        for (int j = posY; j < posY + height; ++j) {
            color = (gameState.getBitmap().getRGB(posX, j) >>> 16) & 0x000000FF;
            if (color == 10) {
                if (player == gameState.tim)
                    System.out.println("TIM right");
                else
                    System.out.println("JACK right");
                return true;
            }
        }
        return false;
    }

    public boolean leftCollision(Player player) {
        int posX = (int) player.getNextPosition().x;
        int posY = (int) player.getNextPosition().y;
        int height = player.height;
        int color;

        for (int j = posY; j < posY + height; ++j) {
            color = (gameState.getBitmap().getRGB(posX, j) >>> 16) & 0x000000FF;
            if (color == 10) {
                if (player == gameState.tim)
                    System.out.println("TIM left");
                else
                    System.out.println("JACK left");
                return true;
            }
        }
        return false;
    }

    public boolean topCollision(Player player) {
        int posX = (int) player.getNextPosition().x;
        int posY = (int) player.getNextPosition().y;
        int width = player.width;
        int color;

        for (int i = posX; i < posX + width; ++i) {
            color = (gameState.getBitmap().getRGB(i, posY) >>> 16) & 0x000000FF;
            if (color == 10) {
                return true;
            }
        }
        return false;
    }

    public boolean bottomCollision(Player player) {
        int posX = (int) player.getNextPosition().x;
        int posY = (int) player.getNextPosition().y + gameState.tim.height;
        int height = player.height;
        int color;

        for (int i = posX; i < posX + height; ++i) {
            color = (gameState.getBitmap().getRGB(i, posY) >>> 16) & 0x000000FF;
            if (color == 10) {
                return true;
            }
        }
        return false;
    }

    public boolean areTimAndJackIntersecting(){
        if (Math.abs(gameState.tim.position.x - gameState.jack.position.x) <= gameState.tim.width
                && Math.abs(gameState.tim.position.y - gameState.jack.position.y) <= gameState.tim.height)
            return true;
        else
            return false;
    }


    private int getRegion(Player player) {
        int xx = (int) player.position.x;
        int yy = (int) player.position.y;
        int width = player.width;
        int height = player.height;

        return (gameState.getBitmap().getRGB(xx + width/2, yy + height/2) >>> 16) & 0x000000FF;
    }

    private int getHookIndex(Player player){
        int xx = (int) player.position.x;
        int yy = (int) player.position.y;
        int width = player.width;
        int height = player.height;

        return ((gameState.getBitmap().getRGB(xx + width/2, yy + height/2) >>> 8) & 0x000000FF)/10 ;
    }
    public void loadNextLevel(){
        // play end cutscenes
        // ...

        // go to next level
        // ...

        // play start cutscenes
        // ...
    }

    public BufferedImage getBackground() {
        return gameState.getBackground();
    }

    public BufferedImage getActivePlayerImage(){
        return gameState.currentPlayer().getSprite();
    }

    public float getActivePlayerX(){
        return gameState.currentPlayer().getPosition().x;
    }

    public float getActivePlayerY(){
        return gameState.currentPlayer().getPosition().y;
    }

    public BufferedImage getInactivePlayerImage(){
        return gameState.inactivePlayer().getSprite();
    }

    public float getInactivePlayerX(){
        return gameState.inactivePlayer().getPosition().x;
    }

    public float getInactivePlayerY(){
        return gameState.inactivePlayer().getPosition().y;
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

    public BufferedImage getJackImage() {
        return gameState.jack.getSprite();
    }

    public float getJackX() {
        return gameState.jack.getPosition().x;
    }

    public float getJackY() {
        return gameState.jack.getPosition().y;
    }
}
