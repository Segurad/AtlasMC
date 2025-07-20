package de.atlasmc.inventory.component.effect;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.configuration.ConfigurationSection;

@RegistryHolder(key = "minecraft:component_effect", target = Target.PROTOCOL)
public class ComponentEffectType extends ProtocolRegistryValueBase {
	
	private static final ProtocolRegistry<ComponentEffectType> REGISTRY;
	
	static {
		REGISTRY = Registries.getRegistry(ComponentEffectType.class);
	}
	
	public static final NamespacedKey
	APPLY_EFFECTS = NamespacedKey.of("minecraft:apply_effects"),
	REMOVE_EFFECTS = NamespacedKey.literal("minecraft:remove_effects"),
	CLEAR_ALL_EFFECTS = NamespacedKey.literal("minecraft:clear_all_effects"),
	TELEPORT_RANDOMLY = NamespacedKey.literal("minecraft:teleport_randomly"),
	PLAY_SOUND = NamespacedKey.literal("minecraft:play_sound");
	
	
	private final ComponentEffectFactory factory;
	
	public ComponentEffectType(ConfigurationSection cfg) {
		super(cfg);
		Registry<ComponentEffectFactory> registry = Registries.getRegistry(ComponentEffectFactory.class);
		factory = registry.get(cfg.getString("effectFactory"));
	}
	
	public ComponentEffect createEffect() {
		return factory.createEffect(this);
	}

	public static ComponentEffectType get(NamespacedKey key) {
		return REGISTRY.get(key);
	}
	
	public static ComponentEffectType get(String key) {
		return REGISTRY.get(key);
	}
	
	public static ComponentEffectType getByID(int id) {
		return REGISTRY.getByID(id);
	}
	
	public static ProtocolRegistry<ComponentEffectType> getRegistry() {
		return REGISTRY;
	}
	
}
