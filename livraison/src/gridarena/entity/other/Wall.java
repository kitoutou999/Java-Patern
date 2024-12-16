package gridarena.entity.other;

/**
 *
 * @author florian
 */
public class Wall extends Other {
    
    public Wall(int x, int y) {
        super(false, x, y, "️█​", false, "wall.png");
    }

    @Override
    public String toString() {
        return super.toString() + "Wall";
    }
    
}
