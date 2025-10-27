package de.atlasmc.node.util;

import org.joml.Vector3d;

import de.atlasmc.node.Location;
import de.atlasmc.node.block.BlockFace;

public final class MathGraphUtil {
	
	private MathGraphUtil() {};
	
	/**
	 * Cast a Ray through cells and calls {@link VoxelRayConsumer#next(BlockFace, double, double, double, int)} each time a new cell is hit<br>
	 * Using <a href="https://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.42.3443&rep=rep1&type=pdf">Fast Voxel Traversal Algorithm</a> made by John Amanatides and Andrew Woo
	 * @param start start location of the ray 
	 * @param direction direction of this ray 
	 * @param length of this ray (if 0 or infinite the length is {@link Integer#MAX_VALUE})
	 * @param consumer the consumer handling results
	 */
	public static void castVoxelRay3D(Vector3d start, Vector3d direction, double length, VoxelRayConsumer consumer) {
		castVoxelRay3D(length, start.x, start.y, start.z, direction.x, direction.y, direction.z, consumer);
	}
	
	/**
	 * Cast a Ray through cells
	 * @see #castVoxelRay3D(Location, Vector, double, VoxelRayConsumer)
	 */
	public static void castVoxelRay3D(double x, double y, double z, double vX, double vY, double vZ, double length, VoxelRayConsumer consumer) {
		if (consumer == null) 
			throw new IllegalArgumentException("Consumer can not be null!");
		
		// coordinate change on Block boundary change
		int stepX;
		int stepY;
		int stepZ; 
		// t in which the next boundary is crossed
		double tMaxX;
		double tMaxY;
		double tMaxZ; 
		double tDeltaX; 
		double tDeltaY;
		double tDeltaZ; 
		int traversed = 0; // number of blocks traversed
		int distance; // number of blocks needed until the end
		BlockFace lastHitFace;
		
		// v init v
		
		stepX = vX >= 0 ? vX != 0 ? 1 : 0 : -1;
		stepY = vY >= 0 ? vY != 0 ? 1 : 0 : -1;
		stepZ = vZ >= 0 ? vZ != 0 ? 1 : 0 : -1;
		
		tMaxX = MathUtil.floor(x) + (stepX < 0 ? 0 : 1) - x;
		tMaxY = MathUtil.floor(y) + (stepY < 0 ? 0 : 1) - y;
		tMaxZ = MathUtil.floor(z) + (stepZ < 0 ? 0 : 1) - z;
		
		tMaxX = vX != 0 ? tMaxX / vX : Double.MAX_VALUE;
		tMaxY = vY != 0 ? tMaxY / vY : Double.MAX_VALUE;
		tMaxZ = vZ != 0 ? tMaxZ / vZ : Double.MAX_VALUE;
		
		tDeltaX = vX != 0 ? 1 / vX * stepX : Double.MAX_VALUE;
		tDeltaY = vY != 0 ? 1 / vY * stepY : Double.MAX_VALUE;
		tDeltaZ = vZ != 0 ? 1 / vZ * stepZ : Double.MAX_VALUE;
		
		// calc the amount of traversals needed for the length input
		if (Double.isFinite(length))
		distance = 1+ (int) (Math.abs(vX * length) + Math.abs(vY * length) + Math.abs(vZ * length));
		else distance = Integer.MAX_VALUE;
		
		while (traversed++ < distance) { // loop through blocks
			if (tMaxX < tMaxY) {
				if (tMaxX < tMaxZ) {
					x += stepX;
					tMaxX += tDeltaX;
					lastHitFace = stepX > 0 ? BlockFace.EAST : BlockFace.WEST;
				} else {
					z += stepZ;
					tMaxZ += tDeltaZ;
					lastHitFace = stepZ > 0 ? BlockFace.SOUTH : BlockFace.NORTH;
				}
			} else if (tMaxY < tMaxZ) {
				y += stepY;
				tMaxY += tDeltaY;
				lastHitFace = stepY > 0 ? BlockFace.DOWN : BlockFace.UP;
			} else {
				z += stepZ;
				tMaxZ += tDeltaZ;
				lastHitFace = stepZ > 0 ? BlockFace.SOUTH : BlockFace.NORTH;
			}
			if (consumer.next(lastHitFace, x, y, z, traversed)) // Notifies the consumer and breaks if it returns true
				break;
		}
	}
	
	@FunctionalInterface
	public static interface VoxelRayConsumer {
		
		/**
		 * Consumer for castVoxelRay functions
		 * @param passed the BlockFace passed while traversal to next Location
		 * @param x current location X
		 * @param y current location Y
		 * @param z current location Z
		 * @param traversed number of blocks traversed
		 * @return true if the function should be cancelled
		 */
		public boolean next(BlockFace passed, double x, double y, double z, int traversed);
	
	}

}
