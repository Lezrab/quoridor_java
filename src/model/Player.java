package quoridor.model;
import java.io.Serializable;
/**
 * Player class is an abstract class that describe the basic behaviour of a Player (HumanPlayer or AutoPlayer)
 */
public abstract class Player implements Serializable {

    /**
     * Player constructor checks attributes and define the attributes
     * @param board Board object to interact with during the game
		 * @param type SquareType of the player (player1 or player 2)
     */
    public Player(Board board, SquareType type) {
        this.board = board;
        this.type = type;
    }

    /**
     * Player plays its turn. This method does every needed task or subtask to manage the turn of a player
     */
    public abstract void play();
    public abstract SquareType getTypeJ();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name != null) this.name = name;
        else System.err.println("[ERROR] setName in Player : can't set a null name.");
    }

    /**name of the Player
     */
    protected String name;
    /**Board on which the player is playing
     */
    protected Board board;
    protected SquareType type;

}
