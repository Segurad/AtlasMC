package de.atlasmc.node.world.positionsource;

import java.util.Objects;
import java.util.function.Supplier;

import de.atlasmc.IDHolder;
import de.atlasmc.util.enums.EnumName;

public enum PositionSourceType implements EnumName, IDHolder {
	
	BLOCK(BlockPositionSource::new),
	ENTITY(EntityPositionSource::new);
	
	private final String name;
	private final Supplier<PositionSource> constructor;
	
	private PositionSourceType(Supplier<PositionSource> constructor) {
		this.name = name().toLowerCase().intern();
		this.constructor = Objects.requireNonNull(constructor);
	}
	
	public PositionSource createSource() {
		return constructor.get();
	}
	
	@Override
	public int getID() {
		return ordinal();
	}

	@Override
	public String getName() {
		return name;
	}

}
