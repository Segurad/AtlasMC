package de.atlasmc.node.inventory.component;

import de.atlasmc.IDHolder;
import de.atlasmc.nbt.codec.CodecFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public interface RarityComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<RarityComponent>
	NBT_CODEC = CodecFieldCodec
					.builder(RarityComponent.class, EnumUtil.enumStringNBTCodec(Rarity.class))
					.include(ItemComponent.NBT_CODEC)
					.getter(RarityComponent::getRarity)
					.setter(RarityComponent::setRarity)
					.build();
	
	Rarity getRarity();
	
	void setRarity(Rarity rarity);
	
	@Override
	RarityComponent clone();

	@Override
	default NBTCodec<? extends RarityComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum Rarity implements IDHolder, EnumName {
		
		COMMON,
		UNCOMMON,
		RARE,
		EPIC;
			
		private final String name;
		
		private Rarity() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}

}
