package de.atlasmc.node.entity;

import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.registry.RegistryKey;

@RegistryHolder(key = "minecraft:damage_type", target = Target.PROTOCOL)
public class DamageType extends ProtocolRegistryValueBase {
	
	public static final RegistryKey<DamageType> REGISTRY_KEY = Registries.getRegistryKey(DamageType.class);

}
