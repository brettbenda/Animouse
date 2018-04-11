import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.net.URL;
import javax.sound.sampled.*;

public class World {
    private Display display;
    private GameState gameState;
    private Level currentLevel;
    private ArrayList<Level> levels = new ArrayList<Level>();
    private ArrayList<Cutscene> cutscenes = new ArrayList<Cutscene>();

    private int plotPoint;
    private boolean isPlayable;

    public Point2D grapplePoint;

    public boolean isOver;

    // constructor
    public World(Display display) {
        // store reference to display
        this.display = display;
        this.plotPoint = 0;

        this.isOver = false;

        loadPlot();
        isPlayable = false;

        //preload levels
        for(int i = 0; i < 8; i++){
            levels.add(new Level(i));
        }

        // initialize game state
        this.gameState = new GameState(levels.get(0));

        SoundEffect.init();
    }

    private void loadPlot(){
        int numOfFrames = 0;
        for (int i = 0; i < 8; ++i){
            switch(i){
                case 0:
                    numOfFrames = 42;
                    break;
                case 1:
                    numOfFrames = 7;
                    break;
                case 2:
                    numOfFrames = 20;
                    break;
                case 3:
                    numOfFrames = 26;
                    break;
                case 4:
                    numOfFrames = 35;
                    break;
                case 5:
                    numOfFrames = 6;
                    break;
                case 6:
                    numOfFrames = 28;
                    break;
                case 7:
                    numOfFrames = 21;
                    break;
            }
            levels.add(new Level(i));
            cutscenes.add(new Cutscene(i, numOfFrames));
        }
        numOfFrames = 31;
        cutscenes.add(new Cutscene(8, numOfFrames));
    }

    public void handleKeyPress(KeyEvent event){
        if (isPlayable) {
            Player currentPlayer = gameState.currentPlayer();

            int keyCode = event.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    if (currentPlayer == gameState.tim) {
                        if (gameState.tim.getState() == CharacterState.CLIMBING) {
                            gameState.tim.velocity.y = -5;
                        } else {
                            gameState.tim.setState(CharacterState.JUMPING);
                            int xx = (int) gameState.tim.position.x;
                            int yy = (int) gameState.tim.position.y + gameState.tim.height;

                            if (!getCollisionStatus(gameState.tim) && areTimAndJackIntersecting() && gameState.tim.velocity.y > 0) {
                                gameState.tim.velocity.y = -40;
                                SoundEffect.JUMPING.playOnce();
                            } else if (getCollisionStatus(gameState.tim) && gameState.tim.velocity.y==0) {
                                gameState.tim.velocity.y = -20;
                                SoundEffect.JUMPING.playOnce();
                            }
                        }
                    } else {
                        if (gameState.jack.isHooked()) {
                            gameState.jack.setState(CharacterState.FALLING);
                            gameState.jack.velocity = new Point2D.Float(0, -20);
                            gameState.jack.setMovementState(0);
                        }
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (currentPlayer == gameState.tim && gameState.tim.getState() == CharacterState.CLIMBING) {
                        gameState.tim.velocity.y = 5;
                    } else {
                        if (gameState.jack.isHooked()) {
                            gameState.jack.setState(CharacterState.FALLING);
                            gameState.jack.velocity = new Point2D.Float(0, 15);
                            gameState.jack.setMovementState(0);
                        }
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (currentPlayer == gameState.tim) {
                        if (gameState.tim.getState() == CharacterState.CLIMBING) {
                            gameState.tim.velocity.x = -5;
                        } else {
                            //System.out.println("Tim's y velocity " + gameState.tim.velocity.y);
                            gameState.tim.setState(CharacterState.WALKING);
                            gameState.tim.velocity.x = -7;
                        }
                        SoundEffect.TIM_WALKING.playLoop();
                        gameState.tim.dir = Player.Direction.LEFT;
                    } else {
                        if (gameState.jack.isHooked()) {
                            gameState.jack.setState(CharacterState.FALLING);
                            gameState.jack.velocity = new Point2D.Float(-15, 0);
                            gameState.jack.setMovementState(0);
                        } else if (!gameState.jack.isHooked() && !gameState.jack.isGrappling()) {
                            //System.out.println("left");
                            gameState.jack.decrementXVelocity(0.5f);
                            gameState.jack.setState(CharacterState.WALKING);
                        }
                        SoundEffect.JACK_WALKING.playLoop();
                        gameState.jack.dir = Player.Direction.LEFT;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (currentPlayer == gameState.tim) {
                        if (gameState.tim.getState() == CharacterState.CLIMBING) {
                            gameState.tim.velocity.x = 5;
                        } else {
                            //System.out.println("Tim's y velocity " + gameState.tim.velocity.y);
                            gameState.tim.velocity.x = 7;
                            gameState.tim.setState(CharacterState.WALKING);
                        }
                        SoundEffect.TIM_WALKING.playLoop();
                        gameState.tim.dir = Player.Direction.RIGHT;
                    } else {
                        if (gameState.jack.isHooked()) {
                            gameState.jack.setState(CharacterState.FALLING);
                            gameState.jack.velocity = new Point2D.Float(15, 0);
                            gameState.jack.setMovementState(0);
                        } else if (!gameState.jack.isHooked() && !gameState.jack.isGrappling()) {
                            //System.out.println("right");
                            gameState.jack.incrementXVelocity(0.5f);
                            gameState.jack.setState(CharacterState.WALKING);
                        }
                        SoundEffect.JACK_WALKING.playLoop();
                        gameState.jack.dir = Player.Direction.RIGHT;
                    }
                    break;
                case KeyEvent.VK_E:
                    if (!gameState.jack.isHooked()) {
                        if (gameState.inactivePlayer() == gameState.tim && currentLevel.playTim) {
                            gameState.switchPlayer();
                            gameState.tim.resetXVelocity();
                            gameState.tim.resetYVelocity();
                            gameState.jack.resetXVelocity();
                            gameState.jack.resetYVelocity();
                        }  else if (gameState.inactivePlayer() == gameState.jack && currentLevel.playJack) {
                            gameState.switchPlayer();
                            gameState.tim.resetXVelocity();
                            gameState.tim.resetYVelocity();
                            gameState.jack.resetXVelocity();
                            gameState.jack.resetYVelocity();
                        }
                    }
                    break;
                case KeyEvent.VK_C:
                    if (currentLevel.id == 7) {
                        if (gameState.currentPlayer() == gameState.tim && getRegion(gameState.tim) == 40 && gameState.tim.getState() != CharacterState.CLIMBING) {
                            gameState.tim.setCarrying();
                            gameState.tim.resetXVelocity();
                            gameState.tim.resetYVelocity();
                        } else if (gameState.currentPlayer() == gameState.tim && gameState.tim.getState() == CharacterState.CLIMBING) {
                            gameState.tim.setFalling();
                        }
                    }
                    else {
                        if (gameState.currentPlayer() == gameState.tim && getRegion(gameState.tim) == 40 && gameState.tim.getState() != CharacterState.CLIMBING) {
                            gameState.tim.setState(CharacterState.CLIMBING);
                            gameState.tim.resetXVelocity();
                            gameState.tim.resetYVelocity();
                        } else if (gameState.currentPlayer() == gameState.tim && gameState.tim.getState() == CharacterState.CLIMBING) {
                            gameState.tim.setState(CharacterState.JUMPING);
                        }
                    }
                    break;
                case KeyEvent.VK_Q:
                    double dX = gameState.tim.getPosition().getX()-gameState.jack.getPosition().getX();
                    double dY = gameState.tim.getPosition().getY()-gameState.jack.getPosition().getY();
                    double distance = Math.sqrt(Math.pow(dX,2)+Math.pow(dY,2));
                    System.out.println("Distance from Jack to Tim" + distance);
                    if (gameState.currentPlayer() == gameState.jack && getRegion(gameState.jack) == 50) {
                        int regionIndex = getHookIndex(gameState.jack);
                        // gameState.jack.setPosition(currentLevel.getHookablePoint(regionIndex));
                        grapplePoint = currentLevel.getHookablePoint(regionIndex);
                        gameState.jack.grappleTo(currentLevel.getHookablePoint(regionIndex));
                        gameState.jack.setState(CharacterState.GRAPPLING);
                        System.out.println("Grapple to region " + regionIndex);
                        System.out.println("Grapple to location " + currentLevel.getHookablePoint(regionIndex).x + ", " + currentLevel.getHookablePoint(regionIndex).y);
                    }else if(gameState.currentPlayer() == gameState.jack && distance<400 && distance>50){
                        gameState.jack.grappleTo(gameState.tim.getMidpoint());
                        grapplePoint = gameState.tim.getMidpoint();
                        gameState.jack.setState(CharacterState.GRAPPLING);
                        System.out.println("Grapple to Tim");
                        System.out.println("Grapple to location " + gameState.tim.getPosition().x + ", " + gameState.tim.getPosition().y);
                    }
                    break;
                case 49: // 1
                    System.out.println("Loading Level 1...");
                    gameState.loadLevel(levels.get(0));
                    currentLevel = levels.get(0);
                    break;
                case 50: // 2
                    System.out.println("Loading Level 2...");
                    gameState.loadLevel(levels.get(1));
                    currentLevel = levels.get(1);
                    break;
                case 51: // 3
                    System.out.println("Loading Level 3...");
                    gameState.loadLevel(levels.get(2));
                    currentLevel = levels.get(2);

                    break;
                case 52: // 4
                    System.out.println("Loading Level 4...");
                    gameState.loadLevel(levels.get(3));
                    currentLevel = levels.get(3);
                    break;
                case 53: // 5
                    System.out.println("Loading Level 5...");
                    gameState.loadLevel(levels.get(4));
                    currentLevel = levels.get(4);
                    break;
                case 54: // 6
                    System.out.println("Loading Level 6...");
                    gameState.loadLevel(levels.get(5));
                    currentLevel = levels.get(5);
                    break;
                case 55: // 7
                    System.out.println("Loading Level 7...");
                    gameState.loadLevel(levels.get(6));
                    currentLevel = levels.get(6);
                    break;
                case 56: // 8
                    System.out.println("Loading Level 8...");
                    gameState.loadLevel(levels.get(7));
                    currentLevel = levels.get(7);
                    break;
            }
        }
    }

    public void handleKeyRelease(KeyEvent event) {
        int keyCode = event.getKeyCode();

        if (isPlayable) {
            Player currentPlayer = gameState.currentPlayer();

            switch (keyCode) {
                case KeyEvent.VK_UP:
                    if (gameState.tim.getState() == CharacterState.CLIMBING)
                        currentPlayer.resetYVelocity();
                    break;
                case KeyEvent.VK_DOWN:
                    currentPlayer.resetYVelocity();
                    break;
                case KeyEvent.VK_LEFT:
                    currentPlayer.resetXVelocity();
                    SoundEffect.TIM_WALKING.stop();
                    SoundEffect.JACK_WALKING.stop();
                    break;
                case KeyEvent.VK_RIGHT:
                    currentPlayer.resetXVelocity();
                    SoundEffect.TIM_WALKING.stop();
                    SoundEffect.JACK_WALKING.stop();
                    break;
            }
        }
        else{
            if (keyCode == KeyEvent.VK_SPACE){
                cutscenes.get(plotPoint).advance();
                if (cutscenes.get(plotPoint).ended){
                    if (plotPoint == 8) {
                        isOver = true;
                    } else {
                        isPlayable = !isPlayable;
                        currentLevel = levels.get(plotPoint);
                        gameState.loadLevel(currentLevel);

                    }
                }
            }
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

        //System.out.println(r + " " + b + " " + g);

        //BELOW PRINTS OUT IMAGE COORDS, USEFUL FOR HOOK POINTS
        System.out.println((xx+getActivePlayerX()-1920/2) + " " + (yy+getActivePlayerY()-1080/2));

        // check the region at the mouse position
        if ( (r == 10) && (g == 0) && (b == 0) ) {
            System.out.println("inside region");
        }
    }

    public void tick(){
        if (isPlayable) {
            Player currentPlayer = gameState.currentPlayer();
            Player inactivePlayer = gameState.inactivePlayer();


            //update Tim
            if (!rightCollision(gameState.tim) && !leftCollision(gameState.tim) && !topCollision(gameState.tim) && !bottomCollision(gameState.tim)) {
                gameState.tim.updatePosition();

                if (gameState.tim.getState() != CharacterState.CLIMBING && !isOnGround(gameState.tim)) {
                    if (currentLevel.playTim)
                        gameState.tim.incrementYVelocity(1);
                }

            } else if (!isCollidingY(gameState.tim)) {
                gameState.tim.resetXVelocity();
                gameState.tim.updatePosition();
            } else if (!isCollidingX(gameState.tim)) {
                gameState.tim.resetYVelocity();
                gameState.tim.updatePosition();
            } else if (topCollision(gameState.tim) || bottomCollision(gameState.tim)) {

                gameState.tim.resetYVelocity();
                if (gameState.tim.velocity.x == 0)
                    gameState.tim.setState(CharacterState.IDLE);

            }

            if (!canClimb() && gameState.tim.getState() == CharacterState.CLIMBING) {
                if (currentLevel.id == 7) {
                    gameState.tim.setFalling();
                }
                gameState.tim.setState(CharacterState.JUMPING);
            }

            // Handle Death scenario for Tim
            if (gameState.tim.velocity.y > 65 && rightCollision(gameState.tim) && !(areTimAndJackIntersecting())) {
                gameState.loadLevel(new Level(3));
            }

            // special case for final level
            if (currentLevel.id == 7 && gameState.tim.position.y > 2500) {
                gameState.loadLevel(levels.get(7));
                currentLevel = levels.get(7);
                System.out.println("restart");
            }


            //update Jack
            if (gameState.jack.isGrappling()) {
                gameState.jack.updatePosition();
            } else if (gameState.jack.isHooked()) {
                // do nothing
            } else if (!rightCollision(gameState.jack) && !leftCollision(gameState.jack) && !topCollision(gameState.jack) && !bottomCollision(gameState.jack)) {
                gameState.jack.updatePosition();
                if (!isOnGround(gameState.jack)) {
                    if (currentLevel.playJack)
                        gameState.jack.incrementYVelocity(1);
                }
            } else if (topCollision(gameState.jack) || bottomCollision(gameState.jack)) {
                if (gameState.jack.velocity.x == 0)
                    gameState.jack.setState(CharacterState.IDLE);
                gameState.jack.resetYVelocity();
            }

            if (getRegion(gameState.tim) == 30 && getRegion(gameState.jack) == 30 || isInsideEventRegion()) {
                System.out.println("🎈" + "End of level!111!!!!1!!!1!11");

                gameState.tim.resetXVelocity();
                gameState.tim.resetYVelocity();
                gameState.jack.resetXVelocity();
                gameState.jack.resetYVelocity();
                if (isPlayable)
                    ++plotPoint;
                isPlayable = !isPlayable;
            }

            //update inactive player
            inactivePlayer.resetXVelocity();

            // update character animations
            gameState.tim.updateImage();
            gameState.jack.updateImage();
        }
    }

    // in progress
    public boolean getCollisionStatus(Player player) {
        if (!rightCollision(player) && !leftCollision(player) && !topCollision(player) && !bottomCollision(player)) {
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
        int posY = (int) player.getNextPosition().y + player.height;
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

    public boolean isCollidingX(Player player){
        int posX = (int) player.getNextX();
        int posY = (int) player.getPosition().y;
        int width = player.width;
        int height = player.height;
        int color;

        // top collision check
        for (int i = posX; i < posX + width; ++i) {
            color = (gameState.getBitmap().getRGB(i, posY) >>> 16) & 0x000000FF;
            if (color == 10) {
                return true;
            }
        }

        // left collision check
        for (int j = posY; j < posY + height; ++j) {
            color = (gameState.getBitmap().getRGB(posX, j) >>> 16) & 0x000000FF;
            if (color == 10) {
                return true;
            }
        }

        // bottom collision check
        posY += player.height;
        for (int i = posX; i < posX + height; ++i) {
            color = (gameState.getBitmap().getRGB(i, posY) >>> 16) & 0x000000FF;
            if (color == 10) {
                return true;
            }
        }

        // right collision check
        posX = (int) player.getNextX() + player.width;
        posY = (int) player.getPosition().y;
        for (int j = posY; j < posY + height; ++j) {
            color = (gameState.getBitmap().getRGB(posX, j) >>> 16) & 0x000000FF;
            if (color == 10) {
                return true;
            }
        }
        return false;
    }

    public boolean isCollidingY(Player player){
        int posX = (int) player.position.x;
        int posY = (int) player.getNextY();
        int width = player.width;
        int height = player.height;
        int color;

        // top collision check
        for (int i = posX; i < posX + width; ++i) {
            color = (gameState.getBitmap().getRGB(i, posY) >>> 16) & 0x000000FF;
            if (color == 10) {
                return true;
            }
        }

        // left collision check
        for (int j = posY; j < posY + height; ++j) {
            color = (gameState.getBitmap().getRGB(posX, j) >>> 16) & 0x000000FF;
            if (color == 10) {
                return true;
            }
        }

        // bottom collision check
        posX = (int) player.position.x;
        posY = (int) player.getNextY() + player.height;
        for (int i = posX; i < posX + width; ++i) {
            color = (gameState.getBitmap().getRGB(i, posY) >>> 16) & 0x000000FF;
            if (color == 10) {
                return true;
            }
        }

        // right collision check
        posX = (int) player.position.x + player.width;
        posY = (int) player.getNextY();
        for (int j = posY; j < posY + height; ++j) {
            color = (gameState.getBitmap().getRGB(posX, j) >>> 16) & 0x000000FF;
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


    public boolean isPlayable(){
        return this.isPlayable;
    }

    public BufferedImage getCutSceneFrame(){
        return cutscenes.get(plotPoint).getCurrentFrame();
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

    public Player getTim() {
        return gameState.tim;
    }

    public Player getJack() {
        return gameState.jack;
    }

    public  Player getActivePlayer() {
        return gameState.currentPlayer();
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

    public GameState getGameState() {
        return gameState;
    }

    private boolean isOnGround(Player player) {
        int width = player.width;
        int height = 7;
        int posX = (int) player.getPosition().x;
        int posY = (int) player.getPosition().y + player.height;
        int color;

//        for (int i = 0; i < posX + width; ++i) {
//            for (int j = 0; j < posY + height; ++j) {
//                color = (gameState.getBitmap().getRGB(i, j) >>> 16) & 0x000000FF;
//                if (color == 10) {
//                    return true;
//                }
//            }
//        }
//
        // top collision check
        for (int i = posX; i < posX + width; ++i) {
            color = (gameState.getBitmap().getRGB(i, posY) >>> 16) & 0x000000FF;
            if (color == 10) {
                return true;
            }
        }

        // left collision check
        for (int j = posY; j < posY + height; ++j) {
            color = (gameState.getBitmap().getRGB(posX, j) >>> 16) & 0x000000FF;
            if (color == 10) {
                return true;
            }
        }

        // bottom collision check
        posY = (int) player.getPosition().y + player.height;
        for (int i = posX; i < posX + height; ++i) {
            color = (gameState.getBitmap().getRGB(i, posY) >>> 16) & 0x000000FF;
            if (color == 10) {
                return true;
            }
        }

        // right collision check
        posX = (int) player.getPosition().x + player.width;
        for (int j = posY; j < posY + height; ++j) {
            color = (gameState.getBitmap().getRGB(posX, j) >>> 16) & 0x000000FF;
            if (color == 10) {
                return true;
            }
        }
        return false;
    }

    public BufferedImage getBitmap() {
        return gameState.getBitmap();
    }

    public boolean renderTim() {
        return currentLevel.playTim;
    }

    public boolean renderJack() {
        return currentLevel.playJack;
    }

    private boolean canClimb() {
        int xx = (int) getActivePlayer().getMidpoint().x;
        int yy = (int) getActivePlayer().getMidpoint().y;

        return ((gameState.getBitmap().getRGB(xx, yy) >>> 16) & 0x000000FF) == 40;
    }

    private boolean isInsideEventRegion() {
        int xT = (int) gameState.tim.getMidpoint().x;
        int yT = (int) gameState.tim.getMidpoint().y;

        int xJ = (int) gameState.jack.getMidpoint().x;
        int yJ = (int) gameState.jack.getMidpoint().y;

        return ((gameState.getBitmap().getRGB(xT, yT) >>> 16) & 0x000000FF) == 30 &&
                ((gameState.getBitmap().getRGB(xJ, yJ) >>> 16) & 0x000000FF) == 30;
    }

    private void loadBGMusic() {
        if (currentLevel.id == 0) {
            SoundEffect.BGMUSIC0.playLoop();
        } else if (currentLevel.id == 1) {
            SoundEffect.BGMUSIC1.playLoop();
        } else if (currentLevel.id == 2) {
            SoundEffect.BGMUSIC2.playLoop();
        } else if (currentLevel.id == 3) {
            SoundEffect.BGMUSIC3.playLoop();
        } else if (currentLevel.id == 4) {
            SoundEffect.BGMUSIC4.playLoop();
        } else if (currentLevel.id == 5) {
            SoundEffect.BGMUSIC5.playLoop();
        } else if (currentLevel.id == 6) {
            SoundEffect.BGMUSIC6.playLoop();
        } else {
            SoundEffect.BGMUSIC7.playLoop();
        }
    }

    private void stopBGMusic() {
        if (currentLevel.id == 0) {
            SoundEffect.BGMUSIC0.stop();
        } else if (currentLevel.id == 1) {
            SoundEffect.BGMUSIC1.stop();
        } else if (currentLevel.id == 2) {
            SoundEffect.BGMUSIC2.stop();
        } else if (currentLevel.id == 3) {
            SoundEffect.BGMUSIC3.stop();
        } else if (currentLevel.id == 4) {
            SoundEffect.BGMUSIC4.stop();
        } else if (currentLevel.id == 5) {
            SoundEffect.BGMUSIC5.stop();
        } else if (currentLevel.id == 6) {
            SoundEffect.BGMUSIC6.stop();
        } else {
            SoundEffect.BGMUSIC7.stop();
        }
    }
}

