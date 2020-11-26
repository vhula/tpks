package io.github.vhula.tpks.model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * ������ ���������.
 * @author Vadym Hula
 *
 */
public class EditorModel extends Observable {
	/**
	 * ����� ������������
	 */
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	/**
	 * ������ �����.
	 */
	private FileModel fileModel = null;
	/**
	 * ��'��� ����� EditorModel
	 */
	private static EditorModel instance = null;
	
	private EditorModel(Observer observer) {
		if (observer != null)
			observers.add(observer);
	}
	/**
	 * ������� ��'��� �����.
	 * @param observer - ����������
	 * @return - ��'��� �����.
	 */
	public static EditorModel getInstance(Observer observer) {
		if (instance == null) {
			instance = new EditorModel(observer);
		}
		return instance;
	}
	/**
	 * ����� ��� ��������� ������ �����.
	 * @param fileModel
	 */
	public void newFile(FileModel fileModel) {
		this.fileModel = fileModel;
		ActionsFlags.SAVE_AS_FILE = ActionsFlags.ENABLED;
		ActionsFlags.SAVE_FILE = ActionsFlags.ENABLED;
		ActionsFlags.CLOSE_FILE = ActionsFlags.ENABLED;
		ActionsFlags.ADD_BEGIN = ActionsFlags.ENABLED;
		ActionsFlags.ADD_OPERATOR = ActionsFlags.DISABLED;
		ActionsFlags.ADD_CONDITION = ActionsFlags.DISABLED;
		ActionsFlags.ADD_UP_ARROW = ActionsFlags.DISABLED;
		ActionsFlags.ADD_DOWN_ARROW = ActionsFlags.DISABLED;
		ActionsFlags.ADD_END = ActionsFlags.DISABLED;
		ActionsFlags.MOVE_LEFT = ActionsFlags.ENABLED;
		ActionsFlags.MOVE_RIGHT = ActionsFlags.ENABLED;
		notifyObservers();
	}
	/**
	 * ����� ��� �������� �����.
	 */
	public void openFile() {
		this.fileModel = FileModel.getInstance();
		ActionsFlags.SAVE_AS_FILE = ActionsFlags.ENABLED;
		ActionsFlags.SAVE_FILE = ActionsFlags.DISABLED;
		ActionsFlags.CLOSE_FILE = ActionsFlags.ENABLED;
		ActionsFlags.ADD_BEGIN = ActionsFlags.DISABLED;
		ActionsFlags.ADD_OPERATOR = ActionsFlags.ENABLED;
		ActionsFlags.ADD_CONDITION = ActionsFlags.ENABLED;
		ActionsFlags.ADD_UP_ARROW = ActionsFlags.ENABLED;
		ActionsFlags.ADD_DOWN_ARROW = ActionsFlags.ENABLED;
		ActionsFlags.ADD_END = ActionsFlags.DISABLED;
		notifyObservers();
	}
	/**
	 * ����� ��� �������� �����.
	 */
	public void closeFile() {
		fileModel = null;
		ActionsFlags.SAVE_AS_FILE = ActionsFlags.DISABLED;
		ActionsFlags.SAVE_FILE = ActionsFlags.DISABLED;
		ActionsFlags.CLOSE_FILE = ActionsFlags.DISABLED;
		ActionsFlags.ADD_BEGIN = ActionsFlags.DISABLED;
		ActionsFlags.ADD_OPERATOR = ActionsFlags.DISABLED;
		ActionsFlags.ADD_CONDITION = ActionsFlags.DISABLED;
		ActionsFlags.ADD_UP_ARROW = ActionsFlags.DISABLED;
		ActionsFlags.ADD_DOWN_ARROW = ActionsFlags.DISABLED;
		ActionsFlags.ADD_END = ActionsFlags.DISABLED;
		ActionsFlags.MOVE_LEFT = ActionsFlags.DISABLED;
		ActionsFlags.MOVE_RIGHT = ActionsFlags.DISABLED;
		notifyObservers();
	}
	
	public void addObserver(Observer o) {
		observers.add(o);
	}
	
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update(instance, null);
		}
	}
	/**
	 * ������� ������ �����.
	 * @return - ������ �����.
	 */
	public FileModel getFileModel() {
		return fileModel;
	}

}
