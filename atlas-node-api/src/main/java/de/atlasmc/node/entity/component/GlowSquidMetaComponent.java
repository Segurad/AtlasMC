package de.atlasmc.node.entity.component;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;

public class GlowSquidMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {
	
	@NotNull
	public static final NBTCodec<GlowSquidMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(GlowSquidMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.intField("DarkTicksRemaining", GlowSquidMetaComponent::getDarkTicksRemaining, GlowSquidMetaComponent::setDarkTicksRemaining, 0)
					.build();
	
	public static final MetaDataField<Integer>
	META_DARK_TICKS = new MetaDataField<Integer>(18, 0, EntityMetaTypes.VAR_INT);
	
	public GlowSquidMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_DARK_TICKS);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	public int getDarkTicksRemaining() {
		return getHolder().getMetaContainer().getData(META_DARK_TICKS);
	}
	
	public void setDarkTicksRemaining(int ticks) {
		getHolder().getMetaContainer().setData(META_DARK_TICKS, ticks);
	}
	
	@Override
	public NBTCodec<? extends GlowSquidMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
