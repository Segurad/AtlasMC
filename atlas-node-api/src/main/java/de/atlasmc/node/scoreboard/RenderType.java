package de.atlasmc.node.scoreboard;

import java.util.List;

public enum RenderType {
	
	INTEGER,
	HEARTS;
	
	private static List<RenderType> VALUES;
	
	public int getID() {
		return ordinal();
	}
	
	public static RenderType getByID(int id) {
		return getValues().get(id);
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<RenderType> getValues() {
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
