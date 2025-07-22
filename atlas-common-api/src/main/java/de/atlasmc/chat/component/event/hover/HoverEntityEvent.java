package de.atlasmc.chat.component.event.hover;

import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class HoverEntityEvent implements HoverEvent {
	
	public static final NBTSerializationHandler<HoverEntityEvent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(HoverEntityEvent.class)
					.include(HoverEvent.NBT_HANDLER)
					.typeCompoundField("name", HoverEntityEvent::getName, HoverEntityEvent::setName, ChatComponent.NBT_HANDLER)
					.namespacedKey("id", HoverEntityEvent::getType, HoverEntityEvent::setType)
					.uuid("uuid", HoverEntityEvent::getUUID, HoverEntityEvent::setUUUID)
					.build();
	
	private ChatComponent name;
	private NamespacedKey type;
	private UUID uuid;

	@Override
	public HoverAction getAction() {
		return HoverAction.SHOW_ENTITY;
	}
	
	public ChatComponent getName() {
		return name;
	}
	
	public void setName(ChatComponent name) {
		this.name = name;
	}
	
	public NamespacedKey getType() {
		return type;
	}
	
	public void setType(NamespacedKey type) {
		this.type = type;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public void setUUUID(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public NBTSerializationHandler<? extends HoverEntityEvent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
