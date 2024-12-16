package gridarena.entity.hero;

import gridarena.entity.Entity;
import gridarena.entity.explosive.*;
import gridarena.entity.other.EnergyPill;

/**
 * Représente le Hero que le joueur incarne.
 * 
 * @author florian
 * @since 1.0
 */
public class Hero extends Entity {

    protected final int healthMax; //vie max du joueur.
    protected int healthRemaining; //vie restante du joueur.
    protected final int ammoMax; //munitions max du joueur.
    protected int ammoRemaining; //munitions restantes du joueur.
    protected final int shieldMax; //nombre de boucliers max du joueur.
    protected int shieldRemaining; //nombre de boucliers restants du joueur.
    protected final int mineMax; //nombre de mines max du joueur.
    protected int mineRemaining; //nombre de mines restantes du joueur.
    protected final int barrelMax; //nombre de barrels max du joueur.
    protected int barrelRemaining; //nombre de barrels restantes du joueur.
    protected boolean immune; //état du bouclier.

    public Hero(int healthMax, int ammoMax, int shieldMax, int mineMax, int barrelMax, int x, int y, String image) {
        super(x, y, "🪖​", image, false);
        this.healthMax = healthMax;
        this.healthRemaining = healthMax;
        this.ammoMax = ammoMax;
        this.ammoRemaining = ammoMax;
        this.shieldMax = shieldMax;
        this.shieldRemaining = shieldMax;
        this.mineMax = mineMax;
        this.mineRemaining = mineMax;
        this.barrelMax = barrelMax;
        this.barrelRemaining = barrelMax;
        this.immune = false;
    }


    public int getHealthMax() {
        return this.healthMax;
    }

    public int getHealthRemaining() {
        return this.healthRemaining;
    }

    public int getAmmoMax() {
        return this.ammoMax;
    }

    public int getAmmoRemaining() {
        return this.ammoRemaining;
    }

    public int getShieldMax() {
        return this.shieldMax;
    }

    public int getShieldRemaining() {
        return this.shieldRemaining;
    }

    public int getMineMax() {
        return this.mineMax;
    }

    public int getMineRemaining() {
        return this.mineRemaining;
    }

    public int getBarrelMax() {
        return this.barrelMax;
    }

    public int getBarrelRemaining() {
        return this.barrelRemaining;
    }
    
    public boolean isImmune() {
        return this.immune;
    }

    public void setHealthRemaining(int healthRemaining) {
        this.healthRemaining = healthRemaining;
        this.fireChange();
    }

    public void setAmmoRemaining(int ammoRemaining) {
        this.ammoRemaining = ammoRemaining;
        this.fireChange();
    }

    public void setShieldRemaining(int shieldRemaining) {
        this.shieldRemaining = shieldRemaining;
        this.fireChange();
    }

    public void setMineRemaining(int mineRemaining) {
        this.mineRemaining = mineRemaining;
        this.fireChange();
    }

    public void setBarrelRemaining(int barrelRemaining) {
        this.barrelRemaining = barrelRemaining;
        this.fireChange();
    }
    
    public void setImmune(boolean state) {
        this.immune = state;
        this.fireChange();
    }

    /**
     * 
     * @return true si le joueur est en vie sinon false.
     */
    public boolean isAlive() {
        return this.healthRemaining > 0;
    }
    
    /**
     * 
     * @return true si le joueur a encore des munitions sinon false.
     */
    public boolean hasAmmosLeft() {
        return this.ammoRemaining > 0;
    }
    
    /**
     * 
     * @return true si le joueur a encore des boucliers sinon false.
     */
    public boolean hasShieldsLeft() {
        return this.shieldRemaining > 0;
    }
    
    /**
     * 
     * @return true si le joueur a encore des mines sinon false.
     */
    public boolean hasMinesLeft() {
        return this.mineRemaining > 0;
    }
    
    /**
     * 
     * @return true si le joueur a encore des barrils sinon false.
     */
    public boolean hasBarrelsLeft() {
        return this.barrelRemaining > 0;
    }
       
    /**
     * Utiliser une pastille d'énergie ramassé sur la grille.
     * 
     * @param e pastille d'énergie.
     */
    public void pickUpEnergyPill(EnergyPill e){
        int pv = this.healthRemaining+e.getEnergy();
        if (this.healthMax > pv) {
            this.setHealthRemaining(pv);
        } else {
            this.setHealthRemaining(this.healthMax);
        }
    }
    
    /**
     * 
     * @param x position (ligne) du joueur sur la grille.
     * @param y position (colonne) du joueur sur la grille.
     * @return une mine si le joueur en a en stock sinon null.
     */
    public Mine deployMine(int x, int y) {
        if (this.getMineRemaining() > 0) {
            this.setMineRemaining(this.mineRemaining-1);
            return new Mine(x, y, this);
        }
        return null;
    }
    
    /**
     * 
     * @param x position (ligne) du joueur sur la grille.
     * @param y position (colonne) du joueur sur la grille.
     * @return un barril si le joueur en a en stock sinon null.
     */
    public Barrel deployBarrel(int x, int y) {
        if (this.getBarrelRemaining() > 0) {
            this.setBarrelRemaining(this.barrelRemaining-1);
            return new Barrel(x, y, this);
        }
        return null;
    }
    
    /**
     * Tirer sur un joueur.
     * 
     * @param h hero cible.
     */
    public void shootHero(Hero h) {
        h.setHealthRemaining(h.getHealthRemaining()-5);
        this.ammoRemaining = this.ammoRemaining-1;
    }
       
    /**
     * Création d'un nouveau joueur.
     * 
     * @param type assassin, warrior ou mastodonte.
     * @param x position (ligne) du joueur sur la grille.
     * @param y position (colonne) du joueur sur la grille.
     * @return une instance d'un hero.
     */
    public static Hero createHero(String type, int x, int y) {
        switch(type) {
            case "assassin":
                return new Hero(40, 25, 2, 4, 4, x, y, "Blue.png");
            case "warrior":
                return new Hero(50, 25, 3, 3, 3, x, y, "Vert.png");
            case "mastodonte":
                return new Hero(60, 25, 4, 2, 2, x, y, "Violet.png");
            case "testeur":
                return new Hero(5, 25, 4, 2, 2, x, y, "Vert.png");
            default:
                //par défaut c'est le warrior.
                return new Hero(50, 25, 3, 3, 3, x, y, "Vert.png");
        }
    }

    @Override
    public String toString() {
        return "Hero : {" + "healthMax=" + healthMax + ", healthRemaining=" + healthRemaining + ", ammoMax=" + ammoMax + ", ammoRemaining=" + ammoRemaining + ", shieldMax=" + shieldMax + ", shieldRemaining=" + shieldRemaining + ", mineMax=" + mineMax + ", mineRemaining=" + mineRemaining + ", barrelMax=" + barrelMax + ", barrelRemaining=" + barrelRemaining + '}';
    }

}
