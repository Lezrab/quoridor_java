package quoridor.view;

import quoridor.model.*;
import quoridor.controller.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.File;
import javax.swing.border.Border;

/**
	* This class displays the menu of the graphical interface.
	* It displats three usable buttons : create a new game, continue a game and
	* credits.
	*/
public class Home extends JPanel {
    public Home(Application appli) {
        this.appli = appli;
        this.initComponents();
    }

		/**
      * Initializes every Component of the JPanel
      */
    public void initComponents() {
        this.setBackground(Color.WHITE);
        JLabel title = new JLabel("", JLabel.CENTER);
        File f = new File("data/fonts/billionthine/Billionthine-Regular.ttf");
        try {
            FileInputStream in = new FileInputStream(f);
            Font titleFont = Font.createFont(Font.TRUETYPE_FONT, in );
            Font titleFont56Pt = titleFont.deriveFont(56f);
            title.setText("Quoridor");
            title.setFont(titleFont56Pt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        File f2 = new File("data/fonts/roboto/Roboto-Regular.ttf");
        Font robotoFont16Pt = null;
        try {
            FileInputStream in2 = new FileInputStream(f2);
            Font robotoFont = Font.createFont(Font.TRUETYPE_FONT, in2);
            robotoFont16Pt = robotoFont.deriveFont(16f);
        } catch (Exception e) {
            e.printStackTrace();
        }


        this.newGame = new JButton("New Game");
        this.newGame.setBackground(Color.LIGHT_GRAY);
        this.newGame.setBorderPainted(false);
        this.newGame.setFont(robotoFont16Pt);
        this.newGame.setFocusPainted(false);
        this.newGame.addMouseListener(new HomeListener(this, this.appli));

        this.loadGame = new JButton("Continue");
        this.loadGame.setBackground(Color.LIGHT_GRAY);
        this.loadGame.setBorderPainted(false);
        this.loadGame.setFont(robotoFont16Pt);
        this.loadGame.setFocusPainted(false);
        this.loadGame.addMouseListener(new HomeListener(this, this.appli));

        this.credits = new JButton("Credits");
        this.credits.setBackground(Color.LIGHT_GRAY);
        this.credits.setBorderPainted(false);
        this.credits.setFont(robotoFont16Pt);
        this.credits.setFocusPainted(false);
        this.credits.addMouseListener(new HomeListener(this, this.appli));

        this.educationalMode = new JButton("Educational Mode");
        this.educationalMode.setBackground(Color.LIGHT_GRAY);
        this.educationalMode.setBorderPainted(false);
        this.educationalMode.setFont(robotoFont16Pt);
        this.educationalMode.setFocusPainted(false);
        this.educationalMode.setEnabled(false);
        this.educationalMode.addMouseListener(new HomeListener(this, this.appli));

        this.setLayout(new GridLayout(5, 1, 0, 50));
        this.setBorder(new CompoundBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK), new EmptyBorder(50, 200, 50, 200)));

        this.add(title);
        this.add(newGame);
        this.add(loadGame);
        this.add(credits);
        this.add(educationalMode);
    }

		/**
      * Returns button newGame
      * @return button newGame
      */
    public JButton getNewGame() {
        return this.newGame;
    }

		/**
      * Sets button newGame
      * @param newGame button newGame
      */
    public void setNewGame(JButton newGame) throws NullPointerException {
        if (newGame != null) this.newGame = newGame;
        else throw new NullPointerException("[ERROR] setNewGame : parameter is null.");
    }

		/**
      * Returns button loadGame
      * @return button loadGame
      */
    public JButton getLoadGame() {
        return this.loadGame;
    }

		/**
      * Sets button loadGame
      * @param loadGame button loadGame
      */
    public void setLoadGame(JButton loadGame) throws NullPointerException {
        if (loadGame != null) this.loadGame = loadGame;
        else throw new NullPointerException("[ERROR] setLoadGame : parameter is null.");
    }

		/**
      * Returns button credits
      * @return button credits
      */
    public JButton getCredits() {
        return this.credits;
    }

		/**
      * Sets button credits
      * @param credits button credits
      */
    public void setCredits(JButton credits) throws NullPointerException {
        if (credits != null) this.credits = credits;
        else throw new NullPointerException("[ERROR] setCredits : parameter is null.");
    }

    /*public JButton getEducationalMode() {
        return this.educationalMode;
    }

    public void setEducationalMode(JButton educationalMode) throws NullPointerException {
        if (educationalMode != null) this.educationalMode = educationalMode;
        else throw new NullPointerException("[ERROR] setEducationalMode : parameter is null.");
    }*/

    private Application appli;
    private JButton newGame;
    private JButton loadGame;
    private JButton credits;
    private JButton educationalMode;
    private QuoridorN quoridor;

}
