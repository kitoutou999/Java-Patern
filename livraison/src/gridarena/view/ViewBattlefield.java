package gridarena.view;

import gridarena.entity.Entity;
import gridarena.entity.explosive.*;
import gridarena.utils.ModelObserver;
import gridarena.entity.other.*;
import gridarena.model.AbstractBattlefield;

import javax.swing.*;
import java.awt.*;

public class ViewBattlefield extends JPanel implements ModelObserver {
    
    private AbstractBattlefield battlefield;
    private Entity[][] map;
    
    public ViewBattlefield(AbstractBattlefield battlefield) {
        super();
        this.battlefield = battlefield;
        this.battlefield.addObserver(this);
        this.setPreferredSize(new Dimension(200, 200));
    }
    
    public void displayMap(Graphics g, Entity[][] map) {

        if (map != null) {
            int cellWidth = getWidth() / map[0].length;
            int cellHeight = getHeight() / map.length;
            
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    int x = j * cellWidth;
                    int y = i * cellHeight;

                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, cellWidth, cellHeight);

                    if (map[i][j] == null) {
                        g.drawString("", x + cellWidth / 2, y + cellHeight / 2);
                    } else if (map[i][j] instanceof Wall) {
                        g.setColor(Color.GRAY);
                        g.fillRect(x+1, y+1, cellWidth, cellHeight);
                    } else {
                        String image_url = map[i][j].getImageUrl();
                        Image img = new ImageIcon(image_url).getImage();
                        if (map[i][j] instanceof Barrel) {
                            g.drawImage(img, x + cellWidth / 3, y + cellHeight / 10, 13 * 2, 26 * 2, null);
                        } else if (map[i][j] instanceof Mine) {
                            g.drawImage(img, x + cellWidth / 10, y + cellHeight / 10, 32*2, 32*2, null);
                        } else if (map[i][j] instanceof EnergyPill) {
                            g.drawImage(img, x + cellWidth / 10, y + cellHeight / 10, 32*2, 32*2, null);
                        } else {
                            g.drawImage(img, x + cellWidth / 3, y + cellHeight / 10, 13 * 2, 26 * 2, null);
                            // Note : à voir pour la mort.
                            //g.drawImage(new ImageIcon("images/rip.png").getImage(), x + cellWidth / 3, y + cellHeight / 10, 50 * 2, 40 * 2, null);
                        }
                    }
                }
            }
        }
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        this.displayMap(g, this.battlefield.getGrid());
    }
    
    @Override
    public void updatedModel(Object source) {
        this.repaint();
    }
    
}