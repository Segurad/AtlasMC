package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Frog;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;

public class CoreFrog extends CoreAgeableMob implements Frog {

	protected static final MetaDataField<Variant> 
	META_FROG_VARIANT = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, Variant.TEMPERATE, MetaDataType.FROG_VARIANT);
	protected static final MetaDataField<Integer> 
	META_TONGUE_TARGET = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, 0, MetaDataType.OPT_VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+2;
	
	private Entity tongueTarget;
	
	public CoreFrog(EntityType type) {
		super(type);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_FROG_VARIANT);
		metaContainer.set(META_TONGUE_TARGET);
	}

	@Override
	public Variant getVariant() {
		return metaContainer.getData(META_FROG_VARIANT);
	}

	@Override
	public void setVariant(Variant variant) {
		metaContainer.get(META_FROG_VARIANT).setData(variant);
	}

	@Override
	public Entity getTongueTarget() {
		return tongueTarget;
	}

	@Override
	public void setTangueTarget(Entity entity) {
		metaContainer.get(META_TONGUE_TARGET).setData(entity != null ? entity.getID() : null);
		this.tongueTarget = entity;
	}

}
