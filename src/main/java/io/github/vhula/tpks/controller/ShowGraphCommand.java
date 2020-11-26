package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.graph.Graph;
import io.github.vhula.tpks.view.graph.GraphFrame;
import io.github.vhula.tpks.view.graph.GraphPanel;

/**
 * Created with IntelliJ IDEA.
 * User: Vadym Hula
 * Date: 22.10.12
 * Time: 0:40
 * To change this template use File | Settings | File Templates.
 */
public class ShowGraphCommand extends Command {

    public ShowGraphCommand() {
        putValue(NAME, "Show Graph...");
    }

    @Override
    public void doCommand() {
        Graph graph = new Graph();
        graph.initGraph();
        graph.generateTables();
        graph.printConnections();
        GraphPanel graphPanel = new GraphPanel(graph);
        GraphFrame frame = new GraphFrame(graphPanel);
        System.out.println(graph.getTables().toString());
    }

    @Override
    public void undoCommand() {
        //TODO
    }
}
