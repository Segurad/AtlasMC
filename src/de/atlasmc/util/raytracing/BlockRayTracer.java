package de.atlasmc.util.raytracing;

import de.atlasmc.Location;
import de.atlasmc.Material;
import de.atlasmc.Vector;
import de.atlasmc.block.BlockFace;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.World;

/**
 * RayTracer designed with the <a href="https://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.42.3443&rep=rep1&type=pdf">Fast Voxel Traversal Algorithm</a> made by John Amanatides and Andrew Woo
 */
public class BlockRayTracer {
	
	private Vector direction;
	private Location location, start;
	private int stepX, stepY, stepZ; // coordinate change on Block boundary change
	private double tMaxX, tMaxY, tMaxZ; // t in which the next boundary is crossed
	private double tDeltaX, tDeltaY, tDeltaZ; //
	private int traversed, distance;
	private boolean end;
	private BlockFace lastHitFace;
	
	/**
	 * Creates a new RayTracer<br>
	 * The start Location is used to store the Results
	 * @param start
	 * @param direction
	 */
	public BlockRayTracer(Location start, Vector direction) {
		this.direction = direction;
		location = start;
		start = start.clone();
	}
	
	/**
	 * Replaces the current Direction<br>
	 * (Restart is required to take effect)
	 * @param direction
	 */
	public void setDirection(Vector direction) {
		direction.normalize();
		this.direction = direction;
	}
	
	/**
	 * Replaces the current Location<br>
	 * (Restart of the RayTracer might be required)
	 * @param location
	 */
	public void setLocation(Location location) {
		start = location.clone();
		this.location = location;
	}

	public BlockFace getLastHitFace() {
		return lastHitFace;
	}
	
	/**
	 * Initializes the RayTracer with the given max length<br>
	 * Resets the start Location
	 * @param length
	 */
	public void start(double length) {
		start.copyTo(location);
		
		stepX = direction.getX() >= 0 ? direction.getX() != 0 ? 1 : 0 : -1;
		stepY = direction.getY() >= 0 ? direction.getY() != 0 ? 1 : 0 : -1;
		stepZ = direction.getZ() >= 0 ? direction.getZ() != 0 ? 1 : 0 : -1;
		
		double nextBoundaryX = location.getBlockX() + (stepX < 0 ? 0 : 1) - location.getX();
		double nextBoundaryY = location.getBlockY() + (stepY < 0 ? 0 : 1) - location.getY();
		double nextBoundaryZ = location.getBlockZ() + (stepZ < 0 ? 0 : 1) - location.getZ();
		
		tMaxX = direction.getX() != 0 ? nextBoundaryX / direction.getX() : Double.MAX_VALUE;
		tMaxY = direction.getY() != 0 ? nextBoundaryY / direction.getY() : Double.MAX_VALUE;
		tMaxZ = direction.getZ() != 0 ? nextBoundaryZ / direction.getZ() : Double.MAX_VALUE;
		
		tDeltaX = direction.getX() != 0 ? 1 / direction.getX() * stepX : Double.MAX_VALUE;
		tDeltaY = direction.getY() != 0 ? 1 / direction.getY() * stepY : Double.MAX_VALUE;
		tDeltaZ = direction.getZ() != 0 ? 1 / direction.getZ() * stepZ : Double.MAX_VALUE;
	
		location.convertToBlock();
		
		distance = 1+ (int) (Math.abs(direction.getX() * length) + Math.abs(direction.getY() * length) + Math.abs(direction.getZ() * length));
		traversed = 0;
	}
	
	/**
	 * Iterates to the next Block and return the hit Face
	 * Does not check if it reached the end therefore use {@link #isEnd()}
	 * @return the hit {@link BlockFace}
	 */
	public BlockFace next() {
		end = !(++traversed < distance);
		if (tMaxX < tMaxY) {
			if (tMaxX < tMaxZ) {
				location.addX(stepX);
				tMaxX += tDeltaX;
				lastHitFace =  stepX > 0 ? BlockFace.EAST : BlockFace.WEST;
			} else {
				location.addZ(stepZ);
				tMaxZ += tDeltaZ;
				lastHitFace =  stepZ > 0 ? BlockFace.SOUTH : BlockFace.NORTH;
			}
		} else if (tMaxY < tMaxZ) {
			location.addY(stepY);
			tMaxY += tDeltaY;
			lastHitFace =  stepY > 0 ? BlockFace.DOWN : BlockFace.UP;
		} else {
			location.setZ(stepZ);
			tMaxZ += tDeltaZ;
			lastHitFace =  stepZ > 0 ? BlockFace.SOUTH : BlockFace.NORTH;
		}
		return lastHitFace;
	}
	
	/**
	 * 
	 * @return if the RayTracer reached the end
	 */
	public boolean isEnd() {
		return end;
	}

	/**
	 * 
	 * @param collisionRule
	 * @param length
	 * @return the chunk containing the hit or last Block
	 */
	public Chunk getFirstBlockHit(BlockRayCollisionRule collisionRule, double length) {
		World world = location.getWorld();
		Chunk chunk = world.getChunk(location);
		int chunkX, chunkZ, newChunkX = ((int) location.getX()) >> 4, newChunkZ = ((int) location.getZ()) >> 4;
		start(length);
		while (!isEnd()) {
			next();
			chunkX = newChunkX;
			chunkZ = newChunkZ;
			newChunkX = (int) location.getX();
			newChunkZ = (int) location.getZ();
			if (chunkZ != newChunkZ || chunkX != newChunkX) {
				chunk = world.getChunk(newChunkX, newChunkZ);
			}
			Material mat = chunk.getBlockType((int) location.getX(), (int) location.getY(), (int) location.getZ());
			if (collisionRule.isValidMaterial(mat)) break;
		}
		return chunk;
	}

}
