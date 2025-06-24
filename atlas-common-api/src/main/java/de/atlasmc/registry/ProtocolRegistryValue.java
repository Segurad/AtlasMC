package de.atlasmc.registry;

import de.atlasmc.IDHolder;
import de.atlasmc.NamespacedKey.Namespaced;

public interface ProtocolRegistryValue extends Namespaced, IDHolder {
	
	boolean hasNBT();

}
