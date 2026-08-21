package de.atlasmc.core.node.entity;

import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.node.entity.Creeper;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.entity.EntityType;

public class CoreCreeper extends CoreMob implements Creeper {
	
	protected static final MetaDataField<Integer>
	META_CREEPER_STATE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, -1, EntityMetaTypes.VAR_INT);
	protected static final MetaDataField<Boolean>
	META_IS_CHARGED = new MetaDataField<>(CoreMob.LAST_META_INDEX+2, false, EntityMetaTypes.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_IS_IGNITED = new MetaDataField<>(CoreMob.LAST_META_INDEX+3, false, EntityMetaTypes.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+3;
	
	private int fuzeTime = -1;
	private int radius = 3;
	
	public CoreCreeper(EntityType type) {
		super(type);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_CREEPER_STATE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public int getFuseTime() {
		return fuzeTime;
	}

	@Override
	public boolean isChared() {
		return metaContainer.getData(META_IS_CHARGED);
	}

	@Override
	public boolean isIgnited() {
		return metaContainer.getData(META_IS_IGNITED);
	}

	@Override
	public void setFuseTime(int fuze) {
		this.fuzeTime = fuze;
	}

	@Override
	public void setChared(boolean charged) {
		metaContainer.setData(META_IS_CHARGED, charged);
	}

	@Override
	public void setIgnited(boolean ignited) {
		metaContainer.setData(META_IS_IGNITED, ignited);
	}

	@Override
	public boolean isFusing() {
		return metaContainer.getData(META_CREEPER_STATE) == 1;
	}

	@Override
	public void setFusing(boolean fuzing) {
		metaContainer.setData(META_CREEPER_STATE, fuzing ? 1 : -1);
	}

	@Override
	public void setExplosionRadius(int radius) {
		if (radius > 127)
			throw new IllegalArgumentException("Radius can not be higher than 127: " + radius);
		this.radius = Math.max(radius, 0);	
	}

	@Override
	public int getExplosionRadius() {
		return radius;
	}
	
}
