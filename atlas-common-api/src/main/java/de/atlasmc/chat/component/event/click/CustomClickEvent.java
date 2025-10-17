package de.atlasmc.chat.component.event.click;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.codec.NBTCodec;

public final class CustomClickEvent implements ClickEvent {
	
	public static final NBTCodec<CustomClickEvent>
	NBT_HANDLER = NBTCodec
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
	public NBTCodec<? extends CustomClickEvent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
