package de.atlascore.block.data;

import java.util.EnumSet;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.Directional;

public class CoreDirectional extends CoreBlockData implements Directional {

	private BlockFace face;
	
	public CoreDirectional(Material material) {
		super(material);
		face = BlockFace.NORTH;
	}

	@Override
	public Set<BlockFace> getFaces() {
		return EnumSet.allOf(BlockFace.class);
	}

	@Override
	public BlockFace getFacing() {
		return face;
	}

	@Override
	public void setFacing(BlockFace face) {
		this.face = face;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+face.ordinal();
	}

}
