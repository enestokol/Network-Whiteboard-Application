package server;

public enum Shapes {

	RECTANGLE(1), CIRCLE(2), LINE(3);

	private int index;

	Shapes(int index) {
		this.index = index;
	}

	public int getType() {
		return index;
	}

}
