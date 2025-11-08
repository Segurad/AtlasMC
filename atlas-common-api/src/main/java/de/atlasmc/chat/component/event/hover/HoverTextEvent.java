package de.atlasmc.chat.component.event.hover;

import de.atlasmc.chat.Chat;
import de.atlasmc.nbt.codec.NBTCodec;

public class HoverTextEvent implements HoverEvent {

	public static final NBTCodec<HoverTextEvent> 
	NBT_HANDLER = NBTCodec
					.builder(HoverTextEvent.class)
					.include(HoverEvent.NBT_HANDLER)
					.codec("value", HoverTextEvent::getValue, HoverTextEvent::setValue, Chat.NBT_CODEC)
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
	public NBTCodec<? extends HoverTextEvent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
