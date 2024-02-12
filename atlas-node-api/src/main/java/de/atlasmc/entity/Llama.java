package de.atlasmc.entity;

import de.atlasmc.DyeColor;
import de.atlasmc.inventory.LlamaInventory;

public interface Llama extends ChestedHorse {
	
	public int getStrength();
	
	public void setStrength(int strength);
	
	public DyeColor getCarpetColor();
	
	public void setCarpedColor(DyeColor color);
	
	public LlamaColor getColor();
	
	public void setColor(LlamaColor color);
	
	public LlamaInventory getInventory();
	
	public static enum LlamaColor {
		CREAMY,
		WHITE,
		BROWN,
		GRAY;
		
		public int getID() {
			return ordinal();
		}
		
		public static LlamaColor getByID(int id) {
			switch (id) {
			case 0:
				return CREAMY;
			case 1:
				return WHITE;
			case 2:
				return BROWN;
			case 3:
				return GRAY;
			default:
				return CREAMY;
			}
		}
		
	}
}
