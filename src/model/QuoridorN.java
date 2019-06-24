package quoridor.model;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.*;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.*;
import java.io.FileNotFoundException;

/**
 * QuoridorN class initilialize the parameters of the game and start the Game
 */
public class QuoridorN {

    /**
     * QuoridorN checks the validity of the parameters transmitted by GameLauncher and creates the Game object
     * @param fileName used to create the Game attribute and its parameters
     */
    public QuoridorN(String fileName) {
        if (fileName.equals("data/config/.homemade_config.txt")) {
            this.setHomemade(true);
            System.out.println(this.homemade);
            this.configure(fileName);
            this.gameplay.start();
        } else if (fileName.equals("data/config/.auto_config.txt")) {
            this.setHomemade(false);
            System.out.println(this.homemade);
            this.configure(fileName);
            this.gameplay.start();
        } else if (fileName.matches("^data/saves/save[0-9]+.ser$")) {
            if (homemade) {
                this.configure("data/config/.homemade_config.txt");
                this.board = this.gameplay.loadGame(fileName);
                System.out.println(board.toString());
                this.gameplay.start();
                System.out.println(board.toString());
            } else if (!homemade) {
                this.configure("data/config/.auto_config.txt");
                this.board = this.gameplay.loadGame(fileName);
                System.out.println(board.toString());
                this.gameplay.start();
                System.out.println(board.toString());
            }
        }
    }

    /**
     * extract the content from a file and initialize the value of the attributes
     * @param fileName path of the file containing values
     */
    public void configure(String fileName) {
        ArrayList < String > liste = new ArrayList < String > ();
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader buff = new BufferedReader(fr);
            Scanner in = new Scanner(buff);
            while ( in .hasNextLine()) {
                liste.add( in .nextLine());
            } in .close();

            //
            String modeS = liste.get(0);
            if (modeS.equals("AA")) this.mode = Mode.AA;
            else if (modeS.equals("HH")) this.mode = Mode.HH;
            else if (modeS.equals("HA")) this.mode = Mode.HA;
            else this.mode = Mode.HH; // Default game mode if the auto_config file is compromised
            //

            //
            this.difficulty = (Integer.parseInt(liste.get(1)));
            if (this.difficulty < 1 && this.difficulty > 2) this.difficulty = 1; // Default difficulty is the auto_config file is compromised
            //

            //
            String typeOfGame = liste.get(2);
            if (typeOfGame.equals("TOURNAMENT")) this.gameType = GameType.TOURNAMENT;
            else if (typeOfGame.equals("NORMAL")) this.gameType = GameType.NORMAL;
            else this.gameType = GameType.NORMAL; // Default match type if the auto_config file is compromised
            //

            //
            this.duration = (double)(Integer.parseInt(liste.get(3)));
            if (this.duration < 3 && this.duration > 600) this.duration = 30; // Default round duration if the auto_config file is compromised
            //

            this.printConfiguration();
            this.gameplay = new Game(this.mode, this.difficulty, this.gameType, this.duration);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error : we cannot find the default configuration file [default = data/config/.auto_config.txt]", "Erreur de fichier de configuration", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * print the current configuration (attributes)
     */
    public void printConfiguration() {
        System.out.println("###### A configuration was set : ######");
        System.out.println("#######################################");
        System.out.println("###### - Mode: " + this.mode + "              ########");

        if ((this.mode == Mode.HA) || (this.mode == Mode.AA)) {
            String difAsAString = "";
            if (this.difficulty == 1) difAsAString = "EASY     ########";
            if (this.difficulty == 2) difAsAString = "MEDIUM   ########";
            System.out.println("###### - Difficulty : " + difAsAString);
        }

        if (this.gameType == GameType.TOURNAMENT) System.out.println("###### - GameType : " + this.gameType + " ########");
        if (this.gameType == GameType.NORMAL) System.out.println("###### - GameType : " + this.gameType + "     ########");
        if (this.gameType == GameType.TOURNAMENT)
            if (this.duration <= 9) System.out.println("###### - Timer : " + this.duration + "s          ########");
        if (this.duration <= 99 && this.duration >= 10) System.out.println("###### - Timer : " + this.duration + "s         ########");
        if (this.duration <= 999 && this.duration >= 100) System.out.println("###### - Timer : " + this.duration + "s        ########");
        System.out.println("#######################################");


    }

		/**
			* Returns the gameplay of the game
			* @return the game attribute
			*/
    public Game getGame() {
        return this.gameplay;
    }

		/**
			* Returns the homemade attribute
			* @return the homemade attribute
			*/
    public boolean getHomemade() {
        return this.homemade;
    }

		/**
			* Sets the homemade attribute
			* @param bool the boolean value of homemade attribute
			*/
    public void setHomemade(boolean bool) {
        this.homemade = bool;
    }

    /** Game object, manage the game when the parameters of QuoridorN are corrects
     */
    private Board board;
    private Game gameplay;
    private GameType gameType;
    private Mode mode;
    private int difficulty;
    private double duration;
    private String savePath;
    private boolean homemade;

}
