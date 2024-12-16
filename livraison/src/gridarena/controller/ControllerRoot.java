package gridarena.controller;

import gridarena.entity.hero.Hero;
import gridarena.model.*;
import gridarena.view.*;

import java.util.*;

/**
 * Représente les interactions entre les vues et le champ de bataille.
 *
 * @author Tom David
 * @version 1.0
 */
public class ControllerRoot {

    private Battlefield battlefield;
    private ControllerGame game;

    /**
     * Constructeur de la classe ControllerUI.
     * Initialise le champ de bataille, les interfaces graphiques des joueurs et l'interface graphique du jeu.
     *
     * @param players Un tableau de chaînes de caractères représentant les classes des joueurs.
     */
    public ControllerRoot(List<String> players) {
        
        this.battlefield = new Battlefield(players);
        this.game = new ControllerGame(this, this.battlefield);
        
        List<Hero> heros =  this.battlefield.getHeros();
        game.addPlayer(new PlayerGUI(new BattlefieldProxy(this.battlefield, heros.get(0)), heros.get(0), this, game, "J"+1));
        game.addPlayer(new PlayerGUI(new BattlefieldProxy(this.battlefield, heros.get(1)), heros.get(1), this, game, "J"+2));
        //gameGUI.addPlayer(new PlayerTerminalUI());
        
        this.demarrer();
    }

    /**
     * Démarre le thread de l'interface graphique du jeu.
     */
    private void demarrer() {
        new Thread(game).start();
    }
    
    /**
     * Supprime le bouclier du joueur précédent.
     * 
     * @param currentPlayer joueur actuel qui vient de finir son tour.
     */
    public void nextTurn(int currentPlayer) {
        List<Hero> heros = this.battlefield.getHeros();
        int previousPlayer = currentPlayer == 0 ? heros.size()-1 : currentPlayer-1;
        if (heros.get(previousPlayer).isImmune()) heros.get(previousPlayer).setImmune(false);
    }

    /**
     * Déplace un héros dans une direction spécifiée.
     *
     * @param h Le héros à déplacer.
     * @param direction La direction dans laquelle déplacer le héros.
     * @return true si le déplacement a réussi, false sinon.
     */
    public synchronized boolean move(Hero h, String direction) {
        boolean move = this.battlefield.moveHero(h, direction);
        return move;
    }

    /**
     * Fait tirer un héros dans une direction spécifiée.
     *
     * @param c Le héros qui tire.
     * @param direction La direction dans laquelle tirer.
     */
    public synchronized void shootPlayer(Hero c, String direction) {
        this.battlefield.shootHero(c, direction);
    }

    /**
     * Ajoute une mine dans une direction spécifiée.
     *
     * @param c Le héros qui place la mine.
     * @param direction La direction dans laquelle placer la mine.
     * @return true si la mine a été placée avec succès, false sinon.
     */
    public synchronized boolean addMine(Hero c, String direction) {
        boolean mine = this.battlefield.addMine(c, direction);
        return mine;
    }

    /**
     * Ajoute un baril dans une direction spécifiée.
     *
     * @param c Le héros qui place le baril.
     * @param direction La direction dans laquelle placer le baril.
     * @return true si le baril a été placé avec succès, false sinon.
     */
    public synchronized boolean addBarrel(Hero c, String direction) {
        boolean barrel = this.battlefield.addBarrel(c, direction);
        return barrel;
    }

    /**
     * Ajouter le bouclier à un héros.
     *
     * @param h Le héros qui reçoit le bouclier.
     * @return true si le bouclier a bien été activé sinon false.
     */
    public synchronized boolean addShield(Hero h) {
        boolean shield = this.battlefield.activateShield(h);
        return shield;
    }
    
    public synchronized void endGame(Map<Player, Boolean> playersStatus) {
        for (Map.Entry<Player, Boolean> entry : playersStatus.entrySet()) {
            entry.getKey().showEndGame(playersStatus);
        }
        this.game.setVisible(false);
    }
    
}