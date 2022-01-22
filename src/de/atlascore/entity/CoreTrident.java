package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Trident;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreTrident extends CoreAbstractArrow implements Trident {

	protected static final MetaDataField<Integer>
	META_LOYALITY_LEVEL = new MetaDataField<>(CoreAbstractArrow.LAST_META_INDEX+1, 0, MetaDataType.INT);
	protected static final MetaDataField<Boolean>
	META_ENCHANTMENT_GLINT = new MetaDataField<>(CoreAbstractArrow.LAST_META_INDEX+2, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreAbstractArrow.LAST_META_INDEX+2;
	
	public CoreTrident(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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
	public ProjectileType getProjectileType() {
		return ProjectileType.TRIDENT;
	}

	@Override
	public void setLoyalityLevel(int level) {
		metaContainer.get(META_LOYALITY_LEVEL).setData(level);
	}

	@Override
	public void setEnchantmentGlint(boolean glint) {
		metaContainer.get(META_ENCHANTMENT_GLINT).setData(glint);
	}

}
