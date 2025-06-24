package de.atlasmc.chat.component;

import java.util.List;
import java.util.function.Supplier;

import de.atlasmc.chat.component.event.click.ClickAction;
import de.atlasmc.util.EnumName;

public enum ComponentType implements EnumName {
	
	TEXT("text", TextComponent::new),
	TRANSLATABLE("translatable", TranslationComponent::new),
	SCORE("score", ScoreComponent::new),
	SELECTOR("selector", SelectorComponent::new),
	KEYBIND("keybind", KeybindComponent::new),
	NBT("nbt", NBTComponent::new);
	
	private static List<ComponentType> VALUES;
	
	private final Supplier<ChatComponent> constructor;
	private final String name;
	
	private ComponentType(String name, Supplier<ChatComponent> constructor) {
		this.name = name;
		this.constructor = constructor;
	}
	
	/**
	 * Returns the lower case name
	 * @return name
	 */
	@Override
	public String getName() {
		return name;
	}
	
	public ChatComponent createComponent() {
		return constructor.get();
	}
	
	public static ComponentType getByNameID(String name) {
		final List<ComponentType> values = getValues();
		final int size = values.size();
		for (int i = 0; i < size; i++) {
			ComponentType action = values.get(i);
			if (action.getName().equals(name))
				return action;
		}
		return null;
	}
	
	public int getID() {
		return ordinal();
	}
	
	public static ComponentType getByID(int id) {
		return getValues().get(id);
	}
	
	/**
	 * Returns a immutable List of all Types.<br>
	 * This method avoid allocation of a new array not like {@link #values()}.
	 * @return list
	 */
	public static List<ComponentType> getValues() {
		if (VALUES == null) {
			synchronized (ClickAction.class) {
				if (VALUES == null)
					VALUES = List.of(values());
			}
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
