package quoridor.view;

import quoridor.model.*;
import quoridor.controller.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;


/**
  * This class displays the classical scenario of the end of a game.
  * It does display the winner and two buttons : one to leave the game and one
  * to go back to the menu.
  */
public class EndOfGame extends JPanel {
    public EndOfGame(Application appli, QuoridorG quoridor) {
        this.appli = appli;
        this.quoridor = quoridor;
        this.initComponents();
    }

    /**
      * Initializes every Component of the JPanel
      */
    public void initComponents() {
        this.setBackground(Color.WHITE);
        File f1 = new File("data/fonts/roboto/Roboto-Regular.ttf");
        Font robotoFontBold16pt = null;
        try {
            FileInputStream in = new FileInputStream(f1);
            Font robotoFont = Font.createFont(Font.TRUETYPE_FONT, in );
            robotoFontBold16pt = robotoFont.deriveFont(16f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.winnerLabel = new JLabel("<html>The " + this.quoridor.getWinner() + " has won. <br/>A screenshot of its win was saved at ws/data/img/history</html>");
        this.home = new JButton("Home");
        this.home.setBackground(Color.LIGHT_GRAY);
        this.home.setBorderPainted(false);
        this.home.setFont(robotoFontBold16pt);
        this.home.setFocusPainted(false);
        this.home.addMouseListener(new EndListener(this, this.appli));

        this.exit = new JButton("Exit");
        this.exit.setBackground(Color.LIGHT_GRAY);
        this.exit.setBorderPainted(false);
        this.exit.setFont(robotoFontBold16pt);
        this.exit.setFocusPainted(false);
        this.exit.addMouseListener(new EndListener(this, this.appli));

        this.winnerLabel.setFont(robotoFontBold16pt);
        this.home.addMouseListener(new EndListener(this, this.appli));
        this.exit.addMouseListener(new EndListener(this, this.appli));
        this.setLayout(new GridLayout(5, 5));

        this.add(new JLabel(""));
        this.add(new JLabel(""));
        this.add(new JLabel(""));
        this.add(new JLabel(""));
        this.add(new JLabel(""));

        this.add(new JLabel(""));
        this.add(new JLabel(""));
        this.add(winnerLabel);
        this.add(new JLabel(""));
        this.add(new JLabel(""));

        this.add(new JLabel(""));
        this.add(new JLabel(""));
        this.add(new JLabel(""));
        this.add(new JLabel(""));
        this.add(new JLabel(""));

        this.add(new JLabel(""));
        this.add(this.home);
        this.add(new JLabel(""));
        this.add(this.exit);
        this.add(new JLabel(""));

        this.add(new JLabel(""));
        this.add(new JLabel(""));
        this.add(new JLabel(""));
        this.add(new JLabel(""));
        this.add(new JLabel(""));
    }

    /**
      * Returns button home
      * @return button home
      */
    public JButton getHome() {
        return this.home;
    }

    /**
      * Returns button exit
      * @return button exit
      */
    public JButton getExit() {
        return this.exit;
    }



    Application appli;
    QuoridorG quoridor;
    JLabel winnerLabel;
    JButton home;
    JButton exit;
}
