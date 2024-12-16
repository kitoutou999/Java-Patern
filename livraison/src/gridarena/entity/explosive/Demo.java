package gridarena.entity.explosive;

import gridarena.entity.hero.Hero;

/**
 *
 * @author florian
 */
public class Demo {
    
    /**
     * Demonstration des deux types d'explosifs.
     * @param arg 
     */
    public static void main(String[] arg) {
        
        int x = 0;
        int y = 1;
        Hero c = Hero.createHero("assassin", x, y);
        Explosive m = new Mine(x, y, c);
        Explosive b = new Barrel(x, y, c);
        System.out.println(m);
        System.out.println(b);
        
    }
    
}
