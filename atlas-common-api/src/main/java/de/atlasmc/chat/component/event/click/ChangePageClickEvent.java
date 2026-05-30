package de.atlasmc.chat.component.event.click;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public final class ChangePageClickEvent implements ClickEvent {
	
	@NotNull
	public static final NBTCodec<ChangePageClickEvent>
	NBT_HANDLER = NBTCodec
					.builder(ChangePageClickEvent.class)
					.include(ClickEvent.NBT_CODEC)
					.intField("page", ChangePageClickEvent::getPage, ChangePageClickEvent::setPage)
					.build();
	
	private int page;
	
	@Override
	public ClickAction getAction() {
		return ClickAction.CHANGE_PAGE;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	@Override
	public NBTCodec<? extends ChangePageClickEvent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
