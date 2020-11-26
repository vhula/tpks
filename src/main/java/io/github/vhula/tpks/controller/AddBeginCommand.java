package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.ActionsFlags;
import io.github.vhula.tpks.model.BeginElement;

import javax.swing.*;

/**
 * Клас, що реалізує команду додавання початкового елементу в ЛСА.
 * @author Vadym Hula
 *
 */
public class AddBeginCommand extends AddCommand {
	/**
	 * Конструктор
	 */
	public AddBeginCommand() {
		putValue(NAME, "Begin");
		putValue(SHORT_DESCRIPTION, "Adds begin element to algorithm");
		putValue(SMALL_ICON, new ImageIcon(imageContent("images/begin_24.png"), "Begin"));
	}

	@Override
	public void doCommand() {
		element = BeginElement.getInstance();
		ActionsFlags.ADD_BEGIN = ActionsFlags.DISABLED;
		ActionsFlags.ADD_OPERATOR = ActionsFlags.ENABLED;
		ActionsFlags.ADD_CONDITION = ActionsFlags.ENABLED;
		ActionsFlags.ADD_UP_ARROW = ActionsFlags.ENABLED;
		ActionsFlags.ADD_DOWN_ARROW = ActionsFlags.ENABLED;
		ActionsFlags.ADD_END = ActionsFlags.ENABLED;
		super.doCommand();
	}

	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		
	}

}
