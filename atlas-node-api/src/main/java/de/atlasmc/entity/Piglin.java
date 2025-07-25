package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Piglin extends AbstractPiglin {
	
	public static final NBTSerializationHandler<Piglin>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Piglin.class)
					.boolField("CannotHunt", Piglin::cannotHunt, Piglin::setCannotHunt, false)
					.typeList("Inventory", Piglin::hasPocketItems, Piglin::getPocketItems, ItemStack.NBT_HANDLER)
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
	default NBTSerializationHandler<? extends Piglin> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
