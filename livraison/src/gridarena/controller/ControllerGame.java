package gridarena.controller;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

import gridarena.model.*;
import gridarena.view.*;

public class ControllerGame extends JFrame implements Runnable {
    
    private ControllerRoot root;
    private JLabel label;
    private List<Player> players;
    private HashMap<Player, Boolean> playersStatus;
    private int playersCounter;
    public int currentPlayer;

    public ControllerGame(ControllerRoot root, AbstractBattlefield battlefield) {
        super("Interface Développeur");
        this.root = root;
        this.players = new ArrayList<>();
        this.playersStatus = new HashMap<>();
        this.playersCounter = 0;
        this.currentPlayer = 0;
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.setLayout(new BorderLayout());
        
        label = new JLabel("Jeu non démarré");
        this.add(label, BorderLayout.PAGE_START);
        
        ViewBattlefield viewBattlefield = new ViewBattlefield(battlefield);
        this.add(viewBattlefield, BorderLayout.CENTER);
       
        this.setVisible(true);
        
    }

    public void addPlayer(Player p) {
        this.players.add(p);
        this.playersStatus.put(p, true);
        this.playersCounter ++;
    }

    public void removePlayer(Player p) {
        this.players.remove(p);
        this.playersStatus.put(p, false);
        this.playersCounter --;
    }

    public void playerAction(String action) {
        this.label.setText(action);
    }

    public synchronized void run() {
        while (this.playersCounter > 1) {
            this.label.setText("Joueur en cours = "+this.players.get(this.currentPlayer).getName());
            this.players.get(this.currentPlayer).takeMyTurn();
            this.root.nextTurn(this.currentPlayer);
            this.currentPlayer = (this.currentPlayer + 1) % this.players.size();
        }
        this.root.endGame(this.playersStatus);
    }
    
}
