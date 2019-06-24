package quoridor.model;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.swing.*;
import java.io.*;

/**
 * AutoPlayer class extends Player class and play as a primitive artificial intelligence (randomly)
 */
public class AutoPlayer extends Player implements Serializable {
    private int difficulty;
    private SquareType joueur;
    private int wallAA1;
    private int wallAA2;
    /**
     * AutoPlayer's constructor initialize its attributes
     * @param board the board that the Player is attached to
     * @param squareType squareType of the Player (player 1 or 2)
     * @param difficulty difficulty of the AutoPlayer
     */
    public AutoPlayer(Board board, SquareType squareType, int difficulty) {
        super(board, squareType);
        if ((difficulty >= 1) && (difficulty <= 2)) {
            this.difficulty = difficulty;
            this.joueur = squareType;
            this.wallAA1 = 10;
            this.wallAA2 = 10;
        }
    }

    /**
     * Returns the SquareType of a player
     * @return joueur the squareType
     */
    public SquareType getTypeJ() {
        return this.joueur;
    }

    /**
     * play the turn of the player. Randomly generate the inputs to play (basic / primitive AI)
     */
    public void play() {
        double randomJ1 = Math.random();
        double randomJ2 = Math.random();

        if (this.difficulty == 1) {
            boolean move = false;
            if (this.joueur == SquareType.PAWN1) {
                int x1 = -1;
                int y1 = -1;
                for (int y = 0; y < 17; y++) {
                    for (int x = 0; x < 17; x++) {
                        if (this.board.getGrid()[y][x].getType() == SquareType.PAWN1) {
                            x1 = y;
                            y1 = x;
                        }
                    }
                }
                if (this.wallAA1 > 0) {
                    if (randomJ1 >= 0.5) { // Cas choisi aléatoirement : le bot se déplace
                        while (!move) {
                            double randomMove = Math.random();
                            //a droite
                            if ((randomMove >= 0) && (randomMove <= 0.20)) {
                                if (y1 + 2 <= 16) {

                                    if (this.board.getGrid()[x1][y1 + 2].getType() == SquareType.PAWN2) {
                                        if (y1 + 4 <= 16) {
                                            if (this.board.checkPosition(x1, y1, x1, y1 + 4) && !move) {
                                                this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                                this.board.getGrid()[x1][y1 + 4].setType(SquareType.PAWN1);
                                                move = true;
                                            }
                                        }
                                    } else if (this.board.checkPosition(x1, y1, x1, y1 + 2) && !move) {

                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1][y1 + 2].setType(SquareType.PAWN1);
                                        move = true;

                                    }
                                }
                                //
                                //
                                //
                                //a gauche
                            } else if ((randomMove > 0.20) && (randomMove <= 0.40)) {
                                if (y1 - 2 >= 0) {
                                    if (this.board.getGrid()[x1][y1 - 2].getType() == SquareType.PAWN2) {
                                        if (y1 - 4 >= 0) {
                                            if (this.board.checkPosition(x1, y1, x1, y1 - 4) && !move) {
                                                this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                                this.board.getGrid()[x1][y1 - 4].setType(SquareType.PAWN1);
                                                move = true;
                                            }
                                        }
                                    } else if (this.board.checkPosition(x1, y1, x1, y1 - 2) && !move) {

                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1][y1 - 2].setType(SquareType.PAWN1);
                                        move = true;

                                    }
                                }
                                //
                                //
                                //
                                //en bas
                            } else if ((randomMove > 0.40) && (randomMove <= 0.60)) {
                                if (x1 + 2 <= 16) {
                                    if (this.board.getGrid()[x1 + 2][y1].getType() == SquareType.PAWN2) {
                                        if (x1 + 4 <= 16) {
                                            if (this.board.checkPosition(x1, y1, x1 + 4, y1) && !move) {
                                                this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                                this.board.getGrid()[x1 + 4][y1].setType(SquareType.PAWN1);
                                                move = true;
                                            }
                                        }
                                    } else if (this.board.checkPosition(x1, y1, x1 + 2, y1) && !move) {

                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1 + 2][y1].setType(SquareType.PAWN1);
                                        move = true;

                                    }
                                }
                                //en haut
                            } else if ((randomMove > 0.60) && (randomMove <= 0.80)) {
                                if (x1 - 2 >= 0) {
                                    if (this.board.getGrid()[x1 - 2][y1].getType() == SquareType.PAWN2) {
                                        if (x1 - 4 >= 0) {
                                            if (this.board.checkPosition(x1, y1, x1 - 4, y1) && !move) {
                                                this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                                this.board.getGrid()[x1 - 4][y1].setType(SquareType.PAWN1);
                                                move = true;
                                            }
                                        }
                                    } else if (this.board.checkPosition(x1, y1, x1 - 2, y1) && !move) {

                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1 - 2][y1].setType(SquareType.PAWN1);
                                        move = true;

                                    }
                                }
                                //
                                //
                                //
                                //diagonal
                            } else {
                                //diagonal haute droite
                                if (x1 - 2 >= 0 && y1 + 2 <= 16) {
                                    if (this.board.checkPosition(x1, y1, x1 - 2, y1 + 2) && !move) {
                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1 - 2][y1 + 2].setType(SquareType.PAWN1);
                                        move = true;
                                    }
                                }
                                //diagonal hautre gauche
                                if (x1 - 2 >= 0 && y1 - 2 >= 0) {
                                    if (this.board.checkPosition(x1, y1, x1 - 2, y1 - 2) && !move) {
                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1 - 2][y1 - 2].setType(SquareType.PAWN1);
                                        move = true;
                                    }
                                }
                                //diagonal basse gauche
                                if (x1 + 2 <= 16 && y1 - 2 >= 0) {
                                    if (this.board.checkPosition(x1, y1, x1 + 2, y1 - 2) && !move) {
                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1 + 2][y1 - 2].setType(SquareType.PAWN1);
                                        move = true;
                                    }
                                }
                                //diagonal basse droite
                                if (x1 + 2 <= 16 && y1 + 2 <= 16) {
                                    if (this.board.checkPosition(x1, y1, x1 + 2, y1 + 2) && !move) {
                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1 + 2][y1 + 2].setType(SquareType.PAWN1);
                                        move = true;
                                    }
                                }


                            }
                            randomJ1 = Math.random();
                        }

                    } else if (this.wallAA1 > 0 && randomJ1 < 0.5) { // Cas choisi aléatoirement : le bot place un mur
                        boolean aaPlaceUnMur = false;
                        while (!aaPlaceUnMur) {
                            int xW = (int)(Math.random() * 17);
                            int yW = (int)(Math.random() * 17);
                            while (!this.board.checkWallPlacement(xW, yW)) {
                                xW = (int)(Math.random() * 17);
                                yW = (int)(Math.random() * 17);
                            }
                            System.out.println("le xW random :" + xW);
                            System.out.println("le yW random :" + yW);
                            int xW2 = -1;
                            int yW2 = -1;
                            boolean verif = false;
                            if (xW != 16 && yW != 16) {
                                if (xW % 2 == 1) {
                                    xW2 = xW;
                                    yW2 = yW + 2;
                                    if (this.board.getGrid()[xW2][yW2].getType() == SquareType.FREE) {

                                        verif = true;
                                    }
                                }
                                if (xW % 2 == 0) {
                                    xW2 = xW + 2;
                                    yW2 = yW;
                                    if (this.board.getGrid()[xW2][yW2].getType() == SquareType.FREE) {

                                        verif = true;
                                    }
                                }
                            }
                            if (xW == 16 && yW != 16) {
                                xW2 = xW - 2;
                                yW2 = yW;
                                if (this.board.getGrid()[xW2][yW2].getType() == SquareType.FREE) {

                                    verif = true;
                                }
                            }
                            if (xW != 16 && yW == 16) {
                                xW2 = xW;
                                yW2 = yW - 2;
                                if (this.board.getGrid()[xW2][yW2].getType() == SquareType.FREE) {

                                    verif = true;
                                }
                            }

                            if (verif) {
                                this.wallAA1--;
                                this.board.getGrid()[xW][yW].setType(SquareType.WALL);
                                this.board.getGrid()[xW2][yW2].setType(SquareType.WALL);
                                if (this.board.BFS(x1, y1).getX() != 0) {
                                    this.wallAA1++;
                                    this.board.getGrid()[xW][yW].setType(SquareType.FREE);
                                    this.board.getGrid()[xW2][yW2].setType(SquareType.FREE);
                                } else aaPlaceUnMur = true;
                            }
                        }
                    }
                } else {

                    while (!move) {
                        double randomMove = Math.random();
                        //a droite
                        if ((randomMove >= 0) && (randomMove <= 0.20)) {
                            if (y1 + 2 <= 16) {

                                if (this.board.getGrid()[x1][y1 + 2].getType() == SquareType.PAWN2) {
                                    if (y1 + 4 <= 16) {
                                        if (this.board.checkPosition(x1, y1, x1, y1 + 4) && !move) {
                                            this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                            this.board.getGrid()[x1][y1 + 4].setType(SquareType.PAWN1);
                                            move = true;
                                        }
                                    }
                                } else if (this.board.checkPosition(x1, y1, x1, y1 + 2) && !move) {

                                    this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                    this.board.getGrid()[x1][y1 + 2].setType(SquareType.PAWN1);
                                    move = true;

                                }
                            }
                            //
                            //
                            //
                            //a gauche
                        } else if ((randomMove > 0.20) && (randomMove <= 0.40)) {
                            if (y1 - 2 >= 0) {
                                if (this.board.getGrid()[x1][y1 - 2].getType() == SquareType.PAWN2) {
                                    if (y1 - 4 >= 0) {
                                        if (this.board.checkPosition(x1, y1, x1, y1 - 4) && !move) {
                                            this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                            this.board.getGrid()[x1][y1 - 4].setType(SquareType.PAWN1);
                                            move = true;
                                        }
                                    }
                                } else if (this.board.checkPosition(x1, y1, x1, y1 - 2) && !move) {

                                    this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                    this.board.getGrid()[x1][y1 - 2].setType(SquareType.PAWN1);
                                    move = true;

                                }
                            }
                            //
                            //
                            //
                            //en bas
                        } else if ((randomMove > 0.40) && (randomMove <= 0.60)) {
                            if (x1 + 2 <= 16) {
                                if (this.board.getGrid()[x1 + 2][y1].getType() == SquareType.PAWN2) {
                                    if (x1 + 4 <= 16) {
                                        if (this.board.checkPosition(x1, y1, x1 + 4, y1) && !move) {
                                            this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                            this.board.getGrid()[x1 + 4][y1].setType(SquareType.PAWN1);
                                            move = true;
                                        }
                                    }
                                } else if (this.board.checkPosition(x1, y1, x1 + 2, y1) && !move) {

                                    this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                    this.board.getGrid()[x1 + 2][y1].setType(SquareType.PAWN1);
                                    move = true;

                                }
                            }
                            //en haut
                        } else if ((randomMove > 0.60) && (randomMove <= 0.80)) {
                            if (x1 - 2 >= 0) {
                                if (this.board.getGrid()[x1 - 2][y1].getType() == SquareType.PAWN2) {
                                    if (x1 - 4 >= 0) {
                                        if (this.board.checkPosition(x1, y1, x1 - 4, y1) && !move) {
                                            this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                            this.board.getGrid()[x1 - 4][y1].setType(SquareType.PAWN1);
                                            move = true;
                                        }
                                    }
                                } else if (this.board.checkPosition(x1, y1, x1 - 2, y1) && !move) {

                                    this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                    this.board.getGrid()[x1 - 2][y1].setType(SquareType.PAWN1);
                                    move = true;

                                }
                            }
                            //
                            //
                            //
                            //diagonal
                        } else {
                            //diagonal haute droite
                            if (x1 - 2 >= 0 && y1 + 2 <= 16) {
                                if (this.board.checkPosition(x1, y1, x1 - 2, y1 + 2) && !move) {
                                    this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                    this.board.getGrid()[x1 - 2][y1 + 2].setType(SquareType.PAWN1);
                                    move = true;
                                }
                            }
                            //diagonal hautre gauche
                            if (x1 - 2 >= 0 && y1 - 2 >= 0) {
                                if (this.board.checkPosition(x1, y1, x1 - 2, y1 - 2) && !move) {
                                    this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                    this.board.getGrid()[x1 - 2][y1 - 2].setType(SquareType.PAWN1);
                                    move = true;
                                }
                            }
                            //diagonal basse gauche
                            if (x1 + 2 <= 16 && y1 - 2 >= 0) {
                                if (this.board.checkPosition(x1, y1, x1 + 2, y1 - 2) && !move) {
                                    this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                    this.board.getGrid()[x1 + 2][y1 - 2].setType(SquareType.PAWN1);
                                    move = true;
                                }
                            }
                            //diagonal basse droite
                            if (x1 + 2 <= 16 && y1 + 2 <= 16) {
                                if (this.board.checkPosition(x1, y1, x1 + 2, y1 + 2) && !move) {
                                    this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                    this.board.getGrid()[x1 + 2][y1 + 2].setType(SquareType.PAWN1);
                                    move = true;
                                }
                            }


                        }
                        randomJ1 = Math.random();
                    }
                }
            }
            try {
                Thread.sleep(250);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.joueur == SquareType.PAWN2) {
                int x1 = -1;
                int y1 = -1;
                for (int y = 0; y < 17; y++) {
                    for (int x = 0; x < 17; x++) {
                        if (this.board.getGrid()[y][x].getType() == SquareType.PAWN2) {
                            x1 = y;
                            y1 = x;
                        }
                    }
                }
                if (this.wallAA2 > 0) {
                    if (randomJ2 >= 0.5) { // Cas choisi aléatoirement : le bot se déplace
                        while (!move) {
                            double randomMove = Math.random();
                            //a droite
                            if ((randomMove >= 0) && (randomMove <= 0.20)) {

                                if (y1 + 2 <= 16) {

                                    if (this.board.getGrid()[x1][y1 + 2].getType() == SquareType.PAWN1) {
                                        if (y1 + 4 <= 16) {
                                            if (this.board.checkPosition(x1, y1, x1, y1 + 4) && !move) {
                                                this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                                this.board.getGrid()[x1][y1 + 4].setType(SquareType.PAWN2);
                                                move = true;
                                            }
                                        }
                                    } else if (this.board.checkPosition(x1, y1, x1, y1 + 2) && !move) {

                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1][y1 + 2].setType(SquareType.PAWN2);
                                        move = true;

                                    }
                                }
                                //
                                //
                                //
                                //a gauche
                            } else if ((randomMove > 0.20) && (randomMove <= 0.40)) {
                                if (y1 - 2 >= 0) {
                                    if (this.board.getGrid()[x1][y1 - 2].getType() == SquareType.PAWN1) {
                                        if (y1 - 4 >= 0) {
                                            if (this.board.checkPosition(x1, y1, x1, y1 - 4) && !move) {
                                                this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                                this.board.getGrid()[x1][y1 - 4].setType(SquareType.PAWN2);
                                                move = true;
                                            }
                                        }
                                    } else if (this.board.checkPosition(x1, y1, x1, y1 - 2) && !move) {

                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1][y1 - 2].setType(SquareType.PAWN2);
                                        move = true;

                                    }
                                }
                                //
                                //
                                //
                                //en bas
                            } else if ((randomMove > 0.40) && (randomMove <= 0.60)) {
                                if (x1 + 2 <= 16) {
                                    if (this.board.getGrid()[x1 + 2][y1].getType() == SquareType.PAWN1) {
                                        if (x1 + 4 <= 16) {
                                            if (this.board.checkPosition(x1, y1, x1 + 4, y1) && !move) {
                                                this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                                this.board.getGrid()[x1 + 4][y1].setType(SquareType.PAWN2);
                                                move = true;
                                            }
                                        }
                                    } else if (this.board.checkPosition(x1, y1, x1 + 2, y1) && !move) {

                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1 + 2][y1].setType(SquareType.PAWN2);
                                        move = true;

                                    }
                                }
                                //en haut
                            } else if ((randomMove > 0.60) && (randomMove <= 0.80)) {
                                if (x1 - 2 >= 0) {
                                    if (this.board.getGrid()[x1 - 2][y1].getType() == SquareType.PAWN1) {
                                        if (x1 - 4 >= 0) {
                                            if (this.board.checkPosition(x1, y1, x1 - 4, y1) && !move) {
                                                this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                                this.board.getGrid()[x1 - 4][y1].setType(SquareType.PAWN2);
                                                move = true;
                                            }
                                        }
                                    } else if (this.board.checkPosition(x1, y1, x1 - 2, y1) && !move) {

                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1 - 2][y1].setType(SquareType.PAWN2);
                                        move = true;

                                    }
                                }
                                //
                                //
                                //
                                //diagonal
                            } else {
                                //diagonal haute droite
                                if (x1 - 2 >= 0 && y1 + 2 <= 16) {
                                    if (this.board.checkPosition(x1, y1, x1 - 2, y1 + 2) && !move) {
                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1 - 2][y1 + 2].setType(SquareType.PAWN2);
                                        move = true;
                                    }
                                }
                                //diagonal hautre gauche
                                if (x1 - 2 >= 0 && y1 - 2 >= 0) {
                                    if (this.board.checkPosition(x1, y1, x1 - 2, y1 - 2) && !move) {
                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1 - 2][y1 - 2].setType(SquareType.PAWN2);
                                        move = true;
                                    }
                                }
                                //diagonal basse gauche
                                if (x1 + 2 <= 16 && y1 - 2 >= 0) {
                                    if (this.board.checkPosition(x1, y1, x1 + 2, y1 - 2) && !move) {
                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1 + 2][y1 - 2].setType(SquareType.PAWN2);
                                        move = true;
                                    }
                                }
                                //diagonal basse droite
                                if (x1 + 2 <= 16 && y1 + 2 <= 16) {
                                    if (this.board.checkPosition(x1, y1, x1 + 2, y1 + 2) && !move) {
                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1 + 2][y1 + 2].setType(SquareType.PAWN2);
                                        move = true;
                                    }
                                }


                            }
                            randomJ2 = Math.random();
                        }
                    } else if (this.wallAA2 > 0 && randomJ2 < 0.5) { // Cas choisi aléatoirement : le bot place un mur
                        boolean aaPlaceUnMur = false;
                        while (!aaPlaceUnMur) {
                            int xW = (int)(Math.random() * 17);
                            int yW = (int)(Math.random() * 17);

                            while (!this.board.checkWallPlacement(xW, yW)) {
                                xW = (int)(Math.random() * 17);
                                yW = (int)(Math.random() * 17);
                            }

                            System.out.println("le xW random :" + xW);
                            System.out.println("le yW random :" + yW);
                            int xW2 = -1;
                            int yW2 = -1;
                            boolean verif = false;
                            if (xW != 16 && yW != 16) {
                                if (xW % 2 == 1) {
                                    xW2 = xW;
                                    yW2 = yW + 2;
                                    if (this.board.getGrid()[xW2][yW2].getType() == SquareType.FREE) {

                                        verif = true;
                                    }
                                }
                                if (xW % 2 == 0) {
                                    xW2 = xW + 2;
                                    yW2 = yW;
                                    if (this.board.getGrid()[xW2][yW2].getType() == SquareType.FREE) {

                                        verif = true;
                                    }
                                }
                            }
                            if (xW == 16 && yW != 16) {
                                xW2 = xW - 2;
                                yW2 = yW;
                                if (this.board.getGrid()[xW2][yW2].getType() == SquareType.FREE) {

                                    verif = true;
                                }
                            }
                            if (xW != 16 && yW == 16) {
                                xW2 = xW;
                                yW2 = yW - 2;
                                if (this.board.getGrid()[xW2][yW2].getType() == SquareType.FREE) {

                                    verif = true;
                                }
                            }
                            System.out.println("verif :" + verif);
                            if (verif) {
                                this.wallAA2--;
                                this.board.getGrid()[xW][yW].setType(SquareType.WALL);
                                this.board.getGrid()[xW2][yW2].setType(SquareType.WALL);
                                if (this.board.BFS(x1, y1).getX() != 16) {
                                    System.out.println("je rentre");
                                    System.out.println("le x du BFS :" + this.board.BFS(x1, y1).getX());
                                    System.out.println("le y du BFS :" + this.board.BFS(x1, y1).getY());
                                    this.wallAA2++;
                                    this.board.getGrid()[xW][yW].setType(SquareType.FREE);
                                    this.board.getGrid()[xW2][yW2].setType(SquareType.FREE);
                                } else aaPlaceUnMur = true;
                            }
                        }
                    }
                } else {


                    if (randomJ2 >= 0.5) { // Cas choisi aléatoirement : le bot se déplace
                        while (!move) {
                            double randomMove = Math.random();
                            //a droite
                            if ((randomMove >= 0) && (randomMove <= 0.20)) {

                                if (y1 + 2 <= 16) {

                                    if (this.board.getGrid()[x1][y1 + 2].getType() == SquareType.PAWN1) {
                                        if (y1 + 4 <= 16) {
                                            if (this.board.checkPosition(x1, y1, x1, y1 + 4) && !move) {
                                                this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                                this.board.getGrid()[x1][y1 + 4].setType(SquareType.PAWN2);
                                                move = true;
                                            }
                                        }
                                    } else if (this.board.checkPosition(x1, y1, x1, y1 + 2) && !move) {

                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1][y1 + 2].setType(SquareType.PAWN2);
                                        move = true;

                                    }
                                }
                                //
                                //
                                //
                                //a gauche
                            } else if ((randomMove > 0.20) && (randomMove <= 0.40)) {
                                if (y1 - 2 >= 0) {
                                    if (this.board.getGrid()[x1][y1 - 2].getType() == SquareType.PAWN1) {
                                        if (y1 - 4 >= 0) {
                                            if (this.board.checkPosition(x1, y1, x1, y1 - 4) && !move) {
                                                this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                                this.board.getGrid()[x1][y1 - 4].setType(SquareType.PAWN2);
                                                move = true;
                                            }
                                        }
                                    } else if (this.board.checkPosition(x1, y1, x1, y1 - 2) && !move) {

                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1][y1 - 2].setType(SquareType.PAWN2);
                                        move = true;

                                    }
                                }
                                //
                                //
                                //
                                //en bas
                            } else if ((randomMove > 0.40) && (randomMove <= 0.60)) {
                                if (x1 + 2 <= 16) {
                                    if (this.board.getGrid()[x1 + 2][y1].getType() == SquareType.PAWN1) {
                                        if (x1 + 4 <= 16) {
                                            if (this.board.checkPosition(x1, y1, x1 + 4, y1) && !move) {
                                                this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                                this.board.getGrid()[x1 + 4][y1].setType(SquareType.PAWN2);
                                                move = true;
                                            }
                                        }
                                    } else if (this.board.checkPosition(x1, y1, x1 + 2, y1) && !move) {

                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1 + 2][y1].setType(SquareType.PAWN2);
                                        move = true;

                                    }
                                }
                                //en haut
                            } else if ((randomMove > 0.60) && (randomMove <= 0.80)) {
                                if (x1 - 2 >= 0) {
                                    if (this.board.getGrid()[x1 - 2][y1].getType() == SquareType.PAWN1) {
                                        if (x1 - 4 >= 0) {
                                            if (this.board.checkPosition(x1, y1, x1 - 4, y1) && !move) {
                                                this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                                this.board.getGrid()[x1 - 4][y1].setType(SquareType.PAWN2);
                                                move = true;
                                            }
                                        }
                                    } else if (this.board.checkPosition(x1, y1, x1 - 2, y1) && !move) {

                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1 - 2][y1].setType(SquareType.PAWN2);
                                        move = true;

                                    }
                                }
                                //
                                //
                                //
                                //diagonal
                            } else {
                                //diagonal haute droite
                                if (x1 - 2 >= 0 && y1 + 2 <= 16) {
                                    if (this.board.checkPosition(x1, y1, x1 - 2, y1 + 2) && !move) {
                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1 - 2][y1 + 2].setType(SquareType.PAWN2);
                                        move = true;
                                    }
                                }
                                //diagonal hautre gauche
                                if (x1 - 2 >= 0 && y1 - 2 >= 0) {
                                    if (this.board.checkPosition(x1, y1, x1 - 2, y1 - 2) && !move) {
                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1 - 2][y1 - 2].setType(SquareType.PAWN2);
                                        move = true;
                                    }
                                }
                                //diagonal basse gauche
                                if (x1 + 2 <= 16 && y1 - 2 >= 0) {
                                    if (this.board.checkPosition(x1, y1, x1 + 2, y1 - 2) && !move) {
                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1 + 2][y1 - 2].setType(SquareType.PAWN2);
                                        move = true;
                                    }
                                }
                                //diagonal basse droite
                                if (x1 + 2 <= 16 && y1 + 2 <= 16) {
                                    if (this.board.checkPosition(x1, y1, x1 + 2, y1 + 2) && !move) {
                                        this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                                        this.board.getGrid()[x1 + 2][y1 + 2].setType(SquareType.PAWN2);
                                        move = true;
                                    }
                                }


                            }
                            randomJ2 = Math.random();
                        }


                    }
                }
                try {
                    Thread.sleep(250);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {


                //
                // BOT DE DIFFICULTE MEDIUM
                //


            }
        }
    }
}
