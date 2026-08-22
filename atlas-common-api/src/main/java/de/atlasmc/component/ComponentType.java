package de.atlasmc.component;

import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.registry.RegistryKey;

@RegistryHolder(key = "atlas:component", target = Target.INSTANCE)
public class ComponentType {

	public static final RegistryKey<ComponentType> REGISTRY_KEY = Registries.getRegistryKey(ComponentType.class);

	public Component createComponent() {
		// TODO create component
		return null;
	}
	
}
