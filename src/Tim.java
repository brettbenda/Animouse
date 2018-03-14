import java.awt.geom.Point2D;

public class Tim extends Player {

    public Tim(float x, float y) {
        this.position = new Point2D.Float(x, y);
        this.velocity = new Point2D.Float(0, 0);
        // ...
    }

    public Point2D.Float getNextPosition() {
        return new Point2D.Float(position.x + velocity.x, position.y + velocity.y);
    }
}
