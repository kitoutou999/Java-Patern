package gridarena.model;

import gridarena.entity.Entity;
import gridarena.utils.ObservableModel;

/**
 *
 * @author Florian Pépin
 * @version 1.0
*/
public abstract class AbstractBattlefield extends ObservableModel {
    
    public abstract Entity[][] getGrid();
    
}
