package de.atlascore.block;

import de.atlasmc.Location;
import de.atlasmc.block.Block;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.DiggingHandler;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.Player;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.player.PlayerDiggingEvent;
import de.atlasmc.event.player.PlayerDiggingEvent.DiggingStatus;
import de.atlasmc.io.protocol.play.PacketOutSetBlockDestroyStage;
import de.atlasmc.util.MathUtil;
import de.atlasmc.util.ViewerSet;
import de.atlasmc.world.World;

/**
 * {@link DiggingHandler} implementation for player controlled digging
 */
public class CorePlayerDiggingHandler implements DiggingHandler {

	protected final Player player;
	private float progress;
	private BlockFace face;
	private boolean digging;
	private boolean autoFinish;
	private Block block;
	private int stage;
	
	public CorePlayerDiggingHandler(Player player) {
		this.player = player;
		this.autoFinish = false;
	}
	
	@Override
	public void tick() {
		if (progress >= 1) {
			if (autoFinish)
				finishDigging();
			return;
		}
		progress += calcProgress();
		updateStage();
	}

	/**
	 * Returns the progress in percent that is made in the current tick
	 * @return progress
	 */
	protected float calcProgress() {
		// TODO implements calc progress
		return 0;
	}
	
	/**
	 * Updates the current stage and sends a animation update if changed
	 */
	protected final void updateStage() {
		int stage = (int) (progress * 10);
		if (stage > 9)
			stage = 9;
		if (stage == this.stage)
			return;
		this.stage = stage;
		sendAnimation();
	}

	@Override
	public float getProgress() {
		return progress;
	}

	@Override
	public void setProgress(float value) {
		if (value > 1 || value < 0)
			throw new IllegalArgumentException("Value must be between 0 and 1: " + value);
		if (!digging)
			return;
		this.progress = value;
	}

	@Override
	public BlockFace getBlockFace() {
		return face;
	}

	@Override
	public boolean isDigging() {
		return digging;
	}

	@Override
	public Location getLocation() {
		return block.getLocation();
	}

	@Override
	public Block getBlock() {
		return block;
	}

	@Override
	public boolean finishDigging(boolean verify) {
		// TODO implement digging finished
		diggingCleanup();
		return false;
	}

	@Override
	public void startDigging(BlockFace face, World world, int x, int y, int z) {
		if (!player.getWorld().equals(world))
			throw new IllegalArgumentException("Can only start digging in the current world!");
		if (digging)
			cancelDigging();
		Location loc = new Location(world, x, y, z);
		PlayerDiggingEvent event = new PlayerDiggingEvent(player, DiggingStatus.START_DIGGING, loc, face);
		HandlerList.callEvent(event);
		if (event.isCancelled())
			return;
		
	}

	@Override
	public void cancelDigging() {
		// TODO implement cancel digging
		diggingCleanup();
	}

	@Override
	public int getStage() {
		return stage;
	}

	@Override
	public int getX() {
		return block.getX();
	}

	@Override
	public int getY() {
		return block.getY();
	}

	@Override
	public int getZ() {
		return block.getZ();
	}

	@Override
	public void sendAnimation() {
		if (!digging)
			return;
		ViewerSet<Entity, Player> viewers = player.getViewers();
		if (viewers.isEmpty())
			return;
		PacketOutSetBlockDestroyStage packet = createAnimationPacket();
		for (Player viewer : viewers) {
			viewer.getConnection().sendPacked(packet);
		}
	}

	@Override
	public void sendAnimation(Player player) {
		if (!digging)
			return;
		PacketOutSetBlockDestroyStage packet = createAnimationPacket();
		player.getConnection().sendPacked(packet);
	}
	
	/**
	 * Returns a animation packet for the current digging state
	 * @return packet
	 */
	protected PacketOutSetBlockDestroyStage createAnimationPacket() {
		PacketOutSetBlockDestroyStage packet = new PacketOutSetBlockDestroyStage();
		packet.setEntityID(player.getID());
		packet.setStage(stage);
		long pos = MathUtil.toPosition(block.getX(), block.getY(), block.getZ());
		packet.setPosition(pos);
		return packet;
	}

	@Override
	public boolean isAutoFinish() {
		return autoFinish;
	}

	@Override
	public void setAutoFinish(boolean autofinish) {
		this.autoFinish = autofinish;
	}

	@Override
	public boolean isFinished() {
		return progress >= 1;
	}
	
	/**
	 * Resets all variables used for digging.
	 * Should be called after digging is cancelled or finished
	 */
	protected void diggingCleanup() {
		block = null;
		face = null;
		progress = 0.0f;
		stage = 0;
		digging = false;
	}

}
