package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.graph.Graph;
import io.github.vhula.tpks.view.DialogWindows;

/**
 * Created with IntelliJ IDEA.
 * User: Vadym Hula
 * Date: 22.10.12
 * Time: 18:02
 * To change this template use File | Settings | File Templates.
 */
public class SaveGraphCommand extends Command {

    public SaveGraphCommand() {
        putValue(NAME, "Save Coded Graph...");
    }

    @Override
    public void doCommand() {
        String filename = DialogWindows.showSaveDialog("graph");
        if (filename != null) {
            Graph graph = new Graph();
            graph.initGraph();
            graph.neighborCoding();
            graph.generateTables();
            graph.printConnections();
            graph.saveGraph(filename);
        }
    }

    @Override
    public void undoCommand() {
        //TODO
    }
}
