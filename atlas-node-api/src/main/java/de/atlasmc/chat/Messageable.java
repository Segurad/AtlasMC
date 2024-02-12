package de.atlasmc.chat;

public interface Messageable {
	
	default void sendMessage(String... messages) {
		for (String message : messages) {
			sendMessage(message);
		}
	}
	
	void sendMessage(Chat chat);
	
	void sendMessage(String message);
	
	default void sendMessage(String message, ChatType type, String source) {
		sendMessage(message, type, source, null);
	}
	
	void sendMessage(String message, ChatType type, String source, String target);

}
