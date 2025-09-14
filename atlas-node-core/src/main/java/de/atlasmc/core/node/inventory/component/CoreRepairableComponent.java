package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.ItemType;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.RepairableComponent;
import de.atlasmc.util.dataset.DataSet;

public class CoreRepairableComponent extends AbstractItemComponent implements RepairableComponent {

	private DataSet<ItemType> items;
	
	public CoreRepairableComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreRepairableComponent clone() {
		return (CoreRepairableComponent) super.clone();
	}

	@Override
	public DataSet<ItemType> getItems() {
		return items;
	}

	@Override
	public void setItems(DataSet<ItemType> items) {
		this.items = items;
	}

}
