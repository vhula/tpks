package io.github.vhula.tpks.view;

import io.github.vhula.tpks.controller.*;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    private JMenu graphMenu = new JMenu("Graph");
    private JMenu tableMenu = new JMenu("Table");

    private JMenuItem showGraphItem = new JMenuItem();
    private JMenuItem openGraphItem = new JMenuItem();
    private JMenuItem saveGraphItem = new JMenuItem();
    private JMenuItem makeCodingItem = new JMenuItem();

    private JMenuItem openTableItem = new JMenuItem();
	
	public MenuBar() {
		super();
        showGraphItem.setAction(new ShowGraphCommand());
        openGraphItem.setAction(new OpenGraphCommand());
        saveGraphItem.setAction(new SaveGraphCommand());
        makeCodingItem.setAction(new MakeCodingCommand());
        openTableItem.setAction(new OpenTableTriggerCommand());

        graphMenu.add(showGraphItem);
        graphMenu.add(openGraphItem);
        graphMenu.add(saveGraphItem);
        graphMenu.add(makeCodingItem);
        tableMenu.add(openTableItem);

        add(graphMenu);

        add(tableMenu);
	}

}
