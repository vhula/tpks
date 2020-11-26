package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.FileModel;

import java.util.Stack;

public class Controller {

	private final Stack<Command> commands = new Stack<>();

	private final FileModel fileModel = null;

	private static Controller controller = null;
	
	public static Controller getInstance() {
		if (controller == null) {
			controller = new Controller();
		}
		return controller;
	}

	public void pushCommand(Command command) {
		commands.push(command);
	}

	public Command popCommand() throws Exception {
		try {
			return commands.pop();
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}

	public FileModel getFileModel() {
		return fileModel;
	}
	

}
