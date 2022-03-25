package de.atlascore.block.data.type;

import java.io.IOException;
import java.util.Set;

import de.atlascore.block.data.CoreAbstractMultipleFacing;
import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.type.Fence;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreFence extends CoreAbstractMultipleFacing implements Fence {

	private static final Set<BlockFace> ALLOWED_FACES = 
			Set.of(BlockFace.NORTH,
					BlockFace.DOWN,
					BlockFace.WEST,
					BlockFace.EAST);
	
	private boolean waterlogged;
	
	public CoreFence(Material material) {
		super(material, 4);
	}

	@Override
	public boolean isWaterlogged() {
		return waterlogged;
	}

	@Override
	public void setWaterlogged(boolean waterlogged) {
		this.waterlogged = waterlogged;
	}

	@Override
	public Set<BlockFace> getAllowedFaces() {
		return ALLOWED_FACES;
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(hasFace(BlockFace.WEST)?0:1)+
				(waterlogged?0:2)+
				(hasFace(BlockFace.SOUTH)?0:4)+
				(hasFace(BlockFace.NORTH)?0:8)+
				(hasFace(BlockFace.EAST)?0:16);
	}

	@Override
	public boolean isValid(BlockFace face) {
		return face.ordinal() < 4;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isWaterlogged()) 
			writer.writeByteTag(CoreWaterlogged.WATERLOGGED, true);
	}

}
