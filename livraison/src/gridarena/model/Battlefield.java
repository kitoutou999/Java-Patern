package gridarena.model;

import gridarena.entity.Entity;
import gridarena.entity.hero.Hero;
import gridarena.entity.explosive.*;
import gridarena.entity.other.*;
import gridarena.utils.ObservableModel;

import java.util.*;

/**
 * Représente un champs de bataille.
 *
 * @authors Emilien Huron, Florian Pépin
 * @version 2.0
 * @see ObservableModel
 */
public class Battlefield extends AbstractBattlefield {
    
    private List<Hero> heros;
    private List<Wall> walls;
    private List<EnergyPill> energyPills;
    private List<Mine> mines;
    private List<Barrel> barrels;
    private Entity[][] grid;
    private int size;
    private BattlefieldProxy viewProxy;
    
    /**
     * Construction d'un nouveau champs de bataille.
     * 
     * @param classes de heros que les joueurs ont choisi.
     */
    public Battlefield(List<String> classes) {
        super();
        this.heros = new ArrayList<>();
        this.walls = new ArrayList<>();
        this.energyPills = new ArrayList<>();
        this.mines = new ArrayList<>();
        this.barrels = new ArrayList<>();
        this.grid = RandomGrid.generateFilledGrid(classes, this.heros, this.walls, this.energyPills);
        this.size = this.grid.length;
    }
    
    public List<Hero> getHeros() {
        return this.heros;
    }
    
    @Override
    public Entity[][] getGrid() {
        return this.grid;
    }
    
    public int getSize() {
        return this.size;
    }

    /**
     * Ajoute une mine dans la grille.
     *
     * @param h le hero qui dépose la mine.
     * @param direction dans laquelle le joueur veut déployer une mine.
     * @return true si la mine a été posé sinon false.
     */
    public boolean addMine(Hero h, String direction) {
        int[] pos = this.wichDirection(direction);
        int x = h.getX()+pos[0];
        int y = h.getY()+pos[1];
        if(this.isPosition(x, y)) {
            Entity e = this.grid[x][y];
            if (e == null) {
                Mine m = h.deployMine(x, y);
                this.grid[x][y] = m;
                this.mines.add(m);
                this.fireChange();
                return true;
            } else if (e instanceof Explosive) {
                this.detonate((Explosive) e);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Ajoute une bombe dans la grille.
     *
     * @param h le héro qui dépose la bomb.
     * @param direction dans laquelle le joueur veut déployer une bombe.
     * @return true si le barrel est posé sinon false.
     */
    public boolean addBarrel(Hero h, String direction) {
        int[] pos = this.wichDirection(direction);
        int x = h.getX()+pos[0];
        int y = h.getY()+pos[1];
        if(this.isPosition(x, y)) {
            Entity e = this.grid[x][y];
            if (e == null) {
                Barrel b = h.deployBarrel(x, y);
                this.grid[x][y] = b;
                this.barrels.add(b);
                this.fireChange();
                return true;
            } else if (e instanceof Mine) {
                this.detonate((Explosive) e);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Déplace un joueur à une nouvelle position sur la grille.
     *
     * @param h Le héro à déplacer.
     * @param direction dans laquelle le joueur souhaite se diriger.
     * @return true si le joueur a été déplacé sinon false.
     */
    public boolean moveHero(Hero h, String direction) {
        int[] pos = this.wichDirection(direction);
        int x = h.getX() + pos[0];
        int y = h.getY() + pos[1];
        if (this.isPosition(x, y)) {
            Entity e = this.grid[x][y];
            if (e == null) {
                this.moveHeroToNewPosition(h, x, y);
                return true;
            } else if (e instanceof Mine) {
                this.detonate((Explosive) e);
                this.moveHeroToNewPosition(h, x, y);
                this.isHeroDead(h);
                return true;
            } else if (e instanceof EnergyPill) {
                h.pickUpEnergyPill((EnergyPill)e);
                this.moveHeroToNewPosition(h, x, y);
                return true;
            }
            return false;
        }
        return false;
    }
    
    /**
     * ACtive le bouclier du hero.
     * 
     * @param h hero qui active son bouclier.
     * @return true si le hero a pu activer son bouclier sinon false.
     */
    public boolean activateShield(Hero h) {
        int shieldCount = h.getShieldRemaining();
        if(shieldCount > 0) {
            h.setImmune(true);
            h.setShieldRemaining(shieldCount-1);
            return true;
        }
        return false;
    }

    /**
     * Détonation d'un explosif.
     * 
     * @param explosive est l'explosif qui va détoner.
     */
    private void detonate(Explosive explosive) {
        List<Entity> entities = this.nearestNeighborsEntity(explosive.getExplosionRadius(), explosive.getX(), explosive.getY());
        this.grid[explosive.getX()][explosive.getY()] = null;
        for (Entity entitie : entities) {
            if (entitie instanceof Hero) {
                Hero h = (Hero) entitie;
                if (!h.isImmune()) {
                    h.setHealthRemaining(h.getHealthRemaining()-explosive.getDamages());
                    this.isHeroDead(h);
                }
            } else if (entitie instanceof EnergyPill || entitie instanceof Explosive) {
                this.grid[entitie.getX()][entitie.getY()] = null;
            }
        }
        this.fireChange();
    }
    
    /**
     * Recherche les entités les plus proches situés dans un rayon donné.
     * 
     * @param n Le rayon de recherche, exprimé en nombre de cases.
     * @param x Coordonné horizontale du point de recherche.
     * @param y Coordonné verticale du point de recherche.
     * @return Une liste contenant toutes les entités répondant aux critères de proximité.
     */
    public List<Entity> nearestNeighborsEntity(int n, int x, int y) {
        List<Entity> neighbors = new ArrayList<>();
        for (int i=0; i < this.size; i++) {
            for (int j=0; j < this.size; j++) {
                if (this.grid[i][j] != null) {
                    int diffX = Math.abs(this.grid[i][j].getX()-x);
                    int diffY = Math.abs(this.grid[i][j].getY()-y);
                    int maxi = Math.max(diffX, diffY);
                    if (maxi <= n) {
                        neighbors.add(this.grid[i][j]);
                    }
                }
            }
        }
        return neighbors;
    }
    
    /**
     * Tirer sur un joueur ou sur un barril.
     * Règle du jeu : tir horizontal ou vertical.
     * 
     * @param h le hero qui tire.
     * @param d direction du tire.
     * @return true si le joueur à touché un joueur ou un barril sinon false.
     */
    public boolean shootHero(Hero h, String d) {
        int[] direction = this.wichDirection(d);
        int x = h.getX();
        int y = h.getY();
        for (int i=0; i < this.size; i++) {
            x += direction[0];
            y += direction[1];
            if(this.isPosition(x, y)) {
                Entity e = this.grid[x][y];
                if (e instanceof Wall) {
                    return false;
                } else if (e instanceof Hero) {
                    Hero hero = (Hero) e;
                    if (hero.isImmune()) return true;
                    h.shootHero(hero);
                    this.isHeroDead(hero);
                    return true;
                } else if (e instanceof Barrel) {
                    this.detonate((Explosive)e);
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Donne le sens de direction (gauche, droite, haut, bas, etc.).
     * 
     * @param direction gauche, droite, haut, bas.
     * @return un tableau contenant la direction {x,y}
     */
    public int[] wichDirection(String direction) {
        switch(direction) {
            case "left":
                return new int[] {0, -1};
            case "right":
                return new int[] {0, 1};
            case "up":
                return new int[] {-1, 0};
            case "down":
                return new int[] {1, 0};
            case "lu":
                return new int[] {-1, -1};
            case "ru":
                return new int[] {-1, 1};
            case "ld":
                return new int[] {1, -1};
            case "rd":
                return new int[] {1, 1};
            default:
                return new int[] {0, 0};
        }
    }
    
    /**
     * Déplace un joueur vers une nouvelle position sur la grille.
     *
     * @param h Le héro à déplacer.
     * @param x Nouvelle coordonné horizontale du joueur.
     * @param y Nouvelle coordonné verticale du joueur.
     */
    private void moveHeroToNewPosition(Hero h, int x, int y) {
        this.grid[h.getX()][h.getY()] = null;
        this.grid[x][y] = h;
        h.setX(x);
        h.setY(y);
        this.fireChange();
    }
    
    /**
     * Supprime le joueur si il est mort.
     * 
     * @param h joueur a supprimer si celui-ci est mort.
     * @return true si le joueur est mort sinon false.
     */
    private boolean isHeroDead(Hero h) {
        if (!h.isAlive()) {
            for (int i=0; i < this.heros.size(); i++) {
                if (this.heros.get(i) == h) {
                    this.heros.remove(i);
                    this.grid[h.getX()][h.getY()] = null;
                    this.fireChange();
                    return true;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Vérifie si une position donnée se trouve dans la grille.
     *
     * @param x Coordonnée horizontale.
     * @param y Coordonnée verticale.
     * @return true si la position est dans la grille, false sinon.
     */
    private boolean isPosition(int x, int y) {
        return x >= 0 && x < this.size && y >= 0 && y < this.size;
    }

    /**
     * Affiche l'état actuel de la grille.
     * 
     * @return l'état de la grille avec toutes les entités présentes.
     */
    @Override
    public String toString() {
        String tmp = "";
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.grid[i][j] == null) {
                    tmp += " . ";
                } else if (this.grid[i][j] instanceof Hero) {
                    tmp += " " + this.grid[i][j].getEmote();
                } else {
                    tmp += " " + this.grid[i][j].getEmote() + " ";
                }
                tmp += " ";
            }
            tmp += "\n";
        }
        return tmp;
    }
    
}