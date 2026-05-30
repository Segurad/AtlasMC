package de.atlasmc.chat.component.event.click;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.util.annotation.NotNull;

public final class OpenFileClickEvent implements ClickEvent {
	
	@NotNull
	public static final NBTCodec<OpenFileClickEvent>
	NBT_CODEC = NBTCodec
					.builder(OpenFileClickEvent.class)
					.include(ClickEvent.NBT_CODEC)
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
		return NBT_CODEC;
	}

}
