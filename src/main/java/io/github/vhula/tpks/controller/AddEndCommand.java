package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.ActionsFlags;
import io.github.vhula.tpks.model.EndElement;

import javax.swing.*;

/**
 * Клас, що реалізує команду додавання кінцевого елемента в ЛСА.
 * @author Vadym Hula
 *
 */
public class AddEndCommand extends AddCommand {
	
	public AddEndCommand() {
		putValue(NAME, "END");
		putValue(SHORT_DESCRIPTION, "Adds end");
		putValue(SMALL_ICON, new ImageIcon(imageContent("images/end_24.png"), "End"));
	}

	@Override
	public void doCommand() {
		element = EndElement.getInstance();
		ActionsFlags.ADD_END = ActionsFlags.DISABLED;
		super.doCommand();
	}

	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		
	}

}
