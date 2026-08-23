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

public class LlamaMetaContainer extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<LlamaMetaContainer>
	NBT_CODEC = NBTCodec
					.builder(LlamaMetaContainer.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.intField("Strength", LlamaMetaContainer::getStrength, LlamaMetaContainer::setStrength, 3)
					.codec("Variant", LlamaMetaContainer::getColor, LlamaMetaContainer::setColor, EnumUtil.enumIntNBTCodec(LlamaColor.class), LlamaColor.CREAMY)
					.build();
					
	
	public static final MetaDataField<Integer>
	META_LLAMA_STRENGTH = new MetaDataField<>(20, 0, EntityMetaTypes.VAR_INT);
	public static final MetaDataField<Integer>
	META_LLAMA_VARIANT = new MetaDataField<>(21, LlamaColor.CREAMY.getID(), EntityMetaTypes.VAR_INT);

	public LlamaMetaContainer(ComponentType type) {
		super(type);
	}

	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_LLAMA_STRENGTH);
		container.set(META_LLAMA_VARIANT);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 2;
	}
	
	public int getStrength() {
		return getHolder().getMetaContainer().getData(META_LLAMA_STRENGTH);
	}

	public LlamaColor getColor() {
		return EnumUtil.getByID(LlamaColor.class, getHolder().getMetaContainer().getData(META_LLAMA_VARIANT));
	}

	public void setStrength(int strength) {
		getHolder().getMetaContainer().setData(META_LLAMA_STRENGTH, strength);
	}

	public void setColor(LlamaColor color) {
		getHolder().getMetaContainer().setData(META_LLAMA_VARIANT, color.getID());
	}
	
	@Override
	public NBTCodec<? extends LlamaMetaContainer> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum LlamaColor implements IDHolder {
		
		CREAMY,
		WHITE,
		BROWN,
		GRAY;
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}
	
}
