package de.atlasmc.chat.component.event.click;

import de.atlasmc.util.nbt.codec.NBTCodec;

public final class OpenFileClickEvent implements ClickEvent {
	
	public static final NBTCodec<OpenFileClickEvent>
	NBT_HANDLER = NBTCodec
					.builder(OpenFileClickEvent.class)
					.include(ClickEvent.NBT_HANDLER)
					.string("path", OpenFileClickEvent::getPath, OpenFileClickEvent::setPath)
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
