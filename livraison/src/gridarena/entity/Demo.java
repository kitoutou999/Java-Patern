package gridarena.entity;

import gridarena.entity.explosive.Mine;
import gridarena.entity.other.Wall;
import gridarena.entity.hero.Hero;

/**
 *
 * @author florian
 */
public class Demo {
    
    public static void main(String[] arg) {
        
        int x = 0;
        int y = 0;
        Hero c = Hero.createHero("assassin", x, y);
        Entity mine = new Mine(x, y, c);
        Entity wall = new Wall(x, y);
        System.out.println(mine);
        System.out.println(wall);
        System.out.println(c);
        
    }
    
}
