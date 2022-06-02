/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Alessandra
 */
public class MinesweeperGUI {

    private JFrame instructionsFrame;
    private JFrame menuFrame;
    private JFrame gameFrame;
    private JFrame difficultyFrame;

    private JButton easy;
    private JButton medium;
    private JButton hard;

    private JButton menuButton1;
    private JButton menuButton2;
    private JButton menuButton3;

    // Constant variables for the difficulty of the field creation
    private static final int EASY = 6;
    private static final int MEDIUM = 8;
    private static final int HARD = 10;

    private final Gameplay gp;
    private int size;

    private User user; // the user of the current game
    private DBUserTable db;
    private DBUserScores score;

    // CONSTRUCTOR
    public MinesweeperGUI(Gameplay gp, DBUserTable db) {
        this.gp = gp;
        this.db = db;
        score = new DBUserScores();

        menu();
        instructions();
        chooseDifficulty();
    }

    public void menu() {
        menuFrame = new JFrame("MineSweeper");
        menuFrame.setSize(500, 500);

        // Create welcome panel which holds the welcome text
        JLabel welcomeText = new JLabel("Welcome to Minesweeper!");
        welcomeText.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
        JPanel welcomePanel = new JPanel();
        welcomePanel.setPreferredSize(new Dimension(500, 100));
        welcomePanel.add(welcomeText);
        menuFrame.add(welcomePanel, BorderLayout.NORTH);
        //----------------------------------------------------------------------

        // Create buttons panel that holds the buttons instructions and play
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setPreferredSize(new Dimension(300, 300));

        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instructionsFrame.setVisible(true);
                menuFrame.setVisible(false);
            }
        });
        JButton playButton = new JButton("Play");
        playButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficultyFrame.setVisible(true);
                menuFrame.setVisible(false);
            }
        });
        buttonsPanel.add(instructionsButton);
        buttonsPanel.add(playButton);
        menuFrame.add(buttonsPanel, BorderLayout.CENTER);
        //----------------------------------------------------------------------

        menuFrame.addWindowListener(new closeWindowAdapter());
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);
        menuFrame.pack();
    }

    public void instructions() {
        instructionsFrame = new JFrame("Instructions");
        instructionsFrame.setSize(700, 700);

        // Create panel that will hold the instructions text
        JPanel instructions = new JPanel();
        JLabel label = new JLabel(""
                + "<html> Objective: <br>"
                + "Uncover the tiles that do not contain mines. If you dig up <br>"
                + "a mine, it is game over! <br>"
                + "Easy difficulty has 9 mines in a 6x6 board. <br>"
                + "Medium difficulty has 12 mines in a 8x8 board. <br>"
                + "Hard difficulty has 15 mines in a 10x10 board.<br><br>"
                + "How to play:<br>"
                + "Enter the row number and column number after each prompt,<br>"
                + "respectively (ensure you enter integers). This will dig <br>"
                + "up the tile that will either contain a mine (game over!) <br>"
                + "or no mine. Digging up an empty tile successfully will <br>"
                + "show a number between 1-8 inclusive or no number (0).<br>"
                + "This will give you hints on how many mines are within a <br>"
                + "one-tile proximity of that tile.<br>"
                + "For example, an uncovered tile with the number 4 indicates <br>"
                + "there are 4 mines within a one-tile proximity,<br>"
                + "i.e.<br>"
                + "[?][?][*]<br>"
                + "[*][4][?]<br>"
                + "[?][*][*]<br><br>"
                + "The hint, however, will not tell you where the mines are, <br> "
                + "as that is your objective!");
        label.setFont(new Font("Lucida Sans Typewriter", Font.PLAIN, 18));
        instructions.add(label);
        instructionsFrame.add(instructions, BorderLayout.NORTH);
        //----------------------------------------------------------------------

        // Create panel that will hold the menu button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(300, 50));
        menuButton1 = new JButton("Menu");
        menuButton1.addActionListener(new menuListener());
        buttonPanel.add(menuButton1);

        instructionsFrame.add(buttonPanel, BorderLayout.SOUTH);
        //----------------------------------------------------------------------

        instructionsFrame.setLocationRelativeTo(null);
        instructionsFrame.addWindowListener(new closeWindowAdapter());
        instructionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void chooseDifficulty() {
        difficultyFrame = new JFrame("Choose Difficulty");
        difficultyFrame.setSize(500, 450);

        // Create user panel which holds a textfield for username input
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setPreferredSize(new Dimension(200, 100));

        JLabel promptUsernameText = new JLabel("Please enter your username:");
        promptUsernameText.setFont(new Font("Lucida Sans Typewriter", Font.PLAIN, 18));
        promptUsernameText.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        JTextField promptUsername = new JTextField(10);
        promptUsername.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        promptUsername.setMaximumSize(promptUsername.getPreferredSize());

        JLabel invalidText = new JLabel(" ");
        invalidText.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        userPanel.add(promptUsernameText);
        userPanel.add(promptUsername);
        userPanel.add(invalidText);

        difficultyFrame.add(userPanel, BorderLayout.NORTH);
        //----------------------------------------------------------------------

        // Create difficulty panel with buttons to get the difficulty
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new BoxLayout(difficultyPanel, BoxLayout.Y_AXIS));
        difficultyPanel.setPreferredSize(new Dimension(300, 150));

        JLabel difficultyText = new JLabel("Please choose a difficulty:");
        difficultyText.setFont(new Font("Lucida Sans Typewriter", Font.PLAIN, 18));
        difficultyText.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        easy = new JButton("Easy");
        easy.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        easy.addActionListener(new difficultyListener(promptUsername, invalidText));

        medium = new JButton("Medium");
        medium.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        medium.addActionListener(new difficultyListener(promptUsername, invalidText));

        hard = new JButton("Hard");
        hard.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        hard.addActionListener(new difficultyListener(promptUsername, invalidText));

        difficultyPanel.add(difficultyText);
        difficultyPanel.add(easy);
        difficultyPanel.add(medium);
        difficultyPanel.add(hard);

        difficultyFrame.add(difficultyPanel, BorderLayout.CENTER);
        //----------------------------------------------------------------------

        // Create menu button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(300, 50));
        menuButton2 = new JButton("Menu");
        menuButton2.addActionListener(new menuListener());
        buttonPanel.add(menuButton2);
        difficultyFrame.add(buttonPanel, BorderLayout.SOUTH);
        //----------------------------------------------------------------------

        difficultyFrame.setLocationRelativeTo(null);
        difficultyFrame.addWindowListener(new closeWindowAdapter());
        difficultyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void createButtonField(JLabel winLoseText, JLabel totalGamesText) {
        gp.setRealField(new HiddenField(size)); // create a new field of mines in the 2d array
        gp.setSize(size);

        JPanel buttonPanel = new JPanel();
        JPanel fieldPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(size, size));
        buttonPanel.setPreferredSize(new Dimension(500, 500));

        gp.setButtonField(new JButton[size][size]);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int fi = i;
                final int fj = j;
                gp.getButtonField()[i][j] = new JButton("?");
                gp.getButtonField()[i][j].setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 20));
                gp.getButtonField()[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton src = (JButton) e.getSource();
                        // Get the number of mines near of the corresponding tile to the button
                        int proximityNum = gp.getRealField().getField()[fi][fj].getMinesNear();

                        // Update the button's text accordingly
                        String number = "" + proximityNum;
                        if (proximityNum == -1) { // if the tile is a mine, game over
                            number = "*";
                            score.updateUserLosses(user); // update losses
                            user = db.getCurrentUser(user.getUsername()); // get the current user wins and losses from the database

                            gp.revealAll();
                            winLoseText.setText("You Lose!");
                            totalGamesText.setText("You've won " + user.getWins() + " games and lost " + user.getLosses() + " games in total.");
                        } else if (proximityNum == 0) { // if the tile is empty, reveal adjacent empty tiles
                            gp.revealEmptyTiles(fi, fj);
                            number = " ";
                        }
                        src.setText(number);
                        src.setContentAreaFilled(false);

                        // Check if won
                        if (gp.checkWin()) {
                            score.updateUserWins(user); // update wins
                            user = db.getCurrentUser(user.getUsername()); // get the current user wins and losses from the database

                            gp.revealAll();
                            winLoseText.setText("You win!");
                            totalGamesText.setText("You've won " + user.getWins() + " games and lost " + user.getLosses() + " games in total.");
                        }
                    }
                });
                buttonPanel.add(gp.getButtonField()[i][j]);
            }
        }
        fieldPanel.add(buttonPanel);
        gameFrame.add(fieldPanel, BorderLayout.CENTER);

        // Uncomment for testing purposes to get the answer
        System.out.println(gp.getRealField());
    }

    public void minesweeperGame() {
        gameFrame = new JFrame("Minesweeper");
        gameFrame.setSize(700, 700);

        // Create bottom panel that will hold text such as if they won
        // or their current game total wins and losses
        // and a button to go back to the menu.
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(100, 100));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        JLabel winLoseText = new JLabel(" ");
        winLoseText.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        JLabel totalGamesText = new JLabel("You've won " + user.getWins() + " games and lost " + user.getLosses() + " games in total.");

        totalGamesText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        menuButton3 = new JButton("Menu");
        menuButton3.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        menuButton3.addActionListener(new menuListener());

        bottomPanel.add(winLoseText);
        bottomPanel.add(totalGamesText);
        bottomPanel.add(menuButton3);
        gameFrame.add(bottomPanel, BorderLayout.SOUTH);
        //----------------------------------------------------------------------

        createButtonField(winLoseText, totalGamesText);

        // Create restart panel that holds a restart button
        JPanel restartPanel = new JPanel();
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create frame again
                gameFrame.dispose();
                minesweeperGame();
                gameFrame.setVisible(true);
            }
        });
        restartPanel.add(restartButton);
        gameFrame.add(restartPanel, BorderLayout.NORTH);
        //----------------------------------------------------------------------

        gameFrame.setLocationRelativeTo(null);
        gameFrame.addWindowListener(new closeWindowAdapter());
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // PRIVATE LISTENER CLASSES
    
    private class difficultyListener implements ActionListener {
        // Get references of the textfield and label
        private JTextField promptUsername;
        private JLabel invalidText;

        public difficultyListener(JTextField promptUsername, JLabel invalidText) {
            this.promptUsername = promptUsername;
            this.invalidText = invalidText;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (promptUsername.getText().equals("")) { // if the prompt textfield is empty
                System.out.println("Invalid! User must enter a username before proceeding");
                invalidText.setText("Please enter a username");
            } else if (promptUsername.getText().length() > 16) { // else if the username is longer than 16 chars 
                System.out.println("Invalid! User must enter a username less than or equal to 16 characters");
                invalidText.setText("Please enter a username less than or equal to 16 characters");
            } else {
                Object source = e.getSource();
                // Set the size of the field accordingly to the difficulty
                if (source == easy) {
                    size = EASY;
                } else if (source == medium) {
                    size = MEDIUM;
                } else {
                    size = HARD;
                }

                // Get the username of the prompt textfield
                String username = promptUsername.getText();
                // Add the user to the database if they don't already exist
                if (!db.ifUserExists(username)) {
                    System.out.println("Creating a new user " + username);
                    user = gp.createUser(username); // the newly created user is the current user
                    db.addUserToTable(user);
                } else {
                    user = db.getCurrentUser(username); // the current user data is extracted from the database
                }

                // Run the minesweeperGame() function to create the frame and hide this frame after
                minesweeperGame();
                gameFrame.setVisible(true);
                difficultyFrame.setVisible(false);
                // Clear invalid text label
                invalidText.setText(" ");
            }
        }
    }

    private class menuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            // Make the menu frame visible and the current frame of the menu button hidden.
            menuFrame.setVisible(true);
            if (source == menuButton1) {
                instructionsFrame.setVisible(false);
            } else if (source == menuButton2) {
                difficultyFrame.setVisible(false);
            } else if (source == menuButton3) {
                gameFrame.setVisible(false);
            }
        }
    }

    private class closeWindowAdapter extends WindowAdapter {
        // This will close the database connection after the frame is closed
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            db.closeDB();
        }
    }
}
