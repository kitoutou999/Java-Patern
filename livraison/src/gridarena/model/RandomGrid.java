package gridarena.model;

import gridarena.entity.Entity;
import gridarena.entity.hero.Hero;
import gridarena.entity.other.*;
import gridarena.utils.*;

import java.util.*;

/**
 * Représente la génération d'une grille aléatoirement.
 * 
 * @author Florian Pépin
 * @version 2.0
 */
public class RandomGrid {
    
    private List<String> classes;
    private List<Hero> heros;
    private List<Wall> walls;
    private List<EnergyPill> energyPills;
    private Entity[][] map;
    private List<Pair<Integer, Integer>> coordinates;
    private int wallQuantity;
    private int energyPillQuantity;
    
    /**
     * Construction d'une instance pour générer une grille.
     * 
     * @param classes des heros que les joueurs ont choisi.
     * @param heros liste des heros dans la grille.
     * @param walls liste des murs dans la grille.
     * @param energyPills liste des pastilles d'énergies dans la grille.
     */
    public RandomGrid(List<String> classes, List<Hero> heros, List<Wall> walls, List<EnergyPill> energyPills) {
        this.classes = classes;
        this.heros = heros;
        this.walls = walls;
        this.energyPills = energyPills;
        this.map = new Entity[this.classes.size()*2][this.classes.size()*2];
        this.coordinates = this.generateSortedRandomCoordinates();
        this.wallQuantity = this.classes.size();
        this.energyPillQuantity = this.classes.size();
    }
    
    /**
     * Obtenir la grille de jeu.
     * 
     * @return la grille de jeu.
     */
    public Entity[][] getMap() {
        return this.map;
    }
    
    /**
     * Générer une grille de jeu aléatoirement.
     * 
     * @param classes des heros que les joueurs ont choisi.
     * @param heros liste des heros dans la grille.
     * @param walls liste des murs dans la grille.
     * @param energyPills liste des pastilles d'énergies dans la grille.
     * @return une grille de jeu aléatoire.
     */
    public static Entity[][] generateFilledGrid(List<String> classes, List<Hero> heros, List<Wall> walls, List<EnergyPill> energyPills) {
        RandomGrid grid = new RandomGrid(classes, heros, walls, energyPills);
        grid.addHeros();
        grid.addEnergyPills();
        grid.addWalls();
        return grid.getMap();
    }
    
    /**
     * Génère une liste mélangé aléaoirement contenant les toutes les positions dans la grille.
     * 
     * @return une liste de toutes les coordonées de la grille.
     */
    private List<Pair<Integer, Integer>> generateSortedRandomCoordinates() {
        int size = this.map.length;
        List<Pair<Integer, Integer>> newCoordinates = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newCoordinates.add(new Pair(i, j));
            }
        }
        Collections.shuffle(newCoordinates, new Random());
        return newCoordinates;
    }
    
    /**
     * Ajoute les joueurs dans la grille de jeu.
     */
    public void addHeros() {
        if (this.classes.size() >= this.coordinates.size()) {
            throw new IllegalArgumentException("Il n'y a pas assez de place pour ajouter les heros.");
        }
        for (int i = 0; i < this.classes.size(); i++) {
            Pair<Integer, Integer> pair = this.coordinates.get(i);
            boolean isPosition = this.isAtCorner(pair) && this.isIsolated(pair);
            while(!isPosition) {
                this.coordinates.remove(i);
                if (this.coordinates.isEmpty()) throw new IllegalArgumentException("Il n'y a pas assez de place pour ajouter les heros.");
                pair = this.coordinates.get(i);
                isPosition = this.isAtCorner(pair) && this.isIsolated(pair);
            }
            Hero hero = Hero.createHero(this.classes.get(i), pair.getFirst(), pair.getSecond());
            this.map[pair.getFirst()][pair.getSecond()] = hero;
            this.heros.add(hero);
            this.coordinates.remove(i);
        }
    }
    
    /**
     * Ajoute les pastilles d'énergie dans la grille de jeu.
     */
    public void addEnergyPills() {
        if (this.energyPillQuantity >= this.coordinates.size()) {
            throw new IllegalArgumentException("Il n'y a pas assez de place pour ajouter les pastilles d'énergie.");
        }
        for (int i = 0; i < this.energyPillQuantity; i++) {
            Pair<Integer, Integer> pair = this.coordinates.get(i);
            EnergyPill energyPill = new EnergyPill(pair.getFirst(), pair.getSecond());
            this.map[pair.getFirst()][pair.getSecond()] = energyPill;
            this.energyPills.add(energyPill);
            this.coordinates.remove(i);
        }
    }
    
    /**
     * Ajoute les murs dans la grille de jeu.
     */
    public void addWalls() {
        if (this.wallQuantity >= this.coordinates.size()) {
            throw new IllegalArgumentException("Il n'y a pas assez de place pour ajouter les murs.");
        }
        for (int i = 0; i < this.wallQuantity; i++) {
            Pair<Integer, Integer> pair = this.coordinates.get(i);
            Wall wall = new Wall(pair.getFirst(), pair.getSecond());
            this.map[pair.getFirst()][pair.getSecond()] = wall;
            this.walls.add(wall);
            this.coordinates.remove(i);
        }
    }
    
    /**
     * Vérifie qu'une coordonée ne soit pas un coin de la grille.
     * 
     * @param pair coordonée à vérifier.
     * @return true si la coordonée n'est pas un coin de la grille sinon false.
     */
    private boolean isAtCorner(Pair<Integer, Integer> pair) {
        int x = pair.getFirst();
        int y = pair.getSecond();
        int size = this.map.length;
        return !((x == 0 && y == 0) || (x == 0 && y == size-1) || (x == size-1 && y == 0) || (x == size-1 && y == size-1));
    }
    
    /**
     * Vérifie qu'une coordonée n'est pas de voisin Hero.
     * Un voisin est une des quatres positions autour de la coordonée.
     * 
     * @param pair coordonée à vérifier.
     * @return true si la coordonée n'a pas de voisin Hero sinon false.
     */
    private boolean isIsolated(Pair<Integer, Integer> pair) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        for (int i = 0; i < 4; i++) {
            int nx = pair.getFirst() + dx[i];
            int ny = pair.getSecond() + dy[i];
            if ((nx >= 0 && nx < this.map.length && ny >= 0 && ny < this.map.length)) {
                if (this.map[nx][ny] instanceof Hero) {
                    return false;
                }
            }
        }
        return true;
    }
    
}
