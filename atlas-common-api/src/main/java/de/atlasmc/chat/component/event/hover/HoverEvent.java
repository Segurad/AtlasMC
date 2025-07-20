package de.atlasmc.chat.component.event.hover;

import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface HoverEvent extends NBTSerializable {
	
	static final NBTSerializationHandler<HoverEvent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(HoverEvent.class)
					.searchKeyEnumConstructor("action", HoverAction::getByName, HoverAction::createEvent, HoverEvent::getAction)
					.build();
	
	HoverAction getAction();
	
	@Override
	default NBTSerializationHandler<? extends HoverEvent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
