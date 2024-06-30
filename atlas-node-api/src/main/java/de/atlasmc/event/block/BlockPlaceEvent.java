package de.atlasmc.event.block;

import de.atlasmc.block.Block;
import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.ItemStack;

public class BlockPlaceEvent extends BlockEvent implements Cancellable {

	private final static ServerHandlerList handlers = new ServerHandlerList();
	
	private boolean cancelled;
	private final Player player;
	private Block placeAgainst;
	private final EquipmentSlot hand;
	private final float cursorX;
	private final float cursorY;
	private final float cursorZ;
	private BlockFace face;
	
	public BlockPlaceEvent(Block block, Block against, Player player, EquipmentSlot hand, BlockFace face, float cursorX, float cursorY, float cursorZ) {
		super(block);
		this.player = player;
		this.hand = hand;
		this.face = face;
		this.cursorX = cursorX;
		this.cursorY = cursorY;
		this.cursorZ = cursorZ;
		this.face = face;
		this.placeAgainst = against;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}
	
	public float getCursorX() {
		return cursorX;
	}
	
	public float getCursorY() {
		return cursorY;
	}
	
	public float getCursorZ() {
		return cursorZ;
	}
	
	public BlockFace getFace() {
		return face;
	}

	public Player getPlayer() {
		return this.player;
	}
	
	public Block getBlockPlaced() {
		return getBlock();
	}
	
	public Block getBlockAgainst() {
		return this.placeAgainst;
	}
	
	public ItemStack getItemInHand() {
		return player.getInventory().getItemInMainHand();
	}
	
	public EquipmentSlot getHand() {
		return this.hand;
	}
	
	public boolean canBuild() {
		return player.getCanBuild();
	}
	
	public void setBuild(boolean canBuild) {
		player.setCanBuild(canBuild);
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
