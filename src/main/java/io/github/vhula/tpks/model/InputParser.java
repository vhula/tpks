package io.github.vhula.tpks.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Клас, що використовується для редагування сигналів в елементах
 * @author Vadym Hula
 *
 */
public class InputParser {
	/**
	 * Метод, що визначає чи є правильними введенні сигнали для елементу.
	 * @param element - Елемент
	 * @param text - Сигнали
	 * @return
	 */
	public static boolean parse(Element element, String text) {
		if (element.getClass().equals(OperatorElement.class)) {
			return parseOperator(text);
		}
		if (element.getClass().equals(ConditionalElement.class)) {
			return parseCondition(text);
		}
		if (element.getClass().equals(UpArrowElement.class) || element.getClass().equals(DownArrowElement.class)) {
			return parseArrow(text);
		}
		throw new IllegalArgumentException();
	}
	/**
	 * Метод, що повертає масив імен сигналів
	 * @param element - Елемент
	 * @param text - Сигнали
	 * @return - Масив імен сигналів
	 */
	public static String[] parseNames(Element element, String text) {
		if (element.getClass().equals(OperatorElement.class)) {
			String[] strs = text.split("\\s+");
			String[] names = new String[strs.length];
			for (int i = 0; i < strs.length; i++) {
				names[i] = "Y";
			}
			return names;
		}
		if (element.getClass().equals(ConditionalElement.class)) {
			String[] strs = text.split("\\s+");
			String[] names = new String[strs.length];
			for (int i = 0; i < strs.length; i++) {
				names[i] = "X";
			}
			return names;
		}
		if (element.getClass().equals(UpArrowElement.class) || element.getClass().equals(DownArrowElement.class)) {
			String[] names = new String[1];
			names[0] = "UD";
			return names;
		}
		throw new IllegalArgumentException();
	}
	/**
	 * Повертає масив номерів сигналів
	 * @param element - Елемент
	 * @param text - Сигнали
	 * @return - Масив номерів сигналів
	 */
	public static int[] parseIndexes(Element element, String text) {
		if (element.getClass().equals(OperatorElement.class) ||
				element.getClass().equals(ConditionalElement.class)) {
			String[] strs = text.split("\\s+");
			int[] indexes = new int[strs.length];
			for (int i = 0; i < strs.length; i++) {
				indexes[i] = Integer.parseInt(strs[i].substring(1));
			}
			return indexes;
		}
		if (element.getClass().equals(UpArrowElement.class) || element.getClass().equals(DownArrowElement.class)) {
			int[] indexes = new int[1];
			indexes[0] = Integer.parseInt(text);
			return indexes;
		}
		throw new IllegalArgumentException();
	}
	/**
	 * Визначає чи є правильними сигнали для оператора
	 * @param text - Сигнали
	 * @return
	 */
	private static boolean parseOperator(String text) {
		String[] names = text.split("\\s+");
		Pattern pattern = Pattern.compile("[Yy]\\d+");
		for (int i = 0; i < names.length; i++) {
			Matcher matcher = pattern.matcher(names[i]);
			if (!matcher.matches())
				return false;
		}
		return true;
	}
	/**
	 * Визначає чи є правильними сигнали для логічної умови
	 * @param text - Сигнали
	 * @return
	 */
	private static boolean parseCondition(String text) {
		String[] names = text.split("\\s+");
		Pattern pattern = Pattern.compile("[Xx]\\d+");
		for (int i = 0; i < names.length; i++) {
			Matcher matcher = pattern.matcher(names[i]);
			if (!matcher.matches())
				return false;
		}
		return true;
	}
	/**
	 * Визначає чи є правильними сигнали для стрілки
	 * @param text - Сигнали
	 * @return
	 */
	private static boolean parseArrow(String text) {
		String[] names = text.split("\\s+");
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(names[0]);
		return matcher.matches();
	}

}
