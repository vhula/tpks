package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.FileModel;
import io.github.vhula.tpks.view.DialogWindows;

import javax.swing.*;

/**
 * Команда для збереження ЛСА в новому файлі.
 * @author Vadym Hula
 *
 */
public class SaveAsFileCommand extends Command {
	
	public SaveAsFileCommand() {
		putValue(NAME, "Save As...");
		putValue(SHORT_DESCRIPTION, "Saving file on disk");
		putValue(SMALL_ICON, new ImageIcon(imageContent("images/save_as_24.png")));
	}

	@Override
	public void doCommand() {
		FileModel fileModel = Controller.getInstance().getFileModel();
		String filename = DialogWindows.showSaveDialog("io/github/vhula/tpks");
		if (filename != null) {
			fileModel = FileModel.getInstance();
			fileModel.setFilename(filename);
			fileModel.saveFile();
		}
	}

	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		
	}

}
