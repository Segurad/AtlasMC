package de.atlasmc.chat.component.event.click;

import de.atlasmc.util.nbt.codec.NBTCodec;

public final class ChangePageClickEvent implements ClickEvent {
	
	public static final NBTCodec<ChangePageClickEvent>
	NBT_HANDLER = NBTCodec
					.builder(ChangePageClickEvent.class)
					.include(ClickEvent.NBT_HANDLER)
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
