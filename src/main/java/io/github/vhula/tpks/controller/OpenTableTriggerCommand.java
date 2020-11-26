package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.graph.StructureTriggerTable;
import io.github.vhula.tpks.view.DialogWindows;
import io.github.vhula.tpks.view.graph.TableTriggerFrame;
import io.github.vhula.tpks.view.graph.ViewTriggerTable;

/**
 * Created with IntelliJ IDEA.
 * User: Vadym Hula
 * Date: 11.11.12
 * Time: 8:19
 * To change this template use File | Settings | File Templates.
 */
public class OpenTableTriggerCommand extends Command {

    public OpenTableTriggerCommand() {
        putValue(NAME, "Open Table...");
    }

    @Override
    public void doCommand() {
        String filename = DialogWindows.showOpenDialog("table");
        if (filename != null) {
            StructureTriggerTable triggerTable = new StructureTriggerTable(null);
            triggerTable.open(filename);
            ViewTriggerTable viewTriggerTable = new ViewTriggerTable(triggerTable);
            TableTriggerFrame triggerFrame = new TableTriggerFrame(viewTriggerTable);
        }
    }

    @Override
    public void undoCommand() {
        //TODO
    }
}
