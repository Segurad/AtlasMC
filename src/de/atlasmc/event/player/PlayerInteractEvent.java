package de.atlasmc.event.player;

import de.atlasmc.block.Block;
import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.ItemStack;

public class PlayerInteractEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	
	private ItemStack item;
	private Block clicked;
	private BlockFace clickedFace;
	private EquipmentSlot hand;
	private Action action;
	private boolean cancelled;
	
	public PlayerInteractEvent(Player player, Action action, ItemStack item, Block clicked, BlockFace clickedFace, EquipmentSlot hand) {
		super(player);
		this.clickedFace = clickedFace;
		this.clicked = clicked;
		this.hand = hand;
		this.action = action;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public Block getBlock() {
		return clicked;
	}
	
	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return handlers;
	}
	
	public static enum Action {
		LEFT_CLICK,
		RIGHT_CLICK,
		RIGHT_CLICK_BLOCK,
		PHYSICAL;
		
		public boolean isLeft() {
			return this == LEFT_CLICK;
		}
		
		public boolean isRight() {
			return this == RIGHT_CLICK || this == RIGHT_CLICK_BLOCK;
		}
	}

}
