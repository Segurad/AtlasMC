package de.atlascore.block.data;

import java.util.EnumSet;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.Bed;

public class CoreBed extends CoreDirectional implements Bed {

	private boolean occupied;
	private Part part;
	
	public CoreBed(Material material) {
		super(material);
		part = Part.FOOT;
	}

	@Override
	public boolean isOccupied() {
		return occupied;
	}

	@Override
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	@Override
	public Part getPart() {
		return part;
	}

	@Override
	public void setPart(Part part) {
		this.part = part;
	}
	
	@Override
	public Set<BlockFace> getFaces() {
		return EnumSet.of(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST);
	}
	
	@Override
	public int getStateID() {
		int bf = 0;
		switch (getFacing()) {
		case EAST: {
			bf = 3;
			break;
		}
		case SOUTH: {
			bf = 1;
			break;
		}
		case WEST: {
			bf = 2;
			break;
		}
		default:
			break;
		}
		return getMaterial().getBlockID()+bf<<2+(occupied?0:2)+part.ordinal();
	}

}
