package de.atlasmc.node.entity.component;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;

public class WitherMetaCompoennt extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<WitherMetaCompoennt>
	NBT_CODEC = NBTCodec
					.builder(WitherMetaCompoennt.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.intField("Invul", WitherMetaCompoennt::getInvulnerableTime, WitherMetaCompoennt::setInvulnerableTime, 0)
					.build();
	
	public static final MetaDataField<Integer>
	META_TARGET_CENTER_HEAD = new MetaDataField<>(16, 0, EntityMetaTypes.VAR_INT);
	public static final MetaDataField<Integer>
	META_TARGET_LEFT_HEAD = new MetaDataField<>(17, 0, EntityMetaTypes.VAR_INT);
	public static final MetaDataField<Integer>
	META_TARGET_RIGHT_HEAD = new MetaDataField<>(18, 0, EntityMetaTypes.VAR_INT);
	public static final MetaDataField<Integer>
	META_INVULNERABLE_TIME = new MetaDataField<>(19, 0, EntityMetaTypes.VAR_INT);
	
	private Entity targetCenter;
	private Entity targetLeft;
	private Entity targetRight; 
	
	public WitherMetaCompoennt(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_TARGET_CENTER_HEAD);
		container.set(META_TARGET_LEFT_HEAD);
		container.set(META_TARGET_RIGHT_HEAD);
		container.set(META_INVULNERABLE_TIME);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 4;
	}

	public Entity getCenterHeadTarget() {
		return targetCenter;
	}

	public Entity getLeftHeadTarget() {
		return targetLeft;
	}

	public Entity getRightHeadTarget() {
		return targetRight;
	}

	public int getInvulnerableTime() {
		return getHolder().getMetaContainer().getData(META_INVULNERABLE_TIME);
	}

	public void setInvulnerableTime(int time) {
		if (time < 0)
			throw new IllegalArgumentException("Time can not be lower than 0: " + time);
		getHolder().getMetaContainer().setData(META_INVULNERABLE_TIME, time);
	}

	public void setCenterHeadTarget(Entity entity) {
		setHeadTarget(META_TARGET_CENTER_HEAD, entity);
		this.targetCenter = entity;
	}

	public void setLeftHeadTarget(Entity entity) {
		setHeadTarget(META_TARGET_LEFT_HEAD, entity);
		this.targetLeft = entity;
	}

	public void setRightHeadTarget(Entity entity) {
		setHeadTarget(META_TARGET_RIGHT_HEAD, entity);
		this.targetRight = entity;
	}
	
	private void setHeadTarget(MetaDataField<Integer> head, Entity entity) {
		if (entity == null) {
			getHolder().getMetaContainer().setData(head, 0);
		} else { 
			if (entity.isRemoved())
				throw new IllegalArgumentException("Target must not be a removed Entity!");
			getHolder().getMetaContainer().setData(head, entity.getID());
		}
	}

	@Override
	public NBTCodec<? extends WitherMetaCompoennt> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
