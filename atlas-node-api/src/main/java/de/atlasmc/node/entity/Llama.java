package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.node.DyeColor;
import de.atlasmc.node.inventory.LlamaInventory;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Llama extends ChestedHorse {
	
	public static final NBTSerializationHandler<Llama>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Llama.class)
					.include(ChestedHorse.NBT_HANDLER)
					.intField("Strength", Llama::getStrength, Llama::setStrength, 3)
					.enumIntField("Variant", Llama::getColor, Llama::setColor, LlamaColor.class, LlamaColor.CREAMY)
					.build();
					
	
	int getStrength();
	
	void setStrength(int strength);
	
	DyeColor getCarpetColor();
	
	void setCarpedColor(DyeColor color);
	
	LlamaColor getColor();
	
	void setColor(LlamaColor color);
	
	LlamaInventory getInventory();
	
	@Override
	default NBTSerializationHandler<? extends Llama> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum LlamaColor implements IDHolder {
		
		CREAMY,
		WHITE,
		BROWN,
		GRAY;
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}
}
