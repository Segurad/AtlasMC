package de.atlasmc.io.protocol;

import java.util.Collection;
import java.util.List;

import de.atlasmc.SimpleLocation;
import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.chat.ChatType;
import de.atlasmc.entity.Player;
import de.atlasmc.io.Packet;
import de.atlasmc.recipe.BookType;
import de.atlasmc.recipe.Recipe;
import de.atlasmc.recipe.RecipeBook;
import de.atlasmc.util.annotation.ThreadSafe;

public interface PlayerConnection extends PacketListenerPlayIn {
	
	/**
	 * Queues a packet for sync handling
	 * @param packet
	 */
	public void queueInboundPacket(Packet packet);
	
	/**
	 * Called by the server to handle all queued packets
	 */
	public void handleQueuedPackets();

	public ProtocolAdapter getProtocolAdapter();
	
	public ProtocolPlay getProtocol();

	/**
	 * 
	 * @return the current server or null if not at this note
	 */
	@ThreadSafe
	public LocalServer getLocalSever();
	
	public int getNextWindowActionID();
	
	public int getNextWindowActionIDAndLock();
	
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
	
	public ChatType getChatMode();
	
	public boolean hasKeepAliveResponse();
	
	public long getLastKeepAlive();
	
	public void sendKeepAlive();
	
	public void sendPacked(PacketProtocol packet);

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

	public String getClientLocal();

	/**
	 * Cancel a click
	 */
	public void setWindowLock();

	/**
	 * Creates a Packet of the Packet class with the current protocol.<br>
	 * @param clazz
	 * @return instance of Packet
	 */
	public <T extends Packet> T createPacket(Class<T> clazz);
	
	public void teleport(double x, double y, double z, float yaw, float pitch);

	/**
	 * Returns the last location the client send.<br>
	 * Should not be changed.
	 * @return location
	 */
	public SimpleLocation getClientLocation();
	
	public boolean hasClientLocationChanged();
	
	/**
	 * Returns the last location the client send and marks it as unchanged
	 * @return location
	 */
	public SimpleLocation acceptClientLocation();
	
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

}
