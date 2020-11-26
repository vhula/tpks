package io.github.vhula.tpks;

import io.github.vhula.tpks.view.MainWindow;

import javax.swing.*;

public class Editor {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.
                        getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
                e1.printStackTrace();
            }
            MainWindow window = new MainWindow();
        });
	}

}
