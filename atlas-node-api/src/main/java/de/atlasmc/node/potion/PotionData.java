package de.atlasmc.node.potion;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryKey;

@RegistryHolder(key = "minecraft:potion_data")
public class PotionData implements Namespaced {
	
	public static final RegistryKey<PotionData> REGISTRY_KEY = Registries.getRegistryKey(PotionData.class);
	
	public static Registry<PotionData> getRegistry() {
		return null;
	}

	@Override
	public NamespacedKey getNamespacedKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
