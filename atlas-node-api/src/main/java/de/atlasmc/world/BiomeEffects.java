package de.atlasmc.world;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Color;
import de.atlasmc.sound.Sound;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class BiomeEffects implements NBTSerializable {

	public static final NBTSerializationHandler<BiomeEffects>
	NBT_HANDLER = NBTSerializationHandler
					.builder(BiomeEffects.class)
					.defaultConstructor(BiomeEffects::new)
					.color("fog_color", BiomeEffects::getFogColor, BiomeEffects::setFogColor)
					.color("sky_color", BiomeEffects::getSkyColor, BiomeEffects::setSkyColor)
					.color("water_color", BiomeEffects::getWaterColor, BiomeEffects::setWaterColor)
					.color("water_fog_color", BiomeEffects::getWaterFogColor, BiomeEffects::setWaterFogColor)
					.color("foliage_color", BiomeEffects::getFoliageColor, BiomeEffects::setFoliageColor)
					.color("dry_foliage_color", BiomeEffects::getDryFoliageColor, BiomeEffects::setDryFoliageColor)
					.color("grass_color", BiomeEffects::getGrassColor, BiomeEffects::setGrassColor)
					.enumStringField("grass_color_modifier", BiomeEffects::getGrassColorModifier, BiomeEffects::setGrassColorModifier, GrassColorModifier::getByName, GrassColorModifier.NONE)
					//.beginComponent("particle")
					.addField(Sound.getNBTSoundField("ambient_sound", BiomeEffects::getAmbientSound, BiomeEffects::setAmbientSound, null))
					.beginComponent("mood_sound", BiomeEffects::hasMoodSound)
					.addField(Sound.getNBTSoundField("sound", BiomeEffects::getMoodSound, BiomeEffects::setMoodSound, null))
					.intField("tick_delay", BiomeEffects::getMoodTickDelay, BiomeEffects::setMoodTickDelay, 0)
					.intField("block_search_extent", BiomeEffects::getMoodBlockSearchExtent, BiomeEffects::setMoodBlockSearchExtent, 0)
					.doubleField("offset", BiomeEffects::getMoodOffset, BiomeEffects::setMoodOffset, 0)
					.endComponent()
					.beginComponent("additions_sound", BiomeEffects::hasAdditionsSound)
					.addField(Sound.getNBTSoundField("sound", BiomeEffects::getAdditionsSound, BiomeEffects::setAdditionsSound, null))
					.doubleField("tick_chance", BiomeEffects::getAddtionsTickChance, BiomeEffects::setAddtionsTickChance)
					.endComponent()
					.typeList("music", BiomeEffects::hasMusic, BiomeEffects::getMusic, BiomeMusic.NBT_HANDLER)
					.build();
	
	private Color fogColor;
	private Color skyColor;
	private Color waterColor;
	private Color waterFogColor;
	private Color foliageColor;
	private Color dryFoliageColor;
	private Color grassColor;
	private GrassColorModifier grassColorModifier;
	private Sound ambientSound;
	private Sound moodSound;
	private int moodTickDelay;
	private int moodBlockSearchExtent;
	private double moodOffset;
	private Sound additionsSound;
	private double additionsTickChance;
	private List<BiomeMusic> music;
	
	public BiomeEffects() {
		grassColorModifier = GrassColorModifier.NONE;
	}
	
	public boolean hasMusic() {
		return music != null && !music.isEmpty();
	}
	
	@NotNull
	public List<BiomeMusic> getMusic() {
		if (music == null)
			music = new ArrayList<>();
		return music;
	}
	
	public boolean hasAdditionsSound() {
		return additionsSound != null;
	}
	
	@Nullable
	public Sound getAdditionsSound() {
		return additionsSound;
	}
	
	public void setAdditionsSound(@Nullable Sound additionsSound) {
		this.additionsSound = additionsSound;
	}
	
	public double getAddtionsTickChance() {
		return additionsTickChance;
	}
	
	public void setAddtionsTickChance(double addtionsTickChance) {
		this.additionsTickChance = addtionsTickChance;
	}
	
	@Nullable
	public Sound getAmbientSound() {
		return ambientSound;
	}
	
	public void setAmbientSound(@Nullable Sound ambientSound) {
		this.ambientSound = ambientSound;
	}
	
	public boolean hasMoodSound() {
		return moodSound != null;
	}
	
	public Sound getMoodSound() {
		return moodSound;
	}
	
	public int getMoodTickDelay() {
		return moodTickDelay;
	}
	
	public void setMoodTickDelay(int moodTickDelay) {
		this.moodTickDelay = moodTickDelay;
	}
	
	public int getMoodBlockSearchExtent() {
		return moodBlockSearchExtent;
	}
	
	public void setMoodBlockSearchExtent(int moodBlockSearchExtent) {
		this.moodBlockSearchExtent = moodBlockSearchExtent;
	}
	
	public double getMoodOffset() {
		return moodOffset;
	}
	
	public void setMoodOffset(double moodOffset) {
		this.moodOffset = moodOffset;
	}
	
	public void setMoodSound(Sound moodSound) {
		this.moodSound = moodSound;
	}
	
	@Nullable
	public Color getFogColor() {
		return fogColor;
	}
	
	public void setFogColor(@Nullable Color fogColor) {
		this.fogColor = fogColor;
	}
	
	@Nullable
	public Color getSkyColor() {
		return skyColor;
	}
	
	public void setSkyColor(@Nullable Color skyColor) {
		this.skyColor = skyColor;
	}
	
	@Nullable
	public Color getWaterColor() {
		return waterColor;
	}
	
	public void setWaterColor(@Nullable Color waterColor) {
		this.waterColor = waterColor;
	}
	
	@Nullable
	public Color getWaterFogColor() {
		return waterFogColor;
	}
	
	public void setWaterFogColor(@Nullable Color waterFogColor) {
		this.waterFogColor = waterFogColor;
	}
	
	@Nullable
	public Color getFoliageColor() {
		return foliageColor;
	}
	
	public void setFoliageColor(@Nullable Color foliageColor) {
		this.foliageColor = foliageColor;
	}
	
	@Nullable
	public Color getDryFoliageColor() {
		return dryFoliageColor;
	}
	
	public void setDryFoliageColor(@Nullable Color dryFoliageColor) {
		this.dryFoliageColor = dryFoliageColor;
	}
	
	@Nullable
	public Color getGrassColor() {
		return grassColor;
	}
	
	public void setGrassColor(@Nullable Color grassColor) {
		this.grassColor = grassColor;
	}
	
	@NotNull
	public GrassColorModifier getGrassColorModifier() {
		return grassColorModifier;
	}
	
	public void setGrassColorModifier(@NotNull GrassColorModifier modifier) {
		if (modifier == null)
			throw new IllegalArgumentException("Modififer can not be null!");
		this.grassColorModifier = modifier;
	}
	
	@Override
	public NBTSerializationHandler<? extends BiomeEffects> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum GrassColorModifier implements EnumName, EnumValueCache {
		
		NONE,
		DARK_FOREST,
		SWAMP;

		private static List<GrassColorModifier> VALUES;
		
		private final String name;
		
		private GrassColorModifier() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		/**
		 * Returns the value represented by the name or null if no matching value has been found
		 * @param name the name of the value
		 * @return value or null
		 */
		public static GrassColorModifier getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<GrassColorModifier> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				GrassColorModifier value = values.get(i);
				if (value.name.equals(name)) 
					return value;
			}
			return null;
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<GrassColorModifier> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}
	
}
