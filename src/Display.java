import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
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

    public void handleKeyPress(KeyEvent event) {
        world.handleKeyPress(event);
    }

    public void handleKeyRelease(KeyEvent event) {
        world.handleKeyRelease(event);
    }

    // handles mouse input or passes it to the world
    public void handleMouseInput(MouseEvent event) {
        world.handleMouseInput(event);
    }

    // updates the world data
    public void tick() {
        world.tick();
    }

    // renders the world data
    public void render() {
        //set background (i.e. area not draw over with an image) to black
        BufferedImage buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bufferG2D = buffer.createGraphics();
        bufferG2D.setColor(Color.BLACK);
        bufferG2D.fillRect(0,0,WIDTH,HEIGHT);
        bufferG2D.setColor(new Color(0xc9c8bb));
        bufferG2D.setStroke(new BasicStroke(5));

        if (world.isPlayable() && world.getPlotPoint()!=7) {
            //draw background offset by location of the active player
            bufferG2D.drawImage(world.getBackground(), 0 - (int) world.getActivePlayerX() + WIDTH / 2, 0, null);

            //draw grapple reticle
            if(world.isTimInGrappleZone()){
                Point2D.Float point = world.getGrapplePoint();
                BufferedImage crosshair = world.getJack().crosshair;
                bufferG2D.drawImage(crosshair, (int)point.x- (int) world.getActivePlayerX() + WIDTH / 2 - crosshair.getWidth()/2, (int)point.y - crosshair.getHeight()/2,null);
            }

            //draw tail if jack is grappling
            if(world.getGameState().jack.getState()==CharacterState.GRAPPLING && world.getGameState().currentPlayer()!= world.getGameState().tim) {
                if (world.getGameState().jack.dir == Player.Direction.LEFT) {
                    bufferG2D.drawLine(WIDTH / 2 + world.getGameState().jack.width, (int)world.getJackY() + 3*world.getGameState().jack.height/4, (int) world.grapplePoint.getX() - (int) world.getActivePlayerX() + WIDTH / 2, (int) world.grapplePoint.getY());
                }else{
                    bufferG2D.drawLine(WIDTH / 2, (int)world.getJackY() + 3*world.getGameState().jack.height/4, (int) world.grapplePoint.getX() - (int) world.getActivePlayerX() + WIDTH / 2, (int) world.grapplePoint.getY());
                }
            }

            // draw players
            if (world.getTim() == world.getActivePlayer()) {
                if (world.renderTim())
                    bufferG2D.drawImage(world.getTimImage(), WIDTH / 2, (int)world.getTimY(), null);
            } else {
                if (world.renderTim())
                    bufferG2D.drawImage(world.getTimImage(), (int) world.getInactivePlayerX() - (int) world.getActivePlayerX() + WIDTH / 2, (int) world.getInactivePlayerY(), null);
            }


            if (world.getJack() == world.getActivePlayer()) {
                if (world.renderJack())
                    bufferG2D.drawImage(world.getJackImage(), WIDTH / 2, (int)world.getJackY(), null);
            } else {
                if (world.renderJack())
                    bufferG2D.drawImage(world.getJackImage(), (int) world.getInactivePlayerX() - (int) world.getActivePlayerX() + WIDTH / 2, (int) world.getInactivePlayerY(), null);
            }
        }else if (world.isPlayable() && world.getPlotPoint()==7) {
            //draw background offset by location of the active player
            bufferG2D.drawImage(world.getBackground(), 0 - (int) world.getActivePlayerX() + WIDTH / 2, 0 - (int) world.getActivePlayerY() + HEIGHT / 2, null);

            //draw grapple crosshair
            if(world.isTimInGrappleZone()){
                Point2D.Float point = world.getGrapplePoint();
                BufferedImage crosshair = world.getJack().crosshair;
                bufferG2D.drawImage(crosshair, (int)point.x- (int) world.getActivePlayerX() + WIDTH / 2 - crosshair.getWidth()/2, (int)point.y- (int) world.getActivePlayerY() + HEIGHT / 2 + crosshair.getHeight()/2,null);
            }

            //draw tail if jack is grappling
            if(world.getGameState().jack.getState()==CharacterState.GRAPPLING && world.getGameState().currentPlayer()!= world.getGameState().tim) {
                if (world.getGameState().jack.dir == Player.Direction.LEFT) {
                    bufferG2D.drawLine(WIDTH / 2 + world.getGameState().jack.width, HEIGHT / 2 + 3*world.getGameState().jack.height/4, (int) world.grapplePoint.getX() - (int) world.getActivePlayerX() + WIDTH / 2, (int) world.grapplePoint.getY() - (int) world.getActivePlayerY() + HEIGHT / 2);
                }else{
                    bufferG2D.drawLine(WIDTH / 2, HEIGHT / 2 + 3*world.getGameState().jack.height/4, (int) world.grapplePoint.getX() - (int) world.getActivePlayerX() + WIDTH / 2, (int) world.grapplePoint.getY() - (int) world.getActivePlayerY() + HEIGHT / 2);
                }
            }

            // draw players
            if (world.getTim() == world.getActivePlayer()) {
                if (world.renderTim())
                    bufferG2D.drawImage(world.getTimImage(), WIDTH / 2, HEIGHT / 2, null);
            } else {
                if (world.renderTim())
                    bufferG2D.drawImage(world.getTimImage(), (int) world.getInactivePlayerX() - (int) world.getActivePlayerX() + WIDTH / 2, (int) world.getInactivePlayerY() - (int) world.getActivePlayerY() + HEIGHT / 2, null);
            }


            if (world.getJack() == world.getActivePlayer()) {
                if (world.renderJack())
                    bufferG2D.drawImage(world.getJackImage(), WIDTH / 2, HEIGHT / 2, null);
            } else {
                if (world.renderJack())
                    bufferG2D.drawImage(world.getJackImage(), (int) world.getInactivePlayerX() - (int) world.getActivePlayerX() + WIDTH / 2, (int) world.getInactivePlayerY() - (int) world.getActivePlayerY() + HEIGHT / 2, null);
            }
        }
        else{
            bufferG2D.drawImage(world.getCutSceneFrame(), 0, 0, null);
        }
        // draw buffer onto display
        if (world.isOver) {
            System.exit(0);
        } else {
            g2d.drawImage(buffer, 0, 0, null);
        }

        repaint();
    }

    // handles the actual rendering logic
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(screen, 0, 0, this);
    }
}
