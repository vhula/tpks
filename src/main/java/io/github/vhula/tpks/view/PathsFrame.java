package io.github.vhula.tpks.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Class which displays paths, cycles and eternal cycles.
 */
public class PathsFrame extends JFrame {

    private static PathsFrame instance = null;
    /**
     * Text area wich contains information.
     */
    private JTextArea textArea = new JTextArea();

    private PathsFrame() {
        super("LSA Editor");
        setLayout(new BorderLayout());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                instance = null;
            }
        });
        setSize(640, 480);
        setVisible(true);
    }

    public static PathsFrame getInstance() {
        if (instance == null)
            instance = new PathsFrame();
        return instance;
    }

    /**
     * Method for changing value of text area.
     * @param text - New value.
     */
    public void setOutput(String text) {
        textArea.setFont(new Font("LucidaConsole", Font.PLAIN, 16));
        textArea.setText(text);
    }

}
