package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.graph.Graph;
import io.github.vhula.tpks.view.graph.GraphFrame;
import io.github.vhula.tpks.view.graph.GraphPanel;

/**
 *
 */
public class MakeCodingCommand extends Command {

    public MakeCodingCommand() {
        putValue(NAME, "Next Coding...");
    }

    @Override
    public void doCommand() {
        Graph graph = new Graph();
        graph.initGraph();
        graph.neighborCoding();
        graph.generateTables();
        graph.printConnections();
        GraphPanel graphPanel = new GraphPanel(graph);
        GraphFrame frame = new GraphFrame(graphPanel);
        System.out.println(graph.getTables().toString());
    }

    @Override
    public void undoCommand() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
