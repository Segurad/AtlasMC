package de.atlascore.entity;

import de.atlasmc.Location;
import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.FallingBlock;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataType;

import java.util.UUID;

public class CoreFallingBlock extends CoreEntity implements FallingBlock {
	
	protected static final int
	META_SPAWN_POS = 8;
	
	public CoreFallingBlock(int id, EntityType type, Location loc, UUID uuid) {
		super(id, type, loc, uuid);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(new MetaData<Long>(META_SPAWN_POS, MetaDataType.POSISTION, 0L));
	}
	
	@Override
	protected int getMetaContainerSize() {
		return super.getMetaContainerSize()+1;
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
