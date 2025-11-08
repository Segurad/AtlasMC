package de.atlasmc.node.entity;

import java.util.UUID;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.node.inventory.AbstractHorseInventory;
import de.atlasmc.node.inventory.InventoryHolder;

public interface AbstractHorse extends Animal, InventoryHolder {
	
	public static final NBTCodec<AbstractHorse>
	NBT_HANDLER = NBTCodec
					.builder(AbstractHorse.class)
					.include(Animal.NBT_CODEC)
					.boolField("Bred", AbstractHorse::canBred, AbstractHorse::setCanBred, false)
					.boolField("EatingHaystack", AbstractHorse::isEating, AbstractHorse::setEating, false)
					.codec("Owner", AbstractHorse::getOwner, AbstractHorse::setOwner, NBTCodecs.UUID_CODEC)
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
	default NBTCodec<? extends AbstractHorse> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
