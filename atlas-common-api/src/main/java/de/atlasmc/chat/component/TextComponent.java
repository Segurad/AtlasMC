package de.atlasmc.chat.component;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class TextComponent extends AbstractBaseComponent<TextComponent> {

	public static final NBTSerializationHandler<TextComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(TextComponent.class)
					.include(AbstractBaseComponent.NBT_HANDLER)
					.string("text", TextComponent::getValue, TextComponent::setValue)
					.build();
	
	private String text;
	
	public TextComponent(String text) {
		this.text = text;
	}

	public TextComponent() {}
	
	@Override
	public ComponentType getType() {
		return ComponentType.TEXT;
	}

	public TextComponent setValue(String text) {
		this.text = text;
		return this;
	}
	
	public String getValue() {
		return text;
	}

	@Override
	protected TextComponent getThis() {
		return this;
	}
	
	@Override
	public NBTSerializationHandler<? extends ChatComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
