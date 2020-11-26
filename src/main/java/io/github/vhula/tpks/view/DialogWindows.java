package io.github.vhula.tpks.view;

import io.github.vhula.tpks.model.FileModel;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Клас, що використовується для відображення діалогових вікон.
 * @author Vadym Hula
 *
 */
public class DialogWindows {
	/**
	 * Створює і відображає попереджувальний діалог.
	 * @return - Вибір користувача.
	 */
	public static int showConfirmDialog() {
		int choose = 0;
		if (!FileModel.getInstance().isSaved()) {
			choose = JOptionPane.showConfirmDialog(null,
					"<html>File "
					+ " is not saved!<br>"
					+ "Are you sure?");
		}
		return choose;
	}
	/**
	 * Відображає діалого відкриття файлу.
	 * @return - Повне ім'я вибраного файлу.
	 */
	public static String showOpenDialog(final String desc) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				return "."+desc;
			}
			
			@Override
			public boolean accept(File file) {
				if (file.getName().endsWith("."+desc)) {
					return true;
				}
				if (file.isDirectory()) {
					return true;
				}
				return false;
			}
		});
		int choice = jfc.showOpenDialog(null);
		if (choice == JFileChooser.APPROVE_OPTION) {
			String filename = jfc.getSelectedFile().getName();
			int n = filename.length() - desc.length();
			String format = filename.substring(n, filename.length());
			if (format.equals(desc)) {
				return jfc.getSelectedFile().getPath();
			} else {
				JOptionPane.showMessageDialog(null,
						"Illegal file format."
						 + "\nPlease, try again.",
						"Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		} else {
			return null;
		}
	}
	/**
	 * Відображає вікно для збереження файлу.
	 * @return - Вибраний файл.
	 */
	public static String showSaveDialog(final String desc) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "*."+desc;
			}

			@Override
			public boolean accept(final File file) {
				if (file.getName().endsWith("."+desc)) {
					return true;
				}
				if (file.isDirectory()) {
					return true;
				}
				return false;
			}
		});
		int choise = jfc.showSaveDialog(null);
		if (choise == JFileChooser.APPROVE_OPTION) {
			return jfc.getSelectedFile().getPath();
		}
		return null;
	}

}
