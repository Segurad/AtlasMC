package de.atlascore.block.data.type;

import java.util.EnumSet;
import java.util.Set;

import de.atlascore.block.data.CoreAbstractMultipleFacing;
import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.type.Tripwire;
import de.atlasmc.util.Validate;

public class CoreTripwire extends CoreAbstractMultipleFacing implements Tripwire {

	private boolean attached, powered, disarmed;
	
	public CoreTripwire(Material material) {
		super(material, 4);
	}

	@Override
	public boolean isAttached() {
		return attached;
	}

	@Override
	public void setAttached(boolean attached) {
		this.attached = attached;
	}

	@Override
	public boolean isPowered() {
		return powered;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}

	@Override
	public boolean isDisarmed() {
		return disarmed;
	}

	@Override
	public void setDisarmed(boolean disarmed) {
		this.disarmed = disarmed;
	}

	@Override
	public Set<BlockFace> getAllowedFaces() {
		return EnumSet.range(BlockFace.NORTH, BlockFace.WEST);
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(hasFace(BlockFace.WEST)?0:1)+
				(hasFace(BlockFace.SOUTH)?0:2)+
				(powered?0:4)+
				(hasFace(BlockFace.NORTH)?0:8)+
				(hasFace(BlockFace.EAST)?0:16)+
				(disarmed?0:32)+
				(attached?0:64);
	}

	@Override
	public boolean isValid(BlockFace face) {
		Validate.notNull(face, "BlockFace can not be null!");
		return face.ordinal() < 4;
	}

}
