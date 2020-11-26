package io.github.vhula.tpks.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ����, �� ��������������� ��� ����������� ������� � ���������
 * @author Vadym Hula
 *
 */
public class InputParser {
	/**
	 * �����, �� ������� �� � ����������� ������� ������� ��� ��������.
	 * @param element - �������
	 * @param text - �������
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
	 * �����, �� ������� ����� ���� �������
	 * @param element - �������
	 * @param text - �������
	 * @return - ����� ���� �������
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
	 * ������� ����� ������ �������
	 * @param element - �������
	 * @param text - �������
	 * @return - ����� ������ �������
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
	 * ������� �� � ����������� ������� ��� ���������
	 * @param text - �������
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
	 * ������� �� � ����������� ������� ��� ������ �����
	 * @param text - �������
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
	 * ������� �� � ����������� ������� ��� ������
	 * @param text - �������
	 * @return
	 */
	private static boolean parseArrow(String text) {
		String[] names = text.split("\\s+");
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(names[0]);
		return matcher.matches();
	}

}
