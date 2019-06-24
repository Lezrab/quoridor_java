package quoridor.model;

/**
 * Enumeration of possibles state for a Square Object
 */
public enum SquareType {
	WALL, // the Square object is a wall
	PAWN1, // the Square object contains a pawn
	PAWN2,
	NONE, // the Square object is not playable
	FREE, // the Square object is free for a player to move to
	OVERRIDDEN // the Square object is not available
}
