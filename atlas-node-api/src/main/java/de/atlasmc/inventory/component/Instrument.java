package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.sound.Sound;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

@RegistryHolder(key = "minecraft:instrument", target = Target.PROTOCOL)
public class Instrument extends ProtocolRegistryValueBase implements NBTSerializable {
	
	public static final NBTSerializationHandler<Instrument> NBT_HANDLER;
	
	public static final RegistryKey<Instrument> REGISTRY_KEY = Registries.getRegistryKey(Instrument.class);
	
	static {
		NBT_HANDLER = NBTSerializationHandler
						.builder(Instrument.class)
						.defaultConstructor(Instrument::new)
						.chat("description", Instrument::getDescription, Instrument::setDescription)
						.addField(Sound.getNBTSoundField("sound_event", Instrument::getSound, Instrument::setSound, null))
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
		return getRegistry().get(key);
	}
	
	public static Instrument get(String key) {
		return getRegistry().get(key);
	}
	
	public static Instrument getByID(int id) {
		return getRegistry().getByID(id);
	}
	
	public static ProtocolRegistry<Instrument> getRegistry() {
		return REGISTRY_KEY.getRegistry();
	}
	
	@Override
	public NBTSerializationHandler<? extends Instrument> getNBTHandler() {
		return NBT_HANDLER;
	}

}
