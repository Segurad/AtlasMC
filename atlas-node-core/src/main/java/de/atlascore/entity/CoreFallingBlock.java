package de.atlascore.entity;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.FallingBlock;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CoreFallingBlock extends CoreEntity implements FallingBlock {
	
	protected static final MetaDataField<Long> 
	META_SPAWN_POS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 0L, MetaDataType.POSITION);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+1;
	
	private boolean canHurtEntities;
	private boolean dropItem;
	private boolean cancelDrop;
	private float damage;
	private int maxDamage = 40;
	private BlockData data;
	private TileEntity tile;
	private int age;
	
	public CoreFallingBlock(EntityType type) {
		super(type);
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
		return canHurtEntities;
	}

	@Override
	public BlockData getBlockData() {
		return data;
	}

	@Override
	public boolean getDropItem() {
		return dropItem;
	}

	@Override
	public BlockType getBlockType() {
		return data == null ? tile == null ? null : tile.getType() : data.getType();
	}

	@Override
	public void setDropItem(boolean drop) {
		this.dropItem = drop;
	}

	@Override
	public void setHurtEntities(boolean hurtEntities) {
		this.canHurtEntities = hurtEntities;
	}

	@Override
	public void setBlockData(BlockData data) {
		this.data = data;
	}

	@Override
	public void setBlockDataType(BlockType type) {
		if (type == null)
			this.data = null;
		else
			this.data = type.createBlockData();
	}

	@Override
	public void setMaxDamage(int damage) {
		this.maxDamage = damage;
	}

	@Override
	public int getMaxDamage() {
		return maxDamage;
	}

	@Override
	public void setTileEntity(TileEntity tile) {
		this.tile = tile;
	}

	@Override
	public TileEntity getTileEntity() {
		return tile;
	}

	@Override
	public float getDamagePerBlock() {
		return damage;
	}

	@Override
	public void setDamagePerBlock(float damage) {
		this.damage = damage;
	}

	@Override
	public boolean getCancelDrop() {
		return cancelDrop;
	}

	@Override
	public void setCancelDrop(boolean cancel) {
		this.cancelDrop = cancel;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}
	
}
