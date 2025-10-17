package de.atlasmc.chat.component;

import de.atlasmc.util.nbt.codec.NBTCodec;

public class TextComponent extends AbstractBaseComponent<TextComponent> {

	public static final NBTCodec<TextComponent>
	NBT_HANDLER = NBTCodec
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
	public NBTCodec<? extends ChatComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
