package io.github.vhula.tpks.view.graph;

import io.github.vhula.tpks.controller.ShowTableTriggerCommand;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Vadym Hula
 * Date: 22.10.12
 * Time: 0:21
 * To change this template use File | Settings | File Templates.
 */
public class GraphFrame extends JFrame {

    private GraphPanel graphPanel;

    public GraphFrame(GraphPanel panel) {
        super();
        this.graphPanel = panel;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(640, 480);
        setJMenuBar(createMenuBar());
        JScrollPane jsp = new JScrollPane();
        jsp.setViewportView(graphPanel);
        add(jsp, BorderLayout.CENTER);
        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuItem showTableItem = new JMenuItem();
        showTableItem.setAction(new ShowTableTriggerCommand(graphPanel.getGraph()));

        JMenu tableMenu = new JMenu("Table");
        tableMenu.add(showTableItem);

        JMenuBar jMenuBar  = new JMenuBar();
        jMenuBar.add(tableMenu);

        return jMenuBar;
    }



}
