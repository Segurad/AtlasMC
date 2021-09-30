package de.atlascore.entity;

import de.atlasmc.Location;
import de.atlasmc.Particle;
import de.atlasmc.entity.AreaEffectCloud;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.world.particle.ParticleObject;
import java.util.UUID;

public class CoreAreaEffectCloud extends CoreEntity implements AreaEffectCloud {

	protected static final int
	META_RADIUS = 8,
	META_COLOR = 9,
	META_IGNORE_RADIUS = 10,
	META_PARTICLE = 11;
	
	private static final ParticleObject DEFAULT_PARTICLE = new ParticleObject(Particle.EFFECT);
	
	public CoreAreaEffectCloud(int id, EntityType type, Location loc, UUID uuid) {
		super(id, type, loc, uuid);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(new MetaData<>(META_RADIUS, MetaDataType.FLOAT, 0.5f));
		metaContainer.set(new MetaData<>(META_COLOR, MetaDataType.INT, 0));
		metaContainer.set(new MetaData<>(META_IGNORE_RADIUS, MetaDataType.BOOLEAN, false));
		metaContainer.set(new MetaData<>(META_PARTICLE, MetaDataType.PARTICLE, DEFAULT_PARTICLE));
	}
	
	@Override
	protected int getMetaContainerSize() {
		return super.getMetaContainerSize()+4;
	}

	@Override
	public float getRadius() {
		return metaContainer.getData(META_RADIUS, MetaDataType.FLOAT);
	}

	@Override
	public int getColor() {
		return metaContainer.getData(META_COLOR, MetaDataType.INT);
	}

	@Override
	public boolean getIgnoreRadius() {
		return metaContainer.getData(META_IGNORE_RADIUS, MetaDataType.BOOLEAN);
	}

	@Override
	public ParticleObject getParticle() {
		return metaContainer.getData(META_PARTICLE, MetaDataType.PARTICLE);
	}

	@Override
	public void setRadius(float radius) {
		metaContainer.get(META_RADIUS, MetaDataType.FLOAT).setData(radius);
	}

	@Override
	public void setColor(int color) {
		metaContainer.get(META_COLOR, MetaDataType.INT).setData(color);
	}

	@Override
	public void setIngnoreRadius(boolean ignore) {
		metaContainer.get(META_IGNORE_RADIUS, MetaDataType.BOOLEAN).setData(ignore);
	}

	@Override
	public void setParticle(ParticleObject particle) {
		metaContainer.get(META_PARTICLE, MetaDataType.PARTICLE).setData(particle);
	}

}
