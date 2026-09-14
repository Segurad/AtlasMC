package de.atlasmc.network;

import static de.atlasmc.registry.RegistryValueKey.ofLiteral;

import java.util.Objects;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.registry.RegistryValueKey;

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
	
	private final StreamCodec<?> codec;
	
	public NetworkType(NamespacedKey key, int id, StreamCodec<?> codec) {
		super(key, id);
		this.codec = Objects.requireNonNull(codec, "codec");
	}
	
	public StreamCodec<?> getCodec() {
		return codec;
	}

}
