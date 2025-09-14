package de.atlasmc.core.node.entity;

import org.joml.Vector3i;

import de.atlasmc.node.entity.Dolphin;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;
import de.atlasmc.node.util.MathUtil;

public class CoreDolphin extends CoreMob implements Dolphin {

	protected static final MetaDataField<Long>
	META_TREASURE_POSITION = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, 0L, MetaDataType.POSITION);
	protected static final MetaDataField<Boolean>
	META_HAS_FISH = new MetaDataField<>(CoreMob.LAST_META_INDEX+2, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Integer>
	META_MOISTURE_LEVEL = new MetaDataField<>(CoreMob.LAST_META_INDEX+3, 2400, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+3;
	
	public CoreDolphin(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_TREASURE_POSITION);
		metaContainer.set(META_HAS_FISH);
		metaContainer.set(META_MOISTURE_LEVEL);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean hasFish() {
		return metaContainer.getData(META_HAS_FISH);
	}

	@Override
	public Vector3i getTreasurePosition(Vector3i loc) {
		return MathUtil.getPositionVector(loc, metaContainer.getData(META_TREASURE_POSITION));
	}

	@Override
	public void setTreasurePosition(int x, int y, int z) {
		metaContainer.get(META_TREASURE_POSITION).setData(MathUtil.toPosition(x, y, z));
	}

	@Override
	public void setFish(boolean fish) {
		metaContainer.get(META_HAS_FISH).setData(fish);
	}

	@Override
	public int getMoistureLevel() {
		return metaContainer.getData(META_MOISTURE_LEVEL);
	}

	@Override
	public void setMoistureLevel(int level) {
		if (level > 2400 || level < 0)
			throw new IllegalArgumentException("Level is not between 0 and 2400: " + level);
		metaContainer.get(META_MOISTURE_LEVEL).setData(level);
	}

	@Override
	public int getMaxMoistureLevel() {
		return 2400;
	}

}
