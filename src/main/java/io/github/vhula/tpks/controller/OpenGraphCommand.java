package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.graph.Graph;
import io.github.vhula.tpks.view.DialogWindows;
import io.github.vhula.tpks.view.graph.GraphFrame;
import io.github.vhula.tpks.view.graph.GraphPanel;

/**
 * Created with IntelliJ IDEA.
 * User: Vadym Hula
 * Date: 22.10.12
 * Time: 18:08
 * To change this template use File | Settings | File Templates.
 */
public class OpenGraphCommand extends Command {

    public OpenGraphCommand() {
        putValue(NAME, "Open Coded Graph...");
    }

    @Override
    public void doCommand() {
        String filename = DialogWindows.showOpenDialog("graph");
        if (filename != null) {
            Graph graph = new Graph(true);
            graph.openGraph(filename);
            GraphPanel graphPanel = new GraphPanel(graph);
            GraphFrame frame = new GraphFrame(graphPanel);
        }
    }

    @Override
    public void undoCommand() {
        //TODO
    }
}
