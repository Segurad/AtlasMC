package de.atlasmc.inventory.component;

import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.sound.EnumSound;
import de.atlasmc.sound.ResourceSound;
import de.atlasmc.sound.Sound;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

@RegistryHolder(key = "minecraft:instrument", target = Target.PROTOCOL)
public class Instrument extends ProtocolRegistryValueBase implements NBTSerializable {
	
	public static final NBTSerializationHandler<Instrument> NBT_HANDLER;
	
	private static final ProtocolRegistry<Instrument> REGISTRY;
	
	static {
		REGISTRY = Registries.createRegistry(Instrument.class);
		NBT_HANDLER = NBTSerializationHandler
						.builder(Instrument.class)
						.defaultConstructor(Instrument::new)
						.chat("description", Instrument::getDescription, Instrument::setDescription)
						.interfacedEnumStringField("sound_event", (Function<Instrument, Sound>) Instrument::getSound, Instrument::setSound, EnumSound::getByName, null)
						.compoundType("sound_event", (Function<Instrument, Sound>) Instrument::getSound, Instrument::setSound, ResourceSound.NBT_HANDLER)
						.floatField("use_duration", Instrument::getUseDuration, Instrument::setUseDuration, 0)
						.floatField("range", Instrument::getRange, Instrument::setRange, 0)
						.build();
	}
	
	private Sound sound;
	private float useDuration;
	private float range;
	private Chat description;
	
	public Instrument() {}
	
	public Instrument(Sound sound, float useDuration, float range, Chat description) {
		super();
		this.sound = sound;
		this.useDuration = useDuration;
		this.range = range;
		this.description = description;
	}
	
	public Instrument(NamespacedKey key, int id, Sound sound, float useDuration, float range, Chat description) {
		super(key, id);
		this.sound = sound;
		this.useDuration = useDuration;
		this.range = range;
		this.description = description;
	}
	
	public Sound getSound() {
		return sound;
	}
	
	public void setSound(Sound sound) {
		this.sound = sound;
	}
	
	public float getUseDuration() {
		return useDuration;
	}
	
	public void setUseDuration(float useDuration) {
		this.useDuration = useDuration;
	}
	
	public float getRange() {
		return range;
	}
	
	public void setRange(float range) {
		this.range = range;
	}
	
	public Chat getDescription() {
		return description;
	}
	
	private void setDescription(Chat description) {
		this.description = description;
	}

	public static Instrument get(NamespacedKey key) {
		return REGISTRY.get(key);
	}
	
	public static Instrument get(String key) {
		return REGISTRY.get(key);
	}
	
	public static Instrument getByID(int id) {
		return REGISTRY.getByID(id);
	}
	
	public static ProtocolRegistry<Instrument> getRegistry() {
		return REGISTRY;
	}
	
	@Override
	public NBTSerializationHandler<? extends Instrument> getNBTHandler() {
		return NBT_HANDLER;
	}

}
