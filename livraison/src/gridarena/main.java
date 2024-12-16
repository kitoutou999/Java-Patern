package gridarena;

import gridarena.controller.ControllerRoot;

import java.util.*;

/**
 * 
 * @author Florian Pépin, Tom David et Emilien Huron.
 * @version 1.0
 */
public class main {
    
    public static void main(String[] args) {
        //classes : mastodonte, assassin et warrior.
        List<String> classes = Arrays.asList("mastodonte", "testeur");
        ControllerRoot controller = new ControllerRoot(classes);
    }
    
}
