/**
 * GameLauncher creates QuoridorN object and manage the parameters to create the object.
 */

import quoridor.model.*;
import quoridor.view.*;
import javax.swing.*;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.*;

public class GameLauncher {

    /**
     * Main method is executed at launch
     * @param args arguments for execution
     */
    public static void main(String[] args) {

        String[] terminalOrGraphicsArray = {
            "TERMINAL",
            "GRAPHIC"
        };
        int saveTOrG = JOptionPane.showOptionDialog(null, "Which version would you like to play ?", "Select a version", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("data/img/msgbox/load.png"), terminalOrGraphicsArray, terminalOrGraphicsArray[0]);
        String terminalOrGraphics = terminalOrGraphicsArray[saveTOrG];

        if (terminalOrGraphics.toUpperCase().equals("TERMINAL")) {

            //															  //
            //															  //
            //                                //
            //															  //
            //															  //

            System.out.println("\n");
            System.out.println("#######################################");
            System.out.println("########                       ########");
            System.out.println("########                       ########");
            System.out.println("######## Welcome to Quoridor ! ########");
            System.out.println("########                       ########");
            System.out.println("########                       ########");
            System.out.println("#######################################");
            System.out.println("#######################################");
            System.out.println("#######################################");

            //															  //
            //															  //
            //															  //
            //															  //
            //															  //

            //															  //
            //															  //
            //															  //
            //															  //

            /* Since a configuration has to be set, we will ask the
               the player to enter either a configuration file, either
               no. The main then creates the QuoridorN object using
               a configuration file. We will distinguish two cases :

            		- the textfile exists and is called : "config.txt"
            		  so the program only read the file and creates the
            		  QuoridorN object using it.

            		- the textfile does not exist and we have to create
            	      it so the program can use it to create the
            	      Quoridor object.									  */

            String[] saveToLoadArray = {
                "YES",
                "NO"
            };
            int saveT = JOptionPane.showOptionDialog(null, "Do you want to load any save ?", "Game configuration", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("data/img/msgbox/load.png"), saveToLoadArray, saveToLoadArray[0]);
            String saveToLoadYesOrNo = saveToLoadArray[saveT];
            String saveAbsolutePath = "";
            String savePath = "data/saves/";
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (saveToLoadYesOrNo.toUpperCase().equals("YES")) {
                JFileChooser choice = new JFileChooser();
                choice.setCurrentDirectory(new File("data/saves"));
                int ret = choice.showOpenDialog(null);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    savePath += choice.getSelectedFile().getName();
                }

                QuoridorN quoridor = new QuoridorN(savePath);
                System.out.println("#######################################");
                System.out.println("#######################################");
                System.out.println("#######################################");
                System.out.println("#######################################");
                System.out.println("########  A game was loaded !  ########");
                System.out.println("#######################################");
                System.out.println("#######################################");
                System.out.println("#######################################");

            } else if (saveToLoadYesOrNo.toUpperCase().equals("NO")) {



                String fileName = (String) JOptionPane.showInputDialog(null, "Please enter a configuration file. (do not fill this if you don't want to implement one!)", "Game configuration", JOptionPane.QUESTION_MESSAGE, new ImageIcon("data/img/msgbox/panneau-de-configuration.png"), null, "data/config/.auto_config.txt");
                while ((!fileName.equals("data/config/.auto_config.txt")) && (!fileName.equals(""))) {
                    fileName = (String) JOptionPane.showInputDialog(null, "Please enter a configuration file. (do not fill this if you don't want to implement one!) [default = data/.auto_config.txt]", null, JOptionPane.QUESTION_MESSAGE, new ImageIcon("data/img/msgbox/panneau-de-configuration.png"), null, "data/config/.auto_config.txt");
                }

                // If there is no error and the fileName is well named ".auto_config.txt"
                if ((fileName.equals("data/config/.auto_config.txt")) && (fileName != null)) {
                    QuoridorN quoridor = new QuoridorN(fileName); // Creates the object with the parameters given in config.txt
                } else if (fileName.equals("") && (fileName != null)) { // If the input is empty (such as just pressing "Enter")
                    try {
                        FileWriter file = new FileWriter("data/config/.homemade_config.txt");
                        BufferedWriter buffer = new BufferedWriter(file);
                        PrintWriter out = new PrintWriter(buffer);

                        // Displays the two options that can be chosen as a match type : NORMAL, TOURNAMENT
                        // and write the chosen one in a text file (homemade_config.txt)
                        String[] gameTypeToWArray = {
                            "NORMAL",
                            "TOURNAMENT"
                        };
                        int gameT = JOptionPane.showOptionDialog(null, "Please chose the match type : ", "Choose a match type", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("data/img/msgbox/match-type.png"), gameTypeToWArray, gameTypeToWArray[0]);
                        String gameTypeToW = gameTypeToWArray[gameT];

                        // Displays the three options that can be chosen as a game mode : HH, HA or AA
                        // if the match type is TOURNAMENT, the game mode is always HH
                        // and write the chosen one in a text file (homemade_config.txt)
                        String modeToW = "";
                        if (gameTypeToW.equals("NORMAL")) {
                            String[] modeToWArray = {
                                "HH",
                                "HA",
                                "AA"
                            };
                            int mod = JOptionPane.showOptionDialog(null, "Please chose a game mode : ", "Choose a game mode", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("data/img/msgbox/game-type.png"), modeToWArray, modeToWArray[0]);
                            modeToW = modeToWArray[mod];
                        } else {
                            modeToW = "HH";
                        }

                        // Displays the two options that can be chosen as a difficulty : 1, 2
                        // if the game mode is HA or AA, then we can chose a difficulty. If not (game mode : HH), an arbitrary
                        // value is set but will not be exploited after.
                        // and write the chosen one in a text file (homemade_config.txt)
                        int difficultyToW = 1; // Arbitrary value that doesn't affect the game ; if the game mode is HH, the attribute difficulty doesn't mind
                        if ((modeToW.equals("HA")) || (modeToW.equals("AA"))) {
                            Integer[] difficultyToWArray = {
                                1,
                                2
                            };
                            int diff = JOptionPane.showOptionDialog(null, "Please chose a difficulty : ", "Choose a difficulty", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("data/img/msgbox/difficulty.png"), difficultyToWArray, difficultyToWArray[0]);
                            difficultyToW = difficultyToWArray[diff];
                        }

                        // Asks the user to enter a time in second so it can be written in a text file (homemade_config.txt)
                        // if the match type is TOURNAMENT we can ask the user to enter a value between 3 and 600
                        // if the match type is NORMAL we set an arbitrary value to -1 but we won't exploit it after
                        int timeToW = -1;
                        if (gameTypeToW.equals("TOURNAMENT")) {
                            timeToW = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter a time in seconds : ", "Choose a round duration", JOptionPane.QUESTION_MESSAGE));
                            while (timeToW <= 3) {
                                timeToW = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter a time in seconds (must be over 3 seconds) : ", "Choose a round duration", JOptionPane.QUESTION_MESSAGE));
                            }
                            if (timeToW >= 600) timeToW = 600;
                        }
                        //

                        // Write informations in the file
                        out.println(modeToW);
                        out.println(difficultyToW);
                        out.println(gameTypeToW);
                        out.println(timeToW);
                        out.close();
                        //

                        // Create the new object using the file we've been filling just earlier
                        QuoridorN quoridor = new QuoridorN("data/config/.homemade_config.txt");
                        quoridor.setHomemade(true);
                        //
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (terminalOrGraphics.toUpperCase().equals("GRAPHIC")) {
            Application appli = new Application();
            appli.setVisible(true);
        }


    }

}
