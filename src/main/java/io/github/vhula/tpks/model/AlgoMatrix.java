package io.github.vhula.tpks.model;

import io.github.vhula.tpks.view.FilePanel;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Клас, що містить матриці структури ЛСА.
 * @author Vadym Hula
 *
 */
public class AlgoMatrix {
	private static AlgoMatrix instance = null;
	/**
	 * Матриця зв'язків між вузлами.
	 */
	private int[][] connectionMatrix;
	/**
	 * Матриця відповідності сигналів елементам.
	 */
	private int[][] signalsMatrix;
	/**
	 * Конструктор за замовчуванням.
	 */
	private AlgoMatrix() {
	}
	/**
	 * Конструктор, що парсить матриці з тексту.
	 * @param text
	 */
	private AlgoMatrix(String text) {
		try {
			String[] matrixes = text.split(":");
			if (matrixes.length != 2)
				throw new IllegalArgumentException();
			String[] rows1 = matrixes[0].split("\n");
			String[][] cells = new String[rows1.length][];
			for (int i = 0; i < rows1.length; i++) {
				cells[i] = rows1[i].split("\\s");
			}
			connectionMatrix = new int[cells.length][cells.length];
			for (int i = 0; i < cells.length; i++) {
				for (int j = 0; j < cells[i].length; j++) {
					connectionMatrix[i][j] = Integer.parseInt(cells[i][j]);
				}
			}
			String[] rows2 = matrixes[1].split("\n");
			String[][] cells2 = new String[rows2.length][];
			for (int i = 0; i < rows2.length; i++) {
				cells2[i] = rows2[i].split("\\s");
			}
			signalsMatrix = new int[cells2.length][cells2[0].length];
			for (int i = 0; i < cells2.length; i++) {
				for (int j = 0; j < cells2[i].length; j++) {
					signalsMatrix[i][j] = Integer.parseInt(cells2[i][j]);
				}
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	/**
	 * Повертає об'єкт класу.
	 * @return - Об'єкт класу.
	 */
	public static AlgoMatrix getInstance() {
		if (instance == null)
			instance = new AlgoMatrix();
		return instance;
	}
	/**
	 * Повертає об'єкт класу.
	 * @param text - Матриці в текстовому вигляді.
	 * @return - Об'єкт класу.
	 */
	public static AlgoMatrix getInstance(String text) {
		instance = new AlgoMatrix(text);
		return instance;
	}
	/**
	 * Метод, що парсить матрицю сигналів.
	 */
	public void parseSignalsMatrix() {
		signalsMatrix = null;
		Algorithm algorithm = FileModel.getInstance().getAlgorithm();
		ArrayList<Integer> signals = new ArrayList<Integer>();
		int width = algorithm.nodesAmount() - 2;
		boolean hasSignal = false;
		for (int i = 0; i < algorithm.size(); i++) {
			Element el = algorithm.getElement(i);
			if (!algorithm.isOperator(el) && !algorithm.isCondition(el))
				continue;
			int[] indexes = el.getIndexes();
			for (int j = 0; j < indexes.length; j++) {
				for (Integer sign: signals) {
					if (sign.intValue() == indexes[j]) {
						hasSignal = true;
						break;
					}
				}
				if (!hasSignal)
					signals.add(indexes[j]);
				hasSignal = false;
			}
		}
		int height = signals.size();
		width++;
		signalsMatrix = new int[width][height];
		for (int i = 0; i < signalsMatrix.length; i++) {
			for (int j = 0; j < signalsMatrix[i].length; j++)
				signalsMatrix[i][j] = 0;
		}
		int idx = 0;
		for (int i = 0; i < algorithm.size(); i++) {
			Element el = algorithm.getElement(i);
			if (!algorithm.isOperator(el) && !algorithm.isCondition(el))
				continue;
			for (int j = 0; j < height; j++) {
				if (hasSignal(el, signals.get(j))) {
					signalsMatrix[idx][j] = 1;
				}
			}
			idx++;
		}
		for (int i = 0; i < signalsMatrix[0].length; i++) {
			signalsMatrix[signalsMatrix.length - 1][i] = signals.get(i);
		}
	}
	/**
	 * Метод, що визначає чи має елемент відповідний сигнал.
	 * @param element - Елемент.
	 * @param signal - Сигнал.
	 * @return - Повертає true, якщо містить, інакше - false.
	 */
	public boolean hasSignal(Element element, int signal) {
		boolean res = false;
		for (int i = 0; i < element.getIndexes().length; i++) {
			if (element.getIndexes()[i] == signal) {
				res = true;
				break;
			}
		}
		return res;
	}
	/**
	 * Метод, що парсить матрицю зв'язків.
	 */
	public void parseConnectionMatrix() {
		try {
			connectionMatrix = null;
			Algorithm algorithm = FileModel.getInstance().getAlgorithm();
			int amount = algorithm.nodesAmount();
			connectionMatrix = new int[amount][amount];
			for (int i = 0; i < amount; i++) {
				for (int j = 0; j < amount; j++) {
					connectionMatrix[i][j] = 0;
				}
			}
			Element current = null;
			Element next = null;
			int j = 0;
			int buf = 0;
			int idx = 1;
			for (int i = 0; i < algorithm.size() - 1; i++) {
				current = algorithm.getElement(i);
				next = algorithm.getElement(idx);
				if (algorithm.isBegin(current) || algorithm.isOperator(current)) {
					if (algorithm.isOperator(next) || algorithm.isCondition(next) || algorithm.isEnd(next)) {
						connectionMatrix[j][j + 1] = 1;
						j++;
						idx = i + 1;
						idx++;
					}
					if (algorithm.isUpArrow(next)) {
						connectionMatrix[j][getNextElement(next)] = -next.getIndexes()[0];
						j++;
						idx++;
					}
					if (algorithm.isDownArrow(next)) {
						i--;
						idx++;
					}
					
				}
				if (algorithm.isEnd(current)) {
					j++;
					idx++;
				}
				if (algorithm.isCondition(current)) {
					if (algorithm.isOperator(next) || algorithm.isCondition(next) || algorithm.isEnd(next)) {
						connectionMatrix[j][j + 1] = buf;  
						j++;
						idx = i + 1;
						idx++;
					}
					if (algorithm.isUpArrow(next)) {
						connectionMatrix[j][getNextElement(next)] = -next.getIndexes()[0];
						buf = next.getIndexes()[0];
						i--;
						idx++;
					}
					if (algorithm.isDownArrow(next)) {
						i--;
						idx++;
					}
				}
				if (algorithm.isUpArrow(current)) {
					idx++;
				}
				if (algorithm.isDownArrow(current)) {
					idx++;
				}
			}
		} catch (IllegalArgumentException exc) {
			JOptionPane.showMessageDialog(FilePanel.getInstance(null), "File is not saved!\nWrong LSA!", "Error", JOptionPane.ERROR_MESSAGE);
			throw new IllegalArgumentException();
		}
	}
	
	public int getNextElement(Element upArrow) {
		Algorithm algorithm = FileModel.getInstance().getAlgorithm();
		Element element = algorithm.lookFor(DownArrowElement.class, upArrow.getIndexes()[0]);
		if (element != null) {
			if (algorithm.getNext(element).getClass().equals(UpArrowElement.class)) {
				return getNextElement(algorithm.getNext(element));
			}
			if (!algorithm.getNext(element).getClass().equals(DownArrowElement.class)) {
				return algorithm.getNodeIndex(algorithm.getNext(element));
			} else {
				element = algorithm.getNext(element);
				while (algorithm.getNext(element).getClass().equals(DownArrowElement.class)) {
					element = algorithm.getNext(element);
				}
				if (algorithm.getNext(element).getClass().equals(UpArrowElement.class)) {
					return getNextElement(algorithm.getNext(element));
				} else {
					return algorithm.getNodeIndex(algorithm.getNext(element));
				}
			}
		} else {
			throw new IllegalArgumentException("Wrong LSA!!!!!!!!!!!!!!");
		}
	}

	/**
	 * Метод для виведення матриці зв'язків.
	 */
	public void printConnectionMatrix() {
		System.out.print(" ");
		for (int i = 0; i < connectionMatrix.length; i++) {
			System.out.printf("   %d", i);
		}
		System.out.println();
		for (int i = 0; i < connectionMatrix.length; i++) {
			System.out.printf("%d", i);
			for (int j = 0; j < connectionMatrix.length; j++) {
				System.out.printf("   %d", connectionMatrix[i][j]);
			}
			System.out.println();
		}
	}
	/**
	 * Метод для виведення матриці сигналів.
	 */
	public void printSignalsMatrix() {
		System.out.print(" ");
		for (int i = 0; i < signalsMatrix[0].length; i++) {
			System.out.printf("   %d", i);
		}
		System.out.println();
		for (int i = 0; i < signalsMatrix.length; i++) {
			System.out.printf("%d", i);
			for (int j = 0; j < signalsMatrix[i].length; j++) {
				System.out.printf("   %d", signalsMatrix[i][j]);
			}
			System.out.println();
		}
	}
	public String toString() {
		String res = "";
		for (int i = 0; i < connectionMatrix.length; i++) {
			for (int j = 0; j < connectionMatrix[i].length; j++) {
				res += ("" + connectionMatrix[i][j]);
				res += " ";
			}
			res = res.substring(0, res.length() - 1);
			res += "\n";
		}
		res += ":";
		for (int i = 0; i < signalsMatrix.length; i++) {
			for (int j = 0; j < signalsMatrix[i].length; j++) {
				res += ("" + signalsMatrix[i][j]);
				res += " ";
			}
			res = res.substring(0, res.length() - 1);
			res += "\n";
		}
		res = res.substring(0, res.length() - 1);
		return res;
	}
	/**
	 * Метод повертає матрицю зв'язків.
	 * @return - Матриця зв'язків.
	 */
	public int[][] getConnectionMatrix() {
		return connectionMatrix;
	}
	/**
	 * Повертає матрицю сигналів.
	 * @return - Матриця сигналів.
	 */
	public int[][] getSignalsMatrix() {
		return signalsMatrix;
	}
	
	public boolean isOperator(int row) {
		int buf = 0;
		for (int i = 0; i < connectionMatrix[row].length; i++) {
			if (connectionMatrix[row][i] != 0) {
				buf++;
			}
		}
		if (buf == 1)
			return true;
		else
			return false;
	}
	
	public boolean isCondition(int row) {
		int buf = 0;
		for (int i = 0; i < connectionMatrix[row].length; i++) {
			if (connectionMatrix[row][i] != 0) {
				buf++;
			}
		}
		if (buf == 2)
			return true;
		else
			return false;
	}
	
	public boolean isEnd(int row) {
		int buf = 0;
		for (int i = 0; i < connectionMatrix[row].length; i++) {
			if (connectionMatrix[row][i] != 0) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Повертає номер переходу.
	 * @param row
	 * @return
	 */
	public int getTransition(int row) {
		for (int i = 0; i < connectionMatrix[row].length; i++) {
			if (connectionMatrix[row][i] < 0)
				return i;
		}
		return -1;
	}
	/**
	 * Повертає номер вузла в який відбувається перехід.
	 * @param row
	 * @return
	 */
	public int getTransitionIndex(int row) {
		for (int i = 0; i < connectionMatrix[row].length; i++) {
			if (connectionMatrix[row][i] < 0)
				return (-connectionMatrix[row][i]);
		}
		throw new IllegalArgumentException();
	}
	
	public int[] getNext(int row) {
		int a = Integer.MIN_VALUE;
		int b = Integer.MIN_VALUE;
		for (int i = 0; i < connectionMatrix.length; i++) {
			if (connectionMatrix[row][i] != 0) {
				if (a == Integer.MIN_VALUE) {
					a = i;
				} else {
					b = i;
				}
			}
		}
		int[] res = null;
		if (b != Integer.MIN_VALUE) {
			res = new int[2];
			res[0] = a;
			res[1] = b;
		} else {
			if (a != Integer.MIN_VALUE) {
				res = new int[1];
				res[0] = a;
			}
		}
		return res;
	}
	
	

}
