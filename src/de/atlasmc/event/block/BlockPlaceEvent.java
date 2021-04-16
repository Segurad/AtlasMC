package de.atlasmc.event.block;

import de.atlasmc.block.Block;
import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.ItemStack;

public class BlockPlaceEvent extends BlockEvent implements Cancellable {

	private final static ServerHandlerList handlers = new ServerHandlerList();
	protected boolean cancelled, canBuild;
	protected Player player;
	protected Block placeAgainst;
	protected ItemStack itemInMainHand;
	protected EquipmentSlot hand;
	
	public BlockPlaceEvent(Block block) {
		super(block);
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
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
		return this.itemInMainHand;
	}
	
	public EquipmentSlot getHand() {
		return this.hand;
	}
	
	public boolean canBuild() {
		return this.canBuild;
	}
	
	public void setBuild(boolean canBuild) {
		this.canBuild = canBuild;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}

}
