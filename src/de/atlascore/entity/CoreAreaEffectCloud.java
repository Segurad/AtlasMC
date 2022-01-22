package de.atlascore.entity;

import de.atlasmc.Particle;
import de.atlasmc.entity.AreaEffectCloud;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.World;
import de.atlasmc.world.particle.ParticleObject;
import java.util.UUID;

public class CoreAreaEffectCloud extends CoreEntity implements AreaEffectCloud {
	
	protected static final MetaDataField<Float> 
	META_RADIUS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 0.5f, MetaDataType.FLOAT);
	protected static final MetaDataField<Integer>
	META_COLOR = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, 0, MetaDataType.INT);
	protected static final MetaDataField<Boolean>
	META_IGNORE_RADIUS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<ParticleObject>
	META_PARTICLE = new MetaDataField<ParticleObject>(CoreEntity.LAST_META_INDEX+4, new ParticleObject(Particle.EFFECT), MetaDataType.PARTICLE);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+4;
	
	public CoreAreaEffectCloud(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_RADIUS);
		metaContainer.set(META_COLOR);
		metaContainer.set(META_IGNORE_RADIUS);
		metaContainer.set(META_PARTICLE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public float getRadius() {
		return metaContainer.getData(META_RADIUS);
	}

	@Override
	public int getColor() {
		return metaContainer.getData(META_COLOR);
	}

	@Override
	public boolean getIgnoreRadius() {
		return metaContainer.getData(META_IGNORE_RADIUS);
	}

	@Override
	public ParticleObject getParticle() {
		return metaContainer.getData(META_PARTICLE);
	}

	@Override
	public void setRadius(float radius) {
		metaContainer.get(META_RADIUS).setData(radius);
	}

	@Override
	public void setColor(int color) {
		metaContainer.get(META_COLOR).setData(color);
	}

	@Override
	public void setIngnoreRadius(boolean ignore) {
		metaContainer.get(META_IGNORE_RADIUS).setData(ignore);
	}

	@Override
	public void setParticle(ParticleObject particle) {
		metaContainer.get(META_PARTICLE).setData(particle);
	}

}
