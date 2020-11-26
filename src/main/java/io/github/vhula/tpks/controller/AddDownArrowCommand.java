package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.DownArrowElement;

import javax.swing.*;

/**
 * Клас, що реалізує команду додавання стрілки спрямованої донизу в ЛСА.
 * @author Vadym Hula
 *
 */
public class AddDownArrowCommand extends AddCommand {
	
	public AddDownArrowCommand() {
		putValue(NAME, "DOWN");
		putValue(SHORT_DESCRIPTION, "Adds down arrow");
		putValue(SMALL_ICON, new ImageIcon(imageContent("images/down_24.png"), "Down"));
	}

	@Override
	public void doCommand() {
		String[] names = {"DOWN"};
		int[] indexes = {1};
		element = new DownArrowElement(names, indexes);
		super.doCommand();
	}

	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		
	}

}
