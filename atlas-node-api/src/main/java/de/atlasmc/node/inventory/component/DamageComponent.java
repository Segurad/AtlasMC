package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;

public interface DamageComponent extends ItemComponent {
	
	public static final NBTCodec<DamageComponent> 
	NBT_HANDLER = NBTCodec
			.builder(DamageComponent.class)
			.include(ItemComponent.NBT_CODEC)
			.intField(ComponentType.DAMAGE.getNamespacedKey(), DamageComponent::getDamage, DamageComponent::setDamage)
			.build();
	
	public static final StreamCodec<DamageComponent>
	STREAM_CODEC = StreamCodec
					.builder(DamageComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.varInt(DamageComponent::getDamage, DamageComponent::setDamage)
					.build();
	
	int getDamage();
	
	void setDamage(int damage);
	
	DamageComponent clone();
	
	@Override
	default NBTCodec<DamageComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	@Override
	default StreamCodec<? extends DamageComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
