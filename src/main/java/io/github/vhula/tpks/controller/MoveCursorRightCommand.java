package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.EditorModel;
import io.github.vhula.tpks.model.FileModel;
import io.github.vhula.tpks.view.FilePanel;

import javax.swing.*;

/**
 * Команда, що зміщує курсор, який вказує на поточний елемент в ЛСА праворуч.
 * @author Vadym Hula
 *
 */
public class MoveCursorRightCommand extends Command {
	
	public MoveCursorRightCommand() {
		putValue(NAME, "Right");
		putValue(SHORT_DESCRIPTION, "Moves cursor to the right");
		putValue(SMALL_ICON, new ImageIcon(imageContent("images/right_24.png"), "Move Right"));
	}

	@Override
	public void doCommand() {
		FileModel.getInstance().getAlgorithm().moveCursorRight();
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
