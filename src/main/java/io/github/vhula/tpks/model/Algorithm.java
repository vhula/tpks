package io.github.vhula.tpks.model;

import io.github.vhula.tpks.controller.*;

import java.util.ArrayList;

/**
 * Клас, що відображає ЛСА.
 * @author Vadym Hula
 *
 */
public class Algorithm {
	
	/**
	 * Буфер для копіювання елементів.
	 */
	private Element buffer = null;
	
	/**
	 * Вибраний елемент в ЛСА.
	 */
	private Element cursor = null;
	/**
	 * Позначає виділений елемент(елемент, на якому натиснули правою кнопною миші)
	 */
	private Element selected = null;
	
	/**
	 * Елементи з ЛСА.
	 */
	private ArrayList<Element> elements = new ArrayList<Element>();
	
	public Algorithm() {
	}
	/**
	 * Метод, що парсить ЛСА з матриць зв'язків і сигналів
	 */
	public void parseAlgorithm() {
		AlgoMatrix algoMatrix = AlgoMatrix.getInstance();
		int[][] conMatrix = AlgoMatrix.getInstance().getConnectionMatrix();
		int[][] sigMatrix = AlgoMatrix.getInstance().getSignalsMatrix();
		new AddBeginCommand().doCommand();
		for (int i = 1; i < conMatrix.length; i++) {
			if (algoMatrix.isOperator(i)) {
				new AddOperatorCommand().doCommand();
			}
			if (algoMatrix.isCondition(i)) {
				new AddConditionCommand().doCommand();
			}
			if (algoMatrix.isEnd(i)) {
				new AddEndCommand().doCommand();
			}
		}
		for (int i = 0; i < conMatrix.length; i++) {
			if (algoMatrix.isOperator(i)) {
				if (algoMatrix.getTransition(i) != -1) {
					setCursor(getNode(i));
					new AddUpArrowCommand().doCommand();
					int idxs[] = {algoMatrix.getTransitionIndex(i)};
					getCursor().setIndexes(idxs);
					Element el = getNode(algoMatrix.getTransition(i));
					setCursor(getPrev(el));
					new AddDownArrowCommand().doCommand();
					getCursor().setIndexes(idxs);
				}
			}
			if (algoMatrix.isCondition(i)) {
				if (algoMatrix.getTransition(i) != -1) {
					setCursor(getNode(i));
					new AddUpArrowCommand().doCommand();
					int idxs[] = {algoMatrix.getTransitionIndex(i)};
					getCursor().setIndexes(idxs);
					Element el = getNode(algoMatrix.getTransition(i));
					setCursor(getPrev(el));
					new AddDownArrowCommand().doCommand();
					getCursor().setIndexes(idxs);
				}
			}
		}
		for (int i = 0; i < sigMatrix.length - 1; i++) {
			Element el = getNode(i + 1);
			String name = "";
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<Integer> indexes = new ArrayList<Integer>();
			if (el.getClass().equals(OperatorElement.class)) {
				name = "Y";
			} else if (el.getClass().equals(ConditionalElement.class)) {
				name = "X";
			} else {
				continue;
			}
			for (int j = 0; j < sigMatrix[i].length; j++) {
				if (sigMatrix[i][j] == 1) {
					names.add(name);
					indexes.add(sigMatrix[sigMatrix.length - 1][j]);
				}
			}
			String[] nms = new String[names.size()];
			for (int k = 0; k < nms.length; k++)
				nms[k] = names.get(k);
			Integer[] ind = new Integer[indexes.size()];
			int[] idxs = new int[ind.length];
			for (int k = 0; k < idxs.length; k++) {
				ind[k] = indexes.get(k);
				idxs[k] = ind[k];
			}
			el.setNames(nms);
			el.setIndexes(idxs);
		}
	}
	
	public Element getBuffer() {
		return buffer;
	}
	
	public void setBuffer(Element buffer) {
		this.buffer = buffer;
	}
	
	public Element getCursor() {
		return cursor;
	}
	
	public void setCursor(int index) {
		Element el = getElement(index);
		setCursor(el);
	}
	
	public void setCursor(Element cursor) {
		this.cursor = cursor;
		Class<?> cl = cursor.getClass();
		if (cl.equals(BeginElement.class)) {
			ActionsFlags.ADD_OPERATOR = ActionsFlags.ENABLED;
			ActionsFlags.ADD_DOWN_ARROW = ActionsFlags.ENABLED;
			ActionsFlags.ADD_CONDITION = ActionsFlags.ENABLED;
			ActionsFlags.ADD_UP_ARROW = ActionsFlags.ENABLED;
		}
		if (cl.equals(OperatorElement.class)) {
			ActionsFlags.ADD_OPERATOR = ActionsFlags.ENABLED;
			ActionsFlags.ADD_DOWN_ARROW = ActionsFlags.ENABLED;
			ActionsFlags.ADD_CONDITION = ActionsFlags.ENABLED;
			ActionsFlags.ADD_UP_ARROW = ActionsFlags.ENABLED;
		}
		if (cl.equals(ConditionalElement.class)) {
			ActionsFlags.ADD_UP_ARROW = ActionsFlags.ENABLED;
			ActionsFlags.ADD_OPERATOR = ActionsFlags.DISABLED;
			ActionsFlags.ADD_DOWN_ARROW = ActionsFlags.DISABLED;
			ActionsFlags.ADD_CONDITION = ActionsFlags.DISABLED;
		}
		if (cl.equals(UpArrowElement.class)) {
			ActionsFlags.ADD_UP_ARROW = ActionsFlags.ENABLED;
			ActionsFlags.ADD_OPERATOR = ActionsFlags.ENABLED;
			ActionsFlags.ADD_DOWN_ARROW = ActionsFlags.ENABLED;
			ActionsFlags.ADD_CONDITION = ActionsFlags.ENABLED;
		}
		if (cl.equals(DownArrowElement.class)) {
			ActionsFlags.ADD_UP_ARROW = ActionsFlags.ENABLED;
			ActionsFlags.ADD_OPERATOR = ActionsFlags.ENABLED;
			ActionsFlags.ADD_DOWN_ARROW = ActionsFlags.ENABLED;
			ActionsFlags.ADD_CONDITION = ActionsFlags.ENABLED;
		}
		if (cl.equals(EndElement.class)) {
			ActionsFlags.ADD_UP_ARROW = ActionsFlags.ENABLED;
			ActionsFlags.ADD_OPERATOR = ActionsFlags.ENABLED;
			ActionsFlags.ADD_DOWN_ARROW = ActionsFlags.ENABLED;
			ActionsFlags.ADD_CONDITION = ActionsFlags.ENABLED;
		}
	}
	/**
	 * Зсуває курсор на позицію ліворуч
	 */
	public void moveCursorLeft() {
		int idx = elements.indexOf(cursor);
		if (idx > 0) {
			idx--;
			setCursor(elements.get(idx));
		}
	}
	/**
	 * Зсуває курсор на позицію праворуч
	 */
	public void moveCursorRight() {
		int idx = elements.indexOf(cursor);
		if (idx < elements.size() - 1) {
			idx++;
			setCursor(elements.get(idx));
		}
	}
	/**
	 * Додає елемент в ЛСА
	 * @param element - Елемент для додавання
	 */
	public void addElement(Element element) {
		try {
			int idx = elements.indexOf(cursor);
			if (idx != -1)
				elements.add(idx + 1, element);
			else
				elements.add(element);
			setCursor(element);
		} catch (NullPointerException exception) {
			exception.printStackTrace();
		}

	}
	/**
	 * Видаляє елемент з ЛСА.
	 * @param element - Елемент, що видаляється
	 */
	public void removeElement(Element element) {
		try {
			if (element == cursor) {
				moveCursorLeft();
			}
			elements.remove(element);
		} catch (NullPointerException exception) {
			exception.printStackTrace();
		}
        FileModel.getInstance().getHandler().eternals = false;
	}
	/**
	 * Повертає кількість елементів в ЛСА.
	 * @return - Кількість елементів в ЛСА.
	 */
	public int size() {
		return elements.size();
	}
	/**
	 * Метод для отримання елементу з ЛСА за позицією.
	 * @param idx - Позиція
	 * @return - Елемент
	 */
	public Element getElement(int idx) {
		try {
			return elements.get(idx);
		} catch (ArrayIndexOutOfBoundsException exc) {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	public void setSelected(int idx) {
		try {
			selected = elements.get(idx);
		} catch (ArrayIndexOutOfBoundsException exc) {
			exc.printStackTrace();
		}
	}
	
	public void setSelected(Element element) {
		this.selected = element;
	}
	
	public Element getSelected() {
		return selected;
	}
	/**
	 * Метод повертає елемент, що знаходиться справа від курсору.
	 * @return - Елемент
	 */
	public Element getNext() {
		int idx = elements.indexOf(cursor);
		idx++;
		if (idx < elements.size())
			return elements.get(idx);
		else
			return null;
	}
	/**
	 * Повертає наступний елемент після заданого.
	 * @param element - Заданий елемент.
	 * @return - Наступний елемент.
	 */
	public Element getNext(Element element) {
		int idx = elements.indexOf(element);
		idx++;
		if (idx < elements.size())
			return elements.get(idx);
		else
			return null;
	}
	/**
	 * Повертає елемент, що розташований зліва від курсору.
	 * @return - Елемент
	 */
	public Element getPrev() {
		int idx = elements.indexOf(cursor);
		idx--;
		if (idx > -1)
			return elements.get(idx);
		else
			return null;
	}
	/**
	 * Повертає елемент, що знаходиться зліва від заданого.
	 * @param element - Заданий елемент.
	 * @return - Попередній елемент.
	 */
	public Element getPrev(Element element) {
		int idx = elements.indexOf(element);
		idx--;
		if (idx >= 0)
			return elements.get(idx);
		else
			return null;
	}
	/**
	 * Метод для пошуку елементу заданого класу із заданим сигналом.
	 * @param cl - Клас
	 * @param idx - Сигнал
	 * @return - Елемент
	 */
	public Element lookFor(Class<?> cl, int idx) {
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i).getClass().equals(cl)) {
				for (int j = 0; j < elements.get(i).getIndexes().length; j++) {
					if (elements.get(i).getIndexes()[j] == idx)
						return elements.get(i);
				}
			}
		}
		return null;
	}
	
	public int nodesAmount() {
		int amount = 0;
		for (Element element: elements) {
			if (!element.getClass().equals(UpArrowElement.class) && !element.getClass().equals(DownArrowElement.class))
				amount++;
		}
		return amount;
	}
	
	public boolean isNode(Element element) {
		boolean res = true;
		if (element.getClass().equals(UpArrowElement.class) && element.getClass().equals(DownArrowElement.class))
			res = false;
		return res;
	}
	
	public boolean isOperator(Element element) {
		if (element.getClass().equals(OperatorElement.class))
			return true;
		return false;
	}
	
	public boolean isCondition(Element element) {
		if (element.getClass().equals(ConditionalElement.class))
			return true;
		return false;
	}
	
	public boolean isBegin(Element element) {
		if (element.getClass().equals(BeginElement.class))
			return true;
		return false;
	}
	
	public boolean isEnd(Element element) {
		if (element.getClass().equals(EndElement.class))
			return true;
		return false;
	}

	public boolean isUpArrow(Element element) {
		if (element.getClass().equals(UpArrowElement.class))
			return true;
		return false;
	}
	
	public boolean isDownArrow(Element element) {
		if (element.getClass().equals(DownArrowElement.class))
			return true;
		return false;
	}
	
	public int getNodeIndex(Element element) {
		int idx = 0;
		for (Element el: elements) {
			if (el.equals(element))
				break;
			if (!isUpArrow(el) && !isDownArrow(el))
				idx++;
		}
		return idx;
	}
	
	public Element getNode(int idx) {
		for (Element el: elements) {
			if (!isUpArrow(el) && !isDownArrow(el))
				idx--;
			if (idx == -1)
				return el;
		}
		return null;
	}
	
	public void clear() {
		elements = null;
	
	}

    public ArrayList<Element> getOperators() {
        ArrayList<Element> res = new ArrayList<Element>();
        for (int i = 0; i < size(); i++) {
            Element element = elements.get(i);
            if (isOperator(element) || isBegin(element) || isEnd(element)) {
                res.add(element);
            }
        }
        return res;
    }

    public ArrayList<Element> getYList() {
        ArrayList<Element> res = new ArrayList<Element>();
        for (int i = 0; i < size(); i++) {
            Element element = elements.get(i);
            if (isOperator(element)) {
                res.add(element);
            }
        }
        return res;
    }

    public ArrayList<Element> getConditions() {
        ArrayList<Element> res = new ArrayList<Element>();
        for (int i = 0; i < size(); i++) {
            Element element = elements.get(i);
            if (isCondition(element)) {
                res.add(element);
            }
        }
        return res;
    }

    public int getElementIndex(Element element) {
        return elements.indexOf(element);
    }
}
