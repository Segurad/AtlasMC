package de.atlasmc.world;

import de.atlasmc.Color;
import de.atlasmc.util.nbt.NBTHolder;

public interface Biome extends NBTHolder {
	
	public String getName();
	
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

	public void setPrecipitation(String precipitation);

	public void setDepth(float depth);

	public void setTemperature(float temperature);

	public void setScale(float scale);

	public void setDownfall(float downfall);

	public void setCategory(String category);

	public void setTemperatureModifier(String temperatureModifier);

	public void setWaterFogColor(Color waterFogColor);

	public void setFogColor(Color fogColor);

	public void setWaterColor(Color waterColor);

	public void setFoliageColor(Color foliageColor);

	public void setGrassColor(Color grassColor);

	public void setGrassColorModifier(String grassColorModifier);

	public void setReplaceCurrentMusic(boolean replaceCurrentMusic);

	public void setSound(String sound);

	public void setMaxDelay(int maxDelay);

	public void setMinDelay(int minDelay);

	public void setAmbientSound(String ambientSound);

	public void setAdditionsSound(String additionsSound, double tickChance);

	public void setMoodSound(String moodSound, int tickDelay, double offset, int blockSearchExtent);

	public void setParticle(float probability, String particleType);

}
