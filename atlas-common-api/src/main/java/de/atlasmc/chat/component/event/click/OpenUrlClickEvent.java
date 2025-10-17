package de.atlasmc.chat.component.event.click;

import de.atlasmc.util.nbt.codec.NBTCodec;

public final class OpenUrlClickEvent implements ClickEvent {
	
	public static final NBTCodec<OpenUrlClickEvent>
	NBT_HANDLER = NBTCodec
					.builder(OpenUrlClickEvent.class)
					.include(ClickEvent.NBT_HANDLER)
					.string("url", OpenUrlClickEvent::getURL, OpenUrlClickEvent::setURL)
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
		return NBT_HANDLER;
	}

}
