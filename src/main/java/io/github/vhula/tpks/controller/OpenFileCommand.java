package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.EditorModel;
import io.github.vhula.tpks.model.FileModel;
import io.github.vhula.tpks.view.DialogWindows;
import io.github.vhula.tpks.view.FilePanel;

import javax.swing.*;

/**
 * Команда для вікриття файлу.
 * @author Vadym Hula
 *
 */
public class OpenFileCommand extends Command {
	
	public OpenFileCommand() {
		putValue(NAME, "Open...");
		putValue(SHORT_DESCRIPTION, "Opens saved file");
		putValue(SMALL_ICON, new ImageIcon(imageContent("images/open_24.png"), "Open File"));
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
			String filename = DialogWindows.showOpenDialog("io/github/vhula/tpks");
			if (filename != null) {
				fileModel = FileModel.getInstance();
				fileModel.setFilename(filename);
				fileModel.openFile();
				FilePanel.getInstance(null).getDrawer().refresh();
				EditorModel.getInstance(null).openFile();
			}
		}
	}

	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		
	}

}
