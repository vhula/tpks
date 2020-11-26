package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.graph.Graph;
import io.github.vhula.tpks.model.graph.StructureTriggerTable;
import io.github.vhula.tpks.view.graph.TableTriggerFrame;
import io.github.vhula.tpks.view.graph.ViewTriggerTable;

/**
 * Class which represents command for showing table of trigger.
 */
public class ShowTableTriggerCommand extends Command {

    private Graph graph;

    public ShowTableTriggerCommand(Graph graph) {
        putValue(NAME, "Show Table...");
        this.graph = graph;
    }

    @Override
    public void doCommand() {
        StructureTriggerTable triggerTable = new StructureTriggerTable(graph);
        triggerTable.generateTable();
        ViewTriggerTable viewTriggerTable = new ViewTriggerTable(triggerTable);
        TableTriggerFrame triggerFrame = new TableTriggerFrame(viewTriggerTable);
    }

    @Override
    public void undoCommand() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
