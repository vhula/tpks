package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.graph.StructureTriggerTable;
import io.github.vhula.tpks.view.DialogWindows;

/**
 * Created with IntelliJ IDEA.
 * User: Vadym Hula
 * Date: 24.11.12
 * Time: 16:28
 * To change this template use File | Settings | File Templates.
 */
public class SaveFunctionsCommand extends Command {

    StructureTriggerTable table;

    public SaveFunctionsCommand(StructureTriggerTable table) {
        putValue(NAME, "Save Functions...");
        this.table = table;
    }

    @Override
    public void doCommand() {
        String filename = DialogWindows.showSaveDialog("func");
        if (filename != null) {
            table.saveFunctions(filename);
        }
    }

    @Override
    public void undoCommand() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
