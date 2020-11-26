package io.github.vhula.tpks.view;

import io.github.vhula.tpks.drawable.AlgorithmDrawer;
import io.github.vhula.tpks.drawable.Drawer;
import io.github.vhula.tpks.model.FileModel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class FilePanel extends JPanel implements Observer {

	private FileModel fileModel = null;
	
	private static FilePanel instance = null;

	private AlgorithmDrawer drawer = new AlgorithmDrawer();
	
	private FilePanel() {
		super();
		setLayout(new BorderLayout());
		add(drawer, BorderLayout.CENTER);
		setFocusable(true);
		setVisible(true);
	}
	
	public static FilePanel getInstance(FileModel fileModel) {
		if (instance == null) {
			instance = new FilePanel();
			instance.fileModel = fileModel;
		}
		return instance;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o != null) {
		} else {
			instance = null;
		}
		drawer.invalidate();
		drawer.validate();
	}
	public Drawer getDrawer() {
		return drawer;
	}
	
	

}
