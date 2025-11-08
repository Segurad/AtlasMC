package de.atlasmc.core.node.inventory.component;

import de.atlasmc.nbt.tag.NBT;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.CustomDataComponent;

public class CoreCustomDataComponent extends AbstractItemComponent implements CustomDataComponent {

	private NBT data;
	
	public CoreCustomDataComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreCustomDataComponent clone() {
		CoreCustomDataComponent clone = (CoreCustomDataComponent) super.clone();
		if (data != null)
			clone.data = data.clone();
		return clone;
	}

	@Override
	public NBT getData() {
		return data;
	}

	@Override
	public void setData(NBT data) {
		this.data = data;
	}

}
