package gridarena.controller;

import gridarena.entity.hero.Hero;
import gridarena.view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ControllerAction extends JPanel implements ActionListener {

    private ControllerRoot root;
    private ControllerGame game;
    private Hero hero;
    private PlayerGUI playerGUI;
    private String selectedButton = "Move";
    private ArrayList<JButton> actionButtons = new ArrayList<>();
    private ArrayList<JButton> moveButtons = new ArrayList<>();
    private HashMap<String, JLabel> leftAmmos = new HashMap<>();

    public ControllerAction(ControllerRoot root, ControllerGame game, Hero hero, PlayerGUI playerGUI) {
        super(new BorderLayout());
        this.root = root;
        this.game = game;
        this.hero = hero;
        this.playerGUI = playerGUI;
        
        this.setPreferredSize(new Dimension(300, 300));
        JPanel topPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        createTopButtons(topPanel);
        this.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        createMoveButtons(centerPanel);
        this.add(centerPanel, BorderLayout.CENTER);

        updateMoveButtonStates(selectedButton);
    }

    private void createTopButtons(JPanel panel) {
        HashMap<String, Object> buttonValues = new HashMap<>();
        buttonValues.put("Move", "∞");
        buttonValues.put("Shoot", "∞");
        buttonValues.put("Mine", hero.getMineRemaining());
        buttonValues.put("Barrel", hero.getBarrelRemaining());
        buttonValues.put("Shield", hero.getShieldRemaining());

        for (String text : buttonValues.keySet()) {
            JPanel buttonPanel = new JPanel(new BorderLayout());
            JButton button = new JButton(text);
            if (text.equals(selectedButton)) {
                button.setBackground(Color.ORANGE);
            }
            button.setPreferredSize(new Dimension(50, 50));
            button.addActionListener(this);
            actionButtons.add(button);

            JLabel label = new JLabel("Left" + ": " + buttonValues.get(text), SwingConstants.CENTER);
            buttonPanel.add(button, BorderLayout.CENTER);
            buttonPanel.add(label, BorderLayout.SOUTH);
            panel.add(buttonPanel);
            leftAmmos.put(text, label);
        }
    }

    private void createMoveButtons(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        int[][] positions = {{1, 0}, {2, 1}, {1, 2}, {0, 1}, {0, 0}, {2, 0}, {2, 2}, {0, 2}, {1, 1}};
        String[] directions = {"↑", "→", "↓", "←", "↖", "↗", "↘", "↙", "+"};

        for (int i = 0; i < directions.length; i++) {
            JButton button = createButton(directions[i]);
            gbc.gridx = positions[i][0];
            gbc.gridy = positions[i][1];
            panel.add(button, gbc);
            moveButtons.add(button);
        }
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(50, 50));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.GRAY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton sourceButton = (JButton) e.getSource();
        String buttonText = sourceButton.getText();

        if (actionButtons.contains(sourceButton)) {
            selectedButton = buttonText;
            updateButtonColors();
            updateMoveButtonStates(buttonText);
            return;
        }


        if (!playerGUI.isMyTurn) {
            return;
        }

        boolean valideAction = false;
        switch (selectedButton) {
            case "Shoot" :
                handleShoot(buttonText);
                valideAction = true;
                break;
            case "Mine" :
                valideAction = handleMine(buttonText);
                break;
            case "Barrel" :
                valideAction = handleBarrel(buttonText);
                break;
            case "Shield" :
                valideAction = handleShield(buttonText);
                break;
            case "Move" :
                valideAction = handleMove(buttonText);
                break;
            default:
                System.out.println("Invalid action");
        }

        if (valideAction) {
            playerGUI.isMyTurn = false;
            synchronized (this.game) {
                game.notifyAll();
            }
            updateLabels();
            updateButtonEnabled();
        }
    }

    private void updateButtonColors() {
        for (JButton button : actionButtons) {
            if (button.getText().equals(selectedButton)) {
                button.setBackground(Color.ORANGE);
            } else {
                button.setBackground(null);
            }
        }
    }

    private void updateButtonEnabled() {
        for (JButton button : actionButtons) {
            if (leftAmmos.get(button.getText()).getText().equals("Left: 0")) {
                if (button.getText().equals(selectedButton)) {
                    selectedButton = "Move";
                    updateButtonColors();
                    updateMoveButtonStates(selectedButton);
                }
                button.setEnabled(false);
            } else {
                button.setEnabled(true);
            }
        }
    }

    private void updateMoveButtonStates(String buttonText) {
        String[] validDirections;
        switch (buttonText) {
            case "Move":
            case "Shoot":
                validDirections = new String[]{"↑", "→", "↓", "←"};
                break;
            case "Mine":
            case "Barrel":
                if (leftAmmos.get(buttonText).getText().equals("Left: 0")) {
                    validDirections = new String[]{};
                    break;
                }
                validDirections = new String[]{"↑", "→", "↓", "←", "↖", "↗", "↘", "↙"};
                break;
            case "Shield":
                if (leftAmmos.get(buttonText).getText().equals("Left: 0")) {
                    validDirections = new String[]{};
                    break;
                }
                validDirections = new String[]{"+"};
                break;
            default:
                validDirections = new String[]{};
        }
        setMoveButtonsEnabled(validDirections);
    }

    private void updateLabels() {
        leftAmmos.get("Mine").setText("Left: " + hero.getMineRemaining());
        leftAmmos.get("Barrel").setText("Left: " + hero.getBarrelRemaining());
        leftAmmos.get("Shield").setText("Left: " + hero.getShieldRemaining());
    }

    private void setMoveButtonsEnabled(String[] validDirections) {
        for (JButton button : moveButtons) {
            button.setEnabled(false);
            for (String direction : validDirections) {
                if (button.getText().equals(direction)) {
                    button.setEnabled(true);
                }
            }
        }
    }

    private boolean handleMove(String direction) {
        switch (direction) {
            case "↑":
                return  this.root.move(hero, "up");
            case "→":
                return  this.root.move(hero, "right");
            case "↓":
                return  this.root.move(hero, "down");
            case "←":
                return  this.root.move(hero, "left");
            default:
                return false;
        }
    }

    private void handleShoot(String direction) {
        switch (direction) {
            case "↑":
                this.root.shootPlayer(hero, "up");
                break;
            case "→":
                this.root.shootPlayer(hero, "right");
                break;
            case "↓":
                this.root.shootPlayer(hero, "down");
                break;
            case "←":
                this.root.shootPlayer(hero, "left");
                break;

        }
    }

    private boolean handleMine(String direction) {
        switch (direction) {
            case "↑":
                return this.root.addMine(hero, "up");

            case "→":
                return this.root.addMine(hero, "right");
            case "↓":
                return this.root.addMine(hero, "down");
            case "←":
                return this.root.addMine(hero, "left");
            case "↖":
                return this.root.addMine(hero, "lu");
            case "↗":
                return this.root.addMine(hero, "ru");
            case "↘":
                return this.root.addMine(hero, "rd");
            case "↙":
                return this.root.addMine(hero, "ld");

            default:
                return true;
        }
    }

    private boolean handleBarrel(String direction) {
        switch (direction) {
            case "↑":
                return this.root.addBarrel(hero, "up");
            case "→":
                return this.root.addBarrel(hero, "right");
            case "↓":
                return this.root.addBarrel(hero, "down");
            case "←":
                return this.root.addBarrel(hero, "left");
            case "↖":
                return this.root.addBarrel(hero, "lu");
            case "↗":
                return this.root.addBarrel(hero, "ru");
            case "↘":
                return this.root.addBarrel(hero, "rd");
            case "↙":
                return this.root.addBarrel(hero, "ld");
            default:
                return true;
        }
    }

    private boolean handleShield(String direction) {
        switch (direction) {
            case "+":
                return this.root.addShield(hero);
            default:
                return true;
        }
    }
}