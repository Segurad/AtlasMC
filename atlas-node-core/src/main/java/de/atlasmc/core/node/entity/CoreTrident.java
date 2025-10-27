package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Trident;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;
import de.atlasmc.node.inventory.ItemStack;

public class CoreTrident extends CoreAbstractArrow implements Trident {

	protected static final MetaDataField<Integer>
	META_LOYALITY_LEVEL = new MetaDataField<>(CoreAbstractArrow.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Boolean>
	META_ENCHANTMENT_GLINT = new MetaDataField<>(CoreAbstractArrow.LAST_META_INDEX+2, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAbstractArrow.LAST_META_INDEX+2;
	
	private ItemStack item;
	private boolean dealtDamage;
	
	public CoreTrident(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_LOYALITY_LEVEL);
		metaContainer.set(META_ENCHANTMENT_GLINT);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public int getLoyalityLevel() {
		return metaContainer.getData(META_LOYALITY_LEVEL);
	}

	@Override
	public boolean hasEnchantmentGlint() {
		return metaContainer.getData(META_ENCHANTMENT_GLINT);
	}

	@Override
	public void setLoyalityLevel(int level) {
		metaContainer.get(META_LOYALITY_LEVEL).setData(level);
	}

	@Override
	public void setEnchantmentGlint(boolean glint) {
		metaContainer.get(META_ENCHANTMENT_GLINT).setData(glint);
	}

	@Override
	public void setItem(ItemStack item) {
		this.item = item;
	}

	@Override
	public ItemStack getItem() {
		return item;
	}

	@Override
	public boolean hasItem() {
		return item != null;
	}

	@Override
	public boolean hasDealtDamage() {
		return dealtDamage;
	}

	@Override
	public void setDealtDamage(boolean dealtDamage) {
		this.dealtDamage = dealtDamage;
	}
	
}
