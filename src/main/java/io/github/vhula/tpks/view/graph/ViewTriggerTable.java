package io.github.vhula.tpks.view.graph;

import io.github.vhula.tpks.model.graph.StructureTriggerTable;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Represents table view.
 */
public class ViewTriggerTable extends JTable {
    /**
     * Table model.
     */
    private StructureTriggerTable table;

    public ViewTriggerTable(StructureTriggerTable table) {
        super(new Object[table.getTable().length][table.getTable()[0].length], (Object[])table.headers);
        this.table = table;
        setRowHeight(25);
        ListSelectionModel selectionModel = getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        fillTable();
    }

    /**
     * Filling table view with table model.
     */
    protected void fillTable() {
        TableModel tableModel = getModel();
        tableModel.setValueAt("ÏÑ -> ÑÏ", 0, 0);
        String buf = "";
        for (int i = 0; i < table.codeSize; i++) {
            buf += "Q" + (table.codeSize - i) + " ";
        }
        tableModel.setValueAt(buf, 0, 1);
        tableModel.setValueAt(buf, 0, 2);
        buf = "";
        for (int i = 0; i < table.conditionsCount; i++) {
            buf += "X" + table.getTable()[0][2 + table.codeSize * 2 + i] + " ";
        }
        tableModel.setValueAt(buf, 0, 3);
        buf = "";
        for (int i = 0; i < table.operatorsCount; i++) {
            buf += "Y" + table.getTable()[0][2 + table.codeSize * 2 + table.conditionsCount + i] + " ";
        }
        tableModel.setValueAt(buf, 0, 4);
        buf = "";
        for (int i = 0; i < table.codeSize; i++) {
            buf += "T" + (table.codeSize - i) + " ";
        }
        tableModel.setValueAt(buf, 0, 5);
        Integer[][] tableValues = table.getTable();
        for (int i = 1; i < tableValues.length; i++) {
            buf = "";
            buf += "" + tableValues[i][0] + " -> " + tableValues[i][1];
            setValueAt(buf, i, 0);
            buf = "";
            for (int j = 0; j < table.codeSize; j++) {
                buf += tableValues[i][j + 2] + "  ";
            }
            tableModel.setValueAt(buf, i, 1);

            buf = "";
            for (int j = 0; j < table.codeSize; j++) {
                buf += tableValues[i][j + 2 + table.codeSize] + "  ";
            }
            tableModel.setValueAt(buf, i, 2);

            buf = "";
            for (int j = 0; j < table.conditionsCount; j++) {
                buf += (tableValues[i][j + 2 + 2 * table.codeSize] != -1 ? tableValues[i][j + 2 + 2 * table.codeSize] : "-") + ( "  ");
            }
            tableModel.setValueAt(buf, i, 3);

            buf = "";
            for (int j = 0; j < table.operatorsCount; j++) {
                buf += tableValues[i][j + 2 + 2 * table.codeSize + table.conditionsCount] + "  ";
            }
            tableModel.setValueAt(buf, i, 4);

            buf = "";
            for (int j = 0; j < table.codeSize; j++) {
                buf += tableValues[i][j + 2 + 2 * table.codeSize + table.conditionsCount + table.operatorsCount] + "  ";
            }
            tableModel.setValueAt(buf, i, 5);
        }
    }

    public StructureTriggerTable getTable() {
        return table;
    }

}
