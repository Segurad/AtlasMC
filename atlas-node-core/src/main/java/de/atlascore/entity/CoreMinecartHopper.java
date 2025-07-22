package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.MinecartHopper;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;

public class CoreMinecartHopper extends CoreAbstractMinecartContainer implements MinecartHopper {
	
	private boolean enabled;
	
	public CoreMinecartHopper(EntityType type) {
		super(type);
	}

	@Override
	protected Inventory createInventory() {
		return ContainerFactory.GENERIC_INV_FACTORY.create(InventoryType.HOPPER, this);
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
}
