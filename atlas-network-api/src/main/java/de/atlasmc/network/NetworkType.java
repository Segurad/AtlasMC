package de.atlasmc.network;

import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.registry.RegistryValueKey;
import static de.atlasmc.registry.RegistryValueKey.ofLiteral;

@RegistryHolder(key = "atlas:network-type", target = Target.PROTOCOL)
public class NetworkType extends ProtocolRegistryValueBase {
	
	public static final RegistryKey<NetworkType> REGISTRY_KEY = Registries.getRegistryKey(NetworkType.class);
	
	public static final RegistryValueKey<NetworkType>
	SERVER = ofLiteral(REGISTRY_KEY, "atlas:server"),
	SERVER_GROUP = ofLiteral(REGISTRY_KEY, "altas:server-group"),
	PLAYER = ofLiteral(REGISTRY_KEY, "atlas:player"),
	NODE = ofLiteral(REGISTRY_KEY, "atlas:node"),
	SOCKET = ofLiteral(REGISTRY_KEY, "atlas:socket")
	;

}
