package io.github.vhula.tpks.controller;

import io.github.vhula.tpks.model.*;
import io.github.vhula.tpks.view.FilePanel;

import javax.swing.*;

/**
 * Клас, що використовується для команди редагування сигналів елементів.
 * @author Vadym Hula
 *
 */
public class EditPropertiesCommand extends Command {
	
	public EditPropertiesCommand() {
		putValue(NAME, "Properties...");
		putValue(SHORT_DESCRIPTION, "");
		putValue(SMALL_ICON, new ImageIcon(imageContent("images/properties_16.png"), "Properties"));
	}

	@Override
	public void doCommand() {
		FileModel fileModel = FileModel.getInstance();
		Algorithm algorithm = fileModel.getAlgorithm();
		Element selected = algorithm.getSelected();
		if (!selected.getClass().equals(BeginElement.class) &&
				!selected.getClass().equals(EndElement.class)) {
			String input = JOptionPane.showInputDialog("Input values: ", algorithm.getSelected().toString());
			if (input != null && InputParser.parse(algorithm.getSelected(), input)) {
				selected.setNames(InputParser.parseNames(selected, input));
				selected.setIndexes(InputParser.parseIndexes(selected, input));
				FilePanel.getInstance(null).getDrawer().refresh();
				fileModel.notifyObservers();
				ActionsFlags.SAVE_FILE = ActionsFlags.ENABLED;
				EditorModel.getInstance(null).notifyObservers();
			} else {
				if (input != null)
				if (!InputParser.parse(algorithm.getSelected(), input)) {
					JOptionPane.showMessageDialog(FilePanel.getInstance(null),
							"Wrong Input!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		
	}

}
