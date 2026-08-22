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
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public class ItemDisplayMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<ItemDisplayMetaComponent>
	NBT_HANDLER = NBTCodec
					.builder(ItemDisplayMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("item", ItemDisplayMetaComponent::getItem, ItemDisplayMetaComponent::setItem, ItemStack.NBT_CODEC)
					.codec("item_display", ItemDisplayMetaComponent::getRenderType, ItemDisplayMetaComponent::setRenderType, EnumUtil.enumStringNBTCodec(RenderType.class), RenderType.NONE)
					.build();
	
	public static final MetaDataField<ItemStack> 
	META_DISPLAYED_ITEM = new MetaDataField<>(23, null, EntityMetaTypes.SLOT);
	public static final MetaDataField<Byte> 
	META_DISPLAY_TYPE = new MetaDataField<>(24, (byte) RenderType.NONE.getID(), EntityMetaTypes.BYTE);
	
	public ItemDisplayMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 2;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_DISPLAYED_ITEM);
		container.set(META_DISPLAY_TYPE);
	}

	public ItemStack getItem() {
		return getHolder().getMetaContainer().getData(META_DISPLAYED_ITEM);
	}

	public void setItem(ItemStack item) {
		getHolder().getMetaContainer().setData(META_DISPLAYED_ITEM, item);
	}

	public RenderType getRenderType() {
		return EnumUtil.getByID(RenderType.class, getHolder().getMetaContainer().getData(META_DISPLAY_TYPE));
	}

	public void setRenderType(RenderType renderType) {
		getHolder().getMetaContainer().setData(META_DISPLAY_TYPE, (byte) renderType.getID());
	}
	
	@Override
	public NBTCodec<? extends ItemDisplayMetaComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	public static enum RenderType implements EnumName, IDHolder {
		
		NONE,
		THIRDPERSON_LEFT_HAND,
		THIRDPERSON_RIGHT_HAND,
		FIRSTPERSON_LEFT_HAND,
		FIRSTPERSON_RIGHT_HAND,
		HEAD,
		GUI,
		GROUND,
		FIXED;
		
		private final String name;
		
		private RenderType() {
			name = name().toLowerCase();
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
