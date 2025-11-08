package de.atlasmc.chat.component.event.click;

import de.atlasmc.NamespacedKey;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;

public final class CustomClickEvent implements ClickEvent {
	
	public static final NBTCodec<CustomClickEvent>
	NBT_HANDLER = NBTCodec
					.builder(CustomClickEvent.class)
					.include(ClickEvent.NBT_HANDLER)
					.codec("id", CustomClickEvent::getID, CustomClickEvent::setID, NamespacedKey.NBT_CODEC)
					.codec("payload", CustomClickEvent::getPayload, CustomClickEvent::setPayload, NBTCodecs.STRING)
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
