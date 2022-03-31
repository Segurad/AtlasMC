package de.atlasmc.chat;

import java.util.List;

/**
 * Defines the Type of a chat message.<br>
 * Chat messages will be handled based on their type an the clients settings.<br>
 * 
 * <table>
 * <tr><th>			</th><th>		</th><th>		</th><th>Type	</th></tr>
 * <tr><th>			</th><td>		</td><td>  Chat	</td><td>System	</td><td>Game	</td></tr>
 * <tr><th>			</th><td>Full	</td><td>| Send	</td><td>| Send	</td><td>| Send	</td></tr>
 * <tr><th>Setting	</th><td>System	</td><td>| X	</td><td>| Send	</td><td>| Send	</td></tr>
 * <tr><th>			</th><td>Hidden	</td><td>| X	</td><td>| X	</td><td>| Send	</td></tr>
 * </table>
 */
public enum ChatType {
	
	MESSAGE,
	SYSTEM,
	GAME;

	private static List<ChatType> VALUES;
	
	public static ChatType getByID(int id) {
		return getValues().get(id);
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<ChatType> getValues() {
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
