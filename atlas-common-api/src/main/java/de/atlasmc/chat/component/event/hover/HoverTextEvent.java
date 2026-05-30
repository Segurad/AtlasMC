package de.atlasmc.chat.component.event.hover;

import de.atlasmc.chat.Chat;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public class HoverTextEvent implements HoverEvent {

	@NotNull
	public static final NBTCodec<HoverTextEvent> 
	NBT_CODEC = NBTCodec
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
		return NBT_CODEC;
	}

}
