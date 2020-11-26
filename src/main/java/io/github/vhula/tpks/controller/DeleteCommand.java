package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.*;
import io.github.vhula.tpks.view.FilePanel;

import javax.swing.*;

/**
 * Клас, що реалізує операцію видалення елементу з ЛСА.
 * @author Vadym Hula
 *
 */
public class DeleteCommand extends Command {
	/**
	 * Елемент, що видаляється.
	 */
	private Element element;
	
	public DeleteCommand() {
		putValue(NAME, "Delete");
		putValue(SHORT_DESCRIPTION, "Removes selected element");
		putValue(SMALL_ICON, new ImageIcon(imageContent("images/delete_16.png"), "Delete element"));
	}

	@Override
	public void doCommand() {
		FileModel fileModel = FileModel.getInstance();
		Algorithm algorithm = fileModel.getAlgorithm();
		element = algorithm.getSelected();
		if (!element.getClass().equals(BeginElement.class)) {
			algorithm.removeElement(element);
			algorithm.setSelected(null);
			if (element.getClass().equals(EndElement.class))
				ActionsFlags.ADD_END = ActionsFlags.ENABLED;
			FilePanel.getInstance(null).getDrawer().refresh();
			FileModel.getInstance().notifyObservers();
			EditorModel.getInstance(null).notifyObservers();
		}
	}

	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		
	}

}
