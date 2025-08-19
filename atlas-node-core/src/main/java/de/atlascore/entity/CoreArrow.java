package de.atlascore.entity;

import de.atlasmc.Color;
import de.atlasmc.entity.Arrow;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.ItemType;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.PotionContentsComponent;

public class CoreArrow extends CoreAbstractArrow implements Arrow {

	protected static final MetaDataField<Integer>
	META_COLOR = new MetaDataField<>(CoreAbstractArrow.LAST_META_INDEX+1, -1, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreAbstractArrow.LAST_META_INDEX+1;
	
	public CoreArrow(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_COLOR);
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public Color getColor() {
		int color = getColorRGB();
		return color == -1 ? null : Color.fromRGB(color);
	}

	@Override
	public void setColor(Color color) {
		setColorRGB(color == null ? -1 : color.asRGB());
	}

	@Override
	public int getColorRGB() {
		return metaContainer.getData(META_COLOR);
	}

	@Override
	public void setColorRGB(int rgb) {
		if (rgb < 0)
			metaContainer.get(META_COLOR).setData(-1);
		else
			metaContainer.get(META_COLOR).setData(rgb & 0xFFFFFF);
	}
	
	@Override
	public void setItem(ItemStack item) {
		super.setItem(item);
		if (item == null) {
			setColor(null);
		} else {
			PotionContentsComponent comp = item.getEffectiveComponent(ComponentType.get(ComponentType.POTION_CONTENTS));
			if (comp == null) {
				setColor(null);
			} else {
				setColor(comp.getCustomColor());
			}
		}
	}

	@Override
	public PotionContentsComponent getPotionContents() {
		ItemStack item = getItem();
		return item == null ? null : item.getEffectiveComponent(ComponentType.get(ComponentType.POTION_CONTENTS));
	}

	@Override
	public void setPotionContents(PotionContentsComponent contents) {
		ItemStack item = getItem();
		if (item == null)
			item = new ItemStack(ItemType.TIPPED_ARROW);
		item.setComponent(contents);
		setItem(item);
	}
	
	

}
