package de.atlasmc.inventory.component.effect;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.registry.RegistryValueKey;
import de.atlasmc.util.configuration.ConfigurationSection;

@RegistryHolder(key = "minecraft:component_effect", target = Target.PROTOCOL)
public class ComponentEffectType extends ProtocolRegistryValueBase {
	
	public static final RegistryKey<ComponentEffectType> REGISTRY_KEY = Registries.getRegistryKey(ComponentEffectType.class);
	
	public static final RegistryValueKey<ComponentEffectType>
	APPLY_EFFECTS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.of("minecraft:apply_effects")),
	REMOVE_EFFECTS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:remove_effects")),
	CLEAR_ALL_EFFECTS = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:clear_all_effects")),
	TELEPORT_RANDOMLY = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:teleport_randomly")),
	PLAY_SOUND = new RegistryValueKey<>(REGISTRY_KEY, NamespacedKey.literal("minecraft:play_sound"));
	
	private final ComponentEffectFactory factory;
	
	public ComponentEffectType(ConfigurationSection cfg) {
		super(cfg);
		Registry<ComponentEffectFactory> registry = Registries.getRegistry(ComponentEffectFactory.class);
		factory = registry.get(cfg.getString("effectFactory"));
	}
	
	public ComponentEffect createEffect() {
		return factory.createEffect(this);
	}

	public static ComponentEffectType get(CharSequence key) {
		return REGISTRY_KEY.getValue(key);
	}
	
	public static ComponentEffectType getByID(int id) {
		return getRegistry().getByID(id);
	}
	
	public static ProtocolRegistry<ComponentEffectType> getRegistry() {
		return REGISTRY_KEY.getRegistry();
	}
	
}
