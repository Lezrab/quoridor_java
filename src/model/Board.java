package quoridor.model;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.*;
import java.util.*;

/**
 * Board class represents the table board. This is where the two Players are interacting (place a wall, move a pawn, ...)
 */
public class Board implements Serializable {

    /**
     * Board constructor initialize the attributes (2D array of Square objects)
     */
    public Board() {
        this.grid = new Square[17][17];
        for (int x = 0; x <= 16; x++) {
            for (int y = 0; y <= 16; y++) {
                this.grid[x][y] = new Square(x, y);
                if (x % 2 == 1 && y % 2 == 1) this.grid[x][y].setType(SquareType.NONE);
                else this.grid[x][y].setType(SquareType.FREE);
            }
        }

        this.grid[0][8].setType(SquareType.PAWN2);
        this.grid[16][8].setType(SquareType.PAWN1);
        System.out.println(this.toString());
        // [paire][paire] = case pion
        // [impaire][impaire]  = case inutile
        // le reste = case wall

    }

    /**
     * create a formatted String that represents the attributes of the object
     * @return String that represents the object
     */
    public String toString() {
        String ret = "    ";
        for (int x = 0; x <= 16; x++) {
            if (x < 10) ret += x + "  ";
            else ret += x + " ";
        }
        ret += "\n";
        for (int i = 0; i <= 16; i++) {
            if (i < 10) ret += i + "   ";
            else ret += i + "  ";
            for (int j = 0; j <= 16; j++) {

                ret += this.grid[i][j].toString() + " ";
            }
            ret += "\n";
        }
        return ret;

    }


    /**
     * The players choose the coordinates (x and y on the grid) of the position he wants to move to.
     * This method checks whether or not the movemenet is possible.
     * This method could be divided into 3 sub-methods :
     * 		- the one who checks if a wall is between the player and the coordinates given
     * 		- the one that checks if the opponent's pawn is at the given coordinates
     * 		- the one that checks if the given coordinate is outside the limits of the grid
     * @param x1 x coordinate where the Player wants to move
     * @param y1 y coordinate where the Player wants to move
     * @param x2 x coordinate where the Player wants to move
     * @param y2 y coordinate where the Player wants to move
		 * @return true if the position is correct
     */
    public boolean checkPosition(int x1, int y1, int x2, int y2) {
        boolean ret = false;
        //convertir les valeurs pour qu'elles soient propicent au tableau qu'on a crée
        /*x1 += x1;
        y1 += y1;
        x2 += x2;
        y2 += y2;*/
        //verif les valeurs/ Si le déplacement est disponible
        if (availablePosition(x1, y1, x2, y2)) {

            ret = true;

        }

        return ret;
    }

    /**
     * same as for the checkPosition method.
     * we must take into account the fact that the wall is two blocks high / long, and that it
     * can thus leave the grid even if we enter a correct coordinates in parameter.
     *
     * Implementation:
     * check that the coordinates do not exceed the grid board length-1, copy the 2D array, in the new 2D array add the wall, and finally
     * in the new grid we check that both players can still access at least one Square of the opponent
     * @param x x coordinate where the Player wants to place the wall
     * @param y y coordinate where the Player wants to place the wall
		 * @return true if the position is correct
     */
    public boolean checkWallPlacement(int x, int y) {

        boolean ret = false;
        if (this.grid[x][y].getType() == SquareType.FREE && (x % 2 != 0 || y % 2 != 0)) {
            if (x != 16 && y != 16) {
                //si le none est en haut et en bas
                if (this.grid[x + 1][y].getType() == SquareType.NONE) {
                    ret = true;
                    if (this.grid[x + 1][y - 1].getType() == SquareType.WALL && this.grid[x + 1][y + 1].getType() == SquareType.WALL) {

                        ret = false;

                    }
                }
                //si le none est a droite et a gauche
                if (this.grid[x][y + 1].getType() == SquareType.NONE) {
                    ret = true;
                    if (this.grid[x - 1][y + 1].getType() == SquareType.WALL && this.grid[x + 1][y + 1].getType() == SquareType.WALL) {

                        ret = false;

                    }
                }
            }
            if (x == 16 && y != 16) {
                if (this.grid[x - 1][y].getType() == SquareType.NONE) {
                    ret = true;
                    if (this.grid[x - 1][y - 1].getType() == SquareType.WALL && this.grid[x - 1][y + 1].getType() == SquareType.WALL) {

                        ret = false;

                    }
                }
            }
            if (y == 16 && x != 16) {
                if (this.grid[x][y - 1].getType() == SquareType.NONE) {
                    ret = true;
                    if (this.grid[x - 1][y - 1].getType() == SquareType.WALL && this.grid[x + 1][y - 1].getType() == SquareType.WALL) {

                        ret = false;

                    }
                }
            }
        }
        return ret;
    }

    /**
     * Pathfinding algorithm : Search if the Player can reach a position or if it is impossible
     * @param x x coordinate (origin)
     * @param y y coordinate (origin)
		 * @return if the player can reach the position, else false
     */
    public Square BFS(int x, int y) {
        Queue < Square > q = new LinkedList < Square > ();
        LinkedList < Square > visited = new LinkedList < Square > ();
        Square current = new Square(x, y);

        boolean finished = false;

        q.add(current);
        visited.add(current);

        while (!q.isEmpty() && !finished) {
            current = q.remove();
            for (Square p: this.verifSQ(current)) {
                if (!visited.contains(p)) {
                    visited.add(p);
                    q.add(p);
                    if (endVerif(p, this.grid[x][y].getType())) {
                        current = p;
                        finished = true;
                        break;
                    }
                }
            }
        }
        return current;
    }
    /**
     * Check if the position of the square searched by player is the end
     * @param sq the square to check
     * @param joueur color of the player
     * @return true if the actual sq is the end for the player
     */
    public boolean endVerif(Square sq, SquareType joueur) {
        boolean ret = false;
        if (joueur == SquareType.PAWN1) {

            if (sq.getX() == 0) {
                ret = true;

            }

        }
        if (joueur == SquareType.PAWN2) {


            if (sq.getX() == 16) {
                ret = true;

            }

        }

        return ret;

    }
    /**
     * check all coordinate of the square searched, to see if he can
     * move to a direction, send the coordinate of these direction
     * to an arrayList and will return it
     * @param sq square to check
     * @return the array of move that the player could do at this place
     */
    public ArrayList < Square > verifSQ(Square sq) {
        ArrayList < Square > listSquareACote = new ArrayList < Square > ();
        //Cas normal
        //haut
        if (sq.getX() - 2 >= 0) {
            if (this.checkPosition(sq.getX(), sq.getY(), sq.getX() - 2, sq.getY())) {
                listSquareACote.add(this.grid[sq.getX() - 2][sq.getY()]);
            }
        }
        //bas
        if (sq.getX() + 2 <= 16) {
            if (this.checkPosition(sq.getX(), sq.getY(), sq.getX() + 2, sq.getY())) {
                listSquareACote.add(this.grid[sq.getX() + 2][sq.getY()]);
            }
        }
        //gauche
        if (sq.getY() - 2 >= 0) {
            if (this.checkPosition(sq.getX(), sq.getY(), sq.getX(), sq.getY() - 2)) {
                listSquareACote.add(this.grid[sq.getX()][sq.getY() - 2]);
            }
        }
        //droite
        if (sq.getY() + 2 <= 16) {
            if (this.checkPosition(sq.getX(), sq.getY(), sq.getX(), sq.getY() + 2)) {
                listSquareACote.add(this.grid[sq.getX()][sq.getY() + 2]);
            }
        }
        //Cas ou le pion adverse bloque le chemin
        //haut
        if (sq.getX() - 4 >= 0) {
            if (this.checkPosition(sq.getX(), sq.getY(), sq.getX() - 4, sq.getY())) {
                listSquareACote.add(this.grid[sq.getX() - 4][sq.getY()]);
            }
        }
        //bas
        if (sq.getX() + 4 <= 16) {
            if (this.checkPosition(sq.getX(), sq.getY(), sq.getX() + 4, sq.getY())) {
                listSquareACote.add(this.grid[sq.getX() + 4][sq.getY()]);
            }
        }
        //gauche
        if (sq.getY() - 4 >= 0) {
            if (this.checkPosition(sq.getX(), sq.getY(), sq.getX(), sq.getY() - 4)) {
                listSquareACote.add(this.grid[sq.getX()][sq.getY() - 4]);
            }
        }
        //droite
        if (sq.getY() + 4 <= 16) {
            if (this.checkPosition(sq.getX(), sq.getY(), sq.getX(), sq.getY() + 4)) {
                listSquareACote.add(this.grid[sq.getX()][sq.getY() + 4]);
            }
        }
        return listSquareACote;
    }

    /**
     * check if there is a wall on the grid between the (x1, y1) and (x2, y2)
     * @param x1 first x coordinate (origin)
     * @param y1 first y coordinate (origin)
     * @param x2 second x coordinate (destination)
     * @param y2 second y coordinate (destination)
		 * @return true if there's a wall, else false
     */
    public boolean noWall(int x1, int y1, int x2, int y2) {

        boolean ret = true;
        //le joueur veut aller a gauche
        if (y2 - 2 == y1 && x2 == x1) {
            if (this.grid[x1][y1 + 1].getType() == SquareType.WALL) {

                ret = false;

            }
        }
        //le joueur veut aller a droite
        if (y2 + 2 == y1 && x2 == x1) {
            if (this.grid[x1][y1 - 1].getType() == SquareType.WALL) {

                ret = false;

            }
        }
        //le joueur veut aller en bas
        if (y2 == y1 && x2 + 2 == x1) {
            if (this.grid[x1 - 1][y1].getType() == SquareType.WALL) {

                ret = false;

            }
        }
        //le joueur veut aller en haut
        if (y2 == y1 && x2 - 2 == x1) {
            if (this.grid[x1 + 1][y1].getType() == SquareType.WALL) {

                ret = false;

            }
        }
        return ret;

    }

    /**
     * check if the type of the Square in the grid at [x][y] has a SquareType attribute equals to FREE
     * @param x1 x1 coordinate of the Square on the grid
     * @param y1 y1 coordinate of the Square on the grid
		 * @return if the position is free, else false
     */
    public boolean isFree(int x1, int y1) {
        boolean ret = false;
        if (this.grid[x1][y1].getType() == SquareType.FREE)
            ret = true;
        return ret;
    }

    /**
     * Checks if the position (x2, y2) is next to (x1, y1) and it is reacheable by the player
     * @param x1 first x coordinate (where the Player is)
     * @param y1 first y coordinate (where the Player is)
     * @param x2 second x coordinate (where the Player wants to go)
     * @param y2 second y coordinate (where the Player wants to go)
		 * @return true if the position is available, else false
     */
    public boolean availablePosition(int x1, int y1, int x2, int y2) {

        boolean ret = false;
        //verif si le joueur veux simplement passer sur une case voisine sans passer en diagonal ou sauter par dessus un joueur
        if (x2 - 2 == x1 && y2 == y1 || x2 + 2 == x1 && y2 == y1 || x2 == x1 && y2 + 2 == y1 || x2 == x1 && y2 - 2 == y1) {
            if (isFree(x2, y2)) {
                if (noWall(x1, y1, x2, y2)) {
                    ret = true;
                }
            }
        }
        //permet de vérif si le pion est à une case ou il pourrait sauté par dessus le pion adverse
        //cette partie ne prend pas en compte les cas limites, tel que le pion ait un x/y maximum/minimum
        // ou encore qu'il ait un x/y à l'avant derniere case/ la deuxieme
        if (x1 > 2 && x1 < 14 && y1 > 2 && y1 < 14) {
            //verif si les coordonnées sont correcte
            if (x2 - 4 == x1 && y2 == y1 || x2 + 4 == x1 && y2 == y1 || x2 == x1 && y2 + 4 == y1 || x2 == x1 && y2 - 4 == y1) {
                //Verif si la case en dessous est un pion
                if (this.grid[x1 + 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 + 2][y1].getType() == SquareType.PAWN2) {
                    //verif s'il n'y a pas de wall entre les cases, s'il y a un wall entre le pion1 et le pion2 et entre le pion2 et la case après lui
                    if (this.grid[x1 + 1][y1].getType() != SquareType.WALL && this.grid[x1 + 3][y1].getType() != SquareType.WALL) {
                        //le joueur peux jouer
                        ret = true;

                    }
                }
                //Verif si la case au-dessus est un pion
                if (this.grid[x1 - 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 - 2][y1].getType() == SquareType.PAWN2) {
                    if (this.grid[x1 - 1][y1].getType() != SquareType.WALL && this.grid[x1 - 3][y1].getType() != SquareType.WALL) {

                        ret = true;

                    }
                }
                //Verif si la case à droite est un pion
                if (this.grid[x1][y1 + 2].getType() == SquareType.PAWN1 || this.grid[x1][y1 + 2].getType() == SquareType.PAWN2) {
                    if (this.grid[x1][y1 + 1].getType() != SquareType.WALL && this.grid[x1][y1 + 3].getType() != SquareType.WALL) {

                        ret = true;

                    }
                }
                //Verif si la case a gauche est un pion
                if (this.grid[x1][y1 - 2].getType() == SquareType.PAWN1 || this.grid[x1][y1 - 2].getType() == SquareType.PAWN2) {
                    if (this.grid[x1][y1 - 1].getType() != SquareType.WALL && this.grid[x1][y1 - 3].getType() != SquareType.WALL) {

                        ret = true;

                    }
                }
            }
        }
        //regarde quand meme si les valeurs sont correcte pour pouvoir sauté par dessus quelqu'un
        if (x2 - 4 == x1 && y2 == y1 || x2 + 4 == x1 && y2 == y1 || x2 == x1 && y2 + 4 == y1 || x2 == x1 && y2 - 4 == y1) {
            //ne peut que verif la partie droite
            if (y1 <= 2) {
                if (this.grid[x1][y1 + 2].getType() == SquareType.PAWN1 || this.grid[x1][y1 + 2].getType() == SquareType.PAWN2) {
                    if (this.grid[x1][y1 + 1].getType() != SquareType.WALL && this.grid[x1][y1 + 3].getType() != SquareType.WALL) {

                        ret = true;

                    }
                }
                if (x1 <= 2) {
                    if (this.grid[x1 + 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 + 2][y1].getType() == SquareType.PAWN2) {
                        if (this.grid[x1 + 1][y1].getType() != SquareType.WALL && this.grid[x1 + 3][y1].getType() != SquareType.WALL) {

                            ret = true;
                        }
                    }
                }
                if (x1 >= 14) {
                    if (this.grid[x1 - 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 - 2][y1].getType() == SquareType.PAWN2) {
                        if (this.grid[x1 - 1][y1].getType() != SquareType.WALL && this.grid[x1 - 3][y1].getType() != SquareType.WALL) {

                            ret = true;

                        }
                    }
                }
                if (x1 > 2 && x1 < 14) {

                    if (this.grid[x1 - 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 + 2][y1].getType() == SquareType.PAWN2) {
                        if (this.grid[x1 - 1][y1].getType() != SquareType.WALL && this.grid[x1 - 3][y1].getType() != SquareType.WALL) {

                            ret = true;

                        }
                    }
                    if (this.grid[x1 + 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 + 2][y1].getType() == SquareType.PAWN2) {
                        if (this.grid[x1 + 1][y1].getType() != SquareType.WALL && this.grid[x1 + 3][y1].getType() != SquareType.WALL) {
                            ret = true;

                        }
                    }
                }

            }
            //ne peut que verif la partie gauche
            if (y1 >= 14) {
                if (this.grid[x1][y1 - 2].getType() == SquareType.PAWN1 || this.grid[x1][y1 - 2].getType() == SquareType.PAWN2) {
                    if (this.grid[x1][y1 - 1].getType() != SquareType.WALL && this.grid[x1][y1 - 3].getType() != SquareType.WALL) {

                        ret = true;

                    }
                }
                if (x1 <= 2) {
                    if (this.grid[x1 + 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 + 2][y1].getType() == SquareType.PAWN2) {
                        if (this.grid[x1 + 1][y1].getType() != SquareType.WALL && this.grid[x1 + 3][y1].getType() != SquareType.WALL) {

                            ret = true;
                        }
                    }
                }
                if (x1 >= 14) {
                    if (this.grid[x1 - 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 - 2][y1].getType() == SquareType.PAWN2) {
                        if (this.grid[x1 - 1][y1].getType() != SquareType.WALL && this.grid[x1 - 3][y1].getType() != SquareType.WALL) {

                            ret = true;

                        }
                    }
                }
                if (x1 > 2 && x1 < 14) {

                    if (this.grid[x1 - 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 - 2][y1].getType() == SquareType.PAWN2) {
                        if (this.grid[x1 - 1][y1].getType() != SquareType.WALL && this.grid[x1 - 3][y1].getType() != SquareType.WALL) {

                            ret = true;

                        }
                    }
                    if (this.grid[x1 + 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 + 2][y1].getType() == SquareType.PAWN2) {
                        if (this.grid[x1 + 1][y1].getType() != SquareType.WALL && this.grid[x1 + 3][y1].getType() != SquareType.WALL) {
                            ret = true;

                        }
                    }
                }
            }
            //ne peut que verif la partie basse
            if (x1 <= 2) {
                if (this.grid[x1 + 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 + 2][y1].getType() == SquareType.PAWN2) {
                    if (this.grid[x1 + 1][y1].getType() != SquareType.WALL && this.grid[x1 + 3][y1].getType() != SquareType.WALL) {

                        ret = true;
                    }
                }
                if (y1 <= 2) {
                    if (this.grid[x1][y1 + 2].getType() == SquareType.PAWN1 || this.grid[x1][y1 + 2].getType() == SquareType.PAWN2) {
                        if (this.grid[x1][y1 + 1].getType() != SquareType.WALL && this.grid[x1][y1 + 3].getType() != SquareType.WALL) {

                            ret = true;

                        }
                    }
                }
                if (y1 >= 14) {
                    if (this.grid[x1][y1 - 2].getType() == SquareType.PAWN1 || this.grid[x1][y1 - 2].getType() == SquareType.PAWN2) {
                        if (this.grid[x1][y1 - 1].getType() != SquareType.WALL && this.grid[x1][y1 - 3].getType() != SquareType.WALL) {

                            ret = true;

                        }
                    }
                }
                if (y1 > 2 && y1 < 14) {

                    if (this.grid[x1][y1 + 2].getType() == SquareType.PAWN1 || this.grid[x1][y1 + 2].getType() == SquareType.PAWN2) {
                        if (this.grid[x1][y1 + 1].getType() != SquareType.WALL && this.grid[x1][y1 + 3].getType() != SquareType.WALL) {

                            ret = true;

                        }
                    }
                    //Verif si la case a gauche est un pion
                    if (this.grid[x1][y1 - 2].getType() == SquareType.PAWN1 || this.grid[x1][y1 - 2].getType() == SquareType.PAWN2) {
                        if (this.grid[x1][y1 - 1].getType() != SquareType.WALL && this.grid[x1][y1 - 3].getType() != SquareType.WALL) {

                            ret = true;

                        }
                    }
                }
            }
            //ne peut que verif la partie haute
            if (x1 >= 14) {
                if (this.grid[x1 - 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 - 2][y1].getType() == SquareType.PAWN2) {
                    if (this.grid[x1 - 1][y1].getType() != SquareType.WALL && this.grid[x1 - 3][y1].getType() != SquareType.WALL) {

                        ret = true;

                    }
                }
                if (y1 <= 2) {
                    if (this.grid[x1][y1 + 2].getType() == SquareType.PAWN1 || this.grid[x1][y1 + 2].getType() == SquareType.PAWN2) {
                        if (this.grid[x1][y1 + 1].getType() != SquareType.WALL && this.grid[x1][y1 + 3].getType() != SquareType.WALL) {

                            ret = true;

                        }
                    }
                }
                if (y1 >= 14) {
                    if (this.grid[x1][y1 - 2].getType() == SquareType.PAWN1 || this.grid[x1][y1 - 2].getType() == SquareType.PAWN2) {
                        if (this.grid[x1][y1 - 1].getType() != SquareType.WALL && this.grid[x1][y1 - 3].getType() != SquareType.WALL) {

                            ret = true;

                        }
                    }
                }
                if (y1 > 2 && y1 < 14) {

                    if (this.grid[x1][y1 + 2].getType() == SquareType.PAWN1 || this.grid[x1][y1 + 2].getType() == SquareType.PAWN2) {
                        if (this.grid[x1][y1 + 1].getType() != SquareType.WALL && this.grid[x1][y1 + 3].getType() != SquareType.WALL) {

                            ret = true;

                        }
                    }
                    //Verif si la case a gauche est un pion
                    if (this.grid[x1][y1 - 2].getType() == SquareType.PAWN1 || this.grid[x1][y1 - 2].getType() == SquareType.PAWN2) {
                        if (this.grid[x1][y1 - 1].getType() != SquareType.WALL && this.grid[x1][y1 - 3].getType() != SquareType.WALL) {

                            ret = true;

                        }
                    }
                }

            }
        }

        /* Verif la diagonal :

        	- En tout premier il faut que les deux pions soient cote à cote
        	- Il faut que la case WALL entre les deux pions soit VIDE
        	( verif si les coordonnées sont bien choisi par rapport au pion et à la case ou il veut aller)
        	- S'il faut descendre par rapport au pion qui veut bouger pour verif s'il n'y a pas de le WALL alors on doit verif
        	  si la case au dessus de la case ou on veut aller en diagonal est indisponible ( donc il y a un  wall)
        	- S'il faut monté par rapport au pion qui veut bouger pour verif s'il n'y a pas de le Wall alors on doit verif
        	  si la case en dessous de la case ou on veut aller en diagonal est indisponible ( donc il y a un  wall)

         Si ces conditions sont respectées, Alors seulement le joueur pourra passer en diagonale.
        */
        //conditions s'il le pion n'est pas dans les cas limites
        if (x1 > 2 && x1 < 14 && y1 > 2 && y1 < 14) {
            //Verif si l'autre pion est au dessus du pion du joueur
            if (this.grid[x1 - 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 - 2][y1].getType() == SquareType.PAWN2) {
                if (this.grid[x1 - 1][y1].getType() != SquareType.WALL) {
                    //diagonal droite
                    if (y2 - 2 == y1 && x2 + 2 == x1) {
                        if (this.grid[x2 + 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 + 1].getType() == SquareType.WALL) {

                            ret = true;
                        }
                    }
                    //diagonal gauche
                    if (y2 + 2 == y1 && x2 + 2 == x1) {
                        if (this.grid[x2 + 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 - 1].getType() == SquareType.WALL) {

                            ret = true;
                        }
                    }
                }
            }
            //Verif si l'autre pion est en dessous du pion du joueur
            if (this.grid[x1 + 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 + 2][y1].getType() == SquareType.PAWN2) {
                if (this.grid[x1 + 1][y1].getType() != SquareType.WALL) {
                    //diagonal droite
                    if (y2 - 2 == y1 && x2 - 2 == x1) {
                        if (this.grid[x2 - 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 + 1].getType() == SquareType.WALL) {

                            ret = true;
                        }
                    }
                    //diagonal gauche

                    if (y2 + 2 == y1 && x2 - 2 == x1) {
                        if (this.grid[x2 - 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 - 1].getType() == SquareType.WALL) {

                            ret = true;
                        }
                    }
                }
            }
            //Verif si l'autre pion est a gauche du pion du joueur
            if (this.grid[x1][y1 - 2].getType() == SquareType.PAWN1 || this.grid[x1][y1 - 2].getType() == SquareType.PAWN2) {
                if (this.grid[x1][y1 - 1].getType() != SquareType.WALL) {
                    //diagonal droite
                    if (y2 + 2 == y1 && x2 + 2 == x1) {
                        if (this.grid[x2][y2 + 1].getType() == SquareType.WALL || this.grid[x1 - 1][y1].getType() == SquareType.WALL) {

                            ret = true;
                        }
                    }
                    //diagonal gauche
                    if (y2 + 2 == y1 && x2 - 2 == x1) {
                        if (this.grid[x2][y2 + 1].getType() == SquareType.WALL || this.grid[x1 + 1][y1].getType() == SquareType.WALL) {

                            ret = true;
                        }
                    }
                }

            }
            //Verif si l'autre pion est a droite du pion du joueur
            if (this.grid[x1][y1 + 2].getType() == SquareType.PAWN1 || this.grid[x1][y1 + 2].getType() == SquareType.PAWN2) {
                if (this.grid[x1][y1 + 1].getType() != SquareType.WALL) {
                    //diagonal gauche
                    if (y2 - 2 == y1 && x2 + 2 == x1) {
                        if (this.grid[x2][y2 - 1].getType() == SquareType.WALL || this.grid[x1 - 1][y1].getType() == SquareType.WALL) {

                            ret = true;
                        }
                    }
                    //diagonal droite
                    if (y2 - 2 == y1 && x2 - 2 == x1) {
                        if (this.grid[x2][y2 - 1].getType() == SquareType.WALL || this.grid[x1 + 1][y1].getType() == SquareType.WALL) {

                            ret = true;
                        }
                    }
                }

            }

        }

        //verif des cas limites
        if (y1 <= 2) {
            //cas où il ne peut pas verif la gauche
            //droite
            if (this.grid[x1][y1 + 2].getType() == SquareType.PAWN1 || this.grid[x1][y1 + 2].getType() == SquareType.PAWN2) {
                if (this.grid[x1][y1 + 1].getType() != SquareType.WALL) {
                    //diagonal droite
                    if (y2 - 2 == y1 && x2 + 2 == x1) {
                        if (this.grid[x2][y2 - 1].getType() == SquareType.WALL || this.grid[x1 - 1][y1].getType() == SquareType.WALL) {

                            ret = true;
                        }
                    }
                    //diagonal gauche
                    if (y2 - 2 == y1 && x2 - 2 == x1) {
                        if (this.grid[x2][y2 - 1].getType() == SquareType.WALL || this.grid[x1 + 1][y1].getType() == SquareType.WALL) {

                            ret = true;
                        }
                    }
                }

            }
            //attention a ne pas verif le haut si x == 0
            if (x1 <= 2) {
                //bas
                if (this.grid[x1 + 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 + 2][y1].getType() == SquareType.PAWN2) {
                    if (this.grid[x1 + 1][y1].getType() != SquareType.WALL) {
                        //diagonal droite
                        if (y2 - 2 == y1 && x2 - 2 == x1) {
                            if (this.grid[x2 - 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 + 1].getType() == SquareType.WALL) {

                                ret = true;
                            }
                        }
                        //diagonal gauche
                        if (y2 + 2 == y1 && x2 - 2 == x1) {
                            if (this.grid[x2 - 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 - 1].getType() == SquareType.WALL) {

                                ret = true;
                            }
                        }
                    }
                }
            }
            //attention a ne pas verif le bas si x == 16
            if (x1 >= 14) {
                //Haut
                if (this.grid[x1 - 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 - 2][y1].getType() == SquareType.PAWN2) {
                    if (this.grid[x1 - 1][y1].getType() != SquareType.WALL) {
                        //diagonal droite
                        if (y2 - 2 == y1 && x2 + 2 == x1) {
                            if (this.grid[x2 + 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 + 1].getType() == SquareType.WALL) {

                                ret = true;
                            }
                        }
                        //diagonal gauche
                        if (y2 + 2 == y1 && x2 + 2 == x1) {
                            if (this.grid[x2 + 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 - 1].getType() == SquareType.WALL) {

                                ret = true;
                            }
                        }
                    }
                }
                //verif le haut et bas
                if (x1 < 14 && x1 > 2) {
                    if (this.grid[x1 + 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 + 2][y1].getType() == SquareType.PAWN2) {
                        if (this.grid[x1 + 1][y1].getType() != SquareType.WALL) {
                            //diagonal droite
                            if (y2 - 2 == y1 && x2 - 2 == x1) {
                                if (this.grid[x2 - 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 + 1].getType() == SquareType.WALL) {

                                    ret = true;
                                }
                            }
                            //diagonal gauche
                            if (y2 + 2 == y1 && x2 - 2 == x1) {
                                if (this.grid[x2 - 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 - 1].getType() == SquareType.WALL) {

                                    ret = true;
                                }
                            }
                        }
                    }
                    if (this.grid[x1 - 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 - 2][y1].getType() == SquareType.PAWN2) {
                        if (this.grid[x1 - 1][y1].getType() != SquareType.WALL) {
                            //diagonal droite
                            if (y2 - 2 == y1 && x2 + 2 == x1) {
                                if (this.grid[x2 + 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 + 1].getType() == SquareType.WALL) {

                                    ret = true;
                                }
                            }
                            //diagonal gauche
                            if (y2 + 2 == y1 && x2 + 2 == x1) {
                                if (this.grid[x2 + 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 - 1].getType() == SquareType.WALL) {

                                    ret = true;
                                }
                            }
                        }
                    }

                }
            }
        }
        if (y1 >= 14) {
            //cas ou il ne peut que verif a gauche et en bas
            //gauche
            if (this.grid[x1][y1 - 2].getType() == SquareType.PAWN1 || this.grid[x1][y1 - 2].getType() == SquareType.PAWN2) {
                if (this.grid[x1][y1 - 1].getType() != SquareType.WALL) {
                    //diagonal droite
                    if (y2 + 2 == y1 && x2 - 2 == x1) {
                        if (this.grid[x2][y2 + 1].getType() == SquareType.WALL || this.grid[x1 - 1][y1].getType() == SquareType.WALL) {

                            ret = true;
                        }
                    }
                    //diagonal gauche
                    if (y2 + 2 == y1 && x2 + 2 == x1) {
                        if (this.grid[x2][y2 + 1].getType() == SquareType.WALL || this.grid[x1 + 1][y1].getType() == SquareType.WALL) {

                            ret = true;
                        }
                    }
                }

            }
            if (x1 <= 2) {
                if (this.grid[x1 + 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 + 2][y1].getType() == SquareType.PAWN2) {
                    if (this.grid[x1 + 1][y1].getType() != SquareType.WALL) {
                        //diagonal droite
                        if (y2 - 2 == y1 && x2 - 2 == x1) {
                            if (this.grid[x2 - 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 + 1].getType() == SquareType.WALL) {

                                ret = true;
                            }
                        }
                        //diagonal gauche
                        if (y2 + 2 == y1 && x2 - 2 == x1) {
                            if (this.grid[x2 - 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 - 1].getType() == SquareType.WALL) {

                                ret = true;
                            }
                        }
                    }
                }
            }
            //cas ou il ne peut que verif a gauche et en haut
            if (x1 >= 14) {
                //Haut
                if (this.grid[x1 - 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 - 2][y1].getType() == SquareType.PAWN2) {
                    if (this.grid[x1 - 1][y1].getType() != SquareType.WALL) {
                        //diagonal droite
                        if (y2 - 2 == y1 && x2 + 2 == x1) {
                            if (this.grid[x2 + 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 + 1].getType() == SquareType.WALL) {

                                ret = true;
                            }
                        }
                        //diagonal gauche
                        if (y2 + 2 == y1 && y2 + 2 == x1) {
                            if (this.grid[x2 + 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 - 1].getType() == SquareType.WALL) {

                                ret = true;
                            }
                        }
                    }
                }
            }
            if (x1 < 14 && x1 > 2) {
                if (this.grid[x1 + 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 + 2][y1].getType() == SquareType.PAWN2) {
                    if (this.grid[x1 + 1][y1].getType() != SquareType.WALL) {
                        //diagonal droite
                        if (y2 - 2 == y1 && x2 - 2 == x1) {
                            if (this.grid[x2 - 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 + 1].getType() == SquareType.WALL) {

                                ret = true;
                            }
                        }
                        //diagonal gauche
                        if (y2 + 2 == y1 && x2 - 2 == x1) {
                            if (this.grid[x2 - 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 - 1].getType() == SquareType.WALL) {

                                ret = true;
                            }
                        }
                    }
                }

                //Haut
                if (this.grid[x1 - 2][y1].getType() == SquareType.PAWN1 || this.grid[x1 - 2][y1].getType() == SquareType.PAWN2) {
                    if (this.grid[x1 - 1][y1].getType() != SquareType.WALL) {
                        //diagonal droite
                        if (y2 - 2 == y1 && x2 + 2 == x1) {
                            if (this.grid[x2 + 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 + 1].getType() == SquareType.WALL) {

                                ret = true;
                            }
                        }
                        //diagonal gauche
                        if (y2 + 2 == y1 && y2 + 2 == x1) {
                            if (this.grid[x2 + 1][y2].getType() == SquareType.WALL || this.grid[x1][y1 - 1].getType() == SquareType.WALL) {

                                ret = true;
                            }
                        }
                    }
                }
            }

        }

        return ret;
    }
    /**
     * return the table, the grid use in the all board
     * @return the grid
     */
    public Square[][] getGrid() {
        return this.grid;
    }

    /**grid is a 2D array of Square that represents the Game happening with the two players that are interacting
     */
    private Square[][] grid;

}
