package quoridor.view;
import javax.swing.*;
import java.awt.*;
import quoridor.model.*;
import quoridor.controller.*;

/**
	* This is the main class responsible of the Graphical version of Quoridor.
	* It manages 3 different JPanel : Home, QuoridorG and EndOfGame
	* and display each one in the correct order.
	*/
public class Application extends JFrame {
    public Application() {
        this.setMinimumSize(new Dimension(1280, 720));
        this.setLocationRelativeTo(null);
        ImageIcon logo = new ImageIcon("data/img/quoridor-logo.png");
        this.setIconImage(logo.getImage());
        this.setTitle("Quoridor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Home home = new Home(this);
        QuoridorG game = new QuoridorG(this);
        EndOfGame end = new EndOfGame(this, game);
        JPanel activePanel = new JPanel();
        activePanel = home;
        activePanel.setVisible(true);
        this.add(activePanel);
    }

		/**
			* Launches the JFrame
			* @param args args
			*/
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Application().setVisible(true);
            }
        });
    }
}
