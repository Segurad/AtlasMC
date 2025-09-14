package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.Armadillo;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;

public class CoreArmadillo extends CoreAgeableMob implements Armadillo {

	protected static final MetaDataField<ArmadilloState>
	META_ARMADILLO_STATE = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, ArmadilloState.IDLE, MetaDataType.ARMADILLO_STATE);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+1;
	
	protected int scuteTime;
	
	public CoreArmadillo(EntityType type) {
		super(type);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_ARMADILLO_STATE);
	}

	@Override
	public int getScuteTime() {
		return scuteTime;
	}

	@Override
	public void setScuteTime(int time) {
		this.scuteTime = time;
	}

	@Override
	public ArmadilloState getState() {
		return metaContainer.getData(META_ARMADILLO_STATE);
	}

	@Override
	public void setState(ArmadilloState state) {
		metaContainer.get(META_ARMADILLO_STATE).setData(state);
	}

}
