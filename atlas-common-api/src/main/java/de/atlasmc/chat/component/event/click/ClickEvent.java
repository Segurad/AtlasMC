package de.atlasmc.chat.component.event.click;

import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

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
