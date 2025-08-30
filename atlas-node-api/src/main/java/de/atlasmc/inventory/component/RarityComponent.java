package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.util.AtlasEnum;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface RarityComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<RarityComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(RarityComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.enumStringField(ComponentType.RARITY.getNamespacedKey(), RarityComponent::getRarity, RarityComponent::setRarity, Rarity::getByName, null)
					.build();
	
	Rarity getRarity();
	
	void setRarity(Rarity rarity);
	
	RarityComponent clone();

	@Override
	default NBTSerializationHandler<? extends RarityComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Rarity implements AtlasEnum {
		
		COMMON,
		UNCOMMON,
		RARE,
		EPIC;
		
		private static List<Rarity> VALUES;
		
		private String name;
		
		private Rarity() {
			String name = name().toLowerCase();
			this.name = name.intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public static Rarity getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Rarity> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Rarity value = values.get(i);
				if (value.name.equals(name))
					return value;
			}
			return null;
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		public static Rarity getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Rarity> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}

		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}

}
