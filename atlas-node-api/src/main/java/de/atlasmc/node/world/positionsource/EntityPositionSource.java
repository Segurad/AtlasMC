package de.atlasmc.node.world.positionsource;

import java.util.UUID;

import org.joml.Vector3d;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.world.World;

public class EntityPositionSource implements PositionSource {

	public static final NBTCodec<EntityPositionSource>
	NBT_CODEC = NBTCodec
				.builder(EntityPositionSource.class)
				.include(PositionSource.NBT_CODEC)
				.codec("source_entity", EntityPositionSource::getUUID, EntityPositionSource::setUUID, NBTCodecs.UUID_CODEC)
				.floatField("y_offset", EntityPositionSource::getOffset, EntityPositionSource::setOffset, 0)
				.build();
	
	public static final StreamCodec<EntityPositionSource>
	STREAM_CODEC = StreamCodec
					.builder(EntityPositionSource.class)
					.include(PositionSource.STREAM_CODEC)
					.varInt(EntityPositionSource::getID, EntityPositionSource::setID)
					.floatValue(EntityPositionSource::getOffset, EntityPositionSource::setOffset)
					.build();
	
	private int id;
	private UUID uuid;
	private float offset;

	@Override
	public Vector3d getPosition(World world, Vector3d vec) {
		var tracker = world.getEntityTracker();
		var entity = uuid != null ? tracker.getEntity(uuid) : tracker.getEntity(id);
		return entity.getLocation(vec);
	}
	
	public EntityPositionSource(Entity entity, float offset) {
		this.offset = offset;
		setEntity(entity);
	}
	
	public EntityPositionSource() {
		// simple constructor
	}

	@Override
	public PositionSourceType getType() {
		return PositionSourceType.ENTITY;
	}
	
	public void setEntity(Entity entity) {
		this.id = entity.getID();
		this.uuid = entity.getUUID();
	}
	
	public int getID() {
		return id;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public float getOffset() {
		return offset;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setOffset(float offset) {
		this.offset = offset;
	}
	
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public NBTCodec<? extends EntityPositionSource> getNBTCodec() {
		return NBT_CODEC;
	}

	@Override
	public StreamCodec<? extends EntityPositionSource> getStreamCodec() {
		return STREAM_CODEC;
	}

}
