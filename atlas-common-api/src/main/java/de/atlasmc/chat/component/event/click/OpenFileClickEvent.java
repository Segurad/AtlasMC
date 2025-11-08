package de.atlasmc.chat.component.event.click;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;

public final class OpenFileClickEvent implements ClickEvent {
	
	public static final NBTCodec<OpenFileClickEvent>
	NBT_HANDLER = NBTCodec
					.builder(OpenFileClickEvent.class)
					.include(ClickEvent.NBT_HANDLER)
					.codec("path", OpenFileClickEvent::getPath, OpenFileClickEvent::setPath, NBTCodecs.STRING)
					.build();
	
	private String path;
	
	@Override
	public ClickAction getAction() {
		return ClickAction.OPEN_FILE;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public NBTCodec<? extends OpenFileClickEvent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
