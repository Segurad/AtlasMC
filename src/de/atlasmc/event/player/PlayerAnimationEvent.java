package de.atlasmc.event.player;

import de.atlasmc.entity.Player;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;

public class PlayerAnimationEvent extends PlayerEvent implements Cancellable {

	private static final ServerHandlerList handlers = new ServerHandlerList();
	
	private PlayerAnimationType animation;
	private boolean cancelled;
	
	public PlayerAnimationEvent(Player player, PlayerAnimationType anim) {
		super(player);
		this.animation = anim;
	}
	
	public PlayerAnimationType getAnimation() {
		return animation;
	}
	
	public void setAnimation(PlayerAnimationType animation) {
		this.animation = animation;
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
	
	public static ServerHandlerList getHandlerList() {
		return handlers;
	}
	
	public static enum PlayerAnimationType {
		SWING_MAIN_HAND,
		SWING_OFF_HAND
	}

}
