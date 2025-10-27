package de.atlasmc.node.entity;

import org.joml.Vector3i;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface EndCrystal extends Entity {
	
	public static final NBTCodec<EndCrystal>
	NBT_HANDLER = NBTCodec
					.builder(EndCrystal.class)
					.include(Entity.NBT_CODEC)
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
	default NBTCodec<? extends EndCrystal> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
