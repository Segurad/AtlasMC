package de.atlasmc.chat.component.event.hover;

import java.util.function.Supplier;

import de.atlasmc.util.EnumName;

public enum HoverAction implements EnumName {

	SHOW_TEXT("show_text", HoverTextEvent::new),
	SHOW_ITEM("show_item", HoverItemEvent::new),
	SHOW_ENTITY("show_entity", HoverEntityEvent::new);

	private final Supplier<HoverEvent> constructor;
	private final String name;
	
	private HoverAction(String name, Supplier<HoverEvent> constructor) {
		this.name = name;
		this.constructor = constructor;
	}
	
	public HoverEvent createEvent() {
		return constructor.get();
	}
	
	/**
	 * Returns the lower case name
	 * @return name
	 */
	@Override
	public String getName() {
		return name;
	}
	
}
