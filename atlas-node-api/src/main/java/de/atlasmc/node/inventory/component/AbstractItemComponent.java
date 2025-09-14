package de.atlasmc.node.inventory.component;

import java.io.IOException;

import de.atlasmc.util.CloneException;
import io.netty.buffer.ByteBuf;

public abstract class AbstractItemComponent implements ItemComponent {

	protected final ComponentType type;
	
	public AbstractItemComponent(ComponentType type) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		this.type = type;
	}
	
	@Override
	public ComponentType getType() {
		return type;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		// not required for custom components
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		// not required for custom components	
	}
	
	public AbstractItemComponent clone() {
		try {
			return (AbstractItemComponent) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new CloneException(e);
		}
	}

}
