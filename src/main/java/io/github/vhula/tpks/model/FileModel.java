package io.github.vhula.tpks.model;

import io.github.vhula.tpks.view.FilePanel;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class FileModel extends Observable {

	private String filename = null;

	private boolean saved = true;

	private boolean savedOnDisk = false;

	private Algorithm algorithm;

    private AlgorithmHandler handler = new AlgorithmHandler();

	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	private static FileModel instance = null;
	
	private FileModel() {
		algorithm = new Algorithm();
		observers.add(FilePanel.getInstance(this));
	}
	
	public static FileModel getInstance() {
		if (instance == null) {
			instance = new FileModel();
		}
		return instance;
	}

    public AlgorithmHandler getHandler() {
        return handler;
    }
	
	public String getFilename() {
		return filename;
	}

	public void newFile() {
		this.filename = null;
		saved = true;
		savedOnDisk = false;
		algorithm = new Algorithm();
		notifyObservers();
	}

	public void saveFile() {
		String text = null;
		try {
			AlgoMatrix.getInstance().parseConnectionMatrix();
			AlgoMatrix.getInstance().parseSignalsMatrix();
			text = AlgoMatrix.getInstance().toString();
		} catch (IllegalArgumentException exc) {
			return;
		}
		try {
			File file = new File(filename + ".io.github.vhula.tpks");
			if (file.exists())
				file.delete();
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(text);
			bw.flush();
			bw.close();
			setSaved(true);
			setSavedOnDisk(true);
		} catch (IOException exc) {
			return;
		}
		
	}

	public void openFile() {
		String text = "";
		try {
			File file = new File(filename);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			while ((line = br.readLine()) != null) {
				text += (line + "\n");
			}
			br.close();
			AlgoMatrix.getInstance(text);
			algorithm = new Algorithm();
			algorithm.parseAlgorithm();
			setSaved(true);
			ActionsFlags.MOVE_LEFT = ActionsFlags.ENABLED;
			ActionsFlags.MOVE_RIGHT = ActionsFlags.ENABLED;
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(FilePanel.getInstance(null), "File is not opened!\nWrong LSA!", "Error", JOptionPane.ERROR_MESSAGE);
		}
        /*Graph graph = new Graph();
        graph.generateTables();
        graph.printConnections();
        GraphPanel graphPanel = new GraphPanel(graph);
        GraphFrame frame = new GraphFrame(graphPanel);
        graph.saveGraph("E://filegraph");*/
        /*ArrayList<Connection> cons = graph.getConnections(graph.getOperators().get(0), 0, new Connection(graph.getState(0)));
        for (int i = 0; i < cons.size(); i++) {
            System.out.println(cons.get(i).toString());
        }*/
	}

	public void closeFile() {
		instance = null;
		algorithm = null;
		filename = null;
		notifyObservers();
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
		saved = true;
		savedOnDisk = true;
		notifyObservers();
	}
	
	public boolean isSaved() {
		return saved;
	}
	
	public void setSaved(boolean saved) {
		this.saved = saved;
		ActionsFlags.SAVE_FILE = !saved;
		notifyObservers();
	}
	
	public boolean isSavedOnDisk() {
		return savedOnDisk;
	}
	
	public void setSavedOnDisk(boolean savedOnDisk) {
		this.savedOnDisk = savedOnDisk;
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
	
	public Algorithm getAlgorithm() {
		return algorithm;
	}

}
