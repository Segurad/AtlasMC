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
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public class MooshroomMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	public static final MetaDataField<Integer>
	META_SHROOM_TYPE = new MetaDataField<>(18, Variant.RED.getID(), EntityMetaTypes.VAR_INT);
	
	public static final NBTCodec<MooshroomMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(MooshroomMetaComponent.class)
					.codec("Type", MooshroomMetaComponent::getVariant, MooshroomMetaComponent::setVariant, EnumUtil.enumStringNBTCodec(Variant.class), Variant.RED)
					.build();
	
	public MooshroomMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_SHROOM_TYPE);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}

	public Variant getVariant() {
		return EnumUtil.getByID(Variant.class, getHolder().getMetaContainer().getData(META_SHROOM_TYPE));
	}

	public void setVariant(Variant variant) {
		getHolder().getMetaContainer().setData(META_SHROOM_TYPE, variant.getID());
	}
	
	@Override
	public NBTCodec<? extends MooshroomMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum Variant implements EnumName, IDHolder {
		
		RED("red"),
		BROWN("brown");
		
		private String name;
		
		private Variant(String name) {
			this.name = name;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}
	
}
