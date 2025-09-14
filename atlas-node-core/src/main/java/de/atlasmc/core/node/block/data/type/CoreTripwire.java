package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreMultipleFacing4;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.data.type.Tripwire;

public class CoreTripwire extends CoreMultipleFacing4 implements Tripwire {
	
	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreMultipleFacing4.PROPERTIES, 
				BlockDataProperty.DISARMED,
				BlockDataProperty.POWERED,
				BlockDataProperty.ATTACHED);
	}
	
	private boolean attached;
	private boolean powered;
	private boolean disarmed;
	
	public CoreTripwire(BlockType type) {
		super(type);
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
	public int getStateID() {
		return getType().getBlockStateID()+
				(hasFace(BlockFace.WEST)?0:1)+
				(hasFace(BlockFace.SOUTH)?0:2)+
				(powered?0:4)+
				(hasFace(BlockFace.NORTH)?0:8)+
				(hasFace(BlockFace.EAST)?0:16)+
				(disarmed?0:32)+
				(attached?0:64);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
