package quoridor.model;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
/**
 * Game class manages the two Player
 */
public class Game implements Serializable {
    //private static final long serialUID = "-930518221021043824l";
    /**
     * Game constructor creates the two Players object depending on the Mode parameter and launch the Game
     * @param mode mode of the Game (HH, HA, AA)
		 * @param difficulty difficulty of the game
     * @param gameType type of the game (NORMAL, TOURNAMENT)
		 * @param duration duration of a round
     */
    public Game(Mode mode, int difficulty, GameType gameType, double duration) {
        this.mode = mode;
        this.gameType = gameType;
        this.initializeBoard();
        this.board = board;
        this.duration = duration;
        switch (mode) {
            case HH:
                this.player1 = new HumanPlayer(this.board, SquareType.PAWN1, this.duration, this.gameType, this);
                this.player2 = new HumanPlayer(this.board, SquareType.PAWN2, this.duration, this.gameType, this);
                this.mode = Mode.HH;
                break;
            case HA:
                this.player1 = new HumanPlayer(this.board, SquareType.PAWN1, this.duration, this.gameType, this);
                this.player2 = new AutoPlayer(this.board, SquareType.PAWN2, difficulty);
                this.mode = Mode.HA;
                break;
            case AA:
                this.player1 = new AutoPlayer(this.board, SquareType.PAWN1, difficulty);
                this.player2 = new AutoPlayer(this.board, SquareType.PAWN2, difficulty);
                this.mode = Mode.AA;
                break;
        }

        switch (gameType) {
            case NORMAL:
                this.gameType = GameType.NORMAL;
                this.duration = -1;
                break;
            case TOURNAMENT:
                this.gameType = GameType.TOURNAMENT;
                this.duration = duration;
                break;
        }

    }


    /**
     * initialize the Board with correct values for the Square (in Board class) depending on the Data Structure we chose.
     */
    private void initializeBoard() {
        this.board = new Board();
    }

    public void start() {
        while (!verifFinParti(SquareType.PAWN1) && !verifFinParti(SquareType.PAWN2)) {
            this.player1.play();
            System.out.println(this.board.toString());
            this.player2.play();
            System.out.println(this.board.toString());
        }
        endOfGame();
    }

		/**
			* Displays if any player has won the game
			*/
    public void endOfGame() {

        boolean end = false;
        int i = 0;

        while (i <= 16 && !end) {

            if (this.board.getGrid()[0][i].getType() == SquareType.PAWN1) {
                end = true;
                System.out.println("PAWN 1 WON");
            }
            if (this.board.getGrid()[16][i].getType() == SquareType.PAWN2) {
                end = true;
                System.out.println("PAWN 2 WON");
            }
            i++;
        }
        System.out.println("Fin ");



    }

		/**
			* Checks if any player has won the game
			* @param joueur checks if joueur has won or not
			* @return true if the player has won, else false
			*/
    public boolean verifFinParti(SquareType joueur) {
        boolean ret = false;

        for (int i = 0; i <= 16; i++) {
            if (joueur == SquareType.PAWN1) {
                if (this.board.getGrid()[0][i].getType() == joueur) {
                    ret = true;
                }
            }
            if (joueur == SquareType.PAWN2) {
                if (this.board.getGrid()[16][i].getType() == joueur) {
                    ret = true;
                }
            }
        }
        return ret;
    }

		/**
			* Saves the actual state of the Board in a file when called
			*	@param filePath the file to save the Board in
			*/
    public void saveBoard(String filePath) {
        try {
            savePath = filePath;
            FileOutputStream fileOut = new FileOutputStream("data/saves/" + filePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this.board);
            objectOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

		/**
			* Loads a board from a file
			* @param filePath the file that loads the Board
			* @return the board loaded
			*/
    public Board loadGame(String filePath) {
        try {
            savePath = filePath;
            FileInputStream fileIn = new FileInputStream(savePath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Board ret = (Board) objectIn.readObject();
            this.board = ret;
            objectIn.close();
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

		/**
			* Returns the Game's board
			* @return the Board
			*/
    public Board getBoard() {
        return this.board;
    }

		/**
			* Sets the Game's board
			* @param board the Board
			*/
    public void setBoard(Board board) {
        this.board = board;
    }

		/**
			* Returns the Game's Mode
			* @return the Mode
			*/
    public Mode getMode() {
        return this.mode;
    }

		/**
			* Returns the Game's Difficulty
			* @return the Difficulty
			*/
    public int getDifficulty() {
        return this.difficulty;
    }

		/**
			* Returns the Game's Duration
			* @return the Duration
			*/
    public double getDuration() {
        return this.duration;
    }

		/**
			* Returns the Game's GameType
			* @return the GameType
			*/
    public GameType getGameType() {
        return this.gameType;
    }

		/**
			* Returns the Game's savePath
			* @return the savePath
			*/
    public String getSavePath() {
        return this.savePath;
    }

		/**
			* Returns the Game's Player1
			* @return the Player1
			*/
    public Player getPlayer1() {
        return player1;
    }

		/**
			* Returns the Game's Player2
			* @return the Player2
			*/
    public Player getPlayer2() {
        return player2;
    }

    /**Board object on which the two player are playing / interacting
     */
    private static Board board;
    /**First player
     */
    private Player player1;
    /**Second player
     */
    private Player player2;

    private int difficulty;
    private Mode mode;
    private GameType gameType;
    private double duration;
    private int NB_WALLS_J1 = 10;
    private int NB_WALLS_J2 = 10;
    private static String savePath;
}
