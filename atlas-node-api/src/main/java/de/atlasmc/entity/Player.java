package de.atlasmc.entity;

import java.util.UUID;

import de.atlasmc.Gamemode;
import de.atlasmc.NamespacedKey;
import de.atlasmc.NodePlayer;
import de.atlasmc.Particle;
import de.atlasmc.SimpleLocation;
import de.atlasmc.SoundCategory;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.block.DiggingHandler;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.Messageable;
import de.atlasmc.inventory.EquipmentSlot;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.permission.Permissible;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.scoreboard.ScoreboardView;
import de.atlasmc.sound.SoundListener;
import de.atlasmc.util.annotation.UnsafeAPI;
import de.atlasmc.world.WorldEvent;

public interface Player extends HumanEntity, Permissible, Messageable, SoundListener {
	
	PermissionHandler getPermissionHandler();
	
	int getLevel();

	void setLevel(int level);
	
	int getExp();
	
	void setExp(int xp);
	
	float getExpBar();
	
	void setExpBar(float progress);
	
	void setExp(int level, int exp, float progress);
	
	void setLevel(int level, float progress);

	default void playEffect(SimpleLocation loc, WorldEvent effect) {
		playEffect(loc, effect, null, true);
	}
	
	/**
	 * 
	 * @param loc
	 * @param effect
	 * @param data
	 * @implNote {@link #playEffect(int, int, int, WorldEvent, Object, boolean)}
	 */
	default void playEffect(SimpleLocation loc, WorldEvent effect, Object data) {
		playEffect(loc, effect, data, true);
	}
	
	/**
	 * Plays a sound or particle effect
	 * @param loc
	 * @param effect
	 * @param data
	 * @param relativeSound
	 */
	void playEffect(SimpleLocation loc, WorldEvent effect, Object data, boolean relativeSound);
	
	void playEffect(int x, int y, int z, WorldEvent effect, Object data, boolean relativeSound);
	
	void setConnection(PlayerConnection con);

	PlayerConnection getConnection();
	
	AtlasPlayer getAtlasPlayer();
	
	NodePlayer getNodePlayer();

	String getName();

	String getLocal();

	boolean getCanBuild();

	void setCanBuild(boolean canBuild);

	Gamemode getGamemode();
	
	void setGamemode(Gamemode gamemode);
	
	/**
	 * Returns a Object stored by a {@link Plugin}<br>
	 * This method is used for storing data for e.g. a the current minigame
	 * @return the stored Object
	 */
	Object getPluginData();
	
	void setPluginData(Object data);
	
	/**
	 * Does the same thing as {@link #getPluginData()} but does cast it and returns null if not matching the class
	 * @param <T>
	 * @param clazz
	 * @return the plugindata or null if not assignable from class
	 */
	<T> T getPluginData(Class<T> clazz); 
	
	boolean hasPluginData();
	
	/**
	 * Sets the {@link ScoreboardView} to this Player.<br>
	 * Calls {@link ScoreboardView#add()} on the added view.<br>
	 * Calls {@link ScoreboardView#remove()} on the old view.
	 * @param view the new view or null
	 * @throws IllegalArgumentException if the view does not belong to the player
	 */
	void setScoreboard(ScoreboardView view);
	
	/**
	 * Returns the current active view or null
	 * @return view or null
	 */
	ScoreboardView getScoreboard();

	// --- Sound ---
	
	void stopSound(SoundCategory category, NamespacedKey sound);
	
	// --- Particle ---
	
	void spawnParticle(Particle particle, double x, double y, double z, float maxSpeed);
	
	void spawnParticle(Particle particle, double x, double y, double z, float maxSpeed, int count);
	
	void spawnParticle(Particle particle, double x, double y, double z, float maxSpeed, int count, Object data);
	
	void spawnParticle(Particle particle, double x, double y, double z, float offX, float offY, float offZ, float maxSpeed, int count, Object data);
	
	// --- Inventory Stuff ---
	
	InventoryView getOpenInventory();
	
	void openInventory(Inventory inv);
	
	/**
	 * Opens the book item given
	 * @param book
	 */
	void openBook(ItemStack book);
	
	/**
	 * Opens the book item given.
	 * Will not create a copy of the book to open.
	 * @param book
	 */
	@UnsafeAPI
	void openBookUsafe(ItemStack book);
	
	/**
	 * Opens the book item held in the specific hand
	 * @param hand
	 */
	void openBook(EquipmentSlot hand);
	
	void closeInventory();

	void setItemOnCursor(ItemStack item);

	ItemStack getItemOnCursor();
	
	void setItemOnCursorUnsafe(ItemStack item);
	
	ItemStack getItemOnCursorUnsafe();
	
	void updateItemOnCursor();

	void disconnect(Chat message);
	
	DiggingHandler getDigging();
	
	void setDiggingHandller(DiggingHandler handler);
	
	/**
	 * Returns the UUID of this Player equivalent to {@link AtlasPlayer#getInternalUUID()}
	 * @return UUID
	 */
	UUID getUUID();
	
	boolean performCommand(String command);
	
}
