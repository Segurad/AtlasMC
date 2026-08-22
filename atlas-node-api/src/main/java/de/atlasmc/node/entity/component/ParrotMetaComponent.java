package de.atlasmc.node.entity.component;

import de.atlasmc.IDHolder;
import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumUtil;

public class ParrotMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<ParrotMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(ParrotMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("Variant", ParrotMetaComponent::getParrotType, ParrotMetaComponent::setParrotType, EnumUtil.enumIntNBTCodec(Type.class), Type.RED_BLUE)
					.build();
	
	public static final MetaDataField<Integer>
	META_PARROT_TYPE = new MetaDataField<>(20, Type.RED_BLUE.getID(), EntityMetaTypes.VAR_INT);
	
	public ParrotMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_PARROT_TYPE);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}

	public Type getParrotType() {
		return EnumUtil.getByID(Type.class, getHolder().getMetaContainer().getData(META_PARROT_TYPE));
	}

	public void setParrotType(Type type) {
		getHolder().getMetaContainer().setData(META_PARROT_TYPE, type.getID());
	}
	
	@Override
	public NBTCodec<? extends ParrotMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum Type implements IDHolder {
		
		RED_BLUE,
		BLUE,
		GREEN,
		YELLOW_BLUE,
		GREY;
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}
	
}
