package gridarena.entity.explosive;

import gridarena.entity.hero.Hero;

/**
 *
 * @author Florian Pépin
 * @version beta
 */
public class Barrel extends Explosive {
   
    public Barrel(int x, int y, Hero belongsTo) {
        super(x, y, belongsTo, "⛽", 1, 10, false, "barrel.png");
    }
    
    @Override
    public String toString() {
        return super.toString() + "Barrel";
    }
    
}
