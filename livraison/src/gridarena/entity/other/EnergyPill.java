package gridarena.entity.other;

/**
 *
 * @author florian
 */
public class EnergyPill extends Other {
    
    private final int energy;
    
    public EnergyPill(int x, int y) {
        super(true, x, y, "❤️​", true, "health.png");
        this.energy = 5;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public String toString() {
        return super.toString() + "EnergyPill";
    }
    
}
