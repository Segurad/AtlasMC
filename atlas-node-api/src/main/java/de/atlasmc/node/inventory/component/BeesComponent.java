package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.node.block.tile.Beehive.Occupant;
import de.atlasmc.node.entity.Bee;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface BeesComponent extends ItemComponent {
	
	public static final NBTCodec<BeesComponent>
	NBT_HANDLER = NBTCodec
					.builder(BeesComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.typeList(ComponentType.BEES.getNamespacedKey(), BeesComponent::hasBees, BeesComponent::getBees, Occupant.NBT_HANDLER)
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
	default NBTCodec<? extends BeesComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
