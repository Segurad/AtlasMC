package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Sniffer;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CoreSniffer extends CoreAgeableMob implements Sniffer {

	protected static final MetaDataField<State> META_SNIFFER_STATE = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, State.IDLING, MetaDataType.SNIFFER_STATE);
	protected static final MetaDataField<Integer> META_DROP_SEED_TICK = new MetaDataField<Integer>(CoreAgeableMob.LAST_META_INDEX+2, 0, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+2;
	
	public CoreSniffer(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_SNIFFER_STATE);
		metaContainer.set(META_DROP_SEED_TICK);
	}

	@Override
	public State getState() {
		return metaContainer.getData(META_SNIFFER_STATE);
	}

	@Override
	public void setState(State state) {
		metaContainer.get(META_SNIFFER_STATE).setData(state);
	}

	@Override
	public int getDropSeedAtTick() {
		return metaContainer.getData(META_DROP_SEED_TICK);
	}

	@Override
	public void setDropSeedAtTick(int tick) {
		metaContainer.get(META_DROP_SEED_TICK).setData(tick);
	}

}
