package quoridor.view;

import quoridor.model.*;
import quoridor.controller.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;
import java.text.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import javax.imageio.ImageIO;

/**
	* QuoridorG class is main class representing the display of the board in graphical version.
	* it does manage every player move, the configuration of the game, and the end of the game
	*/
public class QuoridorG extends JPanel {
	public QuoridorG(Application appli) {
		this.appli = appli;
		this.gameType = GameType.NORMAL;
		this.mode = Mode.HH;
		this.difficulty = 1;
		this.duration = -1;//a change en fct du choix du joueur
    	this.delay = (int)this.duration;
		this.seconde = (int)this.duration;
		this.tempsPasDepasse = false;
		this.wallJ1 = 10;
		this.wallJ2 = 10;
		this.wallAA1 = 10;
		this.wallAA2 = 10;
		this.pawn1 = new JLabel("data/img/board/pawn1.png");
		this.pawn2 = new JLabel("data/img/board/pawn2.png");
		this.P1 = new HumanPlayer(this.board, SquareType.PAWN1, this.duration, this.gameType, this.gameplay);
		this.P2 = new HumanPlayer(this.board, SquareType.PAWN2, this.duration, this.gameType, this.gameplay);
		initComponents();
	}

/**
	* This method initializes every components of the JPanel QuoridorG.
	*/
	public void initComponents() {
		this.setBackground(Color.WHITE);
		File f2 = new File("data/fonts/roboto/Roboto-Regular.ttf");
		Font robotoFont16Pt = null;
		try {
			FileInputStream in2 = new FileInputStream(f2);
			Font robotoFont = Font.createFont(Font.TRUETYPE_FONT, in2);
			robotoFont16Pt = robotoFont.deriveFont(16f);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.caseabc = new JLabel("");

		this.configField = new JTextField("data/config/.auto_config.txt");
		this.configField.addActionListener(new ConfigTextFieldListener(this));
		this.timeField = new JTextField();
		this.timeField.addActionListener(new ConfigTextFieldListener(this));

		this.configLabel = new JLabel("Configuration file : ");
		this.configLabel.setFont(robotoFont16Pt);

		this.gameTypeLabel = new JLabel("GameType : ");
		this.gameTypeLabel.setFont(robotoFont16Pt);

		this.gameModeLabel = new JLabel("Mode : ");
		this.gameModeLabel.setFont(robotoFont16Pt);

		this.difficultyLabel = new JLabel("Difficulty : ");
		this.difficultyLabel.setFont(robotoFont16Pt);

		this.timeLabel = new JLabel("Time per round : ");
		this.timeLabel.setFont(robotoFont16Pt);

		this.checkTimeLabel = new JLabel("Error : bad value");
		this.checkTimeLabel.setFont(robotoFont16Pt);
		this.checkTimeLabel.setForeground(Color.RED);

		this.checkConfigLabel = new JLabel("Error : wrong path");
		this.checkConfigLabel.setFont(robotoFont16Pt);
		this.checkConfigLabel.setForeground(Color.RED);

		this.butNormal = new JButton("NORMAL");
		this.butNormal.setBackground(Color.LIGHT_GRAY);
		this.butNormal.setBorderPainted(false);
		this.butNormal.setFocusPainted(false);
		this.butNormal.addActionListener(new ConfigButtonsListener(this.appli, this));

		this.butTournament = new JButton("TOURNAMENT");
		this.butTournament.setBackground(Color.LIGHT_GRAY);
		this.butTournament.setBorderPainted(false);
		this.butTournament.setFocusPainted(false);
		this.butTournament.addActionListener(new ConfigButtonsListener(this.appli, this));


		this.butHH = new JButton("HH");
		this.butHH.setBackground(Color.LIGHT_GRAY);
		this.butHH.setBorderPainted(false);
		this.butHH.setFocusPainted(false);
		this.butHH.addActionListener(new ConfigButtonsListener(this.appli, this));

		this.butHA = new JButton("HA");
		this.butHA.setBackground(Color.LIGHT_GRAY);
		this.butHA.setFocusPainted(false);
		this.butHA.setBorderPainted(false);
		this.butHA.addActionListener(new ConfigButtonsListener(this.appli, this));

		this.butAA = new JButton("AA");
		this.butAA.setBackground(Color.LIGHT_GRAY);
		this.butAA.setFocusPainted(false);
		this.butAA.setBorderPainted(false);
		this.butAA.addActionListener(new ConfigButtonsListener(this.appli, this));

		this.butDiff1 = new JButton("1");
		this.butDiff1.setBackground(Color.LIGHT_GRAY);
		this.butDiff1.setFocusPainted(false);
		this.butDiff1.setBorderPainted(false);
		this.butDiff1.addActionListener(new ConfigButtonsListener(this.appli, this));

		this.butDiff2 = new JButton("2");
		this.butDiff2.setBackground(Color.LIGHT_GRAY);
		this.butDiff2.setFocusPainted(false);
		this.butDiff2.setBorderPainted(false);
		this.butDiff2.addActionListener(new ConfigButtonsListener(this.appli, this));

		this.butOk = new JButton("OK");
		this.butOk.setBackground(Color.GRAY);
		this.butOk.setFocusPainted(false);
		this.butOk.setBorderPainted(false);;
		this.butOk.addActionListener(new ConfigButtonsListener(this.appli, this));

		this.configPanel = new JPanel();
		this.configPanel.setLayout(new BorderLayout());
		this.configPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
		this.configPanel.add(configLabel, BorderLayout.WEST);
		this.configPanel.add(configField, BorderLayout.CENTER);

		this.checkConfigPanel = new JPanel();
		this.checkConfigPanel.add(checkConfigLabel);
		this.checkConfigPanel.setVisible(true);

		this.gameTypePanel = new JPanel();
		this.gameTypePanel.setLayout(new GridLayout(1, 50));
		this.gameTypePanel.add(gameTypeLabel);
		this.gameTypePanel.add(butNormal);
		this.gameTypePanel.add(new JLabel(""));
		this.gameTypePanel.add(butTournament);
		this.gameTypePanel.setBorder(new EmptyBorder(10, 0, 10, 100));

		this.modePanel = new JPanel();
		this.modePanel.setLayout(new GridLayout(1, 7));
		this.modePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.modePanel.add(gameModeLabel);
		this.modePanel.add(new JLabel(""));
		this.modePanel.add(butHH);
		this.modePanel.add(new JLabel(""));
		this.modePanel.add(butHA);
		this.modePanel.add(new JLabel(""));
		this.modePanel.add(butAA);
		this.modePanel.setBorder(new EmptyBorder(10, 0, 10, 100));
		this.modePanel.setVisible(false);

		this.difficultyPanel = new JPanel();
		this.difficultyPanel.setLayout(new GridLayout(1, 4));
		this.difficultyPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.difficultyPanel.add(difficultyLabel);
		this.difficultyPanel.add(butDiff1);
		this.difficultyPanel.add(new JLabel(""));
		this.difficultyPanel.add(butDiff2);
		this.difficultyPanel.setBorder(new EmptyBorder(10, 0, 10, 100));
		this.difficultyPanel.setVisible(false);


		this.timePanel = new JPanel();
		this.timePanel.setLayout(new BorderLayout());
		this.timePanel.add(timeLabel, BorderLayout.WEST);
		this.timePanel.add(timeField, BorderLayout.CENTER);
		this.timePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.timePanel.setVisible(false);

		this.checkTimePanel = new JPanel();
		this.checkTimePanel.add(checkTimeLabel);
		this.checkTimePanel.setVisible(false);

		this.configPanel.setBackground(new Color(225, 225, 225));
		this.checkConfigPanel.setBackground(new Color(225, 225, 225));
		this.gameTypePanel.setBackground(new Color(225, 225, 225));
		this.modePanel.setBackground(new Color(225, 225, 225));
		this.difficultyPanel.setBackground(new Color(225, 225, 225));
		this.timePanel.setBackground(new Color(225, 225, 225));
		this.checkTimePanel.setBackground(new Color(225, 225, 225));

		this.setLayout(new GridLayout(10, 1));
		this.setBackground(new Color(225, 225, 225));

		this.add(configPanel);
		this.add(checkConfigPanel);
		this.add(gameTypePanel);
		this.add(new JLabel(""));
		this.add(modePanel);
		this.add(new JLabel(""));
		this.add(difficultyPanel);
		this.add(timePanel);
		this.add(checkTimePanel);
		this.add(butOk);
	}

	/**
		* Returns the JTextfield used to get the configuration file.
		* @return the JTextField
		*/
	public JTextField getConfigField() {
		return this.configField;
	}

	/**
     * Returns the JTextfield used to get the duration.
     * @return the JTextField
     */
	public JTextField getTimeField(){
		return this.timeField;
	}

	/**
     * Returns the JLabel used to print the duration.
     * @return the JLabel
     */
	public JLabel getTimerLab(){
		return this.timerLabel;
	}
	public Player getP1(){
		return this.P1;
	}
	public Player getP2(){
		return this.P2;
	}
	/**
		* Returns the button which choses the gameType as "NORMAL"
		*	@return the button NORMAL
		*/
	public JButton getButNormal() {
		return this.butNormal;
	}

	/**
		* Returns the button which choses the gameType as "TOURNAMENT"
		*	@return the button TOURNAMENT
		*/
	public JButton getButTournament() {
		return this.butTournament;
	}

	/**
		* Returns the button which choses the gameMode as "HH"
		*	@return the button HH
		*/
	public JButton getButHH() {
		return this.butHH;
	}

	/**
		* Returns the button which choses the gameMode as "HA"
		*	@return the button HA
		*/
	public JButton getButHA() {
		return this.butHA;
	}

	/**
		* Returns the button which choses the gameMode as "AA"
		*	@return the button AA
		*/
	public JButton getButAA() {
		return this.butAA;
	}

	/**
		*	Returns the button that validates the homemade Configuration
		*	@return the button OK
		*/
	public JButton getButOk() {
		return this.butOk;
	}

	/**
		* Returns the button which choses the difficulty as "1"
		*	@return the button diff1
		*/
	public JButton getButDiff1() {
		return this.butDiff1;
	}

	/**
		* Returns the button which choses the difficulty as "2"
		*	@return the button diff2
		*/
	public JButton getButDiff2() {
		return this.butDiff2;
	}

	/**
		* This method is useful when we want to display the correct configuration options.
		*	It chooses if the JPanel used to chose the gameMode has to be visible or not.
		*	@param visible if true, it displays the JPanel, if false, it doesn't.
		*/
	public void setModePanelVisible(boolean visible) {
		if (visible) {
			this.modePanel.setVisible(true);
		} else {
			this.modePanel.setVisible(false);
		}
	 }

	 /**
 		* This method is useful when we want to display the correct configuration options.
 		*	It chooses if the JPanel used to chose the difficulty has to be visible or not.
 		*	@param visible if true, it displays the JPanel, if false, it doesn't.
 		*/
	 public void setDifficultyPanelVisible(boolean visible) {
		 if (visible) {
			this.difficultyPanel.setVisible(true);
		} else {
			this.difficultyPanel.setVisible(false);
		}
	}

	/**
	 * This method is useful when we want to display the correct configuration options.
	 *	It chooses if the JPanel used to chose the time has to be visible or not.
	 *	@param visible if true, it displays the JPanel, if false, it doesn't.
	 */
	public void setTimePanelVisible(boolean visible) {
		if (visible) {
			this.timePanel.setVisible(true);
			this.checkTimePanel.setVisible(true);
		} else {
			this.timePanel.setVisible(false);
			this.checkTimePanel.setVisible(false);
		}
	}

	/**
		* This methods is like the start method in the terminal version, it does launch the graphical version game.
		* It initializes the array of JPanel representing the board, the commands panel, and the player informations.
		* this method is called at the end of the initComponents method when the configuration is correctly created.
		* @param fileName the file that we used to create the Game object used to set up a Board
		*/
	public void launchGame(String fileName) {

		File f2 = new File("data/fonts/roboto/Roboto-Regular.ttf");
		Font robotoFont24Pt = null;
		try {
			FileInputStream in2 = new FileInputStream(f2);
			Font robotoFont = Font.createFont(Font.TRUETYPE_FONT, in2);
			robotoFont24Pt = robotoFont.deriveFont(24f);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (fileName.equals("data/.auto_config.txt")) {
			ArrayList<String> list = new ArrayList<String>();
			try {
					FileReader fr = new FileReader(fileName);
					BufferedReader buff = new BufferedReader(fr);
					Scanner in = new Scanner(buff);
					while (in.hasNextLine()) {
						list.add(in.nextLine());
					}
					String modeS = list.get(0);
					if (modeS.equals("AA")) this.mode = Mode.AA;
					else if (modeS.equals("HA")) this.mode = Mode.HA;
					else if (modeS.equals("HH")) this.mode = Mode.HH;
					else this.mode = Mode.HH;

					this.difficulty = Integer.parseInt(list.get(1));

					String gameTypeS = list.get(2);
					if (gameTypeS.equals("NORMAL")) this.gameType = GameType.NORMAL;
					else if (gameTypeS.equals("TOURNAMENT")) this.gameType = GameType.TOURNAMENT;
					else this.gameType = GameType.NORMAL;
					this.duration = Integer.parseInt(list.get(3));
					in.close();
					this.gameplay = new Game(this.mode, this.difficulty, this.gameType, this.duration);
					this.board = gameplay.getBoard();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			this.gameplay = new Game(this.mode, this.difficulty, this.gameType, this.duration);
			this.board = gameplay.getBoard();
			if (this.mode.equals("HH")) {
				this.P1 = new HumanPlayer(this.board, SquareType.PAWN1, this.duration, this.gameType, this.gameplay);
				this.P2 = new HumanPlayer(this.board, SquareType.PAWN2, this.duration, this.gameType, this.gameplay);
			} else if (this.mode.equals("HA")) {
				this.P1 = new HumanPlayer(this.board, SquareType.PAWN1, this.duration, this.gameType, this.gameplay);
				this.P2 = new AutoPlayer(this.board, SquareType.PAWN2, this.difficulty);
			} else {
				this.P2 = new AutoPlayer(this.board, SquareType.PAWN1, this.difficulty);
				this.P2 = new AutoPlayer(this.board, SquareType.PAWN2, this.difficulty);
			}
		}

			this.setVisible(false);
			this.removeAll();
			this.setVisible(true);
			this.setLayout(new BorderLayout());
			System.out.println(this.mode);

			// Panel de droite composé de deux parties
			this.commands = new JPanel();
			this.commands.setLayout(new GridLayout(2, 1));
			// Partie de haut avec le choix des déplacements
			this.commandsUP = new JPanel();
			this.commandsUP	.setLayout(new BorderLayout());


			// Partie concernant les 9 boutons de dépacement
			this.moveBox = new JPanel(new GridLayout(3, 7));

			this.diagUPL = new JButton(new ImageIcon("data/img/board/DULarrow.png"));
			this.diagUPL.setFocusPainted(false);
			this.diagUPL.setBorderPainted(false);
			this.diagUPL.setBackground(Color.WHITE);
			this.diagUPL.addActionListener(new CommandsListener(this));


			this.up = new JButton(new ImageIcon("data/img/board/UParrow.png"));
			this.up.setFocusPainted(false);
			this.up.setBorderPainted(false);
			this.up.setBackground(Color.WHITE);
			this.up.addActionListener(new CommandsListener(this));

			this.diagUPR = new JButton(new ImageIcon("data/img/board/DURarrow.png"));
			this.diagUPR.setFocusPainted(false);
			this.diagUPR.setBorderPainted(false);
			this.diagUPR.setBackground(Color.WHITE);
			this.diagUPR.addActionListener(new CommandsListener(this));

			this.left = new JButton(new ImageIcon("data/img/board/LEFTarrow.png"));
			this.left.setFocusPainted(false);
			this.left.setBorderPainted(false);
			this.left.setBackground(Color.WHITE);
			this.left.addActionListener(new CommandsListener(this));

			this.wall = new JButton(new ImageIcon("data/img/board/wall.png"));
			this.wall.setFocusPainted(false);
			this.wall.setBorderPainted(false);
			this.wall.setBackground(Color.WHITE);
			this.wall.addActionListener(new CommandsListener(this));

			this.right = new JButton(new ImageIcon("data/img/board/RIGHTarrow.png"));
			this.right.setFocusPainted(false);
			this.right.setBorderPainted(false);
			this.right.setBackground(Color.WHITE);
			this.right.addActionListener(new CommandsListener(this));

			this.diagDOWNL = new JButton(new ImageIcon("data/img/board/DDLarrow.png"));
			this.diagDOWNL.setFocusPainted(false);
			this.diagDOWNL.setBorderPainted(false);
			this.diagDOWNL.setBackground(Color.WHITE);
			this.diagDOWNL.addActionListener(new CommandsListener(this));

			this.down = new JButton(new ImageIcon("data/img/board/DOWNarrow.png"));
			this.down.setFocusPainted(false);
			this.down.setBackground(Color.WHITE);
			this.down.setBorderPainted(false);
			this.down.addActionListener(new CommandsListener(this));

			this.diagDOWNR = new JButton(new ImageIcon("data/img/board/DDRarrow.png"));
			this.diagDOWNR.setFocusPainted(false);
			this.diagDOWNR.setBorderPainted(false);
			this.diagDOWNR.setBackground(Color.WHITE);
			this.diagDOWNR.addActionListener(new CommandsListener(this));

			this.moveBox.add(new JLabel(""));
			this.moveBox.add(diagUPL, BorderLayout.NORTH);
			this.moveBox.add(new JLabel(""));
			this.moveBox.add(up, BorderLayout.NORTH);
			this.moveBox.add(new JLabel(""));
			this.moveBox.add(diagUPR, BorderLayout.NORTH);
			this.moveBox.add(new JLabel(""));
			this.moveBox.add(new JLabel(""));
			this.moveBox.add(left, BorderLayout.NORTH);
			this.moveBox.add(new JLabel(""));
			this.moveBox.add(wall, BorderLayout.NORTH);
			this.moveBox.add(new JLabel(""));
			this.moveBox.add(right, BorderLayout.NORTH);
			this.moveBox.add(new JLabel(""));
			this.moveBox.add(new JLabel(""));
			this.moveBox.add(diagDOWNL, BorderLayout.NORTH);
			this.moveBox.add(new JLabel(""));
			this.moveBox.add(down, BorderLayout.NORTH);
			this.moveBox.add(new JLabel(""));
			this.moveBox.add(diagDOWNR, BorderLayout.NORTH);
			this.moveBox.add(new JLabel(""));
			this.moveBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

			this.commandsUP.add(moveBox, BorderLayout.CENTER);

			this.commandsDOWN = new JPanel();
			this.commandsDOWN.setLayout(new BorderLayout());

			JPanel text = new JPanel();
			text.setLayout(new GridLayout(4, 4));
			P1Icon = new JLabel(new ImageIcon("data/img/board/pawn1.png"));
			P1RW = new JLabel("Remaining walls : ");
			P1WN = new JLabel("10");

			this.timerLabel = new JLabel("Timer : "+this.duration);
			this.timerLabel.setFont(robotoFont24Pt);

			P2Icon = new JLabel(new ImageIcon("data/img/board/pawn2.png"));
			P2RW = new JLabel("Remaining walls : ");
			P2WN = new JLabel("10");
			P2RW.setForeground(new Color(53, 199, 88));
			P2WN.setForeground(new Color(53, 199, 88));

			P1RW.setForeground(new Color(218, 64, 36));
			P1WN.setForeground(new Color(218, 64, 36));

			P1RW.setFont(robotoFont24Pt);
			P1WN.setFont(robotoFont24Pt);
			P2RW.setFont(robotoFont24Pt);
			P2WN.setFont(robotoFont24Pt);


			text.add(new JLabel(""));
			text.add(timerLabel);
			text.add(new JLabel(""));
			text.add(P1Icon);
			text.add(P1RW);
			text.add(P1WN);
			text.add(new JLabel(""));
			text.add(new JLabel(""));
			text.add(new JLabel(""));
			text.add(P2Icon);
			text.add(P2RW);
			text.add(P2WN	);
			text.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

			JPanel button = new JPanel();
			this.save = new JButton("SAVE");
			this.save.addActionListener(new CommandsListener(this));
			save.setFocusPainted(false);
			save.setBorderPainted(false);
			save.setBackground(Color.LIGHT_GRAY);
			button.add(save);


			this.commandsDOWN.add(text, BorderLayout.NORTH);
			this.commandsDOWN.add(button, BorderLayout.SOUTH);


			this.commandsDOWN.add(new JLabel(""));
			this.commandsDOWN.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
			this.commands.add(commandsUP);
			this.commands.add(commandsDOWN);
			this.commands.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));

			System.out.println(this.gameplay.getBoard().toString());
			this.plateau = new JPanel();
			this.plateau.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
			this.plateau.setLayout(new GridLayout(9, 9));
			this.panelTab = new JPanel[9][9];
			this.gridArray = new JLabel[9][9];
			for (int i = 0; i < this.gridArray.length; i++) {
				for (int j = 0; j < this.gridArray[i].length; j++) {
					this.panelTab[i][j] = new JPanel();
					this.gridArray[i][j] = new JLabel();
					this.gridArray[i][j].setHorizontalAlignment(JTextField.CENTER);
					this.gridArray[i][j].setPreferredSize(new Dimension(70,55));
					this.gridArray[i][j].setVisible(false);
					this.gridArray[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
					this.panelTab[i][j].setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
					this.panelTab[i][j].add(this.gridArray[i][j]);
					this.plateau.add(this.panelTab[i][j]);
					}
			}
		//	this.panelTab[5][5].setBorder(BorderFactory.createMatteBorder(5,1,1,1,Color.BLACK));
		//	this.panelTab[5][6].setBorder(BorderFactory.createMatteBorder(5,1,1,1,Color.BLACK));

			for(int i = 0; i < 9; i++){
				this.panelTab[0][i].setBackground(new Color( 53, 199, 88 ));
				this.panelTab[8][i].setBackground(new Color(31, 119, 238));
			}

			JLabel pawn1 = new JLabel(new ImageIcon("data/img/board/pawn1.png"));
			JLabel pawn2 = new JLabel(new ImageIcon("data/img/board/pawn2.png"));
			this.panelTab[0][4].add(pawn1);
			this.panelTab[8][4].add(pawn2);
			this.add(commands, BorderLayout.EAST);
			this.add(plateau, BorderLayout.CENTER);
			System.out.println(this.gameplay.getBoard());
			System.out.println(this.gameplay);
			System.out.println(this.board);
			this.setActivePlayer(P1);
			this.enableMoveQ(SquareType.PAWN1);
			this.down.setEnabled(false);
			System.out.println("juste avant timer1 :"+tempsPasDepasse);
			System.out.println("le gameType :"+this.gameType);
			if(this.gameType == GameType.TOURNAMENT){
					this.delay = seconde;
					this.taskPerformer = new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						delay --;
						if(delay >= 0){
							timerLabel.setText("Timer : "+delay);
						}
							if(delay <= 0){
								tempsPasDepasse = true;
								changeActivePlayer();
								timer.stop();

							}
						}
					};
					this.timer = new Timer(1000, this.taskPerformer);
					this.timer.start();

				System.out.println("juste apres timer1 :"+tempsPasDepasse);
			}
			if(this.mode == Mode.AA){
				try{
					this.autoPlay(this.activePlayer);
					Thread.sleep(1000);
					this.autoPlay(this.activePlayer);
				}catch(Exception e){
					e.getStackTrace();
				}
			}
		}

		public void autoPlay(Player aaPlayer){
			this.activePlayer = aaPlayer;
					int x1 = -1;
					int y1 = -1;
					for (int y = 0; y < 17; y++) {
					  for (int x = 0; x < 17; x++) {
						if (this.board.getGrid()[y][x].getType() == aaPlayer.getTypeJ()) {
						  x1 = y;
						  y1 = x;
						}
					  }
					}
					boolean move = false;
					while(!move){
					  double moves = Math.random();
					  if(moves >= 0 && moves <= 0.5){
						double randomMove = Math.random();
						if ((randomMove >= 0) && (randomMove <= 0.20)) {
						  this.move(this.getActivePlayer(), "RIGHT");
						  System.out.println(this.gameplay.getBoard().toString());
						  move = true;
						} else if ((randomMove > 0.20) && (randomMove <= 0.40)) {
						  this.move(this.getActivePlayer(), "LEFT");
						  System.out.println(this.gameplay.getBoard().toString());
						  move = true;
						} else if ((randomMove > 0.40) && (randomMove <= 0.60)) {
						  this.move(this.getActivePlayer(), "UP");
						  System.out.println(this.gameplay.getBoard().toString());
						  move = true;
						} else if ((randomMove > 0.60) && (randomMove <= 0.80)) {
						  this.move(this.getActivePlayer(), "DOWN");
						  System.out.println(this.gameplay.getBoard().toString());
						  move = true;
						} else {
						  if (x1 - 2 >= 0 && y1 + 2 <= 16) {
							if (this.board.checkPosition(x1, y1, x1 - 2, y1 + 2) && !move) {
								this.move(this.getActivePlayer(), "DIAGUR");
								move = true;
							  }
							}
							//diagonal hautre gauche
							if (x1 - 2 >= 0 && y1 - 2 >= 0) {
							  if (this.board.checkPosition(x1, y1, x1 - 2, y1 - 2) && !move) {
									this.move(this.getActivePlayer(), "DIAGUL");
								  move = true;
								}
							  }
							  //diagonal basse gauche
							  if (x1 + 2 <= 16 && y1 - 2 >= 0) {
								if (this.board.checkPosition(x1, y1, x1 + 2, y1 - 2) && !move) {
									this.move(this.getActivePlayer(), "DIAGDL");
								  move = true;
								}
							  }
							  //diagonal basse droite
							  if (x1 + 2 <= 16 && y1 + 2 <= 16) {
								if (this.board.checkPosition(x1, y1, x1 + 2, y1 + 2) && !move) {
									this.move(this.getActivePlayer(), "DIAGR");
								  move = true;
								}
							  }
						}
					  }else{
						int x2 = -1;
						int y2 = -1;
						for (int y = 0; y < 17; y++) {
							for (int x = 0; x < 17; x++) {
								if (this.board.getGrid()[y][x].getType() != aaPlayer.getTypeJ() && this.board.getGrid()[y][x].getType() != SquareType.WALL && this.board.getGrid()[y][x].getType() != SquareType.FREE && this.board.getGrid()[y][x].getType() != SquareType.NONE) {
									x2 = y;
									y2 = x;
								}
							}
						}
						boolean aaPlaceUnMur = false;
						while(!aaPlaceUnMur){
						int xW = (int)(Math.random() * 17);
						int yW = (int)(Math.random() * 17);

						while (!this.board.checkWallPlacement(xW, yW)) {
						xW = (int)(Math.random() * 17);
						yW = (int)(Math.random() * 17);
						}

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
						  System.out.println("verif :"+verif);
						  if (verif) {
							  this.wallAA2--;
							  this.board.getGrid()[xW][yW].setType(SquareType.WALL);
							  this.board.getGrid()[xW2][yW2].setType(SquareType.WALL);

							  if(this.board.BFS(x1,y1).getX() != 16 && this.board.BFS(x2,y2).getX() != 0){
								this.wallAA2 ++;
								this.board.getGrid()[xW][yW].setType(SquareType.FREE);
								this.board.getGrid()[xW2][yW2].setType(SquareType.FREE);
								move = false;
							  }else{
								this.board.getGrid()[xW][yW].setType(SquareType.FREE);
								this.board.getGrid()[xW2][yW2].setType(SquareType.FREE);
								this.wall(this.getActivePlayer(),xW,yW);
								move = true;
								aaPlaceUnMur = true;
							  }
						  }
						}

					  }
					}
				}

	/**
		* This method is called to see if a Player object has won the game or not.
		* It checks if any pawn has arrived to its opponent camp ; whenever it does it, it prints the winner and "Game has ended"
		*/
	public void endOfGame() {
		boolean end = false;
		int i = 0;
		while(i <= 16 && !end){
			if(this.board.getGrid()[0][i].getType() == SquareType.PAWN1){
				end = true;
				System.out.println("PAWN 1 WON");
			}
			if(this.board.getGrid()[16][i].getType() == SquareType.PAWN2){
				end = true;
				System.out.println("PAWN 2 WON");
			}
			i++;
		}
		System.out.println("Game has ended ");
	}

	/**
		* Returns the command button responsible of the move : moving to the left top diagonal
		* @return the button diagUPL
		*/
	public JButton getDiagUPL() {
		return this.diagUPL;
	}

	/**
		* Returns the command button responsible of the move : moving to the top
		* @return the button up
		*/
	public JButton getUp() {
		return this.up;
	}

	/**
		* Returns the command button responsible of the move : moving to the right top diagonal
		* @return the button diagUPR
		*/
	public JButton getDiagUPR() {
		return this.diagUPR;
	}

	/**
		* Returns the command button responsible of the move : moving to the left
		* @return the button left
		*/
	public JButton getLeft() {
		return this.left;

	}

	/**
		* Returns the command button responsible of the action : placing a wall
		* @return the button wall
		*/
	public JButton getWall() {
		return this.wall;
	}

	/**
		* Returns the command button responsible of the move : moving to the right
		* @return the button right
		*/
	public JButton getRight() {
		return this.right;
	}

	/**
		* Returns the command button responsible of the move : moving to the left bottom diagonal
		* @return the button diagDOWNDL
		*/
	public JButton getDiagDL() {
		return this.diagDOWNL;
	}

	/**
		* Returns the command button responsible of the move : moving to the bottom
		* @return the button bottom
		*/
	public JButton getDown() {
		return this.down;
	}

	/**
		* Returns the command button responsible of the move : moving to the right bottom diagonal
		* @return the button diagDOWNDR
		*/
	public JButton getDiagDR() {
		return this.diagDOWNR;
	}

	/**
		* Returns the player 1
		* @return the Player P1
		*/
	public Player getPlayer1() {
		return this.P1;
	}

	/**
		* Returns the player 2
		* @return the Player P2
		*/
	public Player getPlayer2() {
		return this.P2;
	}

	/**
		* Returns the Board linked to the Game attribute
		* @return the board
		*/
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Sets the Board linked to the Game attribute
	 * @param board the board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * Returns the Game attribute
	 * @return the game
	 */
	public Game getGame() {
		return this.gameplay;
	}

	/**
		* This method sets the player which has to make an action.
		* @param player the player that has to play
		*/
	public void setActivePlayer(Player player) {
		this.activePlayer = player;
	}

	/**
		* Returns the button that manages the Game which is played
		* @return the button save
		*/
	public JButton getSave() {
		return this.save;
	}

	/**
		* This methods swipes the actual player to the other one :
		*	if the actual player attribute is set on P1 ; swaps it to P2 (and also changes color effect of the text)
		*	if the actual player attribute is set on P2 ; swaps it to P1 (and also changes color effect of the text)
		*/
	public void changeActivePlayer() {

		if (this.activePlayer == P1){
				this.activePlayer = P2;
				this.P1RW.setForeground(new Color(53, 199, 88)); // Couleur verte le joueur peut jouer
				this.P1WN.setForeground(new Color(53, 199, 88));
				this.P2RW.setForeground(new Color(218, 64, 36)); // Couleur rouge le joueur peut jouer
				this.P2WN.setForeground(new Color(218, 64, 36));
				if(this.mode == Mode.HH){
					this.enableMoveQ(SquareType.PAWN2);
				}
				if(this.gameType == GameType.TOURNAMENT){
					tempsPasDepasse = false;
					this.delay = seconde;
					timerLabel.setText("Timer : "+delay);
					this.taskPerformer = new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						delay --;
						if(delay >= 0){
							timerLabel.setText("Timer : "+delay);
						}
							if(delay <= 0){
								tempsPasDepasse = true;
								timer.stop();
								changeActivePlayer();

							}
						}
					};
					this.timer = new Timer(1000, this.taskPerformer);
					this.timer.start();

				System.out.println("juste apres timer1 :"+tempsPasDepasse);
				}
				if(this.mode == Mode.HA){
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
					boolean move = false;
					while(!move){
					  double moves = Math.random();
					  if(moves >= 0 && moves <= 0.5){
						double randomMove = Math.random();
						if ((randomMove >= 0) && (randomMove <= 0.20)) {
						  this.move(this.getActivePlayer(), "RIGHT");
						  System.out.println(this.gameplay.getBoard().toString());
						  move = true;
						} else if ((randomMove > 0.20) && (randomMove <= 0.40)) {
						  this.move(this.getActivePlayer(), "LEFT");
						  System.out.println(this.gameplay.getBoard().toString());
						  move = true;
						} else if ((randomMove > 0.40) && (randomMove <= 0.60)) {
						  this.move(this.getActivePlayer(), "UP");
						  System.out.println(this.gameplay.getBoard().toString());
						  move = true;
						} else if ((randomMove > 0.60) && (randomMove <= 0.80)) {
						  this.move(this.getActivePlayer(), "DOWN");
						  System.out.println(this.gameplay.getBoard().toString());
						  move = true;
						} else {
						  if (x1 - 2 >= 0 && y1 + 2 <= 16) {
							if (this.board.checkPosition(x1, y1, x1 - 2, y1 + 2) && !move) {
								this.move(this.getActivePlayer(), "DIAGUR");
								move = true;
							  }
							}
							//diagonal hautre gauche
							if (x1 - 2 >= 0 && y1 - 2 >= 0) {
							  if (this.board.checkPosition(x1, y1, x1 - 2, y1 - 2) && !move) {
									this.move(this.getActivePlayer(), "DIAGUL");
								  move = true;
								}
							  }
							  //diagonal basse gauche
							  if (x1 + 2 <= 16 && y1 - 2 >= 0) {
								if (this.board.checkPosition(x1, y1, x1 + 2, y1 - 2) && !move) {
									this.move(this.getActivePlayer(), "DIAGDL");
								  move = true;
								}
							  }
							  //diagonal basse droite
							  if (x1 + 2 <= 16 && y1 + 2 <= 16) {
								if (this.board.checkPosition(x1, y1, x1 + 2, y1 + 2) && !move) {
									this.move(this.getActivePlayer(), "DIAGR");
								  move = true;
								}
							  }
						}
					  }else{
						int x2 = -1;
						int y2 = -1;
						for (int y = 0; y < 17; y++) {
							for (int x = 0; x < 17; x++) {
								if (this.board.getGrid()[y][x].getType() == SquareType.PAWN1) {
									x2 = y;
									y2 = x;
								}
							}
						}
						boolean aaPlaceUnMur = false;
						int cmp = 0;
						while(!aaPlaceUnMur && wallAA2 > 0){
							int xW = (int)(Math.random() * 17);
							int yW = (int)(Math.random() * 17);

							cmp ++;
							System.out.println("nb cmp : "+cmp);
							while (!this.board.checkWallPlacement(xW, yW)) {
							xW = (int)(Math.random() * 17);
							yW = (int)(Math.random() * 17);
							}

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
							System.out.println("verif :"+verif);
							if (verif) {
								this.wallAA2--;
								this.board.getGrid()[xW][yW].setType(SquareType.WALL);
								this.board.getGrid()[xW2][yW2].setType(SquareType.WALL);

								if(this.board.BFS(x1,y1).getX() != 16 && this.board.BFS(x2,y2).getX() != 0){
									this.wallAA2 ++;
									this.board.getGrid()[xW][yW].setType(SquareType.FREE);
									this.board.getGrid()[xW2][yW2].setType(SquareType.FREE);
									move = false;
								}else if (this.board.BFS(x1,y1).getX() == 16 && this.board.BFS(x2,y2).getX() == 0){
									move = true;
									aaPlaceUnMur = true;
									this.board.getGrid()[xW][yW].setType(SquareType.FREE);
									this.board.getGrid()[xW2][yW2].setType(SquareType.FREE);
									this.wall(this.getActivePlayer(),xW,yW);
									System.out.println("move :"+move );
									System.out.println("aaPlacerUnMur :"+aaPlaceUnMur );

								}
							}
						}

					  }
					}
				}
		} else if (this.activePlayer == P2) {
			this.activePlayer = P1;
			this.P1RW.setForeground(new Color(218, 64, 36)); // Couleur verte le joueur peut jouer
			this.P1WN.setForeground(new Color(218, 64, 36));
			this.P2RW.setForeground(new Color(53, 199, 88)); // Couleur rouge le joueur peut jouer
			this.P2WN.setForeground(new Color(53, 199, 88));
			this.enableMoveQ(SquareType.PAWN1);
			if(this.gameType == GameType.TOURNAMENT){
				tempsPasDepasse = false;
				this.delay = seconde;
				timerLabel.setText("Timer : "+delay);
				this.taskPerformer = new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					delay --;
					if(delay >= 0){
						timerLabel.setText("Timer : "+delay);
					}
						if(delay <= 0){
							tempsPasDepasse = true;
							timer.stop();
							changeActivePlayer();

						}
					}
				};
				this.timer = new Timer(1000, this.taskPerformer);
				this.timer.start();

			System.out.println("juste apres timer1 :"+tempsPasDepasse);
			}
		}
	}




	/**
		* This method is very useful to decide if a command button has to be enabled or not.
		* It takes one parameter which is the Player that we've to check if :
		*	knowing its x and y position, if the Player is in the very left, right, top or bottom of the Board
		* this method disable the button that cannot be used at the Player's position.
		* @param player the player we've to check coordinate to disable command buttons or not
		*/
	public void enableMoveQ(SquareType player){
			int x1 = -1;
			int y1 = -1;
			for (int x = 0; x < 17 ; x++) {
				for (int y = 0; y < 17  ; y++) {
					if (this.board.getGrid()[x][y].getType() == player ){
						x1 = x;
						y1 = y;

					}
				}
			}
			int x2 = -1;
			int y2 = -1;
			for (int x = 0; x < 17 ; x++) {
				for (int y = 0; y < 17  ; y++) {
					if (this.board.getGrid()[x][y].getType() != player && this.board.getGrid()[x][y].getType() != SquareType.NONE && this.board.getGrid()[x][y].getType() != SquareType.FREE && this.board.getGrid()[x][y].getType() != SquareType.WALL){
						x2 = x;
						y2 = y;

					}
				}
			}

			SquareType otherP = this.board.getGrid()[x2][y2].getType();
			//
		//	System.out.println("x1 :"+x1);
		//	System.out.println("y1 :"+y1);
		//	System.out.println("x2 :"+x2);
		//	System.out.println("y2 :"+y2);

				if (x1 + 2 > 16) {
					down.setEnabled(false);
				}else if (x1 + 4 <= 16 &&  this.board.getGrid()[x1+2][y1].getType() == otherP && this.board.checkPosition(x1,y1,x1+4,y1)){
					down.setEnabled(true);
				}else if(this.board.checkPosition(x1,y1,x1+2,y1)){
					down.setEnabled(true);
				}else down.setEnabled(false);

				if(x1 - 2 < 0){
					up.setEnabled(false);
				}else if(x1 - 4 >= 0 &&   this.board.getGrid()[x1-2][y1].getType() == otherP && this.board.checkPosition(x1,y1,x1+4,y1)){
					up.setEnabled(true);
				}	else if(this.board.checkPosition(x1,y1,x1-2,y1)){
					up.setEnabled(true);
				}else up.setEnabled(false);

				if(y1 - 2 < 0){
					left.setEnabled(false);
				}else if(y1 - 4 >= 0 && this.board.getGrid()[x1][y1-2].getType() == otherP && this.board.checkPosition(x1,y1,x1,y1-4)){
					left.setEnabled(true);
				}	else if(this.board.checkPosition(x1,y1,x1,y1-2)){
					 left.setEnabled(true);
				}else left.setEnabled(false);

				if(y1 + 2 > 16){
					right.setEnabled(false);
				}else if(y1 + 4 <= 16 && this.board.getGrid()[x1][y1+2].getType() == otherP && this.board.checkPosition(x1,y1,x1,y1+4)){
					right.setEnabled(true);
				}else if(this.board.checkPosition(x1,y1,x1,y1+2)){
					right.setEnabled(true);
				}else right.setEnabled(false);

				if(y1 + 2 > 16 || x1 - 2 < 0 ){
					diagUPR.setEnabled(false);
				}else if(y1 + 2 <= 16 && x1 - 2 >= 0  && this.board.checkPosition(x1,y1,x1-2,y1+2)){
					diagUPR.setEnabled(true);
				}else diagUPR.setEnabled(false);

				if(y1 - 2  < 0|| x1 - 2 < 0 ){
					diagUPL.setEnabled(false);
				}else if(y1 - 2 >= 0  && x1 - 2 >= 0  && this.board.checkPosition(x1,y1,x1-2,y1-2)){
					diagUPL.setEnabled(true);
				}else diagUPL.setEnabled(false);

				if(y1 - 2  < 0|| x1 + 2 > 16 ){
					diagDOWNL.setEnabled(false);
				}else if(y1 - 2 >= 0  && x1 + 2 <= 16  && this.board.checkPosition(x1,y1,x1+2,y1-2)){
					diagDOWNL.setEnabled(true);
				}else diagDOWNL.setEnabled(false);

				if(y1 + 2  > 16 || x1 + 2 > 16 ){
					diagDOWNR.setEnabled(false);
				}else if(y1 + 2 <= 16  && x1 + 2 <= 16  && this.board.checkPosition(x1,y1,x1+2,y1+2)){
					diagDOWNR.setEnabled(true);
				}else diagDOWNR.setEnabled(false);

	}
//	System.out.printltn(this.activePlayer);*/



	/**
		* This method moves the Player in parameter to the direction asked in parameter
		* @param player the player which has to be moved
		*	@param move the direction where the player has to move
		*/
	public void move(Player player, String move) {
		this.activePlayer = player;
		boolean pasAMoiMerci = false;
		this.P1RW.setForeground(new Color(53, 199, 88));
		this.P1WN.setForeground(new Color(53, 199, 88));
		this.P2RW.setForeground(new Color(218, 64, 36));
		this.P2WN.setForeground(new Color(218, 64, 36));
		if(this.activePlayer == P1 && !pasAMoiMerci){
				int x = -1;
				int y = -1;
				for (int x1 = 0; x1 < 17 ; x1++) {
					for (int y1 = 0; y1 < 17  ; y1++) {
						if (this.board.getGrid()[x1][y1].getType() == SquareType.PAWN1){
							x = x1;
							y = y1;
						}
					}
				}
				int x2 = -1;
				int y2 = -1;
				for (int x3 = 0; x3 < 17 ; x3++) {
					for (int y3 = 0; y3 < 17  ; y3++) {
						if (this.board.getGrid()[x3][y3].getType() != player.getTypeJ() && this.board.getGrid()[x3][y3].getType() != SquareType.NONE && this.board.getGrid()[x3][y3].getType() != SquareType.FREE && this.board.getGrid()[x3][y3].getType() != SquareType.WALL){
							x2 = x3;
							y2 = y3;
						}
					}
				}
				SquareType otherP = this.board.getGrid()[x2][y2].getType();

				if (move.toUpperCase().equals("RIGHT")) {
					if(y+4 <= 16 && this.board.getGrid()[x][y+2].getType() == otherP && this.board.checkPosition(x,y,x,y+4)){
						this.board.getGrid()[x][y].setType(SquareType.FREE);
						this.board.getGrid()[x][y+4].setType(SquareType.PAWN1);
						System.out.println(this.board.toString());
						this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
						this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
						this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn2.png"));
						this.panelTab[(int)(x/2)][(int)((y+4)/2)].add(pawn2);
						SwingUtilities.updateComponentTreeUI(this);
					}else if(y+2 <= 16){
						this.board.getGrid()[x][y].setType(SquareType.FREE);
						this.board.getGrid()[x][y+2].setType(SquareType.PAWN1);
						System.out.println(this.board.toString());
						this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
						this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
						this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn2.png"));
						this.panelTab[(int)(x/2)][(int)((y+2)/2)].add(pawn2);
						SwingUtilities.updateComponentTreeUI(this);
					}

				}else if(move.toUpperCase().equals("LEFT")){
					if(y-4 >= 0 && this.board.getGrid()[x][y-2].getType() == otherP &&  this.board.checkPosition(x,y,x,y-4)){
						this.board.getGrid()[x][y].setType(SquareType.FREE);
						this.board.getGrid()[x][y-4].setType(SquareType.PAWN1);
						System.out.println(this.board.toString());
						this.panelTab[(x/2)][(y/2)].removeAll();
						this.panelTab[(x/2)][(y/2)].revalidate();
						this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn2.png"));
						this.panelTab[(int)(x/2)][(int)((y-4)/2)].add(pawn2);
						SwingUtilities.updateComponentTreeUI(this);
					}else if(y-2 >= 0){
						this.board.getGrid()[x][y].setType(SquareType.FREE);
						this.board.getGrid()[x][y-2].setType(SquareType.PAWN1);
						System.out.println(this.board.toString());
						this.panelTab[(x/2)][(y/2)].removeAll();
						this.panelTab[(x/2)][(y/2)].revalidate();
						this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn2.png"));
						this.panelTab[(int)(x/2)][(int)((y-2)/2)].add(pawn2);
						SwingUtilities.updateComponentTreeUI(this);
					}

				}else if (move.toUpperCase().equals("UP")) {
					if(x-4 >= 0 && this.board.getGrid()[x-2][y].getType() == otherP &&  this.board.checkPosition(x,y,x-4,y) ){
						this.board.getGrid()[x][y].setType(SquareType.FREE);
						this.board.getGrid()[x-4][y].setType(SquareType.PAWN1);
						System.out.println(this.board.toString());
						this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
						this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
						this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn2.png"));
						this.panelTab[(int)((x-4)/2)][(int)(y/2)].add(pawn2);
						SwingUtilities.updateComponentTreeUI(this);
					}else if(x - 2 >=  0){
						this.board.getGrid()[x][y].setType(SquareType.FREE);
						this.board.getGrid()[x-2][y].setType(SquareType.PAWN1);
						System.out.println(this.board.toString());
						this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
						this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
						this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn2.png"));
						this.panelTab[(int)((x-2)/2)][(int)(y/2)].add(pawn2);
						SwingUtilities.updateComponentTreeUI(this);
					}


				} else if (move.toUpperCase().equals("DOWN")) {
					if(x+4 <= 16 && this.board.getGrid()[x+2][y].getType() == otherP &&  this.board.checkPosition(x,y,x+4,y)){
						this.board.getGrid()[x][y].setType(SquareType.FREE);
						this.board.getGrid()[x+4][y].setType(SquareType.PAWN1);
						System.out.println(this.board.toString());
						this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
						this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
						this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn2.png"));
						this.panelTab[(int)((x+4)/2)][(int)((y)/2)].add(pawn2);
						SwingUtilities.updateComponentTreeUI(this);
					}else if(x+2 <= 16){
						this.board.getGrid()[x][y].setType(SquareType.FREE);
						this.board.getGrid()[x+2][y].setType(SquareType.PAWN1);
						System.out.println(this.board.toString());
						this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
						this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
						this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn2.png"));
						this.panelTab[(int)((x+2)/2)][(int)((y)/2)].add(pawn2);
						SwingUtilities.updateComponentTreeUI(this);
					}
				}
				else if (move.toUpperCase().equals("DIAGUL")) {
					if(y - 2 >= 0 && x - 2 >= 0){
						this.board.getGrid()[x][y].setType(SquareType.FREE);
						this.board.getGrid()[x-2][y-2].setType(SquareType.PAWN1);
						System.out.println(this.board.toString());
						this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
						this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
						this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn2.png"));
						this.panelTab[(int)((x-2)/2)][(int)((y-2)/2)].add(pawn2);
						SwingUtilities.updateComponentTreeUI(this);
					}
				 }
				 else if (move.toUpperCase().equals("DIAGUR")) {
						if(y + 2 <= 16 && x - 2 >= 0){
							this.board.getGrid()[x][y].setType(SquareType.FREE);
							this.board.getGrid()[x-2][y+2].setType(SquareType.PAWN1);
							System.out.println(this.board.toString());
							this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
							this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
							this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn2.png"));
							this.panelTab[(int)((x-2)/2)][(int)((y+2)/2)].add(pawn2);
							SwingUtilities.updateComponentTreeUI(this);
						}
					}
					else if (move.toUpperCase().equals("DIAGDL")) {
						if(y - 2 >= 0 && x + 2 <= 16){
							this.board.getGrid()[x][y].setType(SquareType.FREE);
							this.board.getGrid()[x+2][y-2].setType(SquareType.PAWN1);
							System.out.println(this.board.toString());
							this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
							this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
							this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn2.png"));
							this.panelTab[(int)((x+2)/2)][(int)((y-2)/2)].add(pawn2);
							SwingUtilities.updateComponentTreeUI(this);
						}
					 }
					 else if (move.toUpperCase().equals("DIAGDR")) {
						if(y + 2 <= 16 && x + 2 <= 16){
							this.board.getGrid()[x][y].setType(SquareType.FREE);
							this.board.getGrid()[x+2][y+2].setType(SquareType.PAWN1);
							System.out.println(this.board.toString());
							this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
							this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
							this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn2.png"));
							this.panelTab[(int)((x+2)/2)][(int)((y+2)/2)].add(pawn2);
							SwingUtilities.updateComponentTreeUI(this);
						}
					}

						this.changeActivePlayer();
		 				pasAMoiMerci = true;
		 				this.endOfGame(player);
			 }
			if(this.activePlayer == P2 && !pasAMoiMerci){

				int x = -1;
				int y = -1;
				for (int x1 = 0; x1 < 17 ; x1++) {
					for (int y1 = 0; y1 < 17  ; y1++) {
						if (this.board.getGrid()[x1][y1].getType() == SquareType.PAWN2){
							x = x1;
							y = y1;
						}
					}
				}
				int x2 = -1;
				int y2 = -1;
				for (int x3 = 0; x3 < 17 ; x3++) {
					for (int y3 = 0; y3 < 17  ; y3++) {
						if (this.board.getGrid()[x3][y3].getType() != player.getTypeJ() && this.board.getGrid()[x3][y3].getType() != SquareType.NONE && this.board.getGrid()[x3][y3].getType() != SquareType.FREE && this.board.getGrid()[x3][y3].getType() != SquareType.WALL){
							x2 = x3;
							y2 = y3;
						}
					}
				}
				SquareType otherP = this.board.getGrid()[x2][y2].getType();

				if (move.toUpperCase().equals("RIGHT")) {
					System.out.println("je vais dans le right du j2");
					if(y+4 <= 16 && this.board.getGrid()[x][y+2].getType() == otherP &&  this.board.checkPosition(x,y,x,y+4)){
						System.out.println("je vais dans le right 2 du j2");
						this.board.getGrid()[x][y].setType(SquareType.FREE);
						this.board.getGrid()[x][y+4].setType(SquareType.PAWN2);
						System.out.println(this.board.toString());
						this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
						this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
						this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn1.png"));
						this.panelTab[(int)(x/2)][(int)((y+4)/2)].add(pawn2);
						SwingUtilities.updateComponentTreeUI(this);
					}else if (y+2 <= 16 ){
						System.out.println("je vais dans le right Normal du j2");
						this.board.getGrid()[x][y].setType(SquareType.FREE);
						this.board.getGrid()[x][y+2].setType(SquareType.PAWN2);
						System.out.println(this.board.toString());
						this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
						this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
						this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn1.png"));
						this.panelTab[(int)(x/2)][(int)((y+2)/2)].add(pawn2);
						SwingUtilities.updateComponentTreeUI(this);
					}

				}else if(move.toUpperCase().equals("LEFT")){
					System.out.println("je vais dans le left du j2");
					if(y-4 >= 0 && this.board.getGrid()[x][y-2].getType() == otherP &&  this.board.checkPosition(x,y,x,y-4)){
						System.out.println("je vais dans le left2 du j2");
						this.board.getGrid()[x][y].setType(SquareType.FREE);
						this.board.getGrid()[x][y-4].setType(SquareType.PAWN2);
						System.out.println(this.board.toString());
						this.panelTab[(x/2)][(y/2)].removeAll();
						this.panelTab[(x/2)][(y/2)].revalidate();
						this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn1.png"));
						this.panelTab[(int)(x/2)][(int)((y-4)/2)].add(pawn2);
						SwingUtilities.updateComponentTreeUI(this);
					}else if (y-2 >= 0 ){
						System.out.println("je vais dans le left Normal du j2");
						this.board.getGrid()[x][y].setType(SquareType.FREE);
						this.board.getGrid()[x][y-2].setType(SquareType.PAWN2);
						System.out.println(this.board.toString());
						this.panelTab[(x/2)][(y/2)].removeAll();
						this.panelTab[(x/2)][(y/2)].revalidate();
						this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn1.png"));
						this.panelTab[(int)(x/2)][(int)((y-2)/2)].add(pawn2);
						SwingUtilities.updateComponentTreeUI(this);
					}

				}else if (move.toUpperCase().equals("UP")) {
					System.out.println("je vais dans le up du j2");
					if(x-4 >= 0 && this.board.getGrid()[x-2][y].getType() == otherP &&  this.board.checkPosition(x,y,x-4,y) ){
						System.out.println("je vais dans le up2 du j2");
						this.board.getGrid()[x][y].setType(SquareType.FREE);
						this.board.getGrid()[x-4][y].setType(SquareType.PAWN2);
						System.out.println(this.board.toString());
						this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
						this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
						this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn1.png"));
						this.panelTab[(int)((x-4)/2)][(int)(y/2)].add(pawn2);
						SwingUtilities.updateComponentTreeUI(this);
					}else if (x-2 >= 0 ){
						System.out.println("je vais dans le up normal du j2");
						this.board.getGrid()[x][y].setType(SquareType.FREE);
						this.board.getGrid()[x-2][y].setType(SquareType.PAWN2);
						System.out.println(this.board.toString());
						this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
						this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
						this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn1.png"));
						this.panelTab[(int)((x-2)/2)][(int)(y/2)].add(pawn2);
						SwingUtilities.updateComponentTreeUI(this);
					}



				} else if (move.toUpperCase().equals("DOWN")) {
					System.out.println("je vais dans le down du j2");
					if(x+4 <= 16 && this.board.getGrid()[x+2][y].getType() == otherP &&  this.board.checkPosition(x,y,x+4,y)){
						System.out.println("je vais dans le down 2 du j2");
						this.board.getGrid()[x][y].setType(SquareType.FREE);
						this.board.getGrid()[x+4][y].setType(SquareType.PAWN2);
						System.out.println(this.board.toString());
						this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
						this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
						this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn1.png"));
						this.panelTab[(int)((x+4)/2)][(int)((y)/2)].add(pawn2);
						SwingUtilities.updateComponentTreeUI(this);
					}else  if (x+2 <= 16 ){
						System.out.println("je vais dans le down normal du j2");
						this.board.getGrid()[x][y].setType(SquareType.FREE);
						this.board.getGrid()[x+2][y].setType(SquareType.PAWN2);
						System.out.println(this.board.toString());
						this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
						this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
						this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn1.png"));
						this.panelTab[(int)((x+2)/2)][(int)((y)/2)].add(pawn2);
						SwingUtilities.updateComponentTreeUI(this);
					}
				}
				else if (move.toUpperCase().equals("DIAGUL")) {
					if (y-2 >= 0 && x-2 >= 0 ){
						this.board.getGrid()[x][y].setType(SquareType.FREE);
						this.board.getGrid()[x-2][y-2].setType(SquareType.PAWN2);
						System.out.println(this.board.toString());
						this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
						this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
						this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn1.png"));
						this.panelTab[(int)((x-2)/2)][(int)((y-2)/2)].add(pawn2);
						SwingUtilities.updateComponentTreeUI(this);
					}
				 }
				 else if (move.toUpperCase().equals("DIAGUR")) {
					if (y+2 <= 16 && x-2 >= 0 ){
							this.board.getGrid()[x][y].setType(SquareType.FREE);
							this.board.getGrid()[x-2][y+2].setType(SquareType.PAWN2);
							System.out.println(this.board.toString());
							this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
							this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
							this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn1.png"));
							this.panelTab[(int)((x-2)/2)][(int)((y+2)/2)].add(pawn2);
							SwingUtilities.updateComponentTreeUI(this);
					}
				}
				else if (move.toUpperCase().equals("DIAGDL")) {
					if (y-2 >= 0 && x+2 <= 16 ){
						 this.board.getGrid()[x][y].setType(SquareType.FREE);
						 this.board.getGrid()[x+2][y-2].setType(SquareType.PAWN2);
						 System.out.println(this.board.toString());
						 this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
						 this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
						 this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn1.png"));
						 this.panelTab[(int)((x+2)/2)][(int)((y-2)/2)].add(pawn2);
						 SwingUtilities.updateComponentTreeUI(this);
					}
				}
					 else if (move.toUpperCase().equals("DIAGDR")) {
						if (y+2 <= 16 && x-2 <= 16 ){
							this.board.getGrid()[x][y].setType(SquareType.FREE);
							this.board.getGrid()[x+2][y+2].setType(SquareType.PAWN2);
							System.out.println(this.board.toString());
							this.panelTab[(int)(x/2)][(int)(y/2)].removeAll();
							this.panelTab[(int)(x/2)][(int)(y/2)].revalidate();
							this.pawn2 = new JLabel(new ImageIcon("data/img/board/pawn1.png"));
							this.panelTab[(int)((x+2)/2)][(int)((y+2)/2)].add(pawn2);
							SwingUtilities.updateComponentTreeUI(this);
						}
					}
					this.changeActivePlayer();
					pasAMoiMerci = true;
					this.endOfGame(player);
				}
			}

			/**
				* This method places a wall at the x and y coordinates for the Player entered in parameter
				* @param player the player which has to place a wall
				* @param x x value of the wall
				* @param y y value of the wall
				*/
			public void wall(Player player,int x, int y) {
				if(x <= 16 && y >= 0 && x >= 0 && y <= 16){
					System.out.println("je check les taille");
						if(this.board.checkWallPlacement(x,y)){
							int x2 = -1;
							int y2 = -1;
							boolean verif = false;
							if (x != 16 && y != 16) {
								if (x % 2 == 1) {
									x2 = x;
									y2 = y + 2;
									if (this.board.getGrid()[x2][y2].getType() == SquareType.FREE) {

										verif = true;
									}
								}
								if (x % 2 == 0) {
									x2 = x + 2;
									y2 = y;
									if (this.board.getGrid()[x2][y2].getType() == SquareType.FREE) {

										verif = true;
									}
								}
							}
							if (x == 16 && y != 16) {
								x2 = x - 2;
								y2 = y;
								if (this.board.getGrid()[x2][y2].getType() == SquareType.FREE) {

									verif = true;
								}
							}
							if (x != 16 && y == 16) {
								x2 = x;
								y2 = y - 2;
								if (this.board.getGrid()[x2][y2].getType() == SquareType.FREE) {

									verif = true;
								}
							}
							if (verif) {
									int xJ1 = -1;
									int yJ1 = -1;
									for (int i = 0; i < 17; i++) {
										for (int j = 0; j < 17; j++) {
											if (this.board.getGrid()[i][j].getType() == SquareType.PAWN1) {
												xJ1 = i;
												yJ1 = j;
											}
										}
									}
									int xJ2 = -1;
									int yJ2 = -1;
									for (int i = 0; i < 17; i++) {
										for (int j = 0; j < 17; j++) {
											if (this.board.getGrid()[i][j].getType() == SquareType.PAWN2) {
												xJ2 = i;
												yJ2 = j;
											}
										}
									}
								if (this.board.getGrid()[x2][y2].getType() == SquareType.FREE) {
									this.board.getGrid()[x][y].setType(SquareType.WALL);
									this.board.getGrid()[x2][y2].setType(SquareType.WALL);
									if(player == P1){
										this.wallJ2 --;
										this.P2WN.setText(String.valueOf(this.wallJ2));
									}else{
										this.wallJ1 --;
										this.P1WN.setText(String.valueOf(this.wallJ1));
									}
									if (this.board.BFS(xJ2, yJ2).getX() == 16 && this.board.BFS(xJ1, yJ1).getX() == 0) {
										if(x%2 == 0 && y%2 == 1){
											if(x+1 <= 16 && x-1>= 0 && y+1<= 16 && y-2>= 0 && x != 1){
												if(this.board.getGrid()[x-1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(5,1,1,5,Color.BLACK));

												}
												if(this.board.getGrid()[x+1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x][y-2].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,5,1,5,Color.BLACK));

												}
												if(this.board.getGrid()[x][y-2].getType() == SquareType.WALL && this.board.getGrid()[x-1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(5,5,1,5,Color.BLACK));

												}
												if(this.board.getGrid()[x][y-2].getType() == SquareType.WALL && this.board.getGrid()[x+1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,5,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x+1][y-1].getType() == SquareType.WALL && this.board.getGrid()[x-1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(5,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x+1][y-1].getType() == SquareType.WALL && this.board.getGrid()[x-1][y-1].getType() == SquareType.WALL && this.board.getGrid()[x][y-2].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x+1][y-1].getType() != SquareType.WALL && this.board.getGrid()[x-1][y-1].getType() != SquareType.WALL && this.board.getGrid()[x][y-2].getType() != SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,1,5,Color.BLACK));

												}
											}
											if(y == 1  && x != 0 && x != 16){
												if(this.board.getGrid()[x-1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(5,1,1,5,Color.BLACK));

												}
												if(this.board.getGrid()[x+1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x+1][y-1].getType() == SquareType.WALL && this.board.getGrid()[x-1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(5,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x+1][y-1].getType() != SquareType.WALL && this.board.getGrid()[x-1][y-1].getType() != SquareType.WALL ){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,1,5,Color.BLACK));

												}

											}
											if(y == 1 && x == 0){
												if(this.board.getGrid()[x+1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x+1][y-1].getType() != SquareType.WALL ){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,1,5,Color.BLACK));

												}

											}
											if(y == 1 && x == 16){
												if(this.board.getGrid()[x-1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(5,1,1,5,Color.BLACK));

												}
												if(this.board.getGrid()[x-1][y-1].getType() != SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,1,5,Color.BLACK));

												}

											}
											if(y != 1 && x == 0){
												if(this.board.getGrid()[x+1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x][y-2].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,5,1,5,Color.BLACK));

												}
												if(this.board.getGrid()[x][y-2].getType() == SquareType.WALL && this.board.getGrid()[x+1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,5,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x+1][y-1].getType() != SquareType.WALL && this.board.getGrid()[x][y-2].getType() != SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,1,5,Color.BLACK));

												}
											}
											if(y != 1 && x == 16){
												if(this.board.getGrid()[x-1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(5,1,1,5,Color.BLACK));

												}
												else if(this.board.getGrid()[x][y-2].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,5,1,5,Color.BLACK));

												}
												else if(this.board.getGrid()[x][y-2].getType() == SquareType.WALL && this.board.getGrid()[x-1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(5,5,1,5,Color.BLACK));

												}
												if( this.board.getGrid()[x-1][y-1].getType() != SquareType.WALL && this.board.getGrid()[x][y-2].getType() != SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,1,5,Color.BLACK));

												}
											}
											if(x2+1 <= 16 && x2-1>= 0 && y2+1<= 16 && y2-2>= 0 &&  x2 != 1){
												if(this.board.getGrid()[x2-1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(5,1,1,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2+1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2][y2-2].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,5,1,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2][y2-2].getType() == SquareType.WALL && this.board.getGrid()[x2-1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(5,5,1,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2][y2-2].getType() == SquareType.WALL && this.board.getGrid()[x2+1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,5,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2+1][y2-1].getType() == SquareType.WALL && this.board.getGrid()[x2-1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(5,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2+1][y2-1].getType() == SquareType.WALL && this.board.getGrid()[x2-1][y2-1].getType() == SquareType.WALL && this.board.getGrid()[x2][y2-2].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2+1][y2-1].getType() != SquareType.WALL && this.board.getGrid()[x2-1][y2-1].getType() != SquareType.WALL && this.board.getGrid()[x2][y2-2].getType() != SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,1,5,Color.BLACK));

												}
											}
											if(y2 == 1 && x2 != 0 && x2 != 16){
												if(this.board.getGrid()[x2-1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(5,1,1,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2+1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2+1][y2-1].getType() == SquareType.WALL && this.board.getGrid()[x2-1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(5,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2+1][y2-1].getType() != SquareType.WALL && this.board.getGrid()[x2-1][y2-1].getType() != SquareType.WALL ){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,1,5,Color.BLACK));

												}

											}
											if(y2 == 1 && x2 == 0){
												if(this.board.getGrid()[x2+1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2+1][y2-1].getType() != SquareType.WALL ){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,1,5,Color.BLACK));

												}

											}
											if(y2 == 1 && x2 == 16){
												if(this.board.getGrid()[x2-1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(5,1,1,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2-1][y2-1].getType() != SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,1,5,Color.BLACK));

												}
											}
											if(y2 != 1 && x2 == 0){
												if(this.board.getGrid()[x2+1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2][y2-2].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,5,1,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2][y2-2].getType() == SquareType.WALL && this.board.getGrid()[x2+1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,5,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2+1][y2-1].getType() != SquareType.WALL && this.board.getGrid()[x2][y2-2].getType() != SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,1,5,Color.BLACK));

												}
											}
											if(y2 != 1 && x2 == 16){
												if(this.board.getGrid()[x2-1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(5,1,1,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2][y2-2].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,5,1,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2][y2-2].getType() == SquareType.WALL && this.board.getGrid()[x2-1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(5,5,1,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2-1][y2-1].getType() != SquareType.WALL && this.board.getGrid()[x2][y2-2].getType() != SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,1,5,Color.BLACK));

												}
											}
										}
										//
										//
										//
										//
										if(x%2 == 1 && y%2 == 0){
											System.out.println("val a calcul y-1: "+(y-1));
											System.out.println("val a calcul x+1: "+(x+1));
											System.out.println("val a calcul x-2: "+(x-2));
											System.out.println("val a calcul y+1: "+(y+1));
											if(x+1 <= 16 && x-2>= 0 && y+1<= 16 && y-1>= 0 && x != 1){
												if(this.board.getGrid()[x-1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,5,5,1,Color.BLACK));

												}
												if(this.board.getGrid()[x-1][y+1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x-2][y].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(5,1,5,1,Color.BLACK));

												}
												if(this.board.getGrid()[x-2][y].getType() == SquareType.WALL && this.board.getGrid()[x-1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(5,5,5,1,Color.BLACK));

												}
												if(this.board.getGrid()[x-2][y].getType() == SquareType.WALL && this.board.getGrid()[x-1][y+1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(5,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x-1][y-1].getType() == SquareType.WALL && this.board.getGrid()[x-1][y+1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,5,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x-1][y+1].getType() == SquareType.WALL && this.board.getGrid()[x-1][y-1].getType() == SquareType.WALL && this.board.getGrid()[x-2][y].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x-1][y+1].getType() != SquareType.WALL && this.board.getGrid()[x-1][y-1].getType() != SquareType.WALL && this.board.getGrid()[x-2][y].getType() != SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,1,Color.BLACK));

												}
											}
											if(x ==  1 && y != 0 && y != 16){
												if(this.board.getGrid()[x-1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,5,5,1,Color.BLACK));

												}
												if(this.board.getGrid()[x-1][y+1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x+1][y-1].getType() == SquareType.WALL && this.board.getGrid()[x-1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,5,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x-1][y+1].getType() != SquareType.WALL && this.board.getGrid()[x-1][y-1].getType() != SquareType.WALL ){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,1,Color.BLACK));

												}

											}
											if(x == 1 && y == 0){
												if(this.board.getGrid()[x-1][y+1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x-1][y+1].getType() != SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,1,Color.BLACK));

												}

											}
											if(x == 1 && y == 16){
												if(this.board.getGrid()[x-1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,5,5,1,Color.BLACK));

												}
												if( this.board.getGrid()[x-1][y-1].getType() != SquareType.WALL ){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,1,Color.BLACK));

												}

											}
											if(x != 1 && y == 0){
												if(this.board.getGrid()[x-1][y+1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x-2][y].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(5,1,5,1,Color.BLACK));

												}
												if(this.board.getGrid()[x-2][y].getType() == SquareType.WALL && this.board.getGrid()[x-1][y+1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(5,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x-1][y+1].getType() != SquareType.WALL && this.board.getGrid()[x-2][y].getType() != SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,1,Color.BLACK));

												}

											}
											if(x != 1 && y == 16){
												if(this.board.getGrid()[x-1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,5,5,1,Color.BLACK));

												}
												if(this.board.getGrid()[x-2][y].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(5,1,5,1,Color.BLACK));

												}
												if(this.board.getGrid()[x-2][y].getType() == SquareType.WALL && this.board.getGrid()[x-1][y-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(5,5,5,1,Color.BLACK));

												}
												if( this.board.getGrid()[x-1][y-1].getType() != SquareType.WALL && this.board.getGrid()[x-2][y].getType() != SquareType.WALL){
													this.panelTab[(int)(x/2)][(int)(y/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,1,Color.BLACK));

												}
											}
											if(x2+1 <= 16 && x2-2>= 0 && y2+1<= 16 && y2-1>= 0 &&  x2 != 1){
												if(this.board.getGrid()[x2-1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,5,5,1,Color.BLACK));

												}
												if(this.board.getGrid()[x2-1][y2+1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2-2][y2].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(5,1,5,1,Color.BLACK));

												}
												 if(this.board.getGrid()[x2-2][y2].getType() == SquareType.WALL && this.board.getGrid()[x2-1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(5,5,5,1,Color.BLACK));

												}
												 if(this.board.getGrid()[x2-2][y2].getType() == SquareType.WALL && this.board.getGrid()[x2-1][y2+1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(5,1,5,5,Color.BLACK));

												}
												 if(this.board.getGrid()[x2-1][y2-1].getType() == SquareType.WALL && this.board.getGrid()[x2-1][y2+1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,5,5,5,Color.BLACK));

												}
												 if(this.board.getGrid()[x2-1][y2+1].getType() == SquareType.WALL && this.board.getGrid()[x2-1][y2-1].getType() == SquareType.WALL && this.board.getGrid()[x2-2][y2].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2-1][y2+1].getType() != SquareType.WALL && this.board.getGrid()[x2-1][y2-1].getType() != SquareType.WALL && this.board.getGrid()[x2-2][y2].getType() != SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,1,Color.BLACK));

												}
											}
											if(x2 ==  1 && y2 != 0 && y2 != 16){
												if(this.board.getGrid()[x2-1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,5,5,1,Color.BLACK));

												}
												 if(this.board.getGrid()[x2-1][y2+1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,5,Color.BLACK));

												}
												 if(this.board.getGrid()[x2+1][y2-1].getType() == SquareType.WALL && this.board.getGrid()[x2-1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,5,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2-1][y2+1].getType() != SquareType.WALL && this.board.getGrid()[x2-1][y2-1].getType() != SquareType.WALL ){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,1,Color.BLACK));

												}

											}
											if(x2 == 1 && y2 == 0){
												if(this.board.getGrid()[x2-1][y2+1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2-1][y2+1].getType() != SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,1,Color.BLACK));

												}

											}
											if(x2 == 1 && y2 == 16){
												if(this.board.getGrid()[x2-1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,5,5,1,Color.BLACK));

												}
												if( this.board.getGrid()[x2-1][y2-1].getType() != SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,1,Color.BLACK));

												}

											}
											if(x2 != 1 && y2 == 0){
												if(this.board.getGrid()[x2-1][y2+1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,5,Color.BLACK));

												}
												 if(this.board.getGrid()[x2-2][y2].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(5,1,5,1,Color.BLACK));

												}
												 if(this.board.getGrid()[x2-2][y2].getType() == SquareType.WALL && this.board.getGrid()[x2-1][y2+1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(5,1,5,5,Color.BLACK));

												}
												if(this.board.getGrid()[x2-1][y2+1].getType() != SquareType.WALL && this.board.getGrid()[x2-2][y2].getType() != SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,1,Color.BLACK));

												}

											}
											if(x2 != 1 && y2 == 16){
												if(this.board.getGrid()[x2-1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,5,5,1,Color.BLACK));

												}
												 if(this.board.getGrid()[x2-2][y2].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(5,1,5,1,Color.BLACK));

												}
												 if(this.board.getGrid()[x2-2][y2].getType() == SquareType.WALL && this.board.getGrid()[x2-1][y2-1].getType() == SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(5,5,5,1,Color.BLACK));

												}
												if( this.board.getGrid()[x2-1][y2-1].getType() != SquareType.WALL && this.board.getGrid()[x2-2][y2].getType() != SquareType.WALL){
													this.panelTab[(int)(x2/2)][(int)(y2/2)].setBorder(BorderFactory.createMatteBorder(1,1,5,1,Color.BLACK));

												}
											}
										}
										this.changeActivePlayer();
								}else{
									System.out.println("peut pas jouer ici");
									this.board.getGrid()[x][y].setType(SquareType.FREE);
									this.board.getGrid()[x2][y2].setType(SquareType.FREE);
								}
							}
						}
					}

				}
				System.out.println(this.board.toString());

			}

			/**
				* Saves the game
				*/
				public void saveGame() {
					JFileChooser choice = new JFileChooser("data/saves");
					choice.addChoosableFileFilter(new serFilter());
					int retrival = 	choice.showSaveDialog(null);
					if (retrival == choice.APPROVE_OPTION) {
						String ext = "";
						String extension = choice.getFileFilter().getDescription();
						if (extension.equals("*.res,*.RES")) ext = ".ser";
						else ext = ".ser";


					}
				}

				/**
					* Returns the player which has to play
					* @return the Player
					*/
	public Player getActivePlayer() {
		return this.activePlayer;
	}

	/**
		* This method displays the classical scenario of the end of a game, with its winner as a parameter.
		* @param player the winner
		*/
	public void endOfGame(Player player) {
		int x = -1;
		int y = -1;
		if (player == P1) {
			for (int x1 = 0; x1 < 17; x1++) {
				for (int y1 = 0; y1 < 17; y1++) {
					if (this.board.getGrid()[x1][y1].getType() == SquareType.PAWN1) {
						x = x1;
						y = y1;
					}
				}
			}
			if (x == 0) {
				JOptionPane.showMessageDialog(null, "The game is over !", "Player 1 has won" , JOptionPane.INFORMATION_MESSAGE);
				try {
					BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd '-' HH:mm:ss");
					Date date = new Date(System.currentTimeMillis());
					String out = new SimpleDateFormat("yyyyMMdd-HHmmss'.png'").format(new Date());
					ImageIO.write(image, "png", new File("data/img/history/" + out));
					this.setWinner(player);
					this.appli.getContentPane().removeAll();
					this.appli.getContentPane().add(new EndOfGame(this.appli, this));
					this.appli.repaint();
					this.appli.revalidate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (player == P2) {
			for (int x1 = 0; x1 < 17; x1++) {
				for (int y1 = 0; y1 < 17; y1++) {
					if (this.board.getGrid()[x1][y1].getType() == SquareType.PAWN2) {
						x = x1;
						y = y1;
					}
				}
			}
			if (x == 16) {
				JOptionPane.showMessageDialog(null, "The game is over !", "Player 2 has won", JOptionPane.INFORMATION_MESSAGE);
				try {
					BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd '-' HH:mm:ss");
					Date date = new Date(System.currentTimeMillis());
					String out = new SimpleDateFormat("yyyyMMdd-hhmmss'.png'").format(new Date());
					ImageIO.write(image, "png", new File("data/img/history" + out));
					this.setWinner(player);
					this.appli.getContentPane().removeAll();
					this.appli.getContentPane().add(new EndOfGame(this.appli, this));
					this.appli.repaint();
					this.appli.revalidate();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
		* Sets the winner of a game
		* @param player the Player
		*/
	public void setWinner(Player player) {
		this.winner = player;
	}

	/**
		* Gets the winner of the game
		* @return  player the winner
		*/
	public String getWinner() {
		if (this.winner == P1) return "Player 1";
		else return "Player 2";
	}

	SquareType type;
	JPanel[][] panelTab;
	public Game gameplay;
	public int wallJ1;
	public int wallJ2;
	public int wallAA1;
	public int wallAA2;
	Player winner;
	//timer
  public int seconde;
  public Timer timer;
  public ActionListener taskPerformer;
	public int delay;
	public boolean tempsPasDepasse;
	//
	Board board;
	Player P1;
	Player P2;
	JLabel pawn1;
	JLabel pawn2;

	JLabel timerLabel;

	Application appli;
	JPanel configPanel;
	JPanel checkConfigPanel;
	JPanel gameTypePanel;
	JPanel modePanel;
	JPanel difficultyPanel;
	JPanel timePanel;
	JPanel checkTimePanel;
	JPanel plateau;
	JPanel commands;
	JPanel commandsUP;
	JPanel commandsDOWN;
	JPanel moveBox;

	JLabel configLabel;
	JLabel checkConfigLabel;
	JLabel gameTypeLabel;
	JLabel gameModeLabel;
	JLabel difficultyLabel;
	JLabel timeLabel;
	JLabel checkTimeLabel;
	public JLabel caseabc;
	JLabel P1Icon;
	JLabel P1RW;
	JLabel P1WN;
	JLabel P2Icon;
	JLabel P2RW;
	JLabel P2WN;




	public JTextField configField;
	JTextField timeField;
	JButton butNormal;
	JButton butTournament;
	JButton butHH;
	JButton butHA;
	JButton butAA;
	JButton butDiff1;
	JButton butDiff2;
	JButton butOk;
	JButton save;

	JButton diagUPL;
	JButton up;
	JButton diagUPR;
	JButton left;
	JButton wall;
	JButton right;
	JButton diagDOWNL;
	JButton down;
	JButton diagDOWNR;

	JLabel[][] gridArray;

	public Mode mode;
	public GameType gameType;
	public int difficulty;
	public int duration;
	Player activePlayer;

}
