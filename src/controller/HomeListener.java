package quoridor.controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import quoridor.view.*;
import quoridor.model.*;

public class HomeListener extends MouseAdapter {

	public Home home;
	public Cursor cursor;
	public Application appli;

	public HomeListener(Home home, Application appli) {
		this.home = home;
		this.appli = appli;
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == home.getNewGame()) {
			appli.getContentPane().removeAll();
			appli.setVisible(false);
			appli.dispose();
			appli = new Application();
			appli.setVisible(true);
			appli.getContentPane().removeAll();
			appli.getContentPane().add(new QuoridorG(this.appli));
			appli.repaint();
		} else if (e.getSource() == home.getLoadGame()) {
			appli.getContentPane().removeAll();
			appli.setVisible(false);
			appli.dispose();
			appli = new Application();
			appli.setVisible(true);
			appli.getContentPane().removeAll();
			QuoridorG quoridor = new QuoridorG(this.appli);

			String savePath = "";
			JFileChooser choice = new JFileChooser();
				choice.setCurrentDirectory(new File("data/saves"));
				int ret = choice.showOpenDialog(null);
				if (ret == JFileChooser.APPROVE_OPTION) {
					savePath += choice.getSelectedFile().getName();
				}
				System.out.println(savePath);
				System.out.println(quoridor.getGame());
			quoridor.setBoard(quoridor.getGame().loadGame(savePath));
			appli.getContentPane().add(quoridor);
			appli.repaint();
		} else if (e.getSource() == home.getCredits()) {
			JOptionPane.showMessageDialog(home, "Authors : RAMOS-GIQUEL Joshua & ARZEL Antonin", "Credits", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == home.getNewGame()) {
			home.getNewGame().setBackground(Color.GRAY);
			Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
			home.setCursor(cursor);
		} else if (e.getSource() == home.getLoadGame()) {
			home.getLoadGame().setBackground(Color.GRAY);
			Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
			home.setCursor(cursor);
		} else if (e.getSource() == home.getCredits()) {
			home.getCredits().setBackground(Color.GRAY);
			Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
			home.setCursor(cursor);
		}
	}

	public void mouseExited(MouseEvent e) {
		if (e.getSource() == home.getNewGame()) {
			home.getNewGame().setBackground(Color.LIGHT_GRAY);
			Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			home.setCursor(cursor);
		} else if (e.getSource() == home.getLoadGame()) {
			home.getLoadGame().setBackground(Color.LIGHT_GRAY);
			Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			home.setCursor(cursor);
		} else if (e.getSource() == home.getCredits()) {
			home.getCredits().setBackground(Color.LIGHT_GRAY);
			Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			home.setCursor(cursor);
		}
	}
}
