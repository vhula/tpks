package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.EditorModel;
import io.github.vhula.tpks.model.FileModel;
import io.github.vhula.tpks.view.FilePanel;

import javax.swing.*;

/**
 * Команда, що зміщує курсор, який вказує на поточний елемент в ЛСА ліворуч.
 * @author Vadym Hula
 *
 */
public class MoveCursorLeftCommand extends Command {
	
	public MoveCursorLeftCommand() {
		putValue(NAME, "Left");
		putValue(SHORT_DESCRIPTION, "Moves to the left cursor");
		putValue(SMALL_ICON, new ImageIcon(imageContent("images/left_24.png"), "Move Left"));
	}

	@Override
	public void doCommand() {
		FileModel.getInstance().getAlgorithm().moveCursorLeft();
		FilePanel.getInstance(null).getDrawer().refresh();
		FileModel.getInstance().notifyObservers();
		EditorModel.getInstance(null).notifyObservers();
		Controller.getInstance().pushCommand(this);
	}

	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		
	}

}
