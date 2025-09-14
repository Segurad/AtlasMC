package de.atlasmc.node.entity;

import org.joml.Vector3i;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface EndCrystal extends Entity {
	
	public static final NBTSerializationHandler<EndCrystal>
	NBT_HANDLER = NBTSerializationHandler
					.builder(EndCrystal.class)
					.include(Entity.NBT_HANDLER)
					.vector3i("beam_target", EndCrystal::getBeamTarget, EndCrystal::setBeamTarget)
					.boolField("ShowBottom", EndCrystal::getShowBottom, EndCrystal::setShowBottom, false)
					.build();

	Vector3i getBeamTarget();
	
	Vector3i getBeamTarget(Vector3i loc);
	
	void setBeamTarget(Vector3i loc);
	
	void setBeamTarget(int x, int y, int z);
	
	boolean hasTarget();
	
	void resetTarget();
	
	boolean getShowBottom();
	
	void setShowBottom(boolean show);
	
	@Override
	default NBTSerializationHandler<? extends EndCrystal> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
