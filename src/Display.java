import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
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
        this.world = new World(this);

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

        // add mouselistener
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                world.handleMouseInput(e);
            }
        });
    }

    // handles key input or passes it to the world
    public void handleKeyInput(KeyEvent event) {
        System.out.println("key event handled by display");
    }

    // handles mouse input or passes it to the world
    public void handleMouseInput(MouseEvent event) {
        world.handleMouseInput(event);
    }

    // updates the world data
    public void tick() {
        // update world
    }

    // renders the world data
    public void render() {
        // draw background
        g2d.drawImage(world.getBackground(), 0, 0, null);

        // draw players
        g2d.drawImage(world.getTimImage(), (int) world.getTimX(), (int) world.getTimY(), null);

        repaint();
    }

    // handles the actual rendering logic
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(screen, 0, 0, this);
    }
}
