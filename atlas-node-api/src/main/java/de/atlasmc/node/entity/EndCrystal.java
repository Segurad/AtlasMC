package de.atlasmc.node.entity;

import org.joml.Vector3i;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.util.annotation.NotNull;

public interface EndCrystal extends Entity {
	
	@NotNull
	public static final NBTCodec<EndCrystal>
	NBT_CODEC = NBTCodec
					.builder(EndCrystal.class)
					.include(Entity.NBT_CODEC)
					.codec("beam_target", EndCrystal::getBeamTarget, EndCrystal::setBeamTarget, NBTCodecs.VECTOR_3I)
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
		return NBT_CODEC;
	}
	
}
