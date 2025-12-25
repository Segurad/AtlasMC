package de.atlasmc.io.codec.constructor;

import java.util.function.Function;

import de.atlasmc.IDHolder;
import de.atlasmc.util.enums.EnumUtil;

public class EnumVarIntConstructor<T, K extends Enum<K> & IDHolder> extends AbstractVarIntConstructor<T, K> {

	private final Class<K> clazz;
	
	public EnumVarIntConstructor(Class<K> clazz, Function<K, T> constructor, Function<T, K> keyReverseSupplier) {
		super(constructor, keyReverseSupplier);
		this.clazz = clazz;
	}

	@Override
	protected K getKey(int id) {
		return EnumUtil.getByID(clazz, id);
	}

}
