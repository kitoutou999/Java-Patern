package gridarena.utils;

import java.util.*;

public abstract class ObservableModel {

    protected List<ModelObserver> observers;
    
    public ObservableModel() {
        this.observers = new ArrayList<>();
    }
    
    public void addObserver(ModelObserver m) {
        this.observers.add(m);
    }
    
    public void removeObserver(ModelObserver m) {
        this.observers.remove(m);
    }
    
    protected void fireChange() {
        for (ModelObserver m : this.observers) {
            m.updatedModel(this);
        }
    }

}