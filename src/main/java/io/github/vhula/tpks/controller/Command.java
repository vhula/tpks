package io.github.vhula.tpks.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * ����, �� �������� ������� � ��������.
 * @author Vadym Hula
 *
 */
public abstract class Command extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent event) {
		doCommand();
	}
	/**
	 * �����, �� ������ �������.
	 */
	public abstract void doCommand();
	/**
	 * �����, �� ������ �������.
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
