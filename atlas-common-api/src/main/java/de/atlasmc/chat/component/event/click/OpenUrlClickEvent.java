package de.atlasmc.chat.component.event.click;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public final class OpenUrlClickEvent implements ClickEvent {
	
	public static final NBTSerializationHandler<OpenUrlClickEvent>
	NBT_HANDLER = NBTSerializationHandler
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
	public NBTSerializationHandler<? extends OpenUrlClickEvent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
