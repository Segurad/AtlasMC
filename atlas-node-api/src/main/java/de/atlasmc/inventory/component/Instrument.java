package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.sound.EnumSound;
import de.atlasmc.sound.Sound;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

@RegistryHolder(key = "minecraft:instrument", target = Target.PROTOCOL)
public class Instrument extends ProtocolRegistryValueBase implements NBTSerializable {
	
	public static final NBTSerializationHandler<Instrument> NBT_HANDLER;
	
	private static final ProtocolRegistry<Instrument> REGISTRY;
	
	public static final CharKey
	NBT_SOUND_EVENT = CharKey.literal("sound_event"),
	NBT_USE_DURATION = CharKey.literal("use_duration"),
	NBT_RANGE = CharKey.literal("range"),
	NBT_DESCRIPTION = CharKey.literal("description");
	
	static {
		REGISTRY = Registries.createRegistry(Instrument.class);
		NBT_HANDLER = NBTSerializationHandler
						.builder(Instrument.class)
						.chat("description", Instrument::getDescription, Instrument::setDescription)
						.registryValue("sound_event", Instrument::getSound, Instrument::setSound, , null)
						.build();
	}
	
	private Sound sound;
	private float useDuration;
	private float range;
	private Chat description;
	
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
	
	public float getUseDuration() {
		return useDuration;
	}
	
	public float getRange() {
		return range;
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
