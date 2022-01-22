package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.WitherSkull;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

public class CoreWitherSkull extends CoreFireball implements WitherSkull {

	protected static final MetaDataField<Boolean>
	META_SKULL_CHARGED = new MetaDataField<>(CoreFireball.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreFireball.LAST_META_INDEX+1;
	
	public CoreWitherSkull(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_SKULL_CHARGED);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public ProjectileType getProjectileType() {
		return ProjectileType.WITHER_SKULL;
	}

	@Override
	public boolean isCharged() {
		return metaContainer.getData(META_SKULL_CHARGED);
	}

	@Override
	public void setCharged(boolean charged) {
		metaContainer.get(META_SKULL_CHARGED).setData(charged);		
	}

}
