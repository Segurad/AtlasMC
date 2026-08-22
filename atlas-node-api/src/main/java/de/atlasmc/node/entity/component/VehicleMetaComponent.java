package de.atlasmc.node.entity.component;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;

public class VehicleMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {
	
	public static final MetaDataField<Integer>
	META_SHAKING_POWER = new MetaDataField<>(8, 0, EntityMetaTypes.VAR_INT);
	public static final MetaDataField<Integer>
	META_SHAKING_DIRECTION = new MetaDataField<>(9, 1, EntityMetaTypes.VAR_INT);
	public static final MetaDataField<Float>
	META_SHAKING_MULTIPLIER = new MetaDataField<>(10, 0.0f, EntityMetaTypes.FLOAT);
	
	public VehicleMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 3;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_SHAKING_POWER);
		container.set(META_SHAKING_DIRECTION);
		container.set(META_SHAKING_MULTIPLIER);
	}
	
	public int getShakingPower() {
		return getHolder().getMetaContainer().getData(META_SHAKING_POWER);
	}

	public void setShakingPower(int power) {
		if (power < 0)
			throw new IllegalArgumentException("Power can not be lower than 0: " + power);
		getHolder().getMetaContainer().setData(META_SHAKING_POWER, power);		
	}

	public int getShakingDirection() {
		return getHolder().getMetaContainer().getData(META_SHAKING_DIRECTION);
	}

	public void setShakingDirection(int direction) {
		getHolder().getMetaContainer().setData(META_SHAKING_DIRECTION, direction);
	}

	public float getShakingMultiplier() {
		return getHolder().getMetaContainer().getData(META_SHAKING_MULTIPLIER);
	}

	public void setShakingMultiplier(float multiplier) {
		getHolder().getMetaContainer().setData(META_SHAKING_MULTIPLIER, multiplier);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public NBTCodec<? extends VehicleMetaComponent> getNBTCodec() {
		return (NBTCodec<? extends VehicleMetaComponent>) NBT_CODEC;
	}

}
