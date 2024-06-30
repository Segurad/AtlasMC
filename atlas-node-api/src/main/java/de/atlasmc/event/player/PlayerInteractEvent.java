package de.atlasmc.event.player;

import de.atlasmc.block.Block;
import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.ItemStack;

public class PlayerInteractEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
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
		this.item = item;
	}
	
	public Action getAction() {
		return action;
	}
	
	public EquipmentSlot getHand() {
		return hand;
	}
	
	public BlockFace getClickedFace() {
		return clickedFace;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public Block getClickedBlock() {
		return clicked;
	}
	
	public boolean hasBlock() {
		return clicked != null;
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
		return HANDLERS;
	}
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}
	
	public static enum Action {
		LEFT_CLICK_AIR,
		LEFT_CLICK_BLOCK,
		RIGHT_CLICK_AIR,
		RIGHT_CLICK_BLOCK,
		PHYSICAL;
		
		public boolean isLeft() {
			return this == LEFT_CLICK_AIR || this == LEFT_CLICK_BLOCK;
		}
		
		public boolean isRight() {
			return this == RIGHT_CLICK_AIR || this == RIGHT_CLICK_BLOCK;
		}
	}

}
