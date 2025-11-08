package de.atlasmc.core.node.inventory.component;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.inventory.component.AbstractBlockDataComponent;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;

public class CoreAbstractBlockDataComponent extends AbstractItemComponent implements AbstractBlockDataComponent {

	private Map<String, String> properties;
	
	public CoreAbstractBlockDataComponent(ComponentType type) {
		super(type);
	}

	@Override
	public CoreAbstractBlockDataComponent clone() {
		CoreAbstractBlockDataComponent clone = (CoreAbstractBlockDataComponent) super.clone();
		if (clone == null)
			return null;
		if (properties != null) {
			clone.properties = new HashMap<>(properties);
		}
		return clone;
	}

	@Override
	public Map<String, String> getProperties() {
		if (properties == null)
			properties = new HashMap<>(5);
		return properties;
	}

	@Override
	public boolean hasProperties() {
		return properties != null && !properties.isEmpty();
	}

	@Override
	public void applyTo(BlockData data) {
		if (data == null)
			throw new IllegalArgumentException("Data can not be null!");
		if (!hasProperties())
			return;
		for (Entry<String, String> value : properties.entrySet()) {
			@SuppressWarnings("unchecked")
			PropertyType<Object> property = (PropertyType<Object>) data.getProperty(value.getKey());
			property.set(data, property.fromString(value.getValue()));
		}
	}

}
