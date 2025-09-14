package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.RepairCostComponent;

public class CoreRepairCostComponent extends AbstractItemComponent implements RepairCostComponent {

	private int repairCost;
	
	public CoreRepairCostComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreRepairCostComponent clone() {
		return (CoreRepairCostComponent) super.clone();
	}

	@Override
	public int getRepairCost() {
		return repairCost;
	}

	@Override
	public void setRepairCost(int cost) {
		this.repairCost = cost;
	}

}
