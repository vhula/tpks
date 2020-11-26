package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.OperatorElement;

import javax.swing.*;

/**
 * Клас, що реалізує команду додавання оператора в ЛСА.
 * @author Vadym Hula
 *
 */
public class AddOperatorCommand extends AddCommand {
	
	public AddOperatorCommand() {
		putValue(NAME, "Operator");
		putValue(SHORT_DESCRIPTION, "Adds new operator.");
		putValue(LONG_DESCRIPTION, "Adds new operator to algorithm");
		putValue(SMALL_ICON, new ImageIcon(imageContent("images/operator_24.png"), "Operator"));
	}

	@Override
	public void doCommand() {
		String[] names = {"Y"};
		int[] indexes = {1};
		element = new OperatorElement(names, indexes);
		super.doCommand();
	}

	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		
	}

}
