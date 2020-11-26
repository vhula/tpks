package io.github.vhula.tpks.model;
/**
 * Клас, що відображає початковий елемент ЛСА.
 * @author Vadym Hula
 *
 */
public class BeginElement extends Element {
	/**
	 * Об'єкт класу.
	 */
	private static BeginElement instance = null;

	private BeginElement(String[] names, int[] indexes) {
		super(names, indexes);
	}
	/**
	 * Повертає об'єкт класу.
	 * @return - Об'єкт класу.
	 */
	public static BeginElement getInstance() {
		if (instance == null) {
			String[] names = {"П"};
			int[] indexes = {0};
			instance = new BeginElement(names, indexes);
		}
		return instance;
	}

    public String toString() {
        return "П";
    }
	
	

}
