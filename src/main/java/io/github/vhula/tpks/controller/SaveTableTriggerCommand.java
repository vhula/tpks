package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.graph.StructureTriggerTable;
import io.github.vhula.tpks.view.DialogWindows;

/**
 * Saves table to a file.
 */
public class SaveTableTriggerCommand extends Command {

    private StructureTriggerTable table;

    public SaveTableTriggerCommand(StructureTriggerTable table) {
        putValue(NAME, "Save Table...");
        this.table = table;
    }

    @Override
    public void doCommand() {
        String filename = DialogWindows.showSaveDialog("table");
        if (filename != null) {
            table.save(filename);
        }
    }

    @Override
    public void undoCommand() {
        //TODO
    }
}
