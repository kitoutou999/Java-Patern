package gridarena.view;

import gridarena.controller.*;
import java.util.Map;

/**
 * Représente l'interface utilisateur d'un joueur via le terminal.
 * 
 * @author Emilien Huron
 * @version 1.0
 */
public class PlayerTerminalUI implements Player {
    
    private ControllerGame game;
    private String name;
    
    public PlayerTerminalUI(ControllerGame game, String name) {
        this.game = game;
        this.name = name;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public void showEndGame(Map<Player, Boolean> playersStatus) {
        //à compléter
    }
    
    @Override
    public void takeMyTurn() {
        //à compléter
    }
    
}
