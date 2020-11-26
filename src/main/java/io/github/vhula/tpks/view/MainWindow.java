package io.github.vhula.tpks.view;

import io.github.vhula.tpks.model.EditorModel;
import io.github.vhula.tpks.model.FileModel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class MainWindow extends JFrame implements Observer {
	
	public static final int DEFAULT_WIDTH = 640;
	public static final int DEFAULT_HEIGHT = 480;
	public static final boolean VISIBLE = true;

	private final EditorModel editorModel = EditorModel.getInstance(this);

	private FilePanel filePanel = null;

	private final ToolBar toolBar = new ToolBar();
	
	public MainWindow() {
		super("LSA Editor");
		setLayout(new BorderLayout());
		add(toolBar, BorderLayout.NORTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setFocusable(true);
        setJMenuBar(new MenuBar());
		setVisible(VISIBLE);
	}
	
	public void setFilePanel(FilePanel filePanel) {
		this.filePanel = filePanel;
	}
	
	public FilePanel getFilePanel() {
		return filePanel;
	}

	@Override
	public void update(Observable o, Object arg) {
		toolBar.editEnables();
		if (filePanel != null)
			remove(filePanel);
		if (editorModel.getFileModel() != null) {
			filePanel = FilePanel.getInstance(FileModel.getInstance());
			add(filePanel, BorderLayout.CENTER);
		} else {
			if (filePanel != null)
				remove(filePanel);
		}
		repaint();
		invalidate();
		validate();
	}
	
	
	

}
