package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Wither;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;

public class CoreWither extends CoreMob implements Wither {

	protected static final MetaDataField<Integer>
	META_TARGET_CENTER_HEAD = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Integer>
	META_TARGET_LEFT_HEAD = new MetaDataField<>(CoreMob.LAST_META_INDEX+2, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Integer>
	META_TARGET_RIGHT_HEAD = new MetaDataField<>(CoreMob.LAST_META_INDEX+3, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Integer>
	META_INVULNERABLE_TIME = new MetaDataField<>(CoreMob.LAST_META_INDEX + 4, 0, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+4;
	
	private Entity targetCenter;
	private Entity targetLeft;
	private Entity targetRight; 
	
	public CoreWither(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_TARGET_CENTER_HEAD);
		metaContainer.set(META_TARGET_LEFT_HEAD);
		metaContainer.set(META_TARGET_RIGHT_HEAD);
		metaContainer.set(META_INVULNERABLE_TIME);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public Entity getCenterHeadTarget() {
		return targetCenter;
	}

	@Override
	public Entity getLeftHeadTarget() {
		return targetLeft;
	}

	@Override
	public Entity getRightHeadTarget() {
		return targetRight;
	}

	@Override
	public int getInvulnerableTime() {
		return metaContainer.getData(META_INVULNERABLE_TIME);
	}

	@Override
	public void setInvulnerableTime(int time) {
		if (time < 0)
			throw new IllegalArgumentException("Time can not be lower than 0: " + time);
		metaContainer.get(META_INVULNERABLE_TIME).setData(time);
	}

	@Override
	public void setCenterHeadTarget(Entity entity) {
		setHeadTarget(META_TARGET_CENTER_HEAD, entity);
		this.targetCenter = entity;
	}

	@Override
	public void setLeftHeadTarget(Entity entity) {
		setHeadTarget(META_TARGET_LEFT_HEAD, entity);
		this.targetLeft = entity;
	}

	@Override
	public void setRightHeadTarget(Entity entity) {
		setHeadTarget(META_TARGET_RIGHT_HEAD, entity);
		this.targetRight = entity;
	}
	
	private void setHeadTarget(MetaDataField<Integer> head, Entity entity) {
		if (entity == null) {
			metaContainer.get(head).setData(0);
		} else { 
			if (entity.isRemoved())
				throw new IllegalArgumentException("Target must be a removed Entity!");
			metaContainer.get(head).setData(entity.getID());
		}
	}

}
