package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ItemDisplay extends Display {
	
	public static final NBTSerializationHandler<ItemDisplay>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ItemDisplay.class)
					.include(Display.NBT_HANDLER)
					.typeCompoundField("item", ItemDisplay::getItem, ItemDisplay::setItem, ItemStack.NBT_HANDLER)
					.enumStringField("item_display", ItemDisplay::getRenderType, ItemDisplay::setRenderType, RenderType::getByName, RenderType.NONE)
					.build();
	
	ItemStack getItem();
	
	void setItem(ItemStack item);
	
	RenderType getRenderType();
	
	void setRenderType(RenderType renderType);
	
	@Override
	default NBTSerializationHandler<? extends ItemDisplay> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum RenderType implements EnumName, EnumValueCache {
		
		NONE,
		THIRDPERSON_LEFT_HAND,
		THIRDPERSON_RIGHT_HAND,
		FIRSTPERSON_LEFT_HAND,
		FIRSTPERSON_RIGHT_HAND,
		HEAD,
		GUI,
		GROUND,
		FIXED;
		
		private static List<RenderType> VALUES;
		
		private String name;
		
		private RenderType() {
			name = name().toLowerCase();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public static RenderType getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			final List<RenderType> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				RenderType value = values.get(i);
				if (value.name.equals(name))
					return value;
			}
			throw new IllegalArgumentException("No value with name found: " + name);
		}
		
		public int getID() {
			return ordinal();
		}
		
		public static RenderType getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<RenderType> getValues() {
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
