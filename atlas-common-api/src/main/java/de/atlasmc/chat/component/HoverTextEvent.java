package de.atlasmc.chat.component;

import java.io.IOException;

import de.atlasmc.util.nbt.io.NBTWriter;

public class HoverTextEvent implements HoverEvent {

	private final ChatComponent component;
	
	public HoverTextEvent(ChatComponent component) {
		if (component == null)
			throw new IllegalArgumentException("Component can not be null!");
		this.component = component;
	}
	
	public ChatComponent getComponent() {
		return component;
	}

	@Override
	public HoverAction getAction() {
		return HoverAction.SHOW_TEXT;
	}

	@Override
	public void addContents(NBTWriter writer) throws IOException {
		component.addContents(writer);
	}

}
