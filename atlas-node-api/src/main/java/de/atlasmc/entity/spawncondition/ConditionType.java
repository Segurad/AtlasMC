package de.atlasmc.entity.spawncondition;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryKey;

@RegistryHolder(key = "atlas:entity/spawn_condition_type")
public class ConditionType implements Namespaced {
	
	public static final RegistryKey<ConditionType> REGISTRY_KEY = Registries.getRegistryKey(ConditionType.class);
	
	public Condition createCondition() {
		return null;// TODO
	}

	@Override
	public NamespacedKey getNamespacedKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
