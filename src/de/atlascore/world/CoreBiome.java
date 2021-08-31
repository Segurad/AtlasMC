package de.atlascore.world;

import java.io.IOException;

import de.atlasmc.Color;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Biome;

public class CoreBiome extends AbstractNBTBase implements Biome {
	
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
		// TODO create NBT reader
	}
	
	public CoreBiome(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getID() {
		return id;
	}

	@Override
	public String getPrecipitation() {
		return precipitation;
	}

	@Override
	public float getDepth() {
		return depth;
	}

	@Override
	public float getTemperature() {
		return temperature;
	}

	@Override
	public float getScale() {
		return scale;
	}

	@Override
	public float getDownfall() {
		return downfall;
	}

	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public String getTemperatureModifier() {
		return temperatureModifier;
	}

	@Override
	public Color getWaterFogColor() {
		return waterFogColor;
	}

	@Override
	public Color getFogColor() {
		return fogColor;
	}

	@Override
	public Color getWaterColor() {
		return waterColor;
	}

	@Override
	public Color getFoliageColor() {
		return foliageColor;
	}

	@Override
	public Color getGrassColor() {
		return grassColor;
	}

	@Override
	public String getGrassColorModifier() {
		return grassColorModifier;
	}

	@Override
	public boolean isReplaceCurrentMusic() {
		return replaceCurrentMusic;
	}

	@Override
	public String getSound() {
		return sound;
	}

	@Override
	public int getMaxDelay() {
		return maxDelay;
	}

	@Override
	public int getMinDelay() {
		return minDelay;
	}

	@Override
	public String getAmbientSound() {
		return ambientSound;
	}

	@Override
	public String getAdditionsSound() {
		return additionsSound;
	}

	@Override
	public double getTickChance() {
		return tickChance;
	}

	@Override
	public String getMoodSound() {
		return moodSound;
	}

	@Override
	public int getTickDelay() {
		return tickDelay;
	}

	@Override
	public double getOffset() {
		return offset;
	}

	@Override
	public int getBlockSearchExtent() {
		return blockSearchExtent;
	}

	@Override
	public float getProbability() {
		return probability;
	}

	@Override
	public String getParticleType() {
		return particleType;
	}

	@Override
	public void setPrecipitation(String precipitation) {
		this.precipitation = precipitation;
	}

	@Override
	public void setDepth(float depth) {
		this.depth = depth;
	}

	@Override
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	@Override
	public void setScale(float scale) {
		this.scale = scale;
	}

	@Override
	public void setDownfall(float downfall) {
		this.downfall = downfall;
	}

	@Override
	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public void setTemperatureModifier(String temperatureModifier) {
		this.temperatureModifier = temperatureModifier;
	}

	@Override
	public void setWaterFogColor(Color waterFogColor) {
		this.waterFogColor = waterFogColor;
	}

	@Override
	public void setFogColor(Color fogColor) {
		this.fogColor = fogColor;
	}

	@Override
	public void setWaterColor(Color waterColor) {
		this.waterColor = waterColor;
	}

	@Override
	public void setFoliageColor(Color foliageColor) {
		this.foliageColor = foliageColor;
	}

	@Override
	public void setGrassColor(Color grassColor) {
		this.grassColor = grassColor;
	}

	@Override
	public void setGrassColorModifier(String grassColorModifier) {
		this.grassColorModifier = grassColorModifier;
	}

	@Override
	public void setReplaceCurrentMusic(boolean replaceCurrentMusic) {
		this.replaceCurrentMusic = replaceCurrentMusic;
	}

	@Override
	public void setSound(String sound) {
		this.sound = sound;
	}

	@Override
	public void setMaxDelay(int maxDelay) {
		this.maxDelay = maxDelay;
	}

	@Override
	public void setMinDelay(int minDelay) {
		this.minDelay = minDelay;
	}

	@Override
	public void setAmbientSound(String ambientSound) {
		this.ambientSound = ambientSound;
	}

	@Override
	public void setAdditionsSound(String additionsSound, double tickChance) {
		this.additionsSound = additionsSound;
		this.tickChance = tickChance;
	}

	@Override
	public void setMoodSound(String moodSound, int tickDelay, double offset, int blockSearchExtent) {
		this.moodSound = moodSound;
		this.tickDelay = tickDelay;
		this.offset = offset;
		this.blockSearchExtent = blockSearchExtent;
	}

	@Override
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
			//
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
