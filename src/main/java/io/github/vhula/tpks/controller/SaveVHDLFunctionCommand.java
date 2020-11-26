package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.graph.StructureTriggerTable;
import io.github.vhula.tpks.view.DialogWindows;

/**
 *
 */
public class SaveVHDLFunctionCommand extends Command {

    private StructureTriggerTable table;

    public SaveVHDLFunctionCommand(StructureTriggerTable table) {
        putValue(NAME, "Save VHDL...");
        this.table = table;
    }

    @Override
    public void doCommand() {
        String filename = DialogWindows.showSaveDialog("vhd");
        if (filename != null) {
            table.saveVHDLFunction(filename);
        }
    }

    @Override
    public void undoCommand() {
        //TODO
    }
}
