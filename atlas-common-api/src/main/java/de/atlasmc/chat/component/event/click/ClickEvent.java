package de.atlasmc.chat.component.event.click;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.util.annotation.NotNull;

public interface ClickEvent extends NBTSerializable {
	
	@NotNull
	public static final NBTCodec<ClickEvent> 
	NBT_CODEC = NBTCodec
					.builder(ClickEvent.class)
					.searchKeyEnumConstructor("action", ClickAction.class, ClickAction::createEvent, ClickEvent::getAction)
					.build();
	
	ClickAction getAction();
	
	@Override
	default NBTCodec<? extends ClickEvent> getNBTCodec() {
		return NBT_CODEC;
	}

}
