package de.atlasmc.chat.component;

import de.atlasmc.util.nbt.codec.NBTCodec;

public class SelectorComponent extends AbstractBaseComponent<SelectorComponent> {
	
	public static final NBTCodec<SelectorComponent>
	NBT_HANDLER = NBTCodec
					.builder(SelectorComponent.class)
					.include(AbstractBaseComponent.NBT_CODEC)
					.string("selector", SelectorComponent::getSelector, SelectorComponent::setSelector)
					.typeCompoundField("separator", SelectorComponent::getSeparator, SelectorComponent::setSeparator, ChatComponent.NBT_CODEC)
					.build();
	
	private String selector;
	private ChatComponent separator;
	
	public SelectorComponent() {}
	
	public SelectorComponent(String selector, ChatComponent separator) {
		this.selector = selector;
		this.separator = separator;
	}
	
	public void setSelector(String selector) {
		this.selector = selector;
	}
	
	public String getSelector() {
		return selector;
	}
	
	public ChatComponent getSeparator() {
		return separator;
	}
	
	public void setSeparator(ChatComponent separator) {
		this.separator = separator;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.SELECTOR;
	}
	
	@Override
	protected SelectorComponent getThis() {
		return this;
	}

}
