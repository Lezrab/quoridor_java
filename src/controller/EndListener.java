package quoridor.controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import quoridor.view.*;
import quoridor.model.*;

public class EndListener extends MouseAdapter {

	public EndOfGame end;
	public Cursor cursor;
	public Application appli;

	public EndListener(EndOfGame end, Application appli) {
		this.end = end;
		this.appli = appli;
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == end.getHome()) {
			appli.getContentPane().removeAll();
			appli.setVisible(false);
			appli.dispose();
			appli = new Application();
			appli.setVisible(true);
			appli.getContentPane().removeAll();
			appli.getContentPane().add(new Home(this.appli));
			appli.repaint();
		} else if (e.getSource() == end.getExit()) {
			System.exit(0);
		}
	}

	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == end.getHome()) {
			end.getHome().setBackground(Color.GRAY);
			Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
			end.setCursor(cursor);
		} else if (e.getSource() == end.getExit()) {
			end.getExit().setBackground(Color.GRAY);
			Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
			end.setCursor(cursor);
		}
	}

	public void mouseExited(MouseEvent e) {
		if (e.getSource() == end.getHome()) {
			end.getHome().setBackground(Color.LIGHT_GRAY);
			Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			end.setCursor(cursor);
		} else if (e.getSource() == end.getExit()) {
			end.getExit().setBackground(Color.LIGHT_GRAY);
			Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			end.setCursor(cursor);
		}
	}
}
