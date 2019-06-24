package quoridor.controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import quoridor.view.*;
import quoridor.model.*;
import javax.swing.Timer;

public class CommandsListener implements ActionListener {
    QuoridorG quoridor;
    boolean joueJ2;
    public CommandsListener(QuoridorG quoridor) {
        this.quoridor = quoridor;
        joueJ2 = false;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quoridor.getRight()) {
            if (!this.quoridor.tempsPasDepasse) {
                this.quoridor.move(quoridor.getActivePlayer(), "RIGHT");
                if (this.quoridor.gameType == GameType.TOURNAMENT) {}

            }
        } else if (e.getSource() == quoridor.getLeft()) {
            if (!this.quoridor.tempsPasDepasse) {
                this.quoridor.move(quoridor.getActivePlayer(), "LEFT");


            }
        } else if (e.getSource() == quoridor.getUp()) {
            if (!this.quoridor.tempsPasDepasse) {
                this.quoridor.move(quoridor.getActivePlayer(), "UP");
                if (this.quoridor.gameType == GameType.TOURNAMENT) {
                    quoridor.timer.stop();
                }
            }
        } else if (e.getSource() == quoridor.getDown()) {
            if (!this.quoridor.tempsPasDepasse) {
                this.quoridor.move(quoridor.getActivePlayer(), "DOWN");
                if (this.quoridor.gameType == GameType.TOURNAMENT) {
                    quoridor.timer.stop();
                }
            }
        } else if (e.getSource() == quoridor.getDiagUPL()) {
            if (!this.quoridor.tempsPasDepasse) {
                this.quoridor.move(quoridor.getActivePlayer(), "DIAGUL");
                if (this.quoridor.gameType == GameType.TOURNAMENT) {
                    quoridor.timer.stop();
                }
            }
        } else if (e.getSource() == quoridor.getDiagUPR()) {
            if (!this.quoridor.tempsPasDepasse) {
                this.quoridor.move(quoridor.getActivePlayer(), "DIAGUR");
                if (this.quoridor.gameType == GameType.TOURNAMENT) {
                    quoridor.timer.stop();
                }
            }
        } else if (e.getSource() == quoridor.getDiagDL()) {
            if (!this.quoridor.tempsPasDepasse) {
                this.quoridor.move(quoridor.getActivePlayer(), "DIAGDL");
                if (this.quoridor.gameType == GameType.TOURNAMENT) {
                    quoridor.timer.stop();
                }
            }
        } else if (e.getSource() == quoridor.getDiagDR()) {
            if (!this.quoridor.tempsPasDepasse) {
                this.quoridor.move(quoridor.getActivePlayer(), "DIAGDR");
                if (this.quoridor.gameType == GameType.TOURNAMENT) {
                    quoridor.timer.stop();
                }
            }

        } else if (e.getSource() == quoridor.getWall() && this.quoridor.wallJ1 > 0) {
            boolean sortir = false;
            boolean sortir2 = false;
            int x = Integer.parseInt(JOptionPane.showInputDialog(null, "Wall :\nEnter X :", 1));
            int y = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Y :", 1));
            int x2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter X2 :", 1));
            int y2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Y2 :", 1));
            int i = x;
            int j = y;
            while (!sortir || !sortir2) {
                //bas
                if (y2 * 2 < 16 && y * 2 < 16 && x2 - 1 == x) {
                    x = x * 2;
                    y = (y * 2) + 1;
                    x2 = x2 * 2;
                    y2 = (y2 * 2) + 1;
                }
                //droite
                else if (x2 * 2 < 16 && x * 2 < 16 && y2 - 1 == y) {
                    x = (x * 2) + 1;
                    y = y * 2;
                    x2 = (x2 * 2) + 1;
                    y2 = y2 * 2;
                }
                //haut
                else if (y2 * 2 < 16 && y * 2 < 16 && x2 + 1 == x) {
                    x = x2 * 2;
                    y = (y2 * 2) + 1;
                    x2 = i * 2;
                    y2 = (j * 2) + 1;
                }
                //gauche
                else if (x2 * 2 < 16 && x * 2 < 16 && y2 + 1 == y) {
                    x = (x2 * 2) + 1;
                    y = y2 * 2;
                    x2 = (i * 2) + 1;
                    y2 = j * 2;
                } else if (x2 * 2 == 16 && x * 2 == 16 && y2 - 1 == y) {
                    x = (x * 2) - 1;
                    y = y * 2;
                    x2 = (x2 * 2) - 1;
                    y2 = y2 * 2;
                } else if (x2 * 2 == 16 && x * 2 == 16 && y2 + 1 == y) {
                    x = (x2 * 2) - 1;
                    y = y2 * 2;
                    x2 = (i * 2) - 1;
                    y2 = j * 2;
                } else if (y2 * 2 == 16 && y * 2 == 16 && x2 - 1 == x) {
                    x = x * 2;
                    y = (y * 2) - 1;
                    x2 = x2 * 2;
                    y2 = (y2 * 2) - 1;
                } else if (y2 * 2 == 16 && y * 2 == 16 && x + 1 == x) {
                    x = x2 * 2;
                    y = (y2 * 2) - 1;
                    x2 = i * 2;
                    y2 = (j * 2) - 1;
                } else {
                    x = -1;
                    y = -1;
                    x2 = -1;
                    y2 = -1;
                }
                if (this.quoridor.getBoard().checkWallPlacement(x, y)) {
                    sortir = true;
                }
                if (this.quoridor.getBoard().isFree(x2, y2)) {
                    sortir2 = true;
                }
                if (!sortir || !sortir2) {
                    x = Integer.parseInt(JOptionPane.showInputDialog(null, "Wall :\nEnter X :", 1));
                    y = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Y :", 1));
                    x2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter X2 :", 1));
                    y2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Y2 :", 1));
                }
            }
            if (!this.quoridor.tempsPasDepasse) {
                this.quoridor.wall(quoridor.getActivePlayer(), x, y);
            }

            joueJ2 = true;
        }
    }
}
