package io.github.vhula.tpks.model;

public class EndElement extends Element {
	
	private static EndElement instance = null;

	private EndElement(String[] names, int[] indexes) {
		super(names, indexes);
	}

	public static EndElement getInstance() {
		if (instance == null) {
			String[] names = {"K"};
			int[] indexes = {0};
			instance = new EndElement(names, indexes);
		}
		return instance;
	}

    public String toString() {
        return "K";
    }

}
