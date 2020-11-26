package io.github.vhula.tpks.view;

import io.github.vhula.tpks.controller.DeleteCommand;
import io.github.vhula.tpks.controller.EditPropertiesCommand;

import javax.swing.*;

/**
 * ���� ��� ����������� ������������ ���� ��� �������� ���.
 * @author Vadym Hula
 *
 */
public class ElementPopupMenu extends JPopupMenu {
	/**
	 * ����� ������� ��������
	 */
	private JMenuItem propertiesItem = new JMenuItem();
	/**
	 * ����� ��������� ��������.
	 */
	private JMenuItem deleteItem = new JMenuItem();
	
	private static ElementPopupMenu instance = null;
	
	public static ElementPopupMenu getMenu() {
		if (instance == null) 
			instance = new ElementPopupMenu();
		return instance;
	}
	
	private ElementPopupMenu() {
		super();
		initCommands();
		addButtons();
	}
	/**
	 * ���� �� ������ �������.
	 */
	private void initCommands() {
		propertiesItem.setAction(new EditPropertiesCommand());
		propertiesItem.setText("<html><b>"+propertiesItem.getText());
		deleteItem.setAction(new DeleteCommand());
	}
	/**
	 * ���� ������ � ���������� ����
	 */
	private void addButtons() {
		add(propertiesItem);
		add(deleteItem);
	}
	
	

}
