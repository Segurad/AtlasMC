package de.atlascore.world;

import java.io.IOException;

import de.atlasmc.Color;
import de.atlasmc.NamespacedKey;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Biome;

public class CoreBiome extends AbstractNBTBase implements Biome {
	
	private static final CoreBiomeData NULL_DATA = new CoreBiomeData();
	private static final CoreBiomeEffect NULL_EFFECT = new CoreBiomeEffect();
	
	private static final NBTFieldContainer<CoreBiome> NBT_FIELDS;
	
	protected static final CharKey
	NBT_NAME = CharKey.literal("name"),
	NBT_ID = CharKey.literal("id"),
	NBT_ELEMENT = CharKey.literal("element"),
	NBT_PRECIPITATION = CharKey.literal("precipitation"),
	NBT_DEPTH = CharKey.literal("depth"),
	NBT_TEMPERATURE = CharKey.literal("temperature"),
	NBT_SCALE = CharKey.literal("scale"),
	NBT_DOWNFALL = CharKey.literal("downfall"),
	NBT_CATEGORY = CharKey.literal("category"),
	NBT_TEMPERATURE_MODIFIER = CharKey.literal("temperature_modifier"),
	NBT_EFFECTS = CharKey.literal("effects"),
	NBT_SKY_COLOR = CharKey.literal("sky_color"),
	NBT_WATER_FOG_COLOR = CharKey.literal("water_fog_color"),
	NBT_FOG_COLOR = CharKey.literal("fog_color"),
	NBT_WATER_COLOR = CharKey.literal("water_color"),
	NBT_FOLIAGE_COLOR = CharKey.literal("foliage_color"),
	NBT_GRASS_COLOR = CharKey.literal("grass_color"),
	NBT_GRASS_COLOR_MODIFIER = CharKey.literal("grass_color_modifier"),
	NBT_MUSIC = CharKey.literal("music"),
	NBT_REPLACE_CURRENT_MUSIC = CharKey.literal("replace_current_music"),
	NBT_SOUND = CharKey.literal("sound"),
	NBT_MAX_DELAY = CharKey.literal("max_delay"),
	NBT_MIN_DELAY = CharKey.literal("min_delay"),
	NBT_AMBIENT_SOUND = CharKey.literal("ambient_sound"),
	NBT_ADDITIONS_SOUND = CharKey.literal("additions_sound"),
	NBT_TICK_CHANCE = CharKey.literal("tick_chance"),
	NBT_MOOD_SOUND = CharKey.literal("mood_sound"),
	NBT_TICK_DELAY = CharKey.literal("tick_delay"),
	NBT_OFFSET = CharKey.literal("offset"),
	NBT_BLOCK_SEARCH_EXTENT = CharKey.literal("block_search_extent"),
	NBT_PARTICLE = CharKey.literal("particle"),
	NBT_PROBABILITY = CharKey.literal("probability"),
	NBT_OPTIONS = CharKey.literal("options"),
	NBT_TYPE = CharKey.literal("type");
	
	static {
		NBT_FIELDS = new NBTFieldContainer<>();
		NBT_FIELDS.setField(NBT_NAME, (holder, reader) -> {
			holder.name = reader.readNamespacedKey();
		});
		NBT_FIELDS.setField(NBT_ID, (holder, reader) -> {
			holder.id = reader.readIntTag();
		});
		NBTFieldContainer<CoreBiome> DATA = NBT_FIELDS.setContainer(NBT_ELEMENT);
		DATA.setField(NBT_PRECIPITATION, (holder, reader) -> {
			holder.getOrCreateData().precipitation = reader.readStringTag();
		});
		DATA.setField(NBT_DEPTH, (holder, reader) -> {
			holder.getOrCreateData().depth = reader.readFloatTag();
		});
		DATA.setField(NBT_TEMPERATURE, (holder, reader) -> {
			holder.getOrCreateData().temperature = reader.readFloatTag();
		});
		DATA.setField(NBT_SCALE, (holder, reader) -> {
			holder.getOrCreateData().scale = reader.readFloatTag();
		});
		DATA.setField(NBT_DOWNFALL, (holder, reader) -> {
			holder.getOrCreateData().downfall = reader.readFloatTag();
		});
		DATA.setField(NBT_CATEGORY, (holder, reader) -> {
			holder.getOrCreateData().category = reader.readStringTag();
		});
		DATA.setField(NBT_TEMPERATURE_MODIFIER, (holder, reader) -> {
			holder.getOrCreateData().temperatureModifier = reader.readStringTag();
		});
		NBTFieldContainer<CoreBiome> EFFECTS = DATA.setContainer(NBT_EFFECTS);
		EFFECTS.setField(NBT_SKY_COLOR, (holder, reader) -> {
			holder.getOrCreateEffect().skyColor = Color.fromRGB(reader.readIntTag());
		});
		EFFECTS.setField(NBT_WATER_FOG_COLOR, (holder, reader) -> {
			holder.getOrCreateEffect().skyColor = Color.fromRGB(reader.readIntTag());
		});
		EFFECTS.setField(NBT_FOG_COLOR, (holder, reader) -> {
			holder.getOrCreateEffect().fogColor = Color.fromRGB(reader.readIntTag());
		});
		EFFECTS.setField(NBT_WATER_COLOR, (holder, reader) -> {
			holder.getOrCreateEffect().waterColor = Color.fromRGB(reader.readIntTag());
		});
		EFFECTS.setField(NBT_FOLIAGE_COLOR, (holder, reader) -> {
			holder.getOrCreateEffect().foliageColor = Color.fromRGB(reader.readIntTag());
		});
		EFFECTS.setField(NBT_GRASS_COLOR, (holder, reader) -> {
			holder.getOrCreateEffect().grassColor = Color.fromRGB(reader.readIntTag());
		});
		EFFECTS.setField(NBT_GRASS_COLOR_MODIFIER, (holder, reader) -> {
			holder.getOrCreateEffect().grassColorModifier = reader.readStringTag();
		});
		EFFECTS.setContainer(NBT_MUSIC)
			.setField(NBT_REPLACE_CURRENT_MUSIC, (holder, reader) -> {
				holder.getOrCreateEffect().replaceCurrentMusic = reader.readByteTag() == 1;
			})
			.setField(NBT_SOUND, (holder, reader) -> {
				holder.getOrCreateEffect().sound = reader.readStringTag();
			})
			.setField(NBT_MAX_DELAY, (holder, reader) -> {
				holder.getOrCreateEffect().maxDelay = reader.readIntTag();
			})
			.setField(NBT_MIN_DELAY, (holder, reader) -> {
				holder.getOrCreateEffect().minDelay = reader.readIntTag();
			});
		EFFECTS.setField(NBT_AMBIENT_SOUND, (holder, reader) -> {
			holder.getOrCreateEffect().ambientSound = reader.readStringTag();
		});
		EFFECTS.setContainer(NBT_ADDITIONS_SOUND)
			.setField(NBT_SOUND, (holder, reader) -> {
				holder.getOrCreateEffect().additionsSound = reader.readStringTag();
			})
			.setField(NBT_TICK_CHANCE, (holder, reader) -> {
				holder.getOrCreateEffect().tickChance = reader.readDoubleTag();
			});
		EFFECTS.setContainer(NBT_MOOD_SOUND)
			.setField(NBT_SOUND, (holder, reader) -> {
				holder.getOrCreateEffect().moodSound = reader.readStringTag();
			})
			.setField(NBT_TICK_DELAY, (holder, reader) -> {
				holder.getOrCreateEffect().tickDelay = reader.readIntTag();
			})
			.setField(NBT_OFFSET, (holder, reader) -> {
				holder.getOrCreateEffect().offset = reader.readDoubleTag();
			})
			.setField(NBT_BLOCK_SEARCH_EXTENT, (holder, reader) -> {
				holder.getOrCreateEffect().blockSearchExtent = reader.readIntTag();
			});
		DATA.setContainer(NBT_PARTICLE)
			.setField(NBT_PROBABILITY, (holder, reader) -> {
				holder.getOrCreateData().probability = reader.readFloatTag();
			})
			.setContainer(NBT_OPTIONS).setField(NBT_TYPE, (holder, reader) -> {
				holder.getOrCreateData().particleType = reader.readStringTag();
			});
	}
	
	private NamespacedKey name;
	private int id;
	private CoreBiomeData data;
	
	public CoreBiome(NBTReader reader) throws IOException {
		fromNBT(reader);
	}
	
	public CoreBiome(NamespacedKey name, int id) {
		this.name = name;
		this.id = id;
	}
	
	@Override
	public NamespacedKey getNamespacedKey() {
		return name;
	}
	
	@Override
	public int getID() {
		return id;
	}

	@Override
	public String getPrecipitation() {
		return getData().precipitation;
	}

	@Override
	public float getDepth() {
		return getData().depth;
	}

	@Override
	public float getTemperature() {
		return getData().temperature;
	}

	@Override
	public float getScale() {
		return getData().scale;
	}

	@Override
	public float getDownfall() {
		return getData().downfall;
	}

	@Override
	public String getCategory() {
		return getData().category;
	}

	@Override
	public String getTemperatureModifier() {
		return getData().temperatureModifier;
	}

	@Override
	public Color getWaterFogColor() {
		return getEffect().waterFogColor;
	}

	@Override
	public Color getFogColor() {
		return getEffect().fogColor;
	}

	@Override
	public Color getWaterColor() {
		return getEffect().waterColor;
	}

	@Override
	public Color getFoliageColor() {
		return getEffect().foliageColor;
	}

	@Override
	public Color getGrassColor() {
		return getEffect().grassColor;
	}

	@Override
	public String getGrassColorModifier() {
		return getEffect().grassColorModifier;
	}

	@Override
	public boolean isReplaceCurrentMusic() {
		return getEffect().replaceCurrentMusic;
	}

	@Override
	public String getSound() {
		return getEffect().sound;
	}

	@Override
	public int getMaxDelay() {
		return getEffect().maxDelay;
	}

	@Override
	public int getMinDelay() {
		return getEffect().minDelay;
	}

	@Override
	public String getAmbientSound() {
		return getEffect().ambientSound;
	}

	@Override
	public String getAdditionsSound() {
		return getEffect().additionsSound;
	}

	@Override
	public double getTickChance() {
		return getEffect().tickChance;
	}

	@Override
	public String getMoodSound() {
		return getEffect().moodSound;
	}

	@Override
	public int getTickDelay() {
		return getEffect().tickDelay;
	}

	@Override
	public double getOffset() {
		return getEffect().offset;
	}

	@Override
	public int getBlockSearchExtent() {
		return getEffect().blockSearchExtent;
	}

	@Override
	public float getProbability() {
		return getData().probability;
	}

	@Override
	public String getParticleType() {
		return getData().particleType;
	}

	public void setPrecipitation(String precipitation) {
		getOrCreateData().precipitation = precipitation;
	}

	public void setDepth(float depth) {
		getOrCreateData().depth = depth;
	}

	public void setTemperature(float temperature) {
		getOrCreateData().temperature = temperature;
	}

	public void setScale(float scale) {
		getOrCreateData().scale = scale;
	}

	public void setDownfall(float downfall) {
		getOrCreateData().downfall = downfall;
	}

	public void setCategory(String category) {
		getOrCreateData().category = category;
	}

	public void setTemperatureModifier(String temperatureModifier) {
		getOrCreateData().temperatureModifier = temperatureModifier;
	}

	public void setWaterFogColor(Color waterFogColor) {
		getOrCreateEffect().waterFogColor = waterFogColor;
	}

	public void setFogColor(Color fogColor) {
		getOrCreateEffect().fogColor = fogColor;
	}

	public void setWaterColor(Color waterColor) {
		getOrCreateEffect().waterColor = waterColor;
	}

	public void setFoliageColor(Color foliageColor) {
		getOrCreateEffect().foliageColor = foliageColor;
	}

	public void setGrassColor(Color grassColor) {
		getOrCreateEffect().grassColor = grassColor;
	}

	public void setGrassColorModifier(String grassColorModifier) {
		getOrCreateEffect().grassColorModifier = grassColorModifier;
	}

	public void setReplaceCurrentMusic(boolean replaceCurrentMusic) {
		getOrCreateEffect().replaceCurrentMusic = replaceCurrentMusic;
	}

	public void setSound(String sound) {
		getOrCreateEffect().sound = sound;
	}

	public void setMaxDelay(int maxDelay) {
		getOrCreateEffect().maxDelay = maxDelay;
	}

	public void setMinDelay(int minDelay) {
		getOrCreateEffect().minDelay = minDelay;
	}

	public void setAmbientSound(String ambientSound) {
		getOrCreateEffect().ambientSound = ambientSound;
	}

	public void setAdditionsSound(String additionsSound, double tickChance) {
		CoreBiomeEffect effect = getOrCreateEffect();
		effect.additionsSound = additionsSound;
		effect.tickChance = tickChance;
	}

	public void setMoodSound(String moodSound, int tickDelay, double offset, int blockSearchExtent) {
		CoreBiomeEffect effect = getOrCreateEffect();
		effect.moodSound = moodSound;
		effect.tickDelay = tickDelay;
		effect.offset = offset;
		effect.blockSearchExtent = blockSearchExtent;
	}

	public void setParticle(float probability, String particleType) {
		CoreBiomeData data = getOrCreateData();
		data.probability = probability;
		data.particleType = particleType;
	}
	
	private CoreBiomeData getData() {
		return data != null ? data : NULL_DATA;
	}
	
	private CoreBiomeData getOrCreateData() {
		if (data == null)
			data = new CoreBiomeData();
		return data;
	}


	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(NBT_NAME, name.toString());
		writer.writeIntTag(NBT_ID, id);
		if (data != null) {
			writer.writeCompoundTag(NBT_ELEMENT);
			writer.writeStringTag(NBT_PRECIPITATION, data.precipitation);
			writer.writeFloatTag(NBT_DEPTH, data.depth);
			writer.writeFloatTag(NBT_TEMPERATURE, data.temperature);
			writer.writeFloatTag(NBT_SCALE, data.scale);
			writer.writeFloatTag(NBT_DOWNFALL, data.downfall);
			writer.writeStringTag(NBT_CATEGORY, data.category);
			if (data.temperatureModifier != null)
				writer.writeStringTag(NBT_TEMPERATURE_MODIFIER, data.temperatureModifier);
			//
			if (data.effect != null) {
				CoreBiomeEffect effect = data.effect;
				writer.writeCompoundTag(NBT_EFFECTS);
				writer.writeIntTag(NBT_SKY_COLOR, effect.skyColor.asRGB());
				writer.writeIntTag(NBT_WATER_FOG_COLOR, effect.waterFogColor.asRGB());
				writer.writeIntTag(NBT_FOG_COLOR, effect.fogColor.asRGB());
				writer.writeIntTag(NBT_WATER_COLOR, effect.waterColor.asRGB());
				if (effect.foliageColor != null)
					writer.writeIntTag(NBT_FOLIAGE_COLOR, effect.foliageColor.asRGB());
				if (effect.grassColor != null)
					writer.writeIntTag(NBT_GRASS_COLOR, effect.grassColor.asRGB());
				if (effect.grassColorModifier != null)
					writer.writeStringTag(NBT_GRASS_COLOR_MODIFIER, effect.grassColorModifier);
				if (effect.sound != null) {
					writer.writeCompoundTag(NBT_MUSIC);
					writer.writeByteTag(NBT_REPLACE_CURRENT_MUSIC, effect.replaceCurrentMusic);
					writer.writeStringTag(NBT_SOUND, effect.sound);
					writer.writeIntTag(NBT_MAX_DELAY, effect.maxDelay);
					writer.writeIntTag(NBT_MIN_DELAY, effect.minDelay);
					writer.writeEndTag();
				}
				if (effect.ambientSound != null) 
					writer.writeStringTag(NBT_AMBIENT_SOUND, effect.ambientSound);
				if (effect.additionsSound != null) {
					writer.writeCompoundTag(NBT_ADDITIONS_SOUND);
					writer.writeStringTag(NBT_SOUND, effect.additionsSound);
					writer.writeDoubleTag(NBT_TICK_CHANCE, effect.tickChance);
					writer.writeEndTag();
				}
				if (effect.moodSound != null) {
					writer.writeCompoundTag(NBT_MOOD_SOUND);
					writer.writeStringTag(NBT_SOUND, effect.moodSound);
					writer.writeIntTag(NBT_TICK_DELAY, effect.tickDelay);
					writer.writeDoubleTag(NBT_OFFSET, effect.offset);
					writer.writeIntTag(NBT_BLOCK_SEARCH_EXTENT, effect.blockSearchExtent);
					writer.writeEndTag();
				}
				writer.writeEndTag();
			}
			if (data.particleType != null) {
				writer.writeCompoundTag(NBT_PARTICLE);
				{
					writer.writeFloatTag(NBT_PROBABILITY, data.probability);
					writer.writeCompoundTag(NBT_OPTIONS);
					{
						writer.writeStringTag(NBT_TYPE, data.particleType);
					}
					writer.writeEndTag();
				}
				writer.writeEndTag();
			}
			writer.writeEndTag();
		}
	}
	
	private CoreBiomeEffect getOrCreateEffect() {
		getOrCreateData();
		if (data.effect == null)
			data.effect = new CoreBiomeEffect();
		return data.effect;
	}
	
	private CoreBiomeEffect getEffect() {
		if (data == null)
			return NULL_EFFECT;
		return data.effect != null ? data.effect : NULL_EFFECT;
	}

	@Override
	protected NBTFieldContainer<CoreBiome> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		super.fromNBT(reader);
		if (name == null)
			throw new NBTException("Name could not be found while reading the biome data!");
	}
	
	private static final class CoreBiomeData {
		private String precipitation;
		private float depth;
		private float temperature;
		private float scale;
		private float downfall;
		private String category;
		private String temperatureModifier;
		private CoreBiomeEffect effect;
		// particle {
		private float probability;
		private String particleType;
		// }
	}
	
	private static class CoreBiomeEffect {
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
	}

}
