package de.atlasmc.chat;

import java.io.IOException;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.annotation.NotNull;

public interface ChatFactory {
	
	Chat asChat(CharSequence snbt, CharSequence legacy);
	
	/**
	 * {@inheritDoc ChatUtil#legacyToSNBT(CharSequence, char)}
	 */
	String legacyToSNBT(CharSequence text, char formatPrefix);
	
	String snbtToLegacy(CharSequence text, char formatPrefix);
	
	ChatComponent legacyToComponent(CharSequence legacy, char formatPrefix);
	
	ChatComponent snbtToComponent(CharSequence snbt);
	
	@NotNull
	String componentToRawText(ChatComponent component);
	
	String componentToLegacy(ChatComponent component, char formatPrefix);
	
	String componentToConsole(ChatComponent component);

	String snbtToRawText(String snbt);

	String legacyToRawText(String legacy, char formatPrefix);

	ChatComponent toComponent(Chat chat);
	
	Chat fromNBT(NBTReader reader) throws IOException;
	
	void toNBT(CharSequence key, Chat chat, NBTWriter writer) throws IOException;

}
