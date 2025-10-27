package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.ItemDisplay;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;
import de.atlasmc.node.inventory.ItemStack;

public class CoreItemDisplay extends CoreDisplay implements ItemDisplay {

	protected static final MetaDataField<ItemStack> META_DISPLAYED_ITEM = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+1, null, MetaDataType.SLOT);
	protected static final MetaDataField<RenderType> META_DISPLAY_TYPE = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+2, RenderType.NONE, MetaDataType.getByteEnumType(RenderType.class));
	
	protected static final int LAST_META_INDEX = CoreDisplay.LAST_META_INDEX+2;
	
	public CoreItemDisplay(EntityType type) {
		super(type);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_DISPLAYED_ITEM);
		metaContainer.set(META_DISPLAY_TYPE);
	}

	@Override
	public ItemStack getItem() {
		return metaContainer.getData(META_DISPLAYED_ITEM);
	}

	@Override
	public void setItem(ItemStack item) {
		metaContainer.get(META_DISPLAYED_ITEM).setData(item);
	}

	@Override
	public RenderType getRenderType() {
		return metaContainer.getData(META_DISPLAY_TYPE);
	}

	@Override
	public void setRenderType(RenderType renderType) {
		if (renderType == null)
			throw new IllegalArgumentException("RenderType can not be null!");
		metaContainer.get(META_DISPLAY_TYPE).setData(renderType);
	}

}
