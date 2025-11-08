package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.tile.Beehive.Occupant;
import de.atlasmc.node.entity.Bee;

public interface BeesComponent extends ItemComponent {
	
	public static final NBTCodec<BeesComponent>
	NBT_CODEC = NBTCodec
					.builder(BeesComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codecList(ComponentType.BEES.getNamespacedKey(), BeesComponent::hasBees, BeesComponent::getBees, Occupant.NBT_CODEC)
					.build();
	
	public static final StreamCodec<BeesComponent>
	STREAM_CODEC = StreamCodec
					.builder(BeesComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.listCodec(BeesComponent::hasBees, BeesComponent::getBees, Occupant.STREAM_CODEC)
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
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends BeesComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
