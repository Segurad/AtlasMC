package de.atlasmc.chat.component.event.click;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.util.annotation.NotNull;

public final class OpenUrlClickEvent implements ClickEvent {
	
	@NotNull
	public static final NBTCodec<OpenUrlClickEvent>
	NBT_CODEC = NBTCodec
					.builder(OpenUrlClickEvent.class)
					.include(ClickEvent.NBT_CODEC)
					.codec("url", OpenUrlClickEvent::getURL, OpenUrlClickEvent::setURL, NBTCodecs.STRING)
					.build();
	
	private String url;
	
	@Override
	public ClickAction getAction() {
		return ClickAction.OPEN_URL;
	}
	
	public String getURL() {
		return url;
	}
	
	public void setURL(String url) {
		this.url = url;
	}
	
	@Override
	public NBTCodec<? extends OpenUrlClickEvent> getNBTCodec() {
		return NBT_CODEC;
	}

}
