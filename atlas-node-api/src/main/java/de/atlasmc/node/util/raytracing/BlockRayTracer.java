package de.atlasmc.node.util.raytracing;

import org.joml.Vector3d;

import de.atlasmc.node.Location;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.util.MathGraphUtil;
import de.atlasmc.node.util.MathUtil;
import de.atlasmc.node.util.MathGraphUtil.VoxelRayConsumer;
import de.atlasmc.node.world.Chunk;
import de.atlasmc.node.world.World;

public class BlockRayTracer implements VoxelRayConsumer {

	private final Location loc;
	private final Vector3d direction;
	private final World world;
	private Chunk chunk;
	private int chunkX;
	private int chunkZ;
	private BlockRayCollisionRule rule;
	private BlockFace lastHit;
	
	public BlockRayTracer(Location loc, Vector3d direction, BlockRayCollisionRule rule) {
		if (direction == null) 
			throw new IllegalArgumentException("Direction can not be null!");
		if (loc == null) 
			throw new IllegalArgumentException("Location can not be null!");
		if (rule == null) 
			throw new IllegalArgumentException("Rule can not be null!");
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
	public boolean next(BlockFace passed, double x, double y, double z, int traversed) {
		final int newChunkX = ((int) loc.x) >> 4;
		final int newChunkZ = ((int) loc.z) >> 4;
		if (chunkZ != newChunkZ || chunkX != newChunkX) {
			chunk = world.getChunk(newChunkX, newChunkZ);
			chunkX = newChunkX;
			chunkZ = newChunkZ;
		}
		lastHit = passed;
		BlockData data = chunk.getBlockDataAt(MathUtil.floor(x), MathUtil.floor(y), MathUtil.floor(z));
		return rule.isValid(data);
	}
}
