package io.github.vhula.tpks.view;

import io.github.vhula.tpks.controller.DeleteCommand;
import io.github.vhula.tpks.controller.EditPropertiesCommand;

import javax.swing.*;

/**
 * Клас для відображення контекстного меню для елементу ЛСА.
 * @author Vadym Hula
 *
 */
public class ElementPopupMenu extends JPopupMenu {
	/**
	 * Пункт сигналів елементу
	 */
	private JMenuItem propertiesItem = new JMenuItem();
	/**
	 * Пункт видалення елементу.
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
	 * Додає до пунктів команди.
	 */
	private void initCommands() {
		propertiesItem.setAction(new EditPropertiesCommand());
		propertiesItem.setText("<html><b>"+propertiesItem.getText());
		deleteItem.setAction(new DeleteCommand());
	}
	/**
	 * Додає кнопки в контекстне меню
	 */
	private void addButtons() {
		add(propertiesItem);
		add(deleteItem);
	}
	
	

}
