package de.atlasmc.chat.component.event.click;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public final class OpenFileClickEvent implements ClickEvent {
	
	public static final NBTSerializationHandler<OpenFileClickEvent>
	NBT_HANDLER = NBTSerializationHandler
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
	public NBTSerializationHandler<? extends OpenFileClickEvent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
