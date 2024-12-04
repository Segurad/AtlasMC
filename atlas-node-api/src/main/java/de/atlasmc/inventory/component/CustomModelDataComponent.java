package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public interface CustomModelDataComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:custom_model_data");
	
	Integer getModelDataValue();
	
	void setModelDataValue(Integer data);
	
	ModelData getModelData();
	
	void setModelData(ModelData data);
	
	CustomModelDataComponent clone();
	
	public static class ModelData {
		
		public float[] floats;
		public boolean[] flags;
		public String[] strings;
		public int[] colors;
		
	}

}
