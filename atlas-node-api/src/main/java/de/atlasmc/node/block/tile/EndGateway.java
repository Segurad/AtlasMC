package de.atlasmc.node.block.tile;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.WorldLocation;

public interface EndGateway extends TileEntity {

	public static final NBTCodec<EndGateway>
	NBT_HANDLER = NBTCodec
					.builder(EndGateway.class)
					.include(TileEntity.NBT_HANDLER)
					.longField("Age", EndGateway::getAge, EndGateway::setAge)
					.boolField("ExactTeleport", EndGateway::isExactTeleport, EndGateway::setExactTeleport)
					// TODO exit location int array 3 values
					.build();
	
	long getAge();
	
	void setAge(long age);
	
	/**
	 * Returns whether or not the teleport should be direct at the exit portal or with a random offset 
	 * @return true if it is exact
	 */
	boolean isExactTeleport();
	
	void setExactTeleport(boolean exact);
	
	/**
	 * Returns whether or not the exit portal coordinates are used as relative coordinates from this tile
	 * @return true if the coordinates are handled as relative
	 */
	boolean isRelativeCoordinates();
	
	void setRelativeCoordinates(boolean relative);
	
	WorldLocation getExitPortal();
	
	void setExitPortal(WorldLocation loc);
	
	@Override
	default NBTCodec<? extends EndGateway> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
