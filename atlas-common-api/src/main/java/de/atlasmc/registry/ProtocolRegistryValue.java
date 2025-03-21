package de.atlasmc.registry;

import de.atlasmc.IDHolder;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.nbt.NBTHolder;

public interface ProtocolRegistryValue extends Namespaced, IDHolder, NBTHolder {
	
	boolean hasNBT();

}
