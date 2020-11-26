package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.UpArrowElement;

import javax.swing.*;

/**
 * Клас, що реалізує команду додавання стрілки спрямованої вгору в ЛСА.
 * @author Vadym Hula
 *
 */
public class AddUpArrowCommand extends AddCommand {
	
	public AddUpArrowCommand() {
		putValue(NAME, "UP");
		putValue(SHORT_DESCRIPTION, "Adds up arrow");
		putValue(SMALL_ICON, new ImageIcon(imageContent("images/up_24.png"), "Up"));
	}

	@Override
	public void doCommand() {
		String[] names = {"UP"};
		int[] indexes = {1};
		element = new UpArrowElement(names, indexes);
		super.doCommand();
	}

	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		
	}

}
