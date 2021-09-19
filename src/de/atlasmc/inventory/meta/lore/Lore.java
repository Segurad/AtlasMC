package de.atlasmc.inventory.meta.lore;

import java.util.List;

import de.atlasmc.chat.component.ChatComponent;

public interface Lore extends Iterable<ChatComponent> {

	public boolean isEmpty();
	public List<ChatComponent> getLines();
	public ChatComponent getLine(int line);
	public void setLine(int line, ChatComponent text);
	public int countLines();
	public void addLine(ChatComponent text);

}
