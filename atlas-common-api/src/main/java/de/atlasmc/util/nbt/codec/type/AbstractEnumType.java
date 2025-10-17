package de.atlasmc.util.nbt.codec.type;

import java.util.Objects;

public abstract class AbstractEnumType<V extends Enum<V>> extends ObjectType<V> {
	
	protected final Class<V> clazz;
	
	public AbstractEnumType(Class<V> clazz) {
		this.clazz = Objects.requireNonNull(clazz);
	}

}
