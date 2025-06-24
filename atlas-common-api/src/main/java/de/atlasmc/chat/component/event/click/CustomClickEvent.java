package de.atlasmc.chat.component.event.click;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public final class CustomClickEvent implements ClickEvent {
	
	public static final NBTSerializationHandler<CustomClickEvent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(CustomClickEvent.class)
					.include(ClickEvent.NBT_HANDLER)
					.namespacedKey("id", CustomClickEvent::getID, CustomClickEvent::setID)
					.string("payload", CustomClickEvent::getPayload, CustomClickEvent::setPayload)
					.build();
	
	private NamespacedKey id;
	private String payload;
	
	@Override
	public ClickAction getAction() {
		return ClickAction.CUSTOM;
	}
	
	public NamespacedKey getID() {
		return id;
	}
	
	public void setID(NamespacedKey id) {
		this.id = id;
	}
	
	public String getPayload() {
		return payload;
	}
	
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	@Override
	public NBTSerializationHandler<? extends CustomClickEvent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
