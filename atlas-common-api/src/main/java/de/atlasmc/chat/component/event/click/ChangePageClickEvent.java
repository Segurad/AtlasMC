package de.atlasmc.chat.component.event.click;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public final class ChangePageClickEvent implements ClickEvent {
	
	public static final NBTSerializationHandler<ChangePageClickEvent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ChangePageClickEvent.class)
					.include(ClickEvent.NBT_HANDLER)
					.intTag("page", ChangePageClickEvent::getPage, ChangePageClickEvent::setPage)
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
	public NBTSerializationHandler<? extends ChangePageClickEvent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
