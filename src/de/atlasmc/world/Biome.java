package de.atlasmc.world;

import java.io.IOException;

import de.atlasmc.Color;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class Biome extends AbstractNBTBase {
	
	private final String name;
	private final int id;
	private String precipitation;
	private float depth;
	private float temperature;
	private float scale;
	private float downfall;
	private String category;
	private String temperatureModifier;
	private Color skyColor;
	private Color waterFogColor;
	private Color fogColor;
	private Color waterColor;
	private Color foliageColor;
	private Color grassColor;
	private String grassColorModifier;
	// music {
	private boolean replaceCurrentMusic;
	private String sound;
	private int maxDelay;
	private int minDelay;
	// }
	private String ambientSound;
	// additions_sound {
	private String additionsSound;
	private double tickChance;
	// }
	// mood_sound {
	private String moodSound;
	private int tickDelay;
	private double offset;
	private int blockSearchExtent;
	// }
	// particle {
	private float probability;
	private String particleType;
	// }
	
	private static final NBTFieldContainer NBT_FIELDS;
	
	protected static final String
	NAME = "name",
	ID = "id",
	ELEMENT = "element",
	PRECIPITATION = "precipitation",
	DEPTH = "depth",
	TEMPERATURE = "temperature",
	SCALE = "scale",
	DOWNFALL = "downfall",
	CATEGORY = "category",
	TEMPERATURE_MODIFIER = "temperature_modifier",
	EFFECTS = "effects",
	SKY_COLOR = "sky_color",
	WATER_FOG_COLOR = "water_fog_color",
	FOG_COLOR = "fog_color",
	WATER_COLOR = "water_color",
	FOLIAGE_COLOR = "foliage_color",
	GRASS_COLOR = "grass_color",
	GRASS_COLOR_MODIFIER = "grass_color_modifier",
	MUSIC = "music",
	REPLACE_CURRENT_MUSIC = "replace_current_music",
	SOUND = "sound",
	MAX_DELAY = "max_delay",
	MIN_DELAY = "min_delay",
	AMBIENT_SOUND = "ambient_sound",
	ADDITIONS_SOUND = "additions_sound",
	TICK_CHANCE = "tick_chance",
	MOOD_SOUND = "mood_sound",
	TICK_DELAY = "tick_delay",
	OFFSET = "offset",
	BLOCK_SEARCH_EXTENT = "block_search_extent",
	PARTICLE = "particle",
	PROBABILITY = "probability",
	OPTIONS = "options",
	TYPE = "type";
	
	static {
		NBT_FIELDS = new NBTFieldContainer();
		// TODO
	}
	
	public Biome(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getID() {
		return id;
	}

	public String getPrecipitation() {
		return precipitation;
	}

	public float getDepth() {
		return depth;
	}

	public float getTemperature() {
		return temperature;
	}

	public float getScale() {
		return scale;
	}

	public float getDownfall() {
		return downfall;
	}

	public String getCategory() {
		return category;
	}

	public String getTemperatureModifier() {
		return temperatureModifier;
	}

	public Color getWaterFogColor() {
		return waterFogColor;
	}

	public Color getFogColor() {
		return fogColor;
	}

	public Color getWaterColor() {
		return waterColor;
	}

	public Color getFoliageColor() {
		return foliageColor;
	}

	public Color getGrassColor() {
		return grassColor;
	}

	public String getGrassColorModifier() {
		return grassColorModifier;
	}

	public boolean isReplaceCurrentMusic() {
		return replaceCurrentMusic;
	}

	public String getSound() {
		return sound;
	}

	public int getMaxDelay() {
		return maxDelay;
	}

	public int getMinDelay() {
		return minDelay;
	}

	public String getAmbientSound() {
		return ambientSound;
	}

	public String getAdditionsSound() {
		return additionsSound;
	}

	public double getTickChance() {
		return tickChance;
	}

	public String getMoodSound() {
		return moodSound;
	}

	public int getTickDelay() {
		return tickDelay;
	}

	public double getOffset() {
		return offset;
	}

	public int getBlockSearchExtent() {
		return blockSearchExtent;
	}

	public float getProbability() {
		return probability;
	}

	public String getParticleType() {
		return particleType;
	}

	public void setPrecipitation(String precipitation) {
		this.precipitation = precipitation;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void setDownfall(float downfall) {
		this.downfall = downfall;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setTemperatureModifier(String temperatureModifier) {
		this.temperatureModifier = temperatureModifier;
	}

	public void setWaterFogColor(Color waterFogColor) {
		this.waterFogColor = waterFogColor;
	}

	public void setFogColor(Color fogColor) {
		this.fogColor = fogColor;
	}

	public void setWaterColor(Color waterColor) {
		this.waterColor = waterColor;
	}

	public void setFoliageColor(Color foliageColor) {
		this.foliageColor = foliageColor;
	}

	public void setGrassColor(Color grassColor) {
		this.grassColor = grassColor;
	}

	public void setGrassColorModifier(String grassColorModifier) {
		this.grassColorModifier = grassColorModifier;
	}

	public void setReplaceCurrentMusic(boolean replaceCurrentMusic) {
		this.replaceCurrentMusic = replaceCurrentMusic;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public void setMaxDelay(int maxDelay) {
		this.maxDelay = maxDelay;
	}

	public void setMinDelay(int minDelay) {
		this.minDelay = minDelay;
	}

	public void setAmbientSound(String ambientSound) {
		this.ambientSound = ambientSound;
	}

	public void setAdditionsSound(String additionsSound, double tickChance) {
		this.additionsSound = additionsSound;
		this.tickChance = tickChance;
	}

	public void setMoodSound(String moodSound, int tickDelay, double offset, int blockSearchExtent) {
		this.moodSound = moodSound;
		this.tickDelay = tickDelay;
		this.offset = offset;
		this.blockSearchExtent = blockSearchExtent;
	}

	public void setParticle(float probability, String particleType) {
		this.probability = probability;
		this.particleType = particleType;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(NAME, name);
		writer.writeIntTag(ID, id);
		writer.writeCompoundTag(ELEMENT);
		{
			writer.writeStringTag(PRECIPITATION, precipitation);
			writer.writeFloatTag(DEPTH, depth);
			writer.writeFloatTag(TEMPERATURE, temperature);
			writer.writeFloatTag(SCALE, scale);
			writer.writeFloatTag(DOWNFALL, downfall);
			writer.writeStringTag(CATEGORY, category);
			if (temperatureModifier != null)
				writer.writeStringTag(TEMPERATURE_MODIFIER, temperatureModifier);
			writer.writeCompoundTag(EFFECTS);
			{
				writer.writeIntTag(SKY_COLOR, skyColor.asRGB());
				writer.writeIntTag(WATER_FOG_COLOR, waterFogColor.asRGB());
				writer.writeIntTag(FOG_COLOR, fogColor.asRGB());
				writer.writeIntTag(WATER_COLOR, waterColor.asRGB());
				if (foliageColor != null)
					writer.writeIntTag(FOLIAGE_COLOR, foliageColor.asRGB());
				if (grassColor != null)
					writer.writeIntTag(GRASS_COLOR, grassColor.asRGB());
				if (grassColorModifier != null)
					writer.writeStringTag(GRASS_COLOR_MODIFIER, grassColorModifier);
				if (sound != null) {
					writer.writeByteTag(REPLACE_CURRENT_MUSIC, replaceCurrentMusic);
					writer.writeStringTag(SOUND, sound);
					writer.writeIntTag(MAX_DELAY, maxDelay);
					writer.writeIntTag(MIN_DELAY, minDelay);
				}
				if (ambientSound != null) 
					writer.writeStringTag(AMBIENT_SOUND, ambientSound);
				if (additionsSound != null) {
					writer.writeStringTag(ADDITIONS_SOUND, additionsSound);
					writer.writeDoubleTag(TICK_CHANCE, tickChance);
				}
				if (moodSound != null) {
					writer.writeStringTag(SOUND, moodSound);
					writer.writeIntTag(TICK_DELAY, tickDelay);
					writer.writeDoubleTag(OFFSET, offset);
					writer.writeIntTag(BLOCK_SEARCH_EXTENT, blockSearchExtent);
				}
			}
			writer.writeEndTag();
			if (particleType != null) {
				writer.writeCompoundTag(PARTICLE);
				{
					writer.writeFloatTag(PROBABILITY, probability);
					writer.writeCompoundTag(OPTIONS);
					{
						writer.writeStringTag(TYPE, particleType);
					}
					writer.writeEndTag();
				}
				writer.writeEndTag();
			}
		}
		writer.writeEndTag();
	}

	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}

}
