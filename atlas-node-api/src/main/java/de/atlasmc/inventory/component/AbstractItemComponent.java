package de.atlasmc.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import io.netty.buffer.ByteBuf;

public abstract class AbstractItemComponent implements ItemComponent {

	protected final NamespacedKey key;
	
	public AbstractItemComponent(NamespacedKey key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		this.key = key;
	}
	
	@Override
	public boolean isServerOnly() {
		return true;
	}
	
	@Override
	public ComponentType getType() {
		return null;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		// not required for custom components
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		// not required for custom components	
	}

	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}
	
	public AbstractItemComponent clone() {
		try {
			return (AbstractItemComponent) super.clone();
		} catch (CloneNotSupportedException e) {}
		return null;
	}

}
