package de.atlasmc.node.inventory.component;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface MaxDamageComponent extends ItemComponent {
	
	public static final NBTCodec<MaxDamageComponent>
	NBT_HANDLER = NBTCodec
					.builder(MaxDamageComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.intField(ComponentType.MAX_DAMAGE.getNamespacedKey(), MaxDamageComponent::getMaxDamage, MaxDamageComponent::setMaxDamage, 0)
					.build();
	
	int getMaxDamage();
	
	void setMaxDamage(int damage);
	
	MaxDamageComponent clone();
	
	@Override
	default NBTCodec<? extends MaxDamageComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
