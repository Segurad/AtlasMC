package de.atlasmc.util;

import de.atlasmc.SimpleLocation;
import de.atlasmc.Vector;
import de.atlasmc.block.BlockFace;

public final class MathGraphUtil {
	
	private MathGraphUtil() {};
	
	/**
	 * Cast a Ray through cells and calls {@link VoxelRayConsumer#next(BlockFace, SimpleLocation, int)} each time a new cell is hit<br>
	 * Using <a href="https://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.42.3443&rep=rep1&type=pdf">Fast Voxel Traversal Algorithm</a> made by John Amanatides and Andrew Woo
	 * @param start start location of the ray (location is not cloned and will be updated with new positions)
	 * @param direction direction of this ray 
	 * @param length of this ray (if 0 or infinite the length is {@link Integer#MAX_VALUE})
	 * @param consumer the consumer handling results
	 */
	public static void castVoxelRay3D(SimpleLocation start, Vector direction, double length, VoxelRayConsumer consumer) {
		if (start == null) throw new IllegalArgumentException("Start location can not be null!");
		if (direction == null) throw new IllegalArgumentException("direction can not be null!");
		if (consumer == null) throw new IllegalArgumentException("Consumer can not be null!");
		
		int stepX, stepY, stepZ; // coordinate change on Block boundary change
		double tMaxX, tMaxY, tMaxZ; // t in which the next boundary is crossed
		double tDeltaX, tDeltaY, tDeltaZ; 
		int traversed = 0; // number of blocks traversed
		int distance; // number of blocks needed until the end
		BlockFace lastHitFace;
		
		// v init v
		
		stepX = direction.getX() >= 0 ? direction.getX() != 0 ? 1 : 0 : -1;
		stepY = direction.getY() >= 0 ? direction.getY() != 0 ? 1 : 0 : -1;
		stepZ = direction.getZ() >= 0 ? direction.getZ() != 0 ? 1 : 0 : -1;
		
		tMaxX = start.getBlockX() + (stepX < 0 ? 0 : 1) - start.getX();
		tMaxY = start.getBlockY() + (stepY < 0 ? 0 : 1) - start.getY();
		tMaxZ = start.getBlockZ() + (stepZ < 0 ? 0 : 1) - start.getZ();
		
		tMaxX = direction.getX() != 0 ? tMaxX / direction.getX() : Double.MAX_VALUE;
		tMaxY = direction.getY() != 0 ? tMaxY / direction.getY() : Double.MAX_VALUE;
		tMaxZ = direction.getZ() != 0 ? tMaxZ / direction.getZ() : Double.MAX_VALUE;
		
		tDeltaX = direction.getX() != 0 ? 1 / direction.getX() * stepX : Double.MAX_VALUE;
		tDeltaY = direction.getY() != 0 ? 1 / direction.getY() * stepY : Double.MAX_VALUE;
		tDeltaZ = direction.getZ() != 0 ? 1 / direction.getZ() * stepZ : Double.MAX_VALUE;
		
		// calc the amount of traversals needed for the length input
		if (Double.isFinite(length))
		distance = 1+ (int) (Math.abs(direction.getX() * length) + Math.abs(direction.getY() * length) + Math.abs(direction.getZ() * length));
		else distance = Integer.MAX_VALUE;
		
		while (traversed++ < distance) { // loop through blocks
			if (tMaxX < tMaxY) {
				if (tMaxX < tMaxZ) {
					start.addX(stepX);
					tMaxX += tDeltaX;
					lastHitFace = stepX > 0 ? BlockFace.EAST : BlockFace.WEST;
				} else {
					start.addZ(stepZ);
					tMaxZ += tDeltaZ;
					lastHitFace = stepZ > 0 ? BlockFace.SOUTH : BlockFace.NORTH;
				}
			} else if (tMaxY < tMaxZ) {
				start.addY(stepY);
				tMaxY += tDeltaY;
				lastHitFace = stepY > 0 ? BlockFace.DOWN : BlockFace.UP;
			} else {
				start.setZ(stepZ);
				tMaxZ += tDeltaZ;
				lastHitFace = stepZ > 0 ? BlockFace.SOUTH : BlockFace.NORTH;
			}
			if (consumer.next(lastHitFace, start, traversed)) // Notifies the consumer and breaks if it returns true
				break;
		}
	}
	
	@FunctionalInterface
	public static interface VoxelRayConsumer {
		
		/**
		 * Consumer for castVoxelRay functions
		 * @param passed the BlockFace passed while traversal to next Location
		 * @param loc the current location
		 * @param traversed number of blocks traversed
		 * @return true if the function should be cancelled
		 */
		public boolean next(BlockFace passed, SimpleLocation loc, int traversed);
	
	}

}
