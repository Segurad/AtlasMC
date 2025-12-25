package de.atlasmc.io.codec.constructor;

import java.util.function.Function;

import de.atlasmc.registry.ProtocolRegistryValue;
import de.atlasmc.registry.RegistryKey;

public class RegistryVarIntConstructor<T, K extends ProtocolRegistryValue> extends AbstractVarIntConstructor<T, K> {
	
	private final RegistryKey<K> registry; 
	
	public RegistryVarIntConstructor(RegistryKey<K> registry, Function<K, T> constructor, Function<T, K> keyReverseSupplier) {
		super(constructor, keyReverseSupplier);
		this.registry = registry;
	}

	@Override
	protected K getKey(int id) {
		return registry.getValue(id);
	}

}
