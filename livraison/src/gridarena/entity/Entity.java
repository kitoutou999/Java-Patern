package gridarena.entity;

import gridarena.utils.ObservableModel;

/**
 *
 * @author florian
 */
public abstract class Entity extends ObservableModel {
    
    protected int x;
    protected int y;
    protected final String emote;
    protected final boolean walkable;
    protected final String image;
    private static final String PATH_IMAGE = "images/";
    
    public Entity(int x, int y, String emote, String image, boolean walkable) {
        this.x = x;
        this.y = y;
        this.emote = emote;
        this.walkable = walkable;
        this.image = image;
    }

    public String getImageUrl() {
        return PATH_IMAGE + image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getEmote() {
        return emote;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Entity : {" + "x=" + x + ", y=" + y + "} -> ";
    }
    
}
