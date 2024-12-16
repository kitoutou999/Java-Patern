package gridarena.entity.explosive;

import gridarena.entity.hero.Hero;

/**
 *
 * @author Florian Pépin
 */
public class Mine extends Explosive {
    
    public Mine(int x, int y, Hero belongsTo) {
        super(x, y, belongsTo, "☢️​", 1, 10, true, "mine.png");
    }

    @Override
    public String toString() {
        return super.toString() + "Mine";
    }
    
}
