package de.atlasmc.chat.component.event.click;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public final class CopyToClipboardClickEvent implements ClickEvent {
	
	public static final NBTSerializationHandler<CopyToClipboardClickEvent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(CopyToClipboardClickEvent.class)
					.include(ClickEvent.NBT_HANDLER)
					.string("value", CopyToClipboardClickEvent::getValue, CopyToClipboardClickEvent::setValue)
					.build();
	
	private String value;
	
	@Override
	public ClickAction getAction() {
		return ClickAction.COPY_TO_CLIPBOARD;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public NBTSerializationHandler<? extends CopyToClipboardClickEvent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
