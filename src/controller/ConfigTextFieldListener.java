package quoridor.controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import quoridor.view.*;
import quoridor.model.*;

public class ConfigTextFieldListener implements ActionListener {

	private QuoridorG quoridor;

	public ConfigTextFieldListener(QuoridorG quoridor) {
		this.quoridor = quoridor;
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == quoridor.getConfigField()) {
			System.out.println(quoridor.getConfigField().getText());
			if (quoridor.configField.getText().length() >= 25) {
				JOptionPane.showMessageDialog(quoridor, "");
			} else if (!quoridor.configField.getText().equals("data/config/.auto_config.txt")){
				JOptionPane.showMessageDialog(quoridor, "File couldn't be found [default = data/config/.auto_config.txt]");
			} else {
				JOptionPane.showMessageDialog(quoridor, "The configuration was correctly loaded ! ");
				this.quoridor.launchGame("data/config/.auto_config.txt");
			}
		}
	}
}
