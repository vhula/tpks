package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.ConditionalElement;

import javax.swing.*;

/**
 * Клас, що реалізує команду додавання логічної умови в ЛСА.
 * @author Vadym Hula
 *
 */
public class AddConditionCommand extends AddCommand {
	
	public AddConditionCommand() {
		putValue(NAME, "Condition");
		putValue(SHORT_DESCRIPTION, "Adds condition to algorithm");
		putValue(SMALL_ICON, new ImageIcon(imageContent("images/condition_24.png"), "Condition"));
	}

	@Override
	public void doCommand() {
		String[] names = {"X"};
		int[] indexes = {1};
		element = new ConditionalElement(names, indexes);
		super.doCommand();
	}

	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		
	}

}
