package quoridor.model;
import javax.swing.*;
import java.io.*;
import java.util.Scanner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

/**
 * HumanPlayer class extends Player class and play as a Human (manually)
 */
public class HumanPlayer extends Player implements Serializable {

    private QuoridorN quoridor;
    private Game game;
    private double duration;
    private GameType gameType;
    private SquareType joueur;
    private int wallJ1;
    private int wallJ2;
    //Timer
    private int seconde;
    private Timer timer;
    private ActionListener taskPerformer;
    public int delay;
    //
    private String saveOrNot;
    public boolean tempsPasDepasse;

    public JLabel icon;


    /**
     * HumanPlayer's constructor initialize its attributes
     * @param board on which the player is playing
     * @param type SquareType of the player (player1 or player 2)
     * @param duration duration of a round
     * @param gameType gameType of the Game
     * @param game the game and its parameters
     */
    public HumanPlayer(Board board, SquareType type, double duration, GameType gameType, Game game) {
        super(board, type);
        this.duration = duration;
        this.gameType = gameType;
        this.joueur = type;
        this.wallJ1 = 10;
        this.wallJ2 = 10;
        this.game = game;
        this.delay = (int) duration;
        this.seconde = (int) duration;
    }

    public HumanPlayer(Board board, SquareType type, JLabel icon) {
        super(board, type);
        this.board = board;
        this.icon = icon;
    }
    public SquareType getTypeJ() {
        return this.joueur;
    }

    /**
     * play the turn of the player. Ask for every needed parameter to play a turn on the Board object
     */
    public void play() {
        boolean wantToSave = false;
        Scanner in = new Scanner(System.in);
        System.out.println("Would you like to save the game ? (Y/N)");
        saveOrNot = in .nextLine().toUpperCase();
        if (saveOrNot.equals("Y") || saveOrNot.equals("YE") || saveOrNot.equals("YES") || saveOrNot.equals("YEAH") || saveOrNot.equals("YEA") || saveOrNot.equals("YEEE")) {
            wantToSave = true; in .reset();
        } else if (saveOrNot.equals("N") || saveOrNot.equals("NO") || saveOrNot.equals("NON") || saveOrNot.equals("NADA")) { in .reset();
            if (this.gameType == GameType.TOURNAMENT) {
                this.delay = seconde;
                this.tempsPasDepasse = false;
                this.taskPerformer = new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {

                        delay--;
                        if (delay >= 0) System.out.println("Timer: " + delay);
                        if (delay <= 0) {
                            tempsPasDepasse = true;
                        }
                    }
                };
                this.timer = new Timer(1000, this.taskPerformer);
                this.timer.start();
                System.out.println("le temps : " + this.delay);
                if (this.joueur == SquareType.PAWN1) {
                    this.playJ(SquareType.PAWN2);
                    timer.stop();
                    this.delay = seconde;
                }
                if (this.joueur == SquareType.PAWN2) {
                    this.playJ(SquareType.PAWN1);
                    timer.stop();
                    this.delay = seconde;
                }
                System.out.println("TOURNAMENT");
            } else {
                this.tempsPasDepasse = false;
                if (this.joueur == SquareType.PAWN1) {
                    this.playJ(SquareType.PAWN2);
                }
                if (this.joueur == SquareType.PAWN2) {
                    this.playJ(SquareType.PAWN1);
                }
            }
        } else {
            System.out.println("You should write either Y, either N");
        }

        if (wantToSave) {
            Scanner in2 = new Scanner(System.in);
            System.out.println("Please enter the name of the file.");
            String filePath = in2.nextLine();
            while (!filePath.matches("^save[0-9]+.ser$")) {
                JOptionPane.showMessageDialog(null, "Save format should be like this : save1.ser", "Error : name format", JOptionPane.ERROR_MESSAGE);
                System.out.println("Please enter the name of the file.");
                filePath = in2.nextLine();
            }
            this.game.saveBoard(filePath);
            JOptionPane.showMessageDialog(null, "A save was correctly written. Path : data/saves/" + filePath, "Your game was saved ! ", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /**
      * Checks if the coordinate entered is in the Square[][] array or not
      * @param x x to check
      * @param y y to check
      * @return true if the coordinate is correct, else false
      */
    public boolean verifCoord(int x, int y) {
        boolean ret = false;
        if (x >= 0 && x <= 16 && y >= 0 && y <= 16) {
            ret = true;
        }
        return ret;
    }

    /**
      * Every move that any player can do. This method is called in play()
      * @param joueurAutre the player which has to make an action
      */
    public void playJ(SquareType joueurAutre) {
        ///

        //
        //
        //------------------------------------------------------------------------------------------
        //
        //

        //
        //
        //

        int x1 = -1;
        int y1 = -1;
        for (int y = 0; y < 17; y++) {
            for (int x = 0; x < 17; x++) {
                if (this.board.getGrid()[y][x].getType() == this.joueur) {
                    x1 = y;
                    y1 = x;
                }
            }
        }
        String[] options = {
            "MOVE",
            "PLACE A WALL"
        };
        int selectedOptionIndex = JOptionPane.showOptionDialog(null, this.type + " - please chose a move : ", this.type + " TURN", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        String selectedOption = options[selectedOptionIndex];
        int x2 = -1;
        int y2 = -1;
        if (selectedOption.equals("MOVE")) {
            String[] moves = {
                "DIAGONAL RIGHT",
                "RIGHT",
                "DOWN",
                "UP",
                "LEFT",
                "DIAGONAL LEFT"
            };
            int selectedMoveIndex = JOptionPane.showOptionDialog(null, this.type + " - please chose a direction : ", this.type + " TURN (moving)", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, moves, moves[0]);
            String selectedMove = moves[selectedMoveIndex];
            switch (selectedMove) {
                case "RIGHT":
                    if (y1 + 2 <= 16) {
                        if (this.board.getGrid()[x1][y1 + 2].getType() == joueurAutre) {
                            y2 = y1 + 4;
                            x2 = x1;
                        } else {
                            y2 = y1 + 2;
                            x2 = x1;
                        }
                    }
                    //
                    //
                    //
                    //
                    break;
                case "LEFT":
                    if (y1 - 2 >= 0) {
                        if (this.board.getGrid()[x1][y1 - 2].getType() == joueurAutre) {
                            y2 = y1 - 4;
                            x2 = x1;
                        } else {
                            y2 = y1 - 2;
                            x2 = x1;
                        }
                    }
                    //
                    //
                    //
                    //
                    //
                    break;
                case "DOWN":
                    if (x1 + 2 <= 16) {
                        if (this.board.getGrid()[x1 + 2][y1].getType() == joueurAutre) {
                            y2 = y1;
                            x2 = x1 + 4;
                        } else {
                            x2 = x1 + 2;
                            y2 = y1;
                        }
                    }
                    //
                    //
                    //
                    //
                    //
                    break;
                case "UP":
                    if (x1 - 2 >= 0) {
                        if (this.board.getGrid()[x1 - 2][y1].getType() == joueurAutre) {
                            y2 = y1;
                            x2 = x1 - 4;
                        } else {
                            x2 = x1 - 2;
                            y2 = y1;
                        }
                    }
                    //
                    //
                    //
                    //
                    //
                    break;
                case "DIAGONAL LEFT":
                    //pion2 est au dessus du joueur
                    if (x1 - 2 >= 0) {
                        if (this.board.getGrid()[x1 - 2][y1].getType() == joueurAutre) {
                            y2 = y1 - 2;
                            x2 = x1 - 2;
                        }
                    }
                    //pion2 est en dessous du joueur
                    if (x1 + 2 <= 16) {
                        if (this.board.getGrid()[x1 + 2][y1].getType() == joueurAutre) {
                            y2 = y1 - 2;
                            x2 = x1 + 2;
                        }
                    }
                    //pion2 est a droite du joueur
                    if (y1 + 2 <= 16) {
                        if (this.board.getGrid()[x1][y1 + 2].getType() == joueurAutre) {
                            y2 = y1 + 2;
                            x2 = x1 - 2;
                        }
                    }
                    //pion2 est a gauche du joueur
                    if (y1 - 2 >= 0) {
                        if (this.board.getGrid()[x1][y1 - 2].getType() == joueurAutre) {
                            y2 = y1 - 2;
                            x2 = x1 + 2;
                        }
                    }
                    //
                    //
                    //
                    break;
                case "DIAGONAL RIGHT":
                    //pion2 est au dessus du joueur
                    if (x1 - 2 >= 0) {
                        if (this.board.getGrid()[x1 - 2][y1].getType() == joueurAutre) {
                            y2 = y1 + 2;
                            x2 = x1 - 2;
                        }
                    }
                    //pion2 est en dessous du joueur
                    if (x1 + 2 <= 16) {
                        if (this.board.getGrid()[x1 + 2][y1].getType() == joueurAutre) {
                            y2 = y1 + 2;
                            x2 = x1 + 2;
                        }
                    }
                    //pion2 est a droite du joueur
                    if (y1 + 2 <= 16) {
                        if (this.board.getGrid()[x1][y1 + 2].getType() == joueurAutre) {
                            y2 = y1 + 2;
                            x2 = x1 + 2;
                        }
                    }
                    //pion2 est a gauche du joueur
                    if (y1 - 2 >= 0) {
                        if (this.board.getGrid()[x1][y1 - 2].getType() == joueurAutre) {
                            y2 = y1 - 2;
                            x2 = x1 - 2;
                        }
                    }
                    //
                    //
                    //
                    break;
                default:
                    System.out.println("No match.");
                    break;

            }

            if (verifCoord(x2, y2) && this.board.checkPosition(x1, y1, x2, y2) && !tempsPasDepasse) {
                this.board.getGrid()[x1][y1].setType(SquareType.FREE);
                this.board.getGrid()[x2][y2].setType(this.joueur);

            } else System.out.println("VOUS NE POUVEZ PAS / temps depasse");
        }
        if (selectedOption.equals("PLACE A WALL") && this.wallJ2 > 0) {
            boolean jePeuxPoserUnMur = false;
            System.out.println("temps dep : " + tempsPasDepasse);
            while (!jePeuxPoserUnMur && !tempsPasDepasse) {
                boolean verif = false;
                int xW = Integer.parseInt(JOptionPane.showInputDialog(null, "Wall :" + this.wallJ2 + "\nEnter X :", 1));
                int yW = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Y :", 1));
                int xW2 = -1;
                int yW2 = -1;

                while (!this.board.checkWallPlacement(xW, yW)) {
                    xW = Integer.parseInt(JOptionPane.showInputDialog(null, "Wall :" + this.wallJ2 + "\nEnter X :", 1));
                    yW = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Y :", 1));
                }
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
                    int xJ1 = -1;
                    int yJ1 = -1;
                    for (int i = 0; i < 17; i++) {
                        for (int j = 0; j < 17; j++) {
                            if (this.board.getGrid()[i][j].getType() == joueur) {
                                xJ1 = i;
                                yJ1 = j;
                            }
                        }
                    }
                    if (this.board.getGrid()[xW2][yW2].getType() == SquareType.FREE && !tempsPasDepasse) {
                        this.wallJ2--;
                        this.board.getGrid()[xW][yW].setType(SquareType.WALL);
                        this.board.getGrid()[xW2][yW2].setType(SquareType.WALL);
                        if (this.board.BFS(x1, y1).getX() != 16 && this.board.BFS(xJ1, yJ1).getX() != 0) {
                            System.out.println("peut pas jouer ici");
                            this.wallJ2++;
                            this.board.getGrid()[xW][yW].setType(SquareType.FREE);
                            this.board.getGrid()[xW2][yW2].setType(SquareType.FREE);
                        } else {
                            jePeuxPoserUnMur = true;
                        }
                    }
                } else System.out.println("peut pas / temps depasse");
            }
        }
    }

    /**
			* Saves the actual state of the Board in a file when called
			*	@param filePath the file to save the Board in
			*/
    public void saveGame(String filePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this.board);
            objectOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
