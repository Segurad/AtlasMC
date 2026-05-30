package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface MaxDamageComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<MaxDamageComponent>
	NBT_CODEC = NBTCodec
					.builder(MaxDamageComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.intField(ComponentType.MAX_DAMAGE.getNamespacedKey(), MaxDamageComponent::getMaxDamage, MaxDamageComponent::setMaxDamage, 0)
					.build();
	
	int getMaxDamage();
	
	void setMaxDamage(int damage);
	
	@Override
	MaxDamageComponent clone();
	
	@Override
	default NBTCodec<? extends MaxDamageComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
