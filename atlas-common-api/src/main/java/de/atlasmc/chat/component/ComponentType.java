package de.atlasmc.chat.component;

import java.util.function.Supplier;

import de.atlasmc.IDHolder;
import de.atlasmc.util.enums.EnumName;

public enum ComponentType implements EnumName, IDHolder {
	
	TEXT("text", TextComponent::new),
	TRANSLATABLE("translatable", TranslationComponent::new),
	SCORE("score", ScoreComponent::new),
	SELECTOR("selector", SelectorComponent::new),
	KEYBIND("keybind", KeybindComponent::new),
	NBT("nbt", NBTComponent::new);
	
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
	
	@Override
	public int getID() {
		return ordinal();
	}

}
