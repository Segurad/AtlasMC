package de.atlasmc.block.tile;

import de.atlasmc.Location;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface EndGateway extends TileEntity {

	public static final NBTSerializationHandler<EndGateway>
	NBT_HANDLER = NBTSerializationHandler
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
	
	Location getExitPortal();
	
	void setExitPortal(Location loc);
	
	@Override
	default NBTSerializationHandler<? extends EndGateway> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
