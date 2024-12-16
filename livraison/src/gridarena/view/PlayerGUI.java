package gridarena.view;

import gridarena.controller.*;
import gridarena.entity.hero.Hero;
import gridarena.model.AbstractBattlefield;
import gridarena.utils.ModelObserver;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Représente l'interface utilisateur d'un joueur via une interface graphique Swing.
 * 
 * @author Tom David
 * @version 1.0
 */
public class PlayerGUI extends JFrame implements Player, ModelObserver {

    private Hero hero;
    private ControllerRoot root;
    private ControllerGame game;
    private ControllerAction controllerAction;
    private String name;
    private ViewBattlefield viewBattleField;
    private ViewStatistic viewStatistic;
    public boolean isMyTurn;

    public PlayerGUI(AbstractBattlefield battlefield, Hero hero, ControllerRoot root, ControllerGame game, String name) {
        super("Joueur : " + name);
        this.hero = hero;
        this.hero.addObserver(this);
        this.root = root;
        this.game = game;
        this.name = name;
        this.isMyTurn = false;
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(new BorderLayout());
        
        this.viewBattleField = new ViewBattlefield(battlefield);
        this.add(this.viewBattleField, BorderLayout.CENTER);

        this.viewStatistic = new ViewStatistic(this.hero, this.name);
        this.add(this.viewStatistic, BorderLayout.NORTH);

        this.controllerAction = new ControllerAction(root, this.game, hero, this);
        this.add(this.controllerAction, BorderLayout.WEST);

        this.setVisible(true);
    }

    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public void showEndGame(Map<Player, Boolean> playersStatus) {
        this.getContentPane().removeAll();
        this.add(new ViewEndGame(playersStatus));
        this.revalidate();
        this.repaint();
    }
    
    @Override
    public void takeMyTurn() {
        this.isMyTurn = true;
        synchronized (this.game) {
            try {
                this.game.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void updatedModel(Object source) {
        if(!this.hero.isAlive()) {
            this.game.removePlayer(this);
        }
    }

}