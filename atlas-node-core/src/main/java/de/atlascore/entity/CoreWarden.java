package de.atlascore.entity;

import java.util.ArrayList;
import java.util.List;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Warden;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CoreWarden extends CoreMob implements Warden {

	protected static final MetaDataField<Integer> META_ANGER = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	private List<Suspect> suspects;
	
	public CoreWarden(EntityType type) {
		super(type);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_ANGER);
	}
	
	@Override
	public void setAnger(int anger) {
		metaContainer.get(META_ANGER).setData(anger);
	}

	@Override
	public int getAnger() {
		return metaContainer.getData(META_ANGER);
	}

	@Override
	public boolean hasSuspects() {
		return suspects != null && !suspects.isEmpty();
	}

	@Override
	public List<Suspect> getSuspects() {
		if (suspects == null)
			suspects = new ArrayList<>();
		return suspects;
	}

}
