package de.atlasmc;

public enum Axis {
	
	X,
	Y,
	Z;

	/**
	 * Returns the Axis represented by the name or {@link #Y} if no matching Axis has been found
	 * @param name the name of the Axis
	 * @return the Axis or {@link #Y}
	 */
	public static Axis getByName(String name) {
		name = name.toUpperCase();
		for (Axis a : values()) {
			if (a.name().equals(name)) return a;
		}
		return Y;
	}

}
