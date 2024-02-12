package de.atlasmc.chat;

import java.util.List;

public enum ChatMode {
	
	FULL,
	COMMANDS_ONLY,
	NONE;

	private static List<ChatMode> VALUES;
	
	public static ChatMode getByID(int id) {
		return getValues().get(id);
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<ChatMode> getValues() {
		if (VALUES == null)
			VALUES = List.of(values());
		return VALUES;
	}
	
	public int getID() {
		return ordinal();
	}
	
	/**
	 * Releases the system resources used from the values cache
	 */
	public static void freeValues() {
		VALUES = null;
	}

}
