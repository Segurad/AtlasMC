package de.atlasmc.node.util.raytracing;

import java.util.Objects;

import org.joml.Vector3d;

import de.atlasmc.node.WorldLocation;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.util.MathGraphUtil;
import de.atlasmc.node.util.MathUtil;
import de.atlasmc.node.util.MathGraphUtil.VoxelRayConsumer;
import de.atlasmc.node.world.Chunk;
import de.atlasmc.node.world.World;

public class BlockRayTracer implements VoxelRayConsumer {

	private final WorldLocation loc;
	private final Vector3d direction;
	private final World world;
	private Chunk chunk;
	private int chunkX;
	private int chunkZ;
	private BlockRayCollisionRule rule;
	private BlockFace lastHit;
	
	public BlockRayTracer(WorldLocation loc, Vector3d direction, BlockRayCollisionRule rule) {
		if (direction == null) 
			throw new IllegalArgumentException("Direction can not be null!");
		if (loc == null) 
			throw new IllegalArgumentException("Location can not be null!");
		if (rule == null) 
			throw new IllegalArgumentException("Rule can not be null!");
		this.loc = Objects.requireNonNull(loc);
		this.direction = Objects.requireNonNull(direction);
		world = loc.getWorld();
		chunk = world.getChunk(loc);
		chunkX = loc.getChunkX();
		chunkZ = loc.getChunkZ();
		this.rule = rule;
	}
	
	public BlockRayCollisionRule getRule() {
		return rule;
	}
	
	public void setRule(BlockRayCollisionRule rule) {
		this.rule = Objects.requireNonNull(rule);
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
		final var loc = this.loc;
		loc.set(x, y, z);
		final int newChunkX = loc.getChunkX();
		final int newChunkZ = loc.getBlockZ();
		if (chunkZ != newChunkZ || chunkX != newChunkX) {
			chunk = world.getChunk(newChunkX, newChunkZ);
			chunkX = newChunkX;
			chunkZ = newChunkZ;
		}
		lastHit = passed;
		BlockData data = chunk.getBlockDataAtUnsafe(MathUtil.floor(x), MathUtil.floor(y), MathUtil.floor(z));
		return rule.isValid(data);
	}
}
