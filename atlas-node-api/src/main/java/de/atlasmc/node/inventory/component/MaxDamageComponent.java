package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.PrimitiveFieldCodecs;
import de.atlasmc.util.annotation.NotNull;

public interface MaxDamageComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<MaxDamageComponent>
	NBT_CODEC = PrimitiveFieldCodecs
				.buildInt(MaxDamageComponent.class)
				.include(ItemComponent.NBT_CODEC)
				.getter(MaxDamageComponent::getMaxDamage)
				.setter(MaxDamageComponent::setMaxDamage)
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
