package de.atlasmc.component;

import java.util.Objects;

public abstract class AbstractHolderBoundComponent<T extends ComponentHolder<?>> implements HolderBoundComponent<T> {

	private final ComponentType type;
	private T holder;
	
	public AbstractHolderBoundComponent(ComponentType type) {
		this.type = Objects.requireNonNull(type, "type");
	}
	
	@Override
	public ComponentType getType() {
		return type;
	}

	@Override
	public void registerHolder(T holder) {
		if (this.holder != null)
			throw new IllegalStateException("Component is already bound to another holder!");
		this.holder = holder;
	}

	@Override
	public void unregisterHolder() {
		var holder = this.holder;
		if (holder == null)
			return;
		if (this.holder.removeComponent(this))
			this.holder = null;
	}

	@Override
	public T getHolder() {
		return holder;
	}

}
