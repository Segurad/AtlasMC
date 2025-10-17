package de.atlasmc.chat.component.event.click;

import de.atlasmc.util.nbt.codec.NBTCodec;

public final class CopyToClipboardClickEvent implements ClickEvent {
	
	public static final NBTCodec<CopyToClipboardClickEvent>
	NBT_HANDLER = NBTCodec
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
	public NBTCodec<? extends CopyToClipboardClickEvent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
