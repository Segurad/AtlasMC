package de.atlasmc;

import java.util.List;

public enum Axis {
	
	X,
	Y,
	Z;

	private static List<Axis> VALUES;
	
	/**
	 * Returns the Axis represented by the name or {@link #Y} if no matching Axis has been found
	 * @param name the name of the Axis
	 * @return the Axis or {@link #Y}
	 */
	public static Axis getByName(String name) {
		for (Axis a : getValues()) {
			if (a.name().equalsIgnoreCase(name)) return a;
		}
		return Y;
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<Axis> getValues() {
		if (VALUES == null)
			VALUES = List.of(values());
		return VALUES;
	}
	
	/**
	 * Releases the system resources used from the values cache
	 */
	public static void freeValues() {
		VALUES = null;
	}

}
