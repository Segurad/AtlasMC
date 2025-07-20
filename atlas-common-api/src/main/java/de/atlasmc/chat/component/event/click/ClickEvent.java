package de.atlasmc.chat.component.event.click;

import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ClickEvent extends NBTSerializable {
	
	public static final NBTSerializationHandler<ClickEvent> 
	NBT_HANDLER = NBTSerializationHandler
					.builder(ClickEvent.class)
					.searchKeyEnumConstructor("action", ClickAction::getByName, ClickAction::createEvent, ClickEvent::getAction)
					.build();
	
	ClickAction getAction();
	
	@Override
	default NBTSerializationHandler<? extends ClickEvent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
