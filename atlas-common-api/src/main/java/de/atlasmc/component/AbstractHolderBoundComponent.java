package de.atlasmc.component;

import java.util.Objects;

import de.atlasmc.util.annotation.Nullable;

public abstract class AbstractHolderBoundComponent<T extends ComponentHolder<?>> implements HolderBoundComponent<T> {

	private final ComponentType type;
	private T holder;
	
	public AbstractHolderBoundComponent(ComponentType type) {
		this.type = Objects.requireNonNull(type, "type");
	}
	
	@Override
	public final ComponentType getType() {
		return type;
	}

	@Override
	public final void registerHolder(T holder) {
		if (this.holder != null)
			throw new IllegalStateException("Component is already bound to another holder!");
		this.holder = holder;
		holderChanged(holder);
	}
	
	/**
	 * Called if the holder has changed. Null if the component is unregistered otherwise a valid holder.
	 * @param holder or null
	 */
	protected void holderChanged(@Nullable T holder) {
		// override in child
	}

	@Override
	public final void unregisterHolder() {
		var holder = this.holder;
		if (holder == null)
			return;
		if (this.holder.removeComponent(this))
			this.holder = null;
	}

	@Override
	public final T getHolder() {
		return holder;
	}

}
