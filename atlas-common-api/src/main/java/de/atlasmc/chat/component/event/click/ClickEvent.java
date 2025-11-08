package de.atlasmc.chat.component.event.click;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;

public interface ClickEvent extends NBTSerializable {
	
	public static final NBTCodec<ClickEvent> 
	NBT_HANDLER = NBTCodec
					.builder(ClickEvent.class)
					.searchKeyEnumConstructor("action", ClickAction.class, ClickAction::createEvent, ClickEvent::getAction)
					.build();
	
	ClickAction getAction();
	
	@Override
	default NBTCodec<? extends ClickEvent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
