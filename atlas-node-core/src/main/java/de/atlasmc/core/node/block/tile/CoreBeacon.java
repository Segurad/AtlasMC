package de.atlasmc.core.node.block.tile;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.tile.Beacon;
import de.atlasmc.node.inventory.BeaconInventory;
import de.atlasmc.node.inventory.ContainerFactory;
import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.node.potion.PotionEffectType;

public class CoreBeacon extends CoreAbstractContainerTile<BeaconInventory> implements Beacon {

	public CoreBeacon(BlockType type) {
		super(type);
	}
	
	@Override
	protected BeaconInventory createInventory() {
		return ContainerFactory.BEACON_INV_FACTORY.create(InventoryType.BEACON, this);
	}

	@Override
	public PotionEffectType getPrimaryEffectType() {
		if (inv == null) 
			return null;
		return getInventory().getPrimaryEffectType();
	}

	@Override
	public void setPrimaryEffectType(PotionEffectType effect) {
		getInventory().setPrimaryEffectType(effect);
	}

	@Override
	public PotionEffectType getSecondaryEffectType() {
		if (inv == null) 
			return null;
		return getInventory().getSecondaryEffectType();
	}

	@Override
	public void setSecondaryEffectType(PotionEffectType effect) {
		getInventory().setSecondaryEffectType(effect);
	}
	
}
