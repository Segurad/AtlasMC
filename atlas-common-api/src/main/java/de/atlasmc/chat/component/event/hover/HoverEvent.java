package de.atlasmc.chat.component.event.hover;

import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface HoverEvent extends NBTSerializable {
	
	static final NBTCodec<HoverEvent>
	NBT_HANDLER = NBTCodec
					.builder(HoverEvent.class)
					.searchKeyEnumConstructor("action", HoverAction.class, HoverAction::createEvent, HoverEvent::getAction)
					.build();
	
	HoverAction getAction();
	
	@Override
	default NBTCodec<? extends HoverEvent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
