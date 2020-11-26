package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.EditorModel;
import io.github.vhula.tpks.model.FileModel;

import javax.swing.*;

/**
 * Клас, що реалізує операцію закриття файлу.
 * @author Vadym Hula
 *
 */
public class CloseFileCommand extends Command {
	
	public CloseFileCommand() {
		putValue(NAME, "Close File");
		putValue(SHORT_DESCRIPTION, "Closes file");
		putValue(SMALL_ICON, new ImageIcon(imageContent("images/close_24.png"), "Close file"));
	}

	@Override
	public void doCommand() {
		FileModel fileModel = FileModel.getInstance();
		int choose = 0;
		if (!fileModel.isSaved()) {
			choose = JOptionPane.showConfirmDialog(null,
					"<html>File "
					+ " is not saved!<br>"
					+ "Are you sure?");
		}
		if (choose == JOptionPane.YES_OPTION) {
			fileModel.closeFile();
			EditorModel.getInstance(null).closeFile();
		}
	}

	@Override
	public void undoCommand() {
		// TODO Will be realised in future version
		
	}

}
