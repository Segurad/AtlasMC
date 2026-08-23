package de.atlasmc.node.entity.component;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;

public class FishingBobberMetaContainer extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	public static final MetaDataField<Integer> 
	META_HOCKED_ENTITY = new MetaDataField<>(8, 0, EntityMetaTypes.VAR_INT);
	public static final MetaDataField<Boolean>
	META_CATCHABLE = new MetaDataField<>(9, false, EntityMetaTypes.BOOLEAN);
	
	private Entity hooked;
	private Entity owner;
	
	public FishingBobberMetaContainer(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_HOCKED_ENTITY);
		container.set(META_CATCHABLE);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 2;
	}

	public Entity getHookedEntity() {
		return hooked;
	}

	public void setHookedEntity(Entity hooked) {
		this.hooked = hooked;
		int id = 0;
		if (hooked != null)
			id = hooked.getID()+1;
		getHolder().getMetaContainer().setData(META_HOCKED_ENTITY, id);
	}

	public Entity getOwner() {
		return owner;
	}

	public void setOwner(Entity owner) {
		this.owner = owner;
		getHolder().setObjectData(owner != null ? owner.getID() : 0);
	}
	
}
