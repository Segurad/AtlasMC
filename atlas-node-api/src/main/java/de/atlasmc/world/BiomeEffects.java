package de.atlasmc.world;

import java.io.IOException;
import java.util.List;

import de.atlasmc.Color;
import de.atlasmc.sound.Sound;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.util.nbt.io.NBTWriter;

public class BiomeEffects extends AbstractNBTBase {

	protected static final NBTFieldSet<BiomeEffects> NBT_FIELDS;
	
	protected static final CharKey
	NBT_FOG_COLOR = CharKey.literal("fog_color"),
	NBT_SKY_COLOR = CharKey.literal("sky_color"),
	NBT_WATER_COLOR = CharKey.literal("water_color"),
	NBT_WATER_FOG_COLOR = CharKey.literal("water_fog_color"),
	NBT_FOLIAGE_COLOR = CharKey.literal("foliage_color"),
	NBT_GRASS_COLOR = CharKey.literal("grass_color"),
	NBT_GRASS_COLOR_MODIFIER = CharKey.literal("grass_color_modifier"),
	NBT_PARTICLE = CharKey.literal("particle"),
	NBT_AMBIENT_SOUND = CharKey.literal("ambient_sound"),
	NBT_MOOD_SOUND = CharKey.literal("mood_sound"),
	NBT_SOUND = CharKey.literal("sound"),
	NBT_TICK_DELAY = CharKey.literal("tick_delay"),
	NBT_BLOCK_SEARCH_EXTENT = CharKey.literal("block_search_extent"),
	NBT_OFFSET = CharKey.literal("offset"),
	NBT_ADDITIONS_SOUND = CharKey.literal("additions_sound"),
	NBT_TICK_CHANGE = CharKey.literal("tick_chance"),
	NBT_MUSIC = CharKey.literal("music"),
	NBT_MIN_DELAY = CharKey.literal("min_delay"),
	NBT_MAX_DELAY = CharKey.literal("max_delay"),
	NBT_REPLACE_CURRENT_MUSIC = CharKey.literal("replace_current_music");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_FOG_COLOR, (holder, reader) -> {
			holder.fogColor = Color.fromRGB(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_SKY_COLOR, (holder, reader) -> {
			holder.skyColor = Color.fromRGB(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_WATER_COLOR, (holder, reader) -> {
			holder.waterColor = Color.fromRGB(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_WATER_FOG_COLOR, (holder, reader) -> {
			holder.waterFogColor = Color.fromRGB(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_FOLIAGE_COLOR, (holder, reader) -> {
			holder.foliageColor = Color.fromRGB(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_GRASS_COLOR, (holder, reader) -> {
			holder.grassColor = Color.fromRGB(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_GRASS_COLOR_MODIFIER, (holder, reader) -> {
			holder.setGrassColorModifier(GrassColorModifier.getByName(reader.readStringTag()));
		});
		NBT_FIELDS.setField(NBT_PARTICLE, NBTField.skip()); // TODO particle
		NBT_FIELDS.setField(NBT_AMBIENT_SOUND, (holder, reader) -> {
			holder.ambientSound = Sound.fromNBT(reader);
		});
		NBT_FIELDS.setSet(NBT_MOOD_SOUND)
			.setField(NBT_SOUND, (holder, reader) -> {
				holder.moodSound = Sound.fromNBT(reader);
			}).setField(NBT_TICK_DELAY, (holder, reader) -> {
				holder.moodTickDelay = reader.readIntTag();
			}).setField(NBT_BLOCK_SEARCH_EXTENT, (holder, reader) -> {
				holder.moodBlockSearchExtent = reader.readIntTag();
			}).setField(NBT_OFFSET, (holder, reader) -> {
				holder.moodOffset = reader.readDoubleTag();
			});
		NBT_FIELDS.setSet(NBT_ADDITIONS_SOUND)
			.setField(NBT_SOUND, (holder, reader) -> {
				holder.additionsSound = Sound.fromNBT(reader);
			}).setField(NBT_TICK_CHANGE, (holder, reader) -> {
				holder.addtionsTickChance = reader.readDoubleTag();
			});
		NBT_FIELDS.setSet(NBT_MUSIC)
			.setField(NBT_SOUND, (holder, reader) -> {
				holder.music = Sound.fromNBT(reader);
			}).setField(NBT_MIN_DELAY, (holder, reader) -> {
				holder.musicMinDelay = reader.readIntTag();
			}).setField(NBT_MAX_DELAY, (holder, reader) -> {
				holder.musicMaxDelay = reader.readIntTag();
			}).setField(NBT_REPLACE_CURRENT_MUSIC, (holder, reader) -> {
				holder.replaceCurrentMusic = reader.readBoolean();
			});
	}
	
	private Color fogColor;
	private Color skyColor;
	private Color waterColor;
	private Color waterFogColor;
	private Color foliageColor;
	private Color grassColor;
	private GrassColorModifier grassColorModifier;
	private Sound ambientSound;
	private Sound moodSound;
	private int moodTickDelay;
	private int moodBlockSearchExtent;
	private double moodOffset;
	private Sound additionsSound;
	private double addtionsTickChance;
	private Sound music;
	private int musicMinDelay;
	private int musicMaxDelay;
	private boolean replaceCurrentMusic;
	
	public BiomeEffects() {
		grassColorModifier = GrassColorModifier.NONE;
	}
	
	public Color getFogColor() {
		return fogColor;
	}
	
	public void setFogColor(Color fogColor) {
		this.fogColor = fogColor;
	}
	
	public Color getSkyColor() {
		return skyColor;
	}
	
	public void setSkyColor(Color skyColor) {
		this.skyColor = skyColor;
	}
	
	public Color getWaterColor() {
		return waterColor;
	}
	
	public void setWaterColor(Color waterColor) {
		this.waterColor = waterColor;
	}
	
	public Color getWaterFogColor() {
		return waterFogColor;
	}
	
	public void setWaterFogColor(Color waterFogColor) {
		this.waterFogColor = waterFogColor;
	}
	
	public Color getFoliageColor() {
		return foliageColor;
	}
	
	public void setFoliageColor(Color foliageColor) {
		this.foliageColor = foliageColor;
	}
	
	public Color getGrassColor() {
		return grassColor;
	}
	
	public void setGrassColor(Color grassColor) {
		this.grassColor = grassColor;
	}
	
	public GrassColorModifier getGrassColorModifier() {
		return grassColorModifier;
	}
	
	public void setGrassColorModifier(GrassColorModifier modifier) {
		if (modifier == null)
			throw new IllegalArgumentException("Modififer can not be null!");
		this.grassColorModifier = modifier;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (fogColor != null)
			writer.writeIntTag(NBT_FOG_COLOR, fogColor.asRGB());
		if (skyColor != null)
			writer.writeIntTag(NBT_SKY_COLOR, skyColor.asRGB());
		if (waterColor != null)
			writer.writeIntTag(NBT_WATER_COLOR, waterColor.asRGB());
		if (waterFogColor != null)
			writer.writeIntTag(NBT_WATER_FOG_COLOR, waterFogColor.asRGB());
		if (foliageColor != null)
			writer.writeIntTag(NBT_FOLIAGE_COLOR, foliageColor.asRGB());
		if (grassColor != null)
			writer.writeIntTag(NBT_GRASS_COLOR, grassColor.asRGB());
		if (grassColorModifier != GrassColorModifier.NONE)
			writer.writeStringTag(NBT_GRASS_COLOR_MODIFIER, grassColorModifier.getName());
		if (ambientSound != null)
			Sound.toNBT(NBT_AMBIENT_SOUND, ambientSound, writer, systemData);
		if (moodSound != null) {
			writer.writeCompoundTag(NBT_MOOD_SOUND);
			Sound.toNBT(NBT_SOUND, moodSound, writer, systemData);
			writer.writeIntTag(NBT_TICK_DELAY, moodTickDelay);
			writer.writeIntTag(NBT_BLOCK_SEARCH_EXTENT, moodBlockSearchExtent);
			writer.writeDoubleTag(NBT_OFFSET, moodOffset);
			writer.writeEndTag();
		}
		if (additionsSound != null) {
			writer.writeCompoundTag(NBT_ADDITIONS_SOUND);
			Sound.toNBT(NBT_SOUND, additionsSound, writer, systemData);
			writer.writeDoubleTag(NBT_OFFSET, addtionsTickChance);
			writer.writeEndTag();
		}
		if (music != null) {
			writer.writeCompoundTag(NBT_MUSIC);
			Sound.toNBT(NBT_SOUND, music, writer, systemData);
			writer.writeIntTag(NBT_MIN_DELAY, musicMinDelay);
			writer.writeIntTag(NBT_MAX_DELAY, musicMaxDelay);
			writer.writeByteTag(NBT_REPLACE_CURRENT_MUSIC, replaceCurrentMusic);
			writer.writeEndTag();
		}
	}

	@Override
	protected NBTFieldSet<? extends NBTHolder> getFieldSetRoot() {
		return NBT_FIELDS;
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
