package io.github.vhula.tpks.model;
/**
 * ����, �� �������� ������� � ���.
 * @author Vadym Hula
 *
 */
public abstract class Element {
	
	/**
	 * ����� �������.
	 */
	protected String[] names;
	
	/**
	 * ������� �������.
	 */
	protected int[] indexes;
	
	public Element(String[] names, int[] indexes) {
		setNames(names);
		setIndexes(indexes);
	}
	

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public int[] getIndexes() {
		return indexes;
	}

	public void setIndexes(int[] indexes) {
		this.indexes = indexes;
	}
	
	public String toString() {
		String res = "";
		for (int i = 0; i < names.length; i++) {
			res += (names[i] + indexes[i] + "");
		}
		return res;
	}

}
