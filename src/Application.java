import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;

public class Application extends JFrame implements KeyListener {
    // Application Variables
    private boolean inGame;
    private static final int TICKS_PER_SECOND = 60;
    private static final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;

    // Display Objects
    private Display display;

    // Dimensions
    private int WIDTH;
    private int HEIGHT;

    public Application(int width, int height) {
        this.setTitle("Animouse");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        // create key listener
        addKeyListener(this);

        // set the application as the focus
        setFocusable(true);

        // save dimensions
        this.WIDTH = width;
        this.HEIGHT = height;

        // create display
        this.display = new Display(WIDTH, HEIGHT, this);
        this.pack();
        this.getContentPane().add(display);
        this.validate();

        // set in game to true
        this.inGame = true;

        // enter gameloop
        runGameLoop();
    }

    // gameloop
    private void gameLoop() {
        double next_game_tick = System.currentTimeMillis();
        while (true) {
            while (System.currentTimeMillis() > next_game_tick) {
                display.tick();
                display.render();
                next_game_tick += SKIP_TICKS;
            }
        }
    }

    // runs gameloop on a new thread
    private void runGameLoop() {
        Thread gameLoopThread = new Thread()
        {
            public void run()
            {
                gameLoop();
            }
        };
        gameLoopThread.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // ...
    }

    @Override
    public void keyPressed(KeyEvent e) {
        display.handleKeyPress(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        display.handleKeyRelease(e);
    }
}
