package gridarena.model;

import gridarena.entity.Entity;
import gridarena.entity.explosive.Mine;
import gridarena.entity.hero.Hero;
import gridarena.utils.*;

/**
 * Représente le proxy permettant de filtrer la grille d'un joueur.
 * 
 * @author Florian Pépin
 * @version 1.0
 */
public class BattlefieldProxy extends AbstractBattlefield implements ModelObserver {

    private Battlefield battlefield;
    private Hero hero;
    
    public BattlefieldProxy(Battlefield battlefield, Hero hero) {
        super();
        this.battlefield = battlefield;
        this.battlefield.addObserver(this);
        this.hero = hero;
    }
    
    public Entity[][] filteredGrid(Hero hero, Entity[][] grid) {
        Entity[][] originalGrid = grid;
        int size = grid.length;
        grid = new Entity[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (originalGrid[i][j] instanceof Mine) {
                    Mine e = (Mine) originalGrid[i][j];
                    if (e.getBelongsTo() == hero) {
                        grid[i][j] = e;
                    } else {
                        grid[i][j] = null;
                    }
                } else {
                    grid[i][j] = originalGrid[i][j];
                }
            }
        }
        return grid;
    }

    @Override
    public Entity[][] getGrid() {
        return this.filteredGrid(this.hero, this.battlefield.getGrid());
    }

    @Override
    public void updatedModel(Object source) {
        this.fireChange();
    }
    
}
