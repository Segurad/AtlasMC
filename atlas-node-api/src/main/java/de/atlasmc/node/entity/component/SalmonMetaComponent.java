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
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public class SalmonMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<SalmonMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(SalmonMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("type", SalmonMetaComponent::getSalmonType, SalmonMetaComponent::setSalmonType, EnumUtil.enumStringNBTCodec(Type.class), Type.MEDIUM)
					.build();

	public static final MetaDataField<Integer>
	META_SALMON_TYPE = new MetaDataField<>(17, Type.MEDIUM.getID(), EntityMetaTypes.VAR_INT);
	
	public SalmonMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_SALMON_TYPE);
	}

	public Type getSalmonType() {
		return EnumUtil.getByID(Type.class, getHolder().getMetaContainer().getData(META_SALMON_TYPE));
	}

	public void setSalmonType(Type type) {
		getHolder().getMetaContainer().set(META_SALMON_TYPE, type.getID());
	}
	
	@Override
	public NBTCodec<? extends SalmonMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum Type implements EnumName, IDHolder {
		
		SMALL,
		MEDIUM,
		LARGE;

		private final String name;
		
		private Type() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getID() {
			return ordinal() ;
		}
		
	}
	
}
