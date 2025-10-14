package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ItemDisplay extends Display {
	
	public static final NBTSerializationHandler<ItemDisplay>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ItemDisplay.class)
					.include(Display.NBT_HANDLER)
					.typeCompoundField("item", ItemDisplay::getItem, ItemDisplay::setItem, ItemStack.NBT_HANDLER)
					.enumStringField("item_display", ItemDisplay::getRenderType, ItemDisplay::setRenderType, RenderType.class, RenderType.NONE)
					.build();
	
	ItemStack getItem();
	
	void setItem(ItemStack item);
	
	RenderType getRenderType();
	
	void setRenderType(RenderType renderType);
	
	@Override
	default NBTSerializationHandler<? extends ItemDisplay> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum RenderType implements EnumName, IDHolder {
		
		NONE,
		THIRDPERSON_LEFT_HAND,
		THIRDPERSON_RIGHT_HAND,
		FIRSTPERSON_LEFT_HAND,
		FIRSTPERSON_RIGHT_HAND,
		HEAD,
		GUI,
		GROUND,
		FIXED;
		
		private String name;
		
		private RenderType() {
			name = name().toLowerCase();
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
