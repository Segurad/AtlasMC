package de.atlasmc.io.protocol;

import java.util.Collection;
import java.util.List;

import de.atlasmc.SimpleLocation;
import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.entity.Player;
import de.atlasmc.event.player.PlayerAnimationEvent;
import de.atlasmc.event.player.PlayerDiggingEvent;
import de.atlasmc.event.player.PlayerHeldItemChangeEvent;
import de.atlasmc.event.player.PlayerMoveEvent;
import de.atlasmc.event.player.PlayerToggleSneakEvent;
import de.atlasmc.event.player.PlayerToggleSprintEvent;
import de.atlasmc.plugin.channel.PluginChannelHandler;
import de.atlasmc.recipe.BookType;
import de.atlasmc.recipe.Recipe;
import de.atlasmc.recipe.RecipeBook;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.ThreadSafe;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public interface PlayerConnection {

	public ProtocolAdapter getProtocolAdapter();
	
	public ProtocolPlay getProtocol();

	/**
	 * 
	 * @return the current server or null if not at this note
	 */
	@ThreadSafe
	public LocalServer getLocalSever();
	
	public int getWindowActionID();
	
	public int getNextWindowActionID();
	
	/**
	 * Cancel a click
	 */
	public void lockWindowActions();

	public void unlockWindowActions();
	
	public boolean isWindowActionLocked();
	
	/**
	 * Used to bind a {@link Player} Entity to this connection if the player is currently on this {@link AtlasNode}
	 * @param player
	 */
	public void setPlayer(Player player);
	
	/**
	 * 
	 * @return the current {@link Player} Entity bound to this connection or null
	 */
	public Player getPlayer();
	
	public AtlasPlayer getAtlasPlayer();
	
	public boolean hasKeepAliveResponse();
	
	public long getLastKeepAlive();
	
	public void sendKeepAlive();
	
	/**
	 * Sends a packet to the client.
	 * @param packet
	 */
	public void sendPacked(PacketProtocol packet);
	
	/**
	 * Sends a packet to the client.
	 * The future will inform when the packet is send or failed.
	 * @param packet
	 * @param listener
	 */
	public void sendPacket(PacketProtocol packet, GenericFutureListener<? extends Future<? super Void>> listener);

	/**
	 * 
	 * @return the current inventory id
	 */
	public int getInventoryID();

	/**
	 * increments and returns the inventory id
	 * @return the new current inventory id
	 */
	public int getNextInventoryID();

	/**
	 * Teleports the player and returns the id of the teleport
	 * @param x
	 * @param y
	 * @param z
	 * @param yaw
	 * @param pitch
	 * @return teleport id
	 */
	public int teleport(double x, double y, double z, float yaw, float pitch);
	
	/**
	 * Returns the id of the last teleport
	 * @return teleport id
	 */
	public int getTeleportID();
	
	/**
	 * Sets the last teleport of the client as confirmed
	 */
	public void confirmTeleport();
	
	public boolean isTeleportConfirmed();
	
	public boolean hasClientLocationChanged();
	
	public void setClientLocationChanged(boolean changed);
	
	/**
	 * Returns the last location the client send and marks it as unchanged
	 * @return location
	 */
	public SimpleLocation acceptClientLocation();
	
	/**
	 * Returns the last location the client send.
	 * Can be used for changes.
	 * @return
	 */
	public SimpleLocation getClientLocation();
	
	/**
	 * Returns all {@link RecipeBook}s of this player
	 * @return list of recipe books
	 */
	public List<RecipeBook> getRecipeBooks();
	
	/**
	 * Returns the {@link RecipeBook} represented by the type
	 * @param type
	 * @return book
	 */
	public RecipeBook getRecipeBook(BookType type);
	
	/**
	 * Unlock the given recipes. If the recipe isn't present it will be added to the player
	 * @param whether or not the player should receive a unlock notification
	 * @param recipes
	 */
	public void unlockRecipes(boolean notify, Recipe... recipes);
	
	/**
	 * Locks the given recipes in the player's {@link RecipeBook} but it is still can be used
	 * @param recipes
	 */
	public void lockRecipes(Recipe... recipes);
	
	/**
	 * Removes recipes from players access
	 * @param recipes
	 */
	public void removeRecipes(Recipe... recipes);
	
	/**
	 * Adds recipes to players access
	 * @param recipes
	 */
	public void addRecipes(Recipe... recipes);
	
	public Collection<Recipe> getUnlockedRecipes();
	
	public Collection<Recipe> getAvailableRecipes();
	
	public PluginChannelHandler getPluginChannelHandler();

	/**
	 * Will disconnect the player form the network
	 * @param message
	 */
	public void disconnect(String message);
	
	public LocalProxy getProxy();
	
	/**
	 * Returns the currently open advancement tab of the player or null if none.
	 * @return tab or null
	 */
	@Nullable
	public String getOpenAdvancementTab();
	
	/**
	 * Returns whether or not the player has a open advancement tab.
	 * @return true if open
	 */
	public boolean hasOpenAdvancementTab();
	
	/**
	 * Sets the currently open advancement tab of this player or null to close.
	 * @param tab
	 * @param update true if should update to the client
	 */
	public void setOpenAdvancementTab(@Nullable String tab, boolean update);

	public PlayerAnimationEvent getEventAnimation();

	public PlayerHeldItemChangeEvent getEventHeldItemChange();

	public PlayerToggleSneakEvent getEventSneak();

	public PlayerToggleSprintEvent getEventSprint();

	public PlayerDiggingEvent getEventDigging();

	public PlayerMoveEvent getEventMove();
	
	public boolean isDigging();

	public void confirmKeepAlive(long lastResponseTime);
	
	public boolean isClientOnGround();
	
	/**
	 * Only for updating value send by client
	 * @param onGround
	 */
	public void setClientOnGround(boolean onGround);

	public PlayerSettings getSettings();
	
}
