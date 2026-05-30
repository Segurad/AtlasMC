package de.atlasmc.core.node.block.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.util.CloneException;
import de.atlasmc.util.annotation.NotNull;

public class CoreBlockData implements BlockData {
	
	protected static final List<PropertyType<?>> PROPERTIES = List.of();
	
	protected final BlockType type;
	
	public CoreBlockData(BlockType type) {
		this.type = Objects.requireNonNull(type, "type");
	}
	
	@Override
	public BlockData clone() {
		try {
			return (BlockData) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new CloneException(e);
		}
	}

	@Override
	public BlockType getType() {
		return type;
	}

	@Override
	public int getStateID() {
		return type.getBlockStateID();
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}
	
	@Override
	public int hashCode() {
		int hash = 31;
		hash = 31 * hash + type.hashCode();
		hash = 31 * hash + getStateID();
		return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BlockData data)
			return data.getType() == type && getStateID() == data.getStateID();
		return false;
	}
	
	@Override
	public PropertyType<?> getProperty(CharSequence key) {
		var properties = getProperties();
		for (var property : properties) {
			if (property.getKey().equals(key))
				return property;
		}
		return null;
	}
	
	/**
	 * Merges a property list with the given properties and returns a immutable copy
	 * @param parent the parent property list
	 * @param values the properties to add
	 * @return list
	 */
	@NotNull
	protected static List<PropertyType<?>> merge(List<PropertyType<?>> parent, PropertyType<?>... values) {
		ArrayList<PropertyType<?>> newList = new ArrayList<>(parent.size() + values.length);
		newList.addAll(parent);
		for (PropertyType<?> property : values) {
			if (newList.contains(property))
				continue;
			newList.add(property);
		}
		return List.copyOf(newList);
	}

}
