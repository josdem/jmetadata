package org.jas.helper;

import java.io.File;

import javax.swing.JFileChooser;

public class FileChooserHelper {

	public File getDirectory() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int selection = fileChooser.showOpenDialog(null);
		return (selection == JFileChooser.APPROVE_OPTION) ? fileChooser.getSelectedFile() : null;
	}

}
