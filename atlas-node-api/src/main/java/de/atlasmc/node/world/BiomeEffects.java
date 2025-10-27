package de.atlasmc.node.world;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.Color;
import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.node.sound.ResourceSound;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public class BiomeEffects implements NBTSerializable {

	public static final NBTCodec<BiomeEffects>
	NBT_HANDLER = NBTCodec
					.builder(BiomeEffects.class)
					.defaultConstructor(BiomeEffects::new)
					.color("fog_color", BiomeEffects::getFogColor, BiomeEffects::setFogColor, null)
					.color("sky_color", BiomeEffects::getSkyColor, BiomeEffects::setSkyColor, null)
					.color("water_color", BiomeEffects::getWaterColor, BiomeEffects::setWaterColor, null)
					.color("water_fog_color", BiomeEffects::getWaterFogColor, BiomeEffects::setWaterFogColor, null)
					.color("foliage_color", BiomeEffects::getFoliageColor, BiomeEffects::setFoliageColor, null)
					.color("dry_foliage_color", BiomeEffects::getDryFoliageColor, BiomeEffects::setDryFoliageColor, null)
					.color("grass_color", BiomeEffects::getGrassColor, BiomeEffects::setGrassColor, null)
					.enumStringField("grass_color_modifier", BiomeEffects::getGrassColorModifier, BiomeEffects::setGrassColorModifier, GrassColorModifier.class, GrassColorModifier.NONE)
					//.beginComponent("particle")
					.enumStringOrType("ambient_sound", BiomeEffects::getAmbientSound, BiomeEffects::setAmbientSound, EnumSound.class, ResourceSound.NBT_CODEC)
					.beginComponent("mood_sound", BiomeEffects::hasMoodSound)
					.enumStringOrType("sound", BiomeEffects::getMoodSound, BiomeEffects::setMoodSound, EnumSound.class, ResourceSound.NBT_CODEC)
					.intField("tick_delay", BiomeEffects::getMoodTickDelay, BiomeEffects::setMoodTickDelay, 0)
					.intField("block_search_extent", BiomeEffects::getMoodBlockSearchExtent, BiomeEffects::setMoodBlockSearchExtent, 0)
					.doubleField("offset", BiomeEffects::getMoodOffset, BiomeEffects::setMoodOffset, 0)
					.endComponent()
					.beginComponent("additions_sound", BiomeEffects::hasAdditionsSound)
					.enumStringOrType("sound", BiomeEffects::getAdditionsSound, BiomeEffects::setAdditionsSound, EnumSound.class, ResourceSound.NBT_CODEC)
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
	public NBTCodec<? extends BiomeEffects> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	public static enum GrassColorModifier implements EnumName {
		
		NONE,
		DARK_FOREST,
		SWAMP;

		private final String name;
		
		private GrassColorModifier() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}
	
}
