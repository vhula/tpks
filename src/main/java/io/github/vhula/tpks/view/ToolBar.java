package io.github.vhula.tpks.view;

import io.github.vhula.tpks.controller.*;
import io.github.vhula.tpks.model.ActionsFlags;

import javax.swing.*;

/**
 * Панель інструментів.
 * @author Vadym Hula
 *
 */
public class ToolBar extends JToolBar {
	/**
	 * Кнопки для маніпуляцій з файлами.
	 */
	private JButton newFileButton = new JButton();
	private JButton openFileButton = new JButton();
	private JButton saveFileButton = new JButton();
	private JButton saveAsFileButton = new JButton();
	private JButton closeFileButton = new JButton();
	
	/**
	 * Кнопки для маніпуляцій з ЛСА.
	 */
	private JButton addBeginButton = new JButton();
	private JButton addOperatorButton = new JButton();
	private JButton addConditionButton = new JButton();
	private JButton addUpArrowButton = new JButton();
	private JButton addDownArrowButton = new JButton();
	private JButton addEndButton = new JButton();
	private JButton moveCursorLeftButton = new JButton();
	private JButton moveCursorRightButton = new JButton();

    private JButton findLoopsButton = new JButton();
	
	public ToolBar() {
		super();
		initCommands();
		addButtons();
		setFocusable(false);
		editEnables();
	}
	/**
	 * Додає команди для кнопок.
	 */
	private void initCommands() {
		newFileButton.setAction(new NewFileCommand());
		newFileButton.setText("");
		openFileButton.setAction(new OpenFileCommand());
		openFileButton.setText("");
		saveFileButton.setAction(new SaveFileCommand());
		saveFileButton.setText("");
		saveAsFileButton.setAction(new SaveAsFileCommand());
		saveAsFileButton.setText("");
		closeFileButton.setAction(new CloseFileCommand());
		closeFileButton.setText("");
		
		addBeginButton.setAction(new AddBeginCommand());
		addOperatorButton.setAction(new AddOperatorCommand());
		addConditionButton.setAction(new AddConditionCommand());
		addUpArrowButton.setAction(new AddUpArrowCommand());
		addDownArrowButton.setAction(new AddDownArrowCommand());
		addEndButton.setAction(new AddEndCommand());
		moveCursorLeftButton.setAction(new MoveCursorLeftCommand());
		moveCursorRightButton.setAction(new MoveCursorRightCommand());
		addBeginButton.setText("");
		addOperatorButton.setText("");
		addConditionButton.setText("");
		addUpArrowButton.setText("");
		addDownArrowButton.setText("");
		addEndButton.setText("");
		moveCursorLeftButton.setText("");
		moveCursorRightButton.setText("");

        findLoopsButton.setAction(new FindLoopsCommand());
        findLoopsButton.setText("");
	}
	/**
	 * Додає кнопки до панелі інструментів.
	 */
	private void addButtons() {
		add(newFileButton);
		add(openFileButton);
		add(saveFileButton);
		add(saveAsFileButton);
		add(closeFileButton);
		addSeparator();
		add(addBeginButton);
		add(addOperatorButton);
		add(addConditionButton);
		add(addUpArrowButton);
		add(addDownArrowButton);
		add(addEndButton);
		addSeparator();
		add(moveCursorLeftButton);
		add(moveCursorRightButton);
        addSeparator();
        add(findLoopsButton);
	}
	/**
	 * Редагує доступність кнопок на панелі інструментів.
	 */
	public void editEnables() {
		newFileButton.setEnabled(ActionsFlags.NEW_FILE);
		openFileButton.setEnabled(ActionsFlags.OPEN_FILE);
		saveAsFileButton.setEnabled(ActionsFlags.SAVE_AS_FILE);
		saveFileButton.setEnabled(ActionsFlags.SAVE_FILE);
		closeFileButton.setEnabled(ActionsFlags.CLOSE_FILE);
		addBeginButton.setEnabled(ActionsFlags.ADD_BEGIN);
		addOperatorButton.setEnabled(ActionsFlags.ADD_OPERATOR);
		addConditionButton.setEnabled(ActionsFlags.ADD_CONDITION);
		addUpArrowButton.setEnabled(ActionsFlags.ADD_UP_ARROW);
		addDownArrowButton.setEnabled(ActionsFlags.ADD_DOWN_ARROW);
		addEndButton.setEnabled(ActionsFlags.ADD_END);
		moveCursorLeftButton.setEnabled(ActionsFlags.MOVE_LEFT);
		moveCursorRightButton.setEnabled(ActionsFlags.MOVE_RIGHT);
	}

}
