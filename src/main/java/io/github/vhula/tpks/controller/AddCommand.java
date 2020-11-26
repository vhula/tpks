package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.Algorithm;
import io.github.vhula.tpks.model.EditorModel;
import io.github.vhula.tpks.model.Element;
import io.github.vhula.tpks.model.FileModel;
import io.github.vhula.tpks.view.FilePanel;

import java.io.BufferedInputStream;
import java.io.IOException;

public abstract class AddCommand extends Command {

	protected Element element;
	
	public Element getElement() {
		return element;
	}
	@Override
	public void doCommand() {
		FileModel fileModel = FileModel.getInstance();
		Algorithm algorithm = fileModel.getAlgorithm();
		fileModel.setSaved(false);
		algorithm.addElement(element);
		algorithm.setCursor(element);
		FilePanel.getInstance(null).getDrawer().refresh();
		EditorModel.getInstance(null).notifyObservers();
		fileModel.notifyObservers();
		Controller.getInstance().pushCommand(this);
	}

}
