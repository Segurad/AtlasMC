package de.atlascore.entity;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.FallingBlock;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;

import java.util.UUID;

public class CoreFallingBlock extends CoreEntity implements FallingBlock {
	
	protected static final MetaDataField<Long> 
	META_SPAWN_POS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 0L, MetaDataType.POSISTION);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+1;
	
	public CoreFallingBlock(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_SPAWN_POS);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public boolean canHurtEntities() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BlockData getBlockData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getDropItems() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDropItems(boolean drop) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHurtEntities(boolean hurtEntities) {
		// TODO Auto-generated method stub
		
	}

}
