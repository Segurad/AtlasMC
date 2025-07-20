package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.ItemDisplay;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ItemStack;

public class CoreItemDisplay extends CoreDisplay implements ItemDisplay {

	protected static final MetaDataField<ItemStack> META_DISPLAYED_ITEM = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+1, null, MetaDataType.SLOT);
	protected static final MetaDataField<Byte> META_DISPLAY_TYPE = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+2, (byte) 0, MetaDataType.BYTE);
	
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
		return RenderType.getByID(metaContainer.getData(META_DISPLAY_TYPE));
	}

	@Override
	public void setRenderType(RenderType renderType) {
		if (renderType == null)
			throw new IllegalArgumentException("RenderType can not be null!");
		metaContainer.get(META_DISPLAY_TYPE).setData((byte) renderType.getID());
	}

}
