package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.sound.Sound;

@RegistryHolder(key = "minecraft:instrument", target = Target.PROTOCOL)
public class Instrument extends ProtocolRegistryValueBase {
	
	private static final ProtocolRegistry<Instrument> REGISTRY;
	
	static {
		REGISTRY = Registries.createRegistry(Instrument.class);
	}
	
	private final Sound sound;
	private final float useDuration;
	private final float range;
	private final Chat description;
	
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

}
