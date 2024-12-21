package de.atlasmc.block.data.property;

import de.atlasmc.util.nbt.TagType;

public abstract class AbstractEnumProperty<T extends Enum<?>> extends BlockDataProperty<T> {

	public AbstractEnumProperty(String key) {
		super(key);
	}

	@Override
	public TagType getType() {
		return TagType.STRING;
	}

}
