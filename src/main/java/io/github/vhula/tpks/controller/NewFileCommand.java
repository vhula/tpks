package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.EditorModel;
import io.github.vhula.tpks.model.FileModel;

import javax.swing.*;

/**
 * Команда для створення нового файлу.
 * @author Vadym Hula
 *
 */
public class NewFileCommand extends Command {
	
	public NewFileCommand() {
		putValue(NAME, "New");
		putValue(SHORT_DESCRIPTION, "Creates new file.");
		putValue(LONG_DESCRIPTION, "Creates new file. S");
		putValue(SMALL_ICON, new ImageIcon(imageContent("images/new_24.png"), "New File"));
	}

	@Override 
	public void doCommand() {
		FileModel fileModel = Controller.getInstance().getFileModel();
		int choose = 0;
		if (fileModel != null && !fileModel.isSaved()) {
			choose = JOptionPane.showConfirmDialog(null,
					"<html>File "
					+ " is not saved!<br>"
					+ "Are you sure?");
		}
		if (choose == JOptionPane.YES_OPTION) {
			fileModel = FileModel.getInstance();
			fileModel.newFile();
			EditorModel.getInstance(null).newFile(fileModel);
		}
	}

	@Override
	public void undoCommand() {
		// TODO Will be realised in future version
	}

}
