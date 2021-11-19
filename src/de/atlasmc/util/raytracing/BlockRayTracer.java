package de.atlasmc.util.raytracing;

import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
import de.atlasmc.Vector;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.util.MathGraphUtil;
import de.atlasmc.util.MathGraphUtil.VoxelRayConsumer;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.World;

public class BlockRayTracer implements VoxelRayConsumer {

	private final Location loc;
	private final Vector direction;
	private final World world;
	private Chunk chunk;
	private int chunkX, chunkZ;
	private BlockRayCollisionRule rule;
	private BlockFace lastHit;
	
	public BlockRayTracer(Location loc, Vector direction, BlockRayCollisionRule rule) {
		if (direction == null) throw new IllegalArgumentException("Direction can not be null!");
		if (loc == null) throw new IllegalArgumentException("Location can not be null!");
		if (rule == null) throw new IllegalArgumentException("Rule can not be null!");
		this.loc = loc;
		this.direction = direction;
		world = loc.getWorld();
		chunk = world.getChunk(loc);
		chunkX = loc.getBlockX() >> 4;
		chunkZ = loc.getBlockZ() >> 4;
		this.rule = rule;
	}

	public Chunk getFirstBlockHit(double length) {
		MathGraphUtil.castVoxelRay3D(loc, direction, length, this);
		return chunk;
	}
	
	public BlockFace getLastHitFace() {
		return lastHit;
	}

	@Override
	public boolean next(BlockFace passed, SimpleLocation loc, int traversed) {
		int newChunkX = ((int) loc.getX()) >> 4, newChunkZ = ((int) loc.getZ()) >> 4;
		if (chunkZ != newChunkZ || chunkX != newChunkX) {
			chunk = world.getChunk(newChunkX, newChunkZ);
			chunkX = newChunkX;
			chunkZ = newChunkZ;
		}
		lastHit = passed;
		BlockData data = chunk.getBlockDataAt((int) loc.getX(), (int) loc.getY(), (int) loc.getZ());
		if (rule.isValid(data))
			return true;
		return false;
	}
}
