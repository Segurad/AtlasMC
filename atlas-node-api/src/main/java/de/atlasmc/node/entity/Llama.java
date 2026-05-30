package de.atlasmc.node.entity;

import de.atlasmc.IDHolder;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.DyeColor;
import de.atlasmc.node.inventory.LlamaInventory;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumUtil;

public interface Llama extends ChestedHorse {
	
	@NotNull
	public static final NBTCodec<Llama>
	NBT_CODEC = NBTCodec
					.builder(Llama.class)
					.include(ChestedHorse.NBT_CODEC)
					.intField("Strength", Llama::getStrength, Llama::setStrength, 3)
					.codec("Variant", Llama::getColor, Llama::setColor, EnumUtil.enumIntNBTCodec(LlamaColor.class), LlamaColor.CREAMY)
					.build();
					
	
	int getStrength();
	
	void setStrength(int strength);
	
	DyeColor getCarpetColor();
	
	void setCarpedColor(DyeColor color);
	
	LlamaColor getColor();
	
	void setColor(LlamaColor color);
	
	@Override
	LlamaInventory getInventory();
	
	@Override
	default NBTCodec<? extends Llama> getNBTCodec() {
		return NBT_CODEC;
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
