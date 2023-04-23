package de.atlasmc.chat;

public interface Messageable {
	
	void sendMessage(String message);
	
	default void sendMessage(String[] messages) {
		for (String message : messages) {
			sendMessage(message);
		}
	}
	
	/**
	 * Send a message as {@link ChatType#SYSTEM}
	 * @param chat
	 */
	default void sendMessage(Chat chat) {
		sendMessage(chat, ChatType.SYSTEM);
	}
	
	/**
	 * Sends a message with the given type
	 * @param chat
	 * @param type
	 */
	void sendMessage(Chat chat, ChatType type);

}
