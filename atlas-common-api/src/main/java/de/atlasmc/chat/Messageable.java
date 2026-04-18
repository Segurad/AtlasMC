package de.atlasmc.chat;

public interface Messageable {
	
	default void sendMessage(String... messages) {
		for (var msg : messages) {
			sendMessage(msg);
		}
	}
	
	default void sendMessage(String message) {
		sendMessage(ChatUtil.toChat(message));
	}
	
	default void sendMessage(Chat chat) {
		sendMessage(chat, false);
	}
	
	void sendMessage(Chat chat, boolean overlay);

}
