package gridarena.entity.explosive;

import gridarena.entity.Entity;
import gridarena.entity.hero.Hero;

/**
 *
 * @author Florian Pépin
 */
public abstract class Explosive extends Entity {
    
    protected Hero belongsTo;
    protected int explosionRadius; //rayon d'explosion.
    protected int damages; //dommages causés par l'explosion.
    private static String DEFAULT_IMAGE;

    public Explosive(int x, int y, Hero belongsTo, String emote, int explosionRadius, int damages, boolean walkable, String image) {
        super(x, y, emote,image, walkable);
        this.belongsTo = belongsTo;
        this.explosionRadius = explosionRadius;
        this.damages = damages;
        this.DEFAULT_IMAGE = image;
    }

    public int getExplosionRadius() {
        return this.explosionRadius;
    }

    public int getDamages() {
        return this.damages;
    }
    
    public Hero getBelongsTo() {
        return this.belongsTo;
    }

    @Override
    public String toString() {
        return "Explosive : {" + "explosionRadius=" + explosionRadius + ", damage=" + damages + "} -> ";
    }
    
}
