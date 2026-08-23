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

public class AxolotlMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {
	
	@NotNull
	public static final NBTCodec<AxolotlMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(AxolotlMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("Variant", AxolotlMetaComponent::getVariant, AxolotlMetaComponent::setVariant, EnumUtil.enumIntNBTCodec(Variant.class), Variant.LUCY)
					.build();
	
	public static final MetaDataField<Integer>
	META_VARIANT = new MetaDataField<>(18, Variant.LUCY.getID(), EntityMetaTypes.VAR_INT);
	public static final MetaDataField<Boolean>
	META_IS_PLAYING_DEAD = new MetaDataField<>(19, false, EntityMetaTypes.BOOLEAN);
	public static final MetaDataField<Boolean>
	META_IS_FROM_BUCKET = new MetaDataField<>(20, false, EntityMetaTypes.BOOLEAN);
	
	public AxolotlMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 3;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_VARIANT);
		container.set(META_IS_PLAYING_DEAD);
		container.set(META_IS_FROM_BUCKET);
	}

	public Variant getVariant() {
		return EnumUtil.getByID(Variant.class, getHolder().getMetaContainer().getData(META_VARIANT));
	}

	public void setVariant(Variant variant) {
		getHolder().getMetaContainer().setData(META_VARIANT, variant.getID());
	}

	public boolean isFromBucket() {
		return getHolder().getMetaContainer().getData(META_IS_FROM_BUCKET);
	}

	public void setFromBucket(boolean bucket) {
		getHolder().getMetaContainer().setData(META_IS_FROM_BUCKET, bucket);
	}
	
	public boolean isPlayingDead() {
		return getHolder().getMetaContainer().getData(META_IS_PLAYING_DEAD);
	}
	
	public void setPlayingDead(boolean playing) {
		getHolder().getMetaContainer().setData(META_IS_PLAYING_DEAD, playing);
	}

	@Override
	public NBTCodec<? extends AxolotlMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum Variant implements IDHolder {
		
		LUCY,
		WILD,
		GOLD,
		CYAN,
		BLUE;
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}

}
