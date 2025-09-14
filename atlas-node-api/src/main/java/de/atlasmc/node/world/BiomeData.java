package de.atlasmc.node.world;

import java.util.List;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class BiomeData implements NBTSerializable {
	
	public static final NBTSerializationHandler<BiomeData>
	NBT_HANDLER = NBTSerializationHandler
					.builder(BiomeData.class)
					.boolField("has_precipitation", BiomeData::hasPrecipitation, BiomeData::setPrecipitation, false)
					.floatField("temparature", BiomeData::getTemperature, BiomeData::setTemperature, 0)
					.enumStringField("temperature_modifier", BiomeData::getTemperatureModifier, BiomeData::setTemperatureModifier, TemperatureModifier::getByName, TemperatureModifier.NONE)
					.floatField("downfall", BiomeData::getDownfall, BiomeData::setDownfall, 0)
					.typeCompoundField("effects", BiomeData::getEffects, BiomeData::setEffects, BiomeEffects.NBT_HANDLER)
					.build();
	
	private boolean precipitation;
	private float temperature;
	private TemperatureModifier temperatureModifier;
	private float downfall;
	private BiomeEffects effects;
	
	public BiomeData() {
		temperatureModifier = TemperatureModifier.NONE;
	}
	
	public void setEffects(BiomeEffects effects) {
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
	
	public BiomeEffects getEffects() {
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
	public NBTSerializationHandler<? extends BiomeData> getNBTHandler() {
		return NBT_HANDLER;
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
