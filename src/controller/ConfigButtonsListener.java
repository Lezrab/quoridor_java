package quoridor.controller;
import quoridor.model.*;
import quoridor.view.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import quoridor.view.*;
import quoridor.model.*;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class ConfigButtonsListener implements ActionListener {
	public Application appli;
	public QuoridorG quoridor;
	public Cursor cursor;

	public ConfigButtonsListener(Application appli, QuoridorG quoridor) {
		this.appli = appli;
		this.quoridor = quoridor;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.quoridor.getButNormal()) {
			this.quoridor.setModePanelVisible(true);
			this.quoridor.setTimePanelVisible(false);
			this.quoridor.gameType = GameType.NORMAL;;
			JOptionPane.showMessageDialog(quoridor, "Chosen Game Type : " + this.quoridor.gameType);
		} else if (e.getSource() == this.quoridor.getButTournament()) {
			this.quoridor.setModePanelVisible(false);
			this.quoridor.setTimePanelVisible(true);
			this.quoridor.setDifficultyPanelVisible(false);
			this.quoridor.gameType = GameType.TOURNAMENT;
			this.quoridor.mode = Mode.HH;
			JOptionPane.showMessageDialog(quoridor, "Chosen Game Type : " + this.quoridor.gameType);
		} else if (e.getSource() == this.quoridor.getButHA()) {
			this.quoridor.setDifficultyPanelVisible(true);
			this.quoridor.mode = Mode.HA;
			JOptionPane.showMessageDialog(this.quoridor, "Chosen Game Mode : " + this.quoridor.mode);
		} else if (e.getSource() == this.quoridor.getButAA()) {
			this.quoridor.setDifficultyPanelVisible(true);
			this.quoridor.mode = Mode.AA;
			JOptionPane.showMessageDialog(this.quoridor, "Chosen Game Mode : " + this.quoridor.mode);
		} else if (e.getSource() == this.quoridor.getButHH()) {
			this.quoridor.setDifficultyPanelVisible(false);
			this.quoridor.mode = Mode.HH;
			JOptionPane.showMessageDialog(this.quoridor, "Chosen Game Mode : " + this.quoridor.mode);
		} else if (e.getSource() == this.quoridor.getButDiff1()) {
			this.quoridor.difficulty = 1;
			JOptionPane.showMessageDialog(this.quoridor, "Chosen Difficulty : " + this.quoridor.difficulty);
		} else if (e.getSource() == this.quoridor.getButDiff2()) {
			this.quoridor.difficulty = 2;
			JOptionPane.showMessageDialog(this.quoridor, "Chosen Difficulty : " + this.quoridor.difficulty);
		} else if (e.getSource() == this.quoridor.getButOk()) {
			try {
				 FileWriter file = new FileWriter("data/config/.homemade_config.txt");
				 BufferedWriter buffer = new BufferedWriter(file);
				 PrintWriter out = new PrintWriter(buffer);
				 out.println(this.quoridor.gameType) ;
				 out.println(this.quoridor.mode);
				 Player p1 = this.quoridor.getP1();
				 Player p2 = this.quoridor.getP2();
				 if(this.quoridor.mode == Mode.HA){
					p1 = new HumanPlayer(this.quoridor.getBoard(), SquareType.PAWN1, this.quoridor.duration, this.quoridor.gameType, this.quoridor.gameplay);
					p2 = new AutoPlayer(this.quoridor.getBoard(), SquareType.PAWN2, this.quoridor.difficulty);
				 }
				 else if(this.quoridor.mode == Mode.AA){
					p1 = new AutoPlayer(this.quoridor.getBoard(), SquareType.PAWN1, this.quoridor.difficulty);
					p2 = new AutoPlayer(this.quoridor.getBoard(), SquareType.PAWN2, this.quoridor.difficulty);
				}
				 out.println(this.quoridor.difficulty);
				 if(this.quoridor.gameType == GameType.TOURNAMENT){
					this.quoridor.duration = Integer.parseInt(quoridor.getTimeField().getText());
					this.quoridor.delay = (int)this.quoridor.duration;
					this.quoridor.seconde = (int)this.quoridor.duration;
				 }
				 out.println(this.quoridor.duration);
				 JOptionPane.showMessageDialog(this.quoridor, "Configuration : " + this.quoridor.gameType + "/" + this.quoridor.mode + "/" + this.quoridor.difficulty + "/" + this.quoridor.duration);
				 out.close();
				 this.quoridor.launchGame("data/config/.homemade_config");
			 } catch (Exception ex) {
				 ex.printStackTrace();
			 }
		}
	}
}
