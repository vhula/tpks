package io.github.vhula.tpks.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Клас, що відображає команду в редакторі.
 * @author Vadym Hula
 *
 */
public abstract class Command extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent event) {
		doCommand();
	}
	/**
	 * Метод, що реалізує команду.
	 */
	public abstract void doCommand();
	/**
	 * Метод, що відміняє команду.
	 */
	public abstract void undoCommand();

	protected byte[] imageContent(String imageResource) {
		try (BufferedInputStream bis = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(imageResource))) {
			return bis.readAllBytes();
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}

}
