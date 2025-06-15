package de.atlasmc.util.nbt.serialization;

import java.util.function.Function;

import de.atlasmc.registry.Registry;
import de.atlasmc.util.Builder;

public class NBTSerializationHandlerBuilder<T extends NBTSerializable> extends AbstractNBTCompoundFieldBuilder<T, NBTSerializationHandlerBuilder<T>>  implements Builder<NBTSerializationHandler<T>> {
	
	protected NBTSerializationHandlerBuilder() {
		
	}
	
	@Override
	public NBTSerializationHandler<T> build() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	
	public NBTSerializationHandlerBuilder<T> include(NBTSerializationHandler<? super T> include) {
		return this;
	}
	
	public <K> NBTSerializationHandlerBuilder<T> fieldKeyConstructor(Registry<K> registry, Function<K, T> constructor) {
		return this;
	}
	
	public <K> NBTSerializationHandlerBuilder<T> searchKeyConstructor(String keyField, Registry<K> registry, Function<K, T> constructor) {
		return this;
	}

	@Override
	protected NBTSerializationHandlerBuilder<T> getThis() {
		return this;
	}

}
