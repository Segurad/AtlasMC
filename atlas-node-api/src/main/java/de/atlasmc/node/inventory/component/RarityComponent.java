package de.atlasmc.node.inventory.component;

import de.atlasmc.IDHolder;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public interface RarityComponent extends ItemComponent {
	
	public static final NBTCodec<RarityComponent>
	NBT_HANDLER = NBTCodec
					.builder(RarityComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.RARITY.getNamespacedKey(), RarityComponent::getRarity, RarityComponent::setRarity, EnumUtil.enumStringNBTCodec(Rarity.class))
					.build();
	
	Rarity getRarity();
	
	void setRarity(Rarity rarity);
	
	@Override
	RarityComponent clone();

	@Override
	default NBTCodec<? extends RarityComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	public static enum Rarity implements IDHolder, EnumName {
		
		COMMON,
		UNCOMMON,
		RARE,
		EPIC;
			
		private String name;
		
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
