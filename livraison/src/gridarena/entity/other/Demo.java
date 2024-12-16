package gridarena.entity.other;

/**
 *
 * @author florian
 */
public class Demo {
    
    public static void main(String[] args) {
        
        int x = 0;
        int y = 1;
        Other w = new Wall(x, y);
        Other e = new EnergyPill(x, y);
        System.out.println(w);
        System.out.println(e);
        
    }
    
}
