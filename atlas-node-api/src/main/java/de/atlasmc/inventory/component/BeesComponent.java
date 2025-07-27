package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.block.tile.Beehive.Occupant;
import de.atlasmc.entity.Bee;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface BeesComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:bees");
	
	public static final NBTSerializationHandler<BeesComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(BeesComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.typeList(COMPONENT_KEY, BeesComponent::hasBees, BeesComponent::getBees, Occupant.NBT_HANDLER)
					.build();
	
	BeesComponent clone();
	
	boolean hasBees();
	
	/**
	 * Returns a List containing all {@link Bee}s currently in this hive
	 * @return list of Bees
	 */
	List<Occupant> getBees();
	
	void removeBee(Bee bee);
	
	void addBee(Bee bee);
	
	int getBeeCount();
	
	@Override
	default NBTSerializationHandler<? extends BeesComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
