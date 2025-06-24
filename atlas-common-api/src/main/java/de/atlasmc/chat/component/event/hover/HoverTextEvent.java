package de.atlasmc.chat.component.event.hover;

import de.atlasmc.chat.Chat;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class HoverTextEvent implements HoverEvent {

	public static final NBTSerializationHandler<HoverTextEvent> 
	NBT_HANDLER = NBTSerializationHandler
					.builder(HoverTextEvent.class)
					.include(HoverEvent.NBT_HANDLER)
					.chat("value", HoverTextEvent::getValue, HoverTextEvent::setValue)
					.build();
	
	private Chat value;
	
	public Chat getValue() {
		return value;
	}
	
	public void setValue(Chat value) {
		this.value = value;
	}

	@Override
	public HoverAction getAction() {
		return HoverAction.SHOW_TEXT;
	}

	@Override
	public NBTSerializationHandler<? extends HoverTextEvent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
