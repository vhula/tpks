package io.github.vhula.tpks.view.graph;

import io.github.vhula.tpks.controller.SaveFunctionsCommand;
import io.github.vhula.tpks.controller.SaveTableTriggerCommand;
import io.github.vhula.tpks.controller.SaveVHDLFunctionCommand;

import javax.swing.*;
import java.awt.*;

/**
 * Frame for showing table.
 */
public class TableTriggerFrame extends JFrame {
    /**
     * Table for showing.
     */
    private ViewTriggerTable viewTable;

    public TableTriggerFrame(ViewTriggerTable viewTable) {
        super();
        this.viewTable = viewTable;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(640, 480);
        setJMenuBar(createMenuBar());

        JScrollPane jsp = new JScrollPane();
        jsp.setViewportView(viewTable);
        add(jsp, BorderLayout.NORTH);

        JScrollPane jsp2 = new JScrollPane();
        JTextArea functionsArea = new JTextArea();
        functionsArea.setEditable(false);
        functionsArea.setText(viewTable.getTable().toStringFunctions());
        functionsArea.setCaretPosition(0);
        functionsArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        jsp2.setViewportView(functionsArea);
        jsp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(jsp2, BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Creates menubar for this frame.
     * @return - MenuBar.
     */
    private JMenuBar createMenuBar() {
        JMenuItem saveTableItem = new JMenuItem();
        saveTableItem.setAction(new SaveTableTriggerCommand(viewTable.getTable()));

        JMenu tableMenu = new JMenu("Table");
        tableMenu.add(saveTableItem);

        JMenuItem saveFunctionsItem = new JMenuItem();
        saveFunctionsItem.setAction(new SaveFunctionsCommand(viewTable.getTable()));
        JMenuItem saveVHDLFunction = new JMenuItem();
        saveVHDLFunction.setAction(new SaveVHDLFunctionCommand(viewTable.getTable()));

        JMenu functionMenu = new JMenu("Function");
        functionMenu.add(saveFunctionsItem);
        functionMenu.add(saveVHDLFunction);

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(tableMenu);
        jMenuBar.add(functionMenu);
        return jMenuBar;
    }

}
