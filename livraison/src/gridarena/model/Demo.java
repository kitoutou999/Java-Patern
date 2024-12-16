package gridarena.model;

import java.util.*;

/**
 * Représente une démonstration de la classe Grid.
 *
 * @author Emilien, Huron, Florian Pépin
 * @version 2.0
 */
public class Demo {

    public static void main(String[] args) {
        
        //test sur un champ de bataille.
        List<String> players = List.of("mastodonte", "assassin", "warrior");
        Battlefield battlefield = new Battlefield(players);
        System.out.println("###### CHAMP DE BATAILLE ######");
        System.out.println(battlefield);
        
    }
}

