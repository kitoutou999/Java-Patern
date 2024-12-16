package gridarena.view;

import java.util.Map;

/**
 * Représente le joueur qui joue au jeu.
 * 
 * @author florian
 * @version 1.0
 */
public interface Player {
    
    /**
     * Obtenir le nom du joueur.
     * 
     * @return le nom du joueur.
     */
    String getName();
    
    /**
     * Afficher la fin du jeu.
     * 
     * @param playersStatus contient les joueurs et leur status (mort/vivant).
     */
    void showEndGame(Map<Player, Boolean> playersStatus);
    
    /**
     * C'est le tour du joueur.
     */
    void takeMyTurn();
    
}
