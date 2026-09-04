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

public class RabbitMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<RabbitMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(RabbitMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("RabbitType", RabbitMetaComponent::getRabbitType, RabbitMetaComponent::setRabbitType, EnumUtil.enumIntNBTCodec(Type.class), Type.BROWN)
					.build();
	
	public static final MetaDataField<Integer>
	META_RABBIT_TYPE = new MetaDataField<>(18, 0, EntityMetaTypes.VAR_INT);

	public RabbitMetaComponent(ComponentType type) {
		super(type);
	}

	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_RABBIT_TYPE);
	}
	
	public Type getRabbitType() {
		return EnumUtil.getByID(Type.class, getHolder().getMetaContainer().getData(META_RABBIT_TYPE));
	}

	public void setRabbitType(Type type) {
		getHolder().getMetaContainer().setData(META_RABBIT_TYPE, type.getID());		
	}
	
	@Override
	public NBTCodec<? extends RabbitMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum Type implements IDHolder {
		
		BROWN(0),
		WHITE(1),
		BLACK(2),
		BLACK_AND_WIHTE(3),
		GOLD(4),
		SALT_AND_PEPPER(5),
		THE_KILLER_BUNNY(99),
		TOAST(Integer.MAX_VALUE);
		
		private int id;
		
		private Type(int id) {
			this.id = id;
		}
		
		@Override
		public int getID() {
			return id;
		}
		
	}
	
}
