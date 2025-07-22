package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Interaction;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CoreInteraction extends CoreEntity implements Interaction {

	protected static final MetaDataField<Float> META_WIDTH = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 1f, MetaDataType.FLOAT);
	protected static final MetaDataField<Float> META_HEIGHT = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, 1f, MetaDataType.FLOAT);
	protected static final MetaDataField<Boolean> META_RESPONSIVE = new MetaDataField<Boolean>(CoreEntity.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+3;
	
	private PreviousInteraction lastAttack;
	private PreviousInteraction lastInteraction;
	
	public CoreInteraction(EntityType type) {
		super(type);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_WIDTH);
		metaContainer.set(META_HEIGHT);
		metaContainer.set(META_RESPONSIVE);
	}
	
	@Override
	public void setWidth(float width) {
		metaContainer.get(META_WIDTH).setData(width);
	}

	@Override
	public float getWidth() {
		return metaContainer.getData(META_WIDTH);
	}

	@Override
	public void setHeight(float height) {
		metaContainer.get(META_HEIGHT).setData(height);
	}

	@Override
	public float getHeight() {
		return metaContainer.getData(META_HEIGHT);
	}

	@Override
	public boolean isResponsive() {
		return metaContainer.getData(META_RESPONSIVE);
	}

	@Override
	public void setResponsive(boolean responsive) {
		metaContainer.get(META_RESPONSIVE).setData(responsive);
	}

	@Override
	public PreviousInteraction getLastAttack() {
		return lastAttack;
	}

	@Override
	public void setLastAttack(PreviousInteraction interaction) {
		this.lastAttack = interaction;
	}

	@Override
	public PreviousInteraction getLastInteraction() {
		return lastInteraction;
	}

	@Override
	public void setLastInteraction(PreviousInteraction interaction) {
		this.lastInteraction = interaction;
	}

}
