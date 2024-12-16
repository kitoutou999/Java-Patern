package gridarena.entity.hero;

/**
 *
 * @author florian
 */
public class Demo {
    
    /**
     * Demonstration des trois types de joueurs.
     * 
     * @param args
     */
    public static void main(String[] args) {

        int x = 0;
        int y = 1;
        Hero w = Hero.createHero("warrior", x, y);
        Hero a = Hero.createHero("assassin", x, y);
        Hero m = Hero.createHero("mastodonte", x, y);
        System.out.println(w);
        System.out.println(a);
        System.out.println(m);
        
    }
}
