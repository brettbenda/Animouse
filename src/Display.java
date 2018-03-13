import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.security.Key;

public class Display extends JPanel{
    // Application Objects
    private Application application;
    private World world;

    // Display Objects
    private BufferedImage screen;
    private Graphics2D g2d;

    // Dimensions
    private int HEIGHT;
    private int WIDTH;

    // TEST VARIABLE
    private int COLOR = 0;

    // constructor
    public Display(int width, int height, Application application) {
        super(true);

        // load world data
        this.world = new World();

        // save a reference to Application object
        this.application = application;

        // save display dimensions
        this.WIDTH = width;
        this.HEIGHT = height;

        // create image that the application renders onto
        this.screen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

        // create graphics2D object
        this.g2d = screen.createGraphics();

        // set display dimensions
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
    }

    // handles key input or passes it to the world
    public void handleKeyInput(KeyEvent event) {
        System.out.println("key event handled by display");
        // --- TEST ---
        if (event.getKeyChar() == 'c') {
            COLOR += 10 % 255;
        }
        // --- TEST ---
    }

    // handles mouse input or passes it to the world
    public void handleMouseInput(MouseEvent event) {
        System.out.println("mouse event handled by display");
    }

    // updates the world data
    public void tick() {
        // update world
    }

    // renders the world data
    public void render() {
        // render world
        g2d.setColor(new Color(COLOR));
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        repaint();
    }

    // handles the actual rendering logic
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(screen, 0, 0, this);
    }
}