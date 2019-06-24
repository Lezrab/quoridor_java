package quoridor.model;
import java.io.Serializable;

/**
 * Square class is only created by the Board class.
 * A Square represent a tile on the grid of the Board of the game on which the two players are playing against each other
 */
public class Square implements Serializable {

    /**x value of the Square on the Board
     */
    private int x;
    /**y value of the Square on the Board
     */
    private int y;
    /**type of the Square, represent what the square is (wall, pawn, ...)
     */
    private SquareType type;

    /**
     * Square constructor initialize the x and y attributes
     * @param x x attribute value
     * @param y y attribute value
     */
    public Square(int x, int y) {
        if (x >= 0 && x <= 16) {
            if (y >= 0 && y <= 16) {
                this.x = x;
                this.y = y;
            } else System.out.println("Constructor Square : value Y out of limits");
        } else System.out.println("Constructor Square : value X out of limits");
    }

    /**
     * Checks if the Square is FREE or not
		 * @return true if it's FREE, else false
     */
    public boolean isFree() {
        return type == SquareType.FREE;
    }

    /**
     * getter for x attribute
     * @return x attribute value
     */
    public int getX() {
        return this.x;
    }

    /**
     * setter for x attribute
     * @param x new x attribute value
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * getter for y attribute
     * @return y attribute value
     */
    public int getY() {
        return this.y;
    }

    /**
     * setter for y attribute
     * @param y new y attribute value
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * getter for squareType attribute
     * @return squareType attribute
     */
    public SquareType getType() {
        return this.type;
    }

    /**
     * setter for squareType attribute
     * @param squareType new squareTyoe attribute value
     */
    public void setType(SquareType squareType) {
        this.type = squareType;
    }

		/**
			* converts and display SquareType as a String
			* @return the SquareType as a String
			*/
    public String toString() {
        String ret = "";
        if (this.getType() == SquareType.WALL && this.x % 2 == 0 && this.y % 2 != 0) ret = " \u25A0 ";
        if (this.getType() == SquareType.WALL && this.x % 2 == 1) ret = "\u25A0   ";
        if (this.getType() == SquareType.FREE && this.x % 2 == 0 && this.y % 2 != 0) ret = " . ";
        if (this.getType() == SquareType.FREE && this.x % 2 == 1) ret = ".   ";
        if (this.getType() == SquareType.PAWN1) ret = "W";
        if (this.getType() == SquareType.PAWN2) ret = "B";
        if (this.getType() == SquareType.FREE && this.x % 2 == 0 && this.y % 2 == 0) ret = "O";
        return ret;
    }
}
