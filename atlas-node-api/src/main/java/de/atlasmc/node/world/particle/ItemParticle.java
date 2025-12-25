package de.atlasmc.node.world.particle;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.ItemStack;

public class ItemParticle extends AbstractParticle {

	public static final NBTCodec<ItemParticle>
	NBT_CODEC = NBTCodec
				.builder(ItemParticle.class)
				.include(AbstractParticle.NBT_CODEC)
				.codec("item", ItemParticle::getItem, ItemParticle::setItem, ItemStack.NBT_CODEC)
				.build();
	
	public static final StreamCodec<ItemParticle>
	STREAM_CODEC = StreamCodec
					.builder(ItemParticle.class)
					.include(AbstractParticle.STREAM_CODEC)
					.codec(ItemParticle::getItem, ItemParticle::setItem, ItemStack.STREAM_CODEC)
					.build();
	
	private ItemStack item;
	
	public ItemParticle(ParticleType type) {
		super(type);
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public void setItem(ItemStack item) {
		this.item = item;
	}
	
	@Override
	public StreamCodec<? extends ItemParticle> getStreamCodec() {
		return STREAM_CODEC;
	}
	
	@Override
	public NBTCodec<? extends ItemParticle> getNBTCodec() {
		return NBT_CODEC;
	}

}
