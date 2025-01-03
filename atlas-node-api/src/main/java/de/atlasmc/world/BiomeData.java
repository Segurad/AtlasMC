package de.atlasmc.world;

import java.io.IOException;
import java.util.List;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.util.nbt.io.NBTWriter;

public class BiomeData extends AbstractNBTBase {

	protected static final NBTFieldSet<BiomeData> NBT_FIELDS;
	
	protected static final CharKey
	NBT_HAS_PRECIPITATION = CharKey.literal("has_precipitation"),
	NBT_TEMPERATURE = CharKey.literal("temperature"),
	NBT_TEMPERATURE_MODIFIER = CharKey.literal("temperature_modifier"),
	NBT_DOWNFALL = CharKey.literal("downfall"),
	NBT_EFFECTS = CharKey.literal("effects"),
	NBT_CARVES = CharKey.literal("carves"),
	NBT_FEATURES = CharKey.literal("features"),
	NBT_CREATURE_SPAWN_PROBABILITY = CharKey.literal("creature_spawn_probability"),
	NBT_SPAWNERS = CharKey.literal("spawners"),
	NBT_SPAWNN_COSTS = CharKey.literal("spawn_costs");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_HAS_PRECIPITATION, (holder, reader) -> {
			holder.precipitation = reader.readBoolean();
		});
		NBT_FIELDS.setField(NBT_TEMPERATURE, (holder, reader) -> {
			holder.temperature = reader.readFloatTag();
		});
		NBT_FIELDS.setField(NBT_TEMPERATURE_MODIFIER, (holder, reader) -> {
			holder.setTemperatureModifier(TemperatureModifier.getByName(reader.readStringTag()));
		});
		NBT_FIELDS.setField(NBT_DOWNFALL, (holder, reader) -> {
			holder.downfall = reader.readFloatTag();
		});
		NBT_FIELDS.setField(NBT_EFFECTS, (holder, reader) -> {
			reader.readNextEntry();
			holder.effects = new BiomeEffects();
			holder.effects.fromNBT(reader);
		});
		NBT_FIELDS.setField(NBT_CARVES, NBTField.skip()); // Separate implementation with world generation
		NBT_FIELDS.setField(NBT_FEATURES, NBTField.skip()); // Separate implementation with world generation
		NBT_FIELDS.setField(NBT_CREATURE_SPAWN_PROBABILITY, (holder, reader) -> {
			holder.creatureSpawnProbability = reader.readFloatTag();
		});
		NBT_FIELDS.setField(NBT_SPAWNERS, NBTField.skip()); // TODO Biome spawner
		NBT_FIELDS.setField(NBT_SPAWNN_COSTS, NBTField.skip()); // TODO Biome spawner
	}
	
	private boolean precipitation;
	private float temperature;
	private TemperatureModifier temperatureModifier;
	private float downfall;
	private BiomeEffects effects;
	private float creatureSpawnProbability;
	
	public BiomeData() {
		temperatureModifier = TemperatureModifier.NONE;
	}
	
	public float getCreatureSpawnProbability() {
		return creatureSpawnProbability;
	}
	
	public void setCreatureSpawnProbability(float creatureSpawnProbability) {
		this.creatureSpawnProbability = creatureSpawnProbability;
	}
	
	public void setEffect(BiomeEffects effects) {
		this.effects = effects;
	}
	
	public TemperatureModifier getTemperatureModifier() {
		return temperatureModifier;
	}
	
	public void setTemperatureModifier(TemperatureModifier modifier) {
		if (modifier == null)
			throw new IllegalArgumentException("Modifier can not be null!");
		this.temperatureModifier = modifier;
	}
	
	public BiomeEffects getEffect() {
		return effects;
	}

	public float getTemperature() {
		return temperature;
	}

	public float getDownfall() {
		return downfall;
	}
	
	public boolean hasPrecipitation() {
		return precipitation;
	}

	public void setPrecipitation(boolean precipitation) {
		this.precipitation = precipitation;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public void setDownfall(float downfall) {
		this.downfall = downfall;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeByteTag(NBT_HAS_PRECIPITATION, precipitation);
		writer.writeFloatTag(NBT_TEMPERATURE, temperature);
		if (temperatureModifier != TemperatureModifier.NONE)
			writer.writeStringTag(NBT_TEMPERATURE, temperatureModifier.name);
		writer.writeFloatTag(NBT_DOWNFALL, downfall);
		if (effects != null) {
			writer.writeCompoundTag(NBT_EFFECTS);
			effects.toNBT(writer, systemData);
			writer.writeEndTag();
		}
		writer.writeFloatTag(NBT_CREATURE_SPAWN_PROBABILITY, creatureSpawnProbability);
	}

	@Override
	protected NBTFieldSet<? extends NBTHolder> getFieldSetRoot() {
		return NBT_FIELDS;
	}
	
	public static enum TemperatureModifier implements EnumName, EnumValueCache {
		
		NONE,
		FROZEN;

		private static List<TemperatureModifier> VALUES;
		
		private final String name;
		
		private TemperatureModifier() {
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
		public static TemperatureModifier getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<TemperatureModifier> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				TemperatureModifier value = values.get(i);
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
		public static List<TemperatureModifier> getValues() {
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
