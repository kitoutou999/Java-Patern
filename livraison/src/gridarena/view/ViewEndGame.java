package gridarena.view;

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * Représente la vue de la fin du jeu.
 * 
 * @author Tom David
 * @version 1.0
 */
public class ViewEndGame extends JPanel {
    
    public ViewEndGame(Map<Player, Boolean> playersStatus) {
        super();
        this.setLayout(new BorderLayout());
        String[] columnNames = {"Joueur", "Statut"};
        String[][] data = new String[playersStatus.size()][columnNames.length];
        int i = 0;
        for (Map.Entry<Player, Boolean> entry : playersStatus.entrySet()) {
            data[i][0] = entry.getKey().getName();
            data[i][1] = entry.getValue() ? "Winner" : "Loser";
            i++;
        }
        JTable table = new JTable(data, columnNames);
        JLabel label = new JLabel("TABLEAU DES SCORES");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.TOP);
        JButton buttonQuit = new JButton("Rejouer");
        buttonQuit.setHorizontalAlignment(SwingConstants.CENTER);
        buttonQuit.setVerticalAlignment(SwingConstants.BOTTOM);
        this.add(label, BorderLayout.NORTH);
        this.add(new JScrollPane(table), BorderLayout.CENTER);
        this.add(buttonQuit, BorderLayout.SOUTH);
    }
    
}
