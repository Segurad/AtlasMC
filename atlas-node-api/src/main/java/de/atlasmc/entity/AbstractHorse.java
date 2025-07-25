package de.atlasmc.entity;

import java.util.UUID;

import de.atlasmc.inventory.AbstractHorseInventory;
import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface AbstractHorse extends Animal, InventoryHolder {
	
	public static final NBTSerializationHandler<AbstractHorse>
	NBT_HANDLER = NBTSerializationHandler
					.builder(AbstractHorse.class)
					.include(Animal.NBT_HANDLER)
					.boolField("Bred", AbstractHorse::canBred, AbstractHorse::setCanBred, false)
					.boolField("EatingHaystack", AbstractHorse::isEating, AbstractHorse::setEating, false)
					.uuid("Owner", AbstractHorse::getOwner, AbstractHorse::setOwner)
					.boolField("Tame", AbstractHorse::isTamed, AbstractHorse::setTamed, false)
					.intField("Temper", AbstractHorse::getTemper, AbstractHorse::setTemper, 0)
					.build();
	
	int getTemper();
	
	void setTemper(int temper);
	
	boolean isTamed();
	
	void setTamed(boolean tamed);
	
	boolean isSaddled();
	
	void setSaddled(boolean saddled);
	
	boolean canBred();
	
	void setCanBred(boolean bred);
	
	boolean isEating();
	
	void setEating(boolean eating);
	
	boolean isRearing();
	
	void setRearing(boolean rearing);
	
	boolean isMouthOpen();
	
	void setMouthOpen(boolean open);
	
	UUID getOwner();

	void setOwner(UUID owner);
	
	/**
	 * May not return the same Inventory every time e.g. {@link ChestedHorse} may return a new Inventory if the has chest value is changed
	 * @return inventory
	 */
	AbstractHorseInventory getInventory();
	
	@Override
	default NBTSerializationHandler<? extends AbstractHorse> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
