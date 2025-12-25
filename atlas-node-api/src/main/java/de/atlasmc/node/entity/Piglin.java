package de.atlasmc.node.entity;

import java.util.List;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.ItemStack;

public interface Piglin extends AbstractPiglin {
	
	public static final NBTCodec<Piglin>
	NBT_HANDLER = NBTCodec
					.builder(Piglin.class)
					.boolField("CannotHunt", Piglin::cannotHunt, Piglin::setCannotHunt, false)
					.codecList("Inventory", Piglin::hasPocketItems, Piglin::getPocketItems, ItemStack.NBT_CODEC)
					.boolField("IsBaby", Piglin::isBaby, Piglin::setBaby, false)
					// non standard
					.boolField("IsDancing", Piglin::isDancing, Piglin::setDancing, false)
					.boolField("IsChargingCrossbow", Piglin::isChargingCrossbow, Piglin::setChargingCorssbow, false)
					.build();
	
	boolean isBaby();
	
	void setBaby(boolean baby);
	
	boolean isChargingCrossbow();
	
	void setChargingCorssbow(boolean charging);
	
	boolean isDancing();

	void setDancing(boolean dancing);

	void setCannotHunt(boolean hunt);
	
	boolean cannotHunt();
	
	List<ItemStack> getPocketItems();
	
	boolean hasPocketItems();
	
	void addPocketItem(ItemStack item);
	
	void removePocketItem(ItemStack item);
	
	@Override
	default NBTCodec<? extends Piglin> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
