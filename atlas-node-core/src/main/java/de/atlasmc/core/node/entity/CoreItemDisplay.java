package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.ItemDisplay;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.enums.EnumUtil;

public class CoreItemDisplay extends CoreDisplay implements ItemDisplay {

	protected static final MetaDataField<ItemStack> 
	META_DISPLAYED_ITEM = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+1, null, EntityMetaTypes.SLOT);
	protected static final MetaDataField<Byte> 
	META_DISPLAY_TYPE = new MetaDataField<>(CoreDisplay.LAST_META_INDEX+2, (byte) RenderType.NONE.getID(), EntityMetaTypes.BYTE);
	
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
		metaContainer.setData(META_DISPLAYED_ITEM, item);
	}

	@Override
	public RenderType getRenderType() {
		return EnumUtil.getByID(RenderType.class, metaContainer.getData(META_DISPLAY_TYPE));
	}

	@Override
	public void setRenderType(RenderType renderType) {
		metaContainer.setData(META_DISPLAY_TYPE, (byte) renderType.getID());
	}

}
