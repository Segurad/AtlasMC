package de.atlasmc.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.util.annotation.NotNull;

public interface Component extends NBTSerializable {
	
	@NotNull
	public static final NBTCodec<Component> 
	NBT_CODEC = NBTCodec
					.builder(Component.class)
					.fieldKeyRegistryConstructor(ComponentType.REGISTRY_KEY, ComponentType::createComponent)
					.build();
	
	@NotNull
	ComponentType getType();
	
	@Override
	default NBTCodec<? extends Component> getNBTCodec() {
		return NBT_CODEC;
	}

}
