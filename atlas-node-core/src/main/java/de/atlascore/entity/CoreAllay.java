package de.atlascore.entity;

import de.atlasmc.entity.Allay;
import de.atlasmc.entity.EntityType;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;

public class CoreAllay extends CoreMob implements Allay {
	
	private Inventory inv;
	private boolean canDuplicate;
	private long duplicationCooldown;
	
	public CoreAllay(EntityType type) {
		super(type);
	}

	@Override
	public Inventory getInventory() {
		if (inv == null)
			inv = InventoryType.GENERIC_9X1.create(this);
		return inv;
	}
	
	@Override
	public boolean hasInventory() {
		return inv != null;
	}

	@Override
	public boolean canDuplicate() {
		return canDuplicate;
	}

	@Override
	public void setCanDuplicate(boolean canDuplicate) {
		this.canDuplicate = canDuplicate;
	}

	@Override
	public long getDuplicationCooldown() {
		return duplicationCooldown;
	}

	@Override
	public void setDuplicationCooldown(long cooldown) {
		this.duplicationCooldown = cooldown;
	}

}
