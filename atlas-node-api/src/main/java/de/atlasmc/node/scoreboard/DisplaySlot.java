package de.atlasmc.node.scoreboard;

import java.util.List;

public enum DisplaySlot {
	
	LIST,
	SIDEBAR,
	BELOW_NAME;
	
	private static List<DisplaySlot> VALUES;
	
	public int getID() {
		return ordinal();
	}
	
	public static DisplaySlot getByID(int id) {
		return getValues().get(id);
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<DisplaySlot> getValues() {
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
