package de.atlasmc.node.enchantments;

import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.registry.RegistryKey;

@RegistryHolder(key = "minecraft:enchantment", target = Target.PROTOCOL)
public abstract class Enchantment extends ProtocolRegistryValueBase {

	public static final RegistryKey<Enchantment> REGISTRY_KEY = Registries.getRegistryKey(Enchantment.class);
	
	public static Enchantment LUCK;

	public abstract boolean conflictsWith(Enchantment echantment);

	public String getNamespacedName() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Enchantment getEnchantment(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Enchantment getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
