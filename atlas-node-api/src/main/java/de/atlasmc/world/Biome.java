package de.atlasmc.world;

import de.atlasmc.Color;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.nbt.NBTHolder;

public interface Biome extends NBTHolder, Namespaced {
	
	public int getID();

	public String getPrecipitation();

	public float getDepth();

	public float getTemperature();

	public float getScale();

	public float getDownfall();

	public String getCategory();

	public String getTemperatureModifier();

	public Color getWaterFogColor();

	public Color getFogColor();

	public Color getWaterColor();

	public Color getFoliageColor();

	public Color getGrassColor();

	public String getGrassColorModifier();

	public boolean isReplaceCurrentMusic();

	public String getSound();

	public int getMaxDelay();

	public int getMinDelay();

	public String getAmbientSound();

	public String getAdditionsSound();

	public double getTickChance();

	public String getMoodSound();

	public int getTickDelay();

	public double getOffset();

	public int getBlockSearchExtent();

	public float getProbability();

	public String getParticleType();

}
