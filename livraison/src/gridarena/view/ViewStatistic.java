package gridarena.view;

import gridarena.entity.hero.Hero;
import gridarena.utils.ModelObserver;

import javax.swing.*;
import java.awt.*;

public class ViewStatistic extends JPanel implements ModelObserver {
    
    private Hero hero;
    private JLabel health;
    private JLabel myTurn;
    
    public ViewStatistic(Hero hero, String name) {
        super();
        this.hero = hero;
        this.hero.addObserver(this);
      
        this.setPreferredSize(new Dimension(200, 100));
        this.myTurn = new JLabel("");
        JLabel label1 = new JLabel("Joueur : " + name);
        this.add(label1);
        this.health = new JLabel(" | Vie : " + this.hero.getHealthRemaining()+"/"+this.hero.getHealthMax());
        this.add(this.health);
        String url = this.hero.getImageUrl();
        JLabel label2 = new JLabel(new ImageIcon(url));
        this.add(label2);

    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.LIGHT_GRAY);
        this.health.setText(" | Vie : " + this.hero.getHealthRemaining()+"/"+this.hero.getHealthMax());
    }
    
    @Override
    public void updatedModel(Object source) {
        this.repaint();
    }

}