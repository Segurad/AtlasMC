package de.atlasmc.node.entity.component;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;

public class BoatMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {
	
	public static final MetaDataField<Boolean>
	META_LEFT_PADDLE_TURNING = new MetaDataField<>(11, false, EntityMetaTypes.BOOLEAN);
	public static final MetaDataField<Boolean>
	META_RIGHT_PADDLE_TURNING = new MetaDataField<>(12, false, EntityMetaTypes.BOOLEAN);
	public static final MetaDataField<Integer>
	META_SPLASH_TIMER = new MetaDataField<>(13, 0, EntityMetaTypes.VAR_INT);
	
	public BoatMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_LEFT_PADDLE_TURNING);
		container.set(META_RIGHT_PADDLE_TURNING);
		container.set(META_SPLASH_TIMER);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 3;
	}

	public boolean isLeftPaddleTurning() {
		return getHolder().getMetaContainer().getData(META_LEFT_PADDLE_TURNING);
	}

	public boolean isRightPaddleTurning() {
		return getHolder().getMetaContainer().getData(META_RIGHT_PADDLE_TURNING);
	}

	public int getSplashTimer() {
		return getHolder().getMetaContainer().getData(META_SPLASH_TIMER);
	}
	
	public void setLeftPaddleTurning(boolean turning) {
		getHolder().getMetaContainer().setData(META_LEFT_PADDLE_TURNING, turning);
	}

	public void setRightPaddleTurning(boolean turning) {
		getHolder().getMetaContainer().setData(META_RIGHT_PADDLE_TURNING, turning);
	}

}
