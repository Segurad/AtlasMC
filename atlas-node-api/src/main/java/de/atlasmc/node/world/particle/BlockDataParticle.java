package de.atlasmc.node.world.particle;

import de.atlasmc.node.block.data.BlockData;

public class BlockDataParticle extends AbstractParticle {

	public BlockData data;
	
	public BlockDataParticle(ParticleType type) {
		super(type);
	}
	
	public BlockData getData() {
		return data;
	}
	
	public void setData(BlockData data) {
		this.data = data;
	}

	@Override
	public BlockDataParticle clone() {
		BlockDataParticle clone = (BlockDataParticle) super.clone();
		if (data != null)
			clone.data = data.clone();
		return clone;
	}

}
