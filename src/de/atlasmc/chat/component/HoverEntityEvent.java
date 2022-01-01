package de.atlasmc.chat.component;

import de.atlasmc.entity.Entity;

public class HoverEntityEvent implements HoverEvent {

	private final Entity entity;
	
	public HoverEntityEvent(Entity entity) {
		this.entity = entity;
	}
	
	@Override
	public String getValue() {
		return null; // TODO to entity nbt json
	}

	@Override
	public HoverAction getAction() {
		return HoverAction.SHOW_ENTITY;
	}

}
