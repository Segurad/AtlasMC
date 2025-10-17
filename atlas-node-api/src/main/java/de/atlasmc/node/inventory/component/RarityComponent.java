package de.atlasmc.node.inventory.component;

import de.atlasmc.IDHolder;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface RarityComponent extends ItemComponent {
	
	public static final NBTCodec<RarityComponent>
	NBT_HANDLER = NBTCodec
					.builder(RarityComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.enumStringField(ComponentType.RARITY.getNamespacedKey(), RarityComponent::getRarity, RarityComponent::setRarity, Rarity.class, null)
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
