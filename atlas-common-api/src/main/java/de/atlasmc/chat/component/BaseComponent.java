package de.atlasmc.chat.component;

public class BaseComponent extends AbstractBaseComponent<BaseComponent> implements ChatComponent {

	@Override
	protected BaseComponent getThis() {
		return this;
	}

	@Override
	public ComponentType getType() {
		return null;
	}
		
}
