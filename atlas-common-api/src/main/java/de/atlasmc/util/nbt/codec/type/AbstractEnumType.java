package de.atlasmc.util.nbt.codec.type;

import java.util.Objects;

public abstract class AbstractEnumType<V, E extends Enum<E>> extends ObjectType<V> {
	
	protected final Class<E> clazz;
	
	public AbstractEnumType(Class<E> clazz) {
		this.clazz = Objects.requireNonNull(clazz);
	}

}
