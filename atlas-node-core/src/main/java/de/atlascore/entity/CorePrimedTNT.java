package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.PrimedTNT;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CorePrimedTNT extends CoreEntity implements PrimedTNT {

	protected static final MetaDataField<Integer>
	META_FUSE_TIME = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 80, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+1;
	
	private Entity source;
	private float explosionPower;
	private BlockData blockData;
	private UUID sourceUUID;
	
	public CorePrimedTNT(EntityType type) {
		super(type);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_FUSE_TIME);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public int getFuseTime() {
		return metaContainer.getData(META_FUSE_TIME);
	}

	@Override
	public void setFuseTime(int time) {
		if (time < 0)
			throw new IllegalArgumentException("Time can not be lower than 0: " + time);
		metaContainer.get(META_FUSE_TIME).setData(time);		
	}

	@Override
	public Entity getSource() {
		return source;
	}

	@Override
	public void setSource(Entity source) {
		this.source = source;
	}

	@Override
	public float getExplosionPower() {
		return explosionPower;
	}

	@Override
	public void setExplosionPower(float power) {
		this.explosionPower = power;
	}

	@Override
	public BlockData getBlockData() {
		return blockData;
	}

	@Override
	public void setBlockData(BlockData data) {
		this.blockData = data;
	}

	@Override
	public UUID getSourceUUID() {
		return sourceUUID;
	}

	@Override
	public void setSourceUUID(UUID uuid) {
		this.sourceUUID = uuid;
	}

}
