package de.atlasmc.io.protocol;

import java.util.Collection;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.SimpleLocation;
import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.atlasnetwork.NodePlayer;
import de.atlasmc.chat.Messageable;
import de.atlasmc.entity.Player;
import de.atlasmc.event.player.PlayerAnimationEvent;
import de.atlasmc.event.player.PlayerHeldItemChangeEvent;
import de.atlasmc.event.player.PlayerMoveEvent;
import de.atlasmc.event.player.PlayerToggleSneakEvent;
import de.atlasmc.event.player.PlayerToggleSprintEvent;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.channel.PluginChannelHandler;
import de.atlasmc.proxy.LocalProxy;
import de.atlasmc.recipe.BookType;
import de.atlasmc.recipe.Recipe;
import de.atlasmc.recipe.RecipeBook;
import de.atlasmc.server.LocalServer;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.ThreadSafe;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public interface PlayerConnection extends Messageable {

	ProtocolAdapter getProtocolAdapter();
	
	ProtocolPlay getProtocol();

	/**
	 * 
	 * @return the current server or null if not at this note
	 */
	@ThreadSafe
	LocalServer getLocalSever();
	
	/**
	 * Used to bind a {@link Player} Entity to this connection if the player is currently on this {@link AtlasNode}
	 * @param player
	 */
	void setPlayer(Player player);
	
	/**
	 * 
	 * @return the current {@link Player} Entity bound to this connection or null
	 */
	Player getPlayer();
	
	NodePlayer getNodePlayer();
	
	boolean hasKeepAliveResponse();
	
	long getLastKeepAlive();
	
	void sendKeepAlive();
	
	/**
	 * Sends a packet to the client.
	 * @param packet
	 */
	void sendPacked(PacketProtocol packet);
	
	/**
	 * Sends a packet to the client.
	 * The future will inform when the packet is send or failed.
	 * @param packet
	 * @param listener
	 */
	void sendPacket(PacketProtocol packet, GenericFutureListener<? extends Future<? super Void>> listener);

	/**
	 * 
	 * @return the current inventory id
	 */
	int getInventoryID();

	/**
	 * increments and returns the inventory id
	 * @return the new current inventory id
	 */
	int getNextInventoryID();

	/**
	 * Teleports the player and returns the id of the teleport
	 * @param x
	 * @param y
	 * @param z
	 * @param yaw
	 * @param pitch
	 * @return teleport id
	 */
	int teleport(double x, double y, double z, float yaw, float pitch);
	
	/**
	 * Returns the id of the last teleport
	 * @return teleport id
	 */
	int getTeleportID();
	
	/**
	 * Sets the last teleport of the client as confirmed
	 */
	void confirmTeleport();
	
	boolean isTeleportConfirmed();
	
	boolean hasClientLocationChanged();
	
	void setClientLocationChanged(boolean changed);
	
	/**
	 * Returns the last location the client send and marks it as unchanged
	 * @return location
	 */
	SimpleLocation acceptClientLocation();
	
	/**
	 * Returns the last location the client send.
	 * Can be used for changes.
	 * @return
	 */
	SimpleLocation getClientLocation();
	
	/**
	 * Returns all {@link RecipeBook}s of this player
	 * @return list of recipe books
	 */
	List<RecipeBook> getRecipeBooks();
	
	/**
	 * Returns the {@link RecipeBook} represented by the type
	 * @param type
	 * @return book
	 */
	RecipeBook getRecipeBook(BookType type);
	
	/**
	 * Unlock the given recipes. If the recipe isn't present it will be added to the player
	 * @param notify whether or not the player should receive a unlock notification
	 * @param recipes the recipes to unlock
	 */
	void unlockRecipes(boolean notify, Recipe... recipes);
	
	/**
	 * Locks the given recipes in the player's {@link RecipeBook} but it is still can be used
	 * @param recipes
	 */
	void lockRecipes(Recipe... recipes);
	
	/**
	 * Removes recipes from players access
	 * @param recipes
	 */
	void removeRecipes(Recipe... recipes);
	
	/**
	 * Adds recipes to players access
	 * @param recipes
	 */
	void addRecipes(Recipe... recipes);
	
	Collection<Recipe> getUnlockedRecipes();
	
	Collection<Recipe> getAvailableRecipes();
	
	PluginChannelHandler getPluginChannelHandler();

	/**
	 * Will disconnect the player form the network
	 * @param message
	 */
	void disconnect(String message);
	
	LocalProxy getProxy();
	
	/**
	 * Returns the currently open advancement tab of the player or null if none.
	 * @return tab or null
	 */
	@Nullable
	NamespacedKey getOpenAdvancementTab();
	
	/**
	 * Returns whether or not the player has a open advancement tab.
	 * @return true if open
	 */
	boolean hasOpenAdvancementTab();
	
	/**
	 * Sets the currently open advancement tab of this player or null to close.
	 * @param tab
	 * @param update true if should update to the client
	 */
	void setOpenAdvancementTab(@Nullable NamespacedKey tab, boolean update);

	PlayerAnimationEvent getEventAnimation();

	PlayerHeldItemChangeEvent getEventHeldItemChange();

	PlayerToggleSneakEvent getEventSneak();

	PlayerToggleSprintEvent getEventSprint();

	PlayerMoveEvent getEventMove();

	void confirmKeepAlive(long lastResponseTime);
	
	boolean isClientOnGround();
	
	/**
	 * Only for updating value send by client
	 * @param onGround
	 */
	void setClientOnGround(boolean onGround);

	PlayerSettings getSettings();

	void setChunksPerTick(float chunksPerTick);
	
	float getChunksPerTick();
	
	/**
	 * Commands this client to change to configuration protocol
	 */
	void switchToConfiguration();
	
	/**
	 * Returns whether or not this client is in configuration
	 * @return true if in configuration
	 */
	boolean isInConfiguration();
	
	/**
	 * Returns whether or not a protocol change is in progress
	 * @return true if in change
	 */
	boolean isWaitingForProtocolChange();
	
	/**
	 * Changes the protocol to the new protocol
	 */
	void protocolChangeAcknowledged();
	
	void handleSyncPackets(Log logger);
	
}
