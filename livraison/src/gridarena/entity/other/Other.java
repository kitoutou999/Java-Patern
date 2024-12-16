package gridarena.entity.other;

import gridarena.entity.Entity;

/**
 *
 * @author florian
 */
public abstract class Other extends Entity {
    
    private final boolean destructible; //true si l'élément est destructible sinon false.

    private static String DEFAULT_IMAGE;
    
    public Other(boolean destructible, int x, int y, String emote, boolean walkable, String image) {
        super(x, y, emote, image,walkable);
        this.destructible = destructible;
        this.DEFAULT_IMAGE = image;
    }
    
    public boolean isDestructible() {
        return destructible;
    }

    @Override
    public String toString() {
        return super.toString() + "Other : {" + "destructible=" + destructible + "} -> ";
    }
    
}
