package de.atlasmc.inventory.component.effect;

import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.util.factory.Factory;

@RegistryHolder(key="atlas:factory/component_effect_factory")
public interface ComponentEffectFactory extends Factory {

	public static final Registry<ComponentEffectFactory> REGISTRY = Registries.createRegistry(ComponentEffectFactory.class);
	
	ComponentEffect createEffect();
	
}
