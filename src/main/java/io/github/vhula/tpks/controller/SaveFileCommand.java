package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.FileModel;

import javax.swing.*;

/**
 * Команда для збереження ЛСА в файлі.
 * @author Vadym Hula
 *
 */
public class SaveFileCommand extends Command {
	
	public SaveFileCommand() {
		putValue(NAME, "Save");
		putValue(SHORT_DESCRIPTION, "Saving file");
		putValue(SMALL_ICON, new ImageIcon(imageContent("images/save_24.png"), "Save"));
	}

	@Override
	public void doCommand() {
		FileModel fileModel = FileModel.getInstance();
		if (fileModel.isSavedOnDisk()) {
			fileModel.saveFile();
		} else {
			new SaveAsFileCommand().doCommand();
		}
	}

	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		
	}

}
