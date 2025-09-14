package de.atlasmc.core.node.entity;

import java.util.UUID;

import de.atlasmc.node.entity.AgeableMob;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.data.MetaDataField;
import de.atlasmc.node.entity.data.MetaDataType;

public class CoreAgeableMob extends CoreMob implements AgeableMob {

	protected static final MetaDataField<Boolean>
	META_IS_BABY = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	private int forceAge;
	private int age;
	private int inLove;
	private UUID loveCause;
	
	public CoreAgeableMob(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IS_BABY);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isBaby() {
		return metaContainer.getData(META_IS_BABY);
	}

	@Override
	public void setBaby(boolean baby) {
		metaContainer.get(META_IS_BABY).setData(baby);
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public void setInLove(int time) {
		this.inLove = time;
	}

	@Override
	public int getInLove() {
		return inLove;
	}

	@Override
	public void setLoveCause(UUID uuid) {
		this.loveCause = uuid;
	}

	@Override
	public UUID getLoveCause() {
		return loveCause;
	}

	@Override
	public int getForcedAge() {
		return forceAge;
	}

	@Override
	public void setForcedAge(int age) {
		this.forceAge = age;
	}
	
}
