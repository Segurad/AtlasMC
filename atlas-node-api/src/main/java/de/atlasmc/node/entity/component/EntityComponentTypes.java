package de.atlasmc.node.entity.component;

import de.atlasmc.component.ComponentType;
import de.atlasmc.registry.RegistryValueKey;
import static de.atlasmc.registry.RegistryValueKey.ofLiteral;
import static de.atlasmc.component.ComponentType.REGISTRY_KEY;

public final class EntityComponentTypes {
	
	private EntityComponentTypes() {
		// not required
	}
	
	public static final RegistryValueKey<ComponentType>
	HANGING_META = ofLiteral(REGISTRY_KEY, "atlas:entity/hanging_meta")
	;

}
