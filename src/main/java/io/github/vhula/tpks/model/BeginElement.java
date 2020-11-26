package io.github.vhula.tpks.model;
/**
 * ����, �� �������� ���������� ������� ���.
 * @author Vadym Hula
 *
 */
public class BeginElement extends Element {
	/**
	 * ��'��� �����.
	 */
	private static BeginElement instance = null;

	private BeginElement(String[] names, int[] indexes) {
		super(names, indexes);
	}
	/**
	 * ������� ��'��� �����.
	 * @return - ��'��� �����.
	 */
	public static BeginElement getInstance() {
		if (instance == null) {
			String[] names = {"�"};
			int[] indexes = {0};
			instance = new BeginElement(names, indexes);
		}
		return instance;
	}

    public String toString() {
        return "�";
    }
	
	

}
