package de.atlasmc.node.entity.component;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;

public class PufferFishMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	public static final NBTCodec<PufferFishMetaComponent>
	NBT_HANDLER = NBTCodec
					.builder(PufferFishMetaComponent.class)
					.include(PufferFishMetaComponent.NBT_CODEC)
					.intField("PuffState", PufferFishMetaComponent::getPuffState, PufferFishMetaComponent::setPuffState, 0)
					.build();
	
	public static final MetaDataField<Integer>
	META_PUFF_STATE = new MetaDataField<>(17, 0, EntityMetaTypes.VAR_INT);
	
	public PufferFishMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_PUFF_STATE);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}

	public int getPuffState() {
		return getHolder().getMetaContainer().getData(META_PUFF_STATE);
	}

	/**
	 * Sets the puff state between 0 and 2
	 * @param state
	 */
	public void setPuffState(int state) {
		if (state > 2 || state < 0) 
			throw new IllegalArgumentException("State is not between 0 and 2: " + state);
		getHolder().getMetaContainer().setData(META_PUFF_STATE, state);		
	}
	
}
