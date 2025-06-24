package de.atlasmc.chat.component.event.hover;

import java.util.List;
import java.util.function.Supplier;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public enum HoverAction implements EnumName, EnumValueCache {

	SHOW_TEXT("show_text", HoverTextEvent::new),
	SHOW_ITEM("show_item", HoverItemEvent::new),
	SHOW_ENTITY("show_entity", HoverEntityEvent::new);

	private static List<HoverAction> VALUES;
	
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
	
	@Nullable
	public static HoverAction getByName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		final List<HoverAction> values = getValues();
		final int size = values.size();
		for (int i = 0; i < size; i++) {
			HoverAction action = values.get(i);
			if (action.getName().equals(name))
				return action;
		}
		return null;
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	@NotNull
	public static List<HoverAction> getValues() {
		if (VALUES == null) {
			VALUES = List.of(values());
		}
		return VALUES;
	}
	
	/**
	 * Releases the system resources used from the values cache
	 */
	public static void freeValues() {
		VALUES = null;
	}
	
}
