package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.AlgorithmHandler;
import io.github.vhula.tpks.model.EditorModel;
import io.github.vhula.tpks.model.FileModel;
import io.github.vhula.tpks.view.FilePanel;
import io.github.vhula.tpks.view.PathsFrame;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Class which represents command looking for path, cycles and eternal cycles.
 */
public class FindLoopsCommand extends Command {

    public FindLoopsCommand() {
        putValue(NAME, "Find...");
        putValue(SHORT_DESCRIPTION, "Looks for paths and loops");
        putValue(SMALL_ICON, new ImageIcon(imageContent("images/clear_24.png"), "Find Loops"));
    }

    @Override
    public void doCommand() {
        AlgorithmHandler handler = FileModel.getInstance().getHandler();
        handler.findAllPaths();
        handler.findAllCycles(0, new ArrayList<Integer>());
        handler.findEternalCycles();
        PathsFrame pathsFrame = PathsFrame.getInstance();
        pathsFrame.setOutput(handler.toString());
        FilePanel.getInstance(null).getDrawer().refresh();
        EditorModel.getInstance(null).notifyObservers();
        FileModel.getInstance().notifyObservers();
        System.out.println(handler.toString());
    }

    @Override
    public void undoCommand() {
        //TODO
    }
}
