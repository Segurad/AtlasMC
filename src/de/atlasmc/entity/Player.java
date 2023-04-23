package de.atlasmc.entity;

import java.util.UUID;

import de.atlasmc.Effect;
import de.atlasmc.Gamemode;
import de.atlasmc.Particle;
import de.atlasmc.SimpleLocation;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.block.DiggingHandler;
import de.atlasmc.chat.Messageable;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.permission.Permissible;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.scoreboard.ScoreboardView;

public interface Player extends HumanEntity, Permissible, Messageable {
	
	public PermissionHandler getPermissionHandler();
	
	public void setPermissionHandler(PermissionHandler handler);

	public int getLevel();

	public void setLevel(int level);

	public default void playEffect(SimpleLocation loc, Effect effect) {
		playEffect(loc, effect, null, true);
	}
	
	/**
	 * 
	 * @implNote #playEffect(loc, effect, data, true)
	 * @param loc
	 * @param effect
	 * @param data
	 */
	public default void playEffect(SimpleLocation loc, Effect effect, Object data) {
		playEffect(loc, effect, data, true);
	}
	
	/**
	 * Plays a sound or particle effect
	 * @param loc
	 * @param effect
	 * @param data
	 * @param relativeSound
	 */
	public void playEffect(SimpleLocation loc, Effect effect, Object data, boolean relativeSound);
	
	public void playEffect(int x, int y, int z, Effect effect, Object data, boolean relativeSound);

	public PlayerConnection getConnection();
	
	public AtlasPlayer getAtlasPlayer();

	public String getName();

	public String getLocal();

	public boolean getCanBuild();

	public void setCanBuild(boolean canBuild);

	public Gamemode getGamemode();
	
	public void setGamemode(Gamemode gamemode);
	
	/**
	 * Returns a Object stored by a {@link Plugin}<br>
	 * This method is used for storing data for e.g. a the current minigame
	 * @return the stored Object
	 */
	public Object getPluginData();
	
	public void setPluginData(Object data);
	
	/**
	 * Does the same thing as {@link #getPluginData()} but does cast it and returns null if not matching the class
	 * @param <T>
	 * @param clazz
	 * @return the plugindata or null if not assignable from class
	 */
	public <T> T getPluginData(Class<T> clazz); 
	
	public boolean hasPluginData();
	
	/**
	 * Sets the {@link ScoreboardView} to this Player.<br>
	 * Calls {@link ScoreboardView#add()} on the added view.<br>
	 * Calls {@link ScoreboardView#remove()} on the old view.
	 * @param view the new view or null
	 * @throws IllegalArgumentException if the view does not belong to the player
	 */
	public void setScoreboard(ScoreboardView view);
	
	/**
	 * Returns the current active view or null
	 * @return view or null
	 */
	public ScoreboardView getScoreboard();

	// --- Sound ---
	
	void stopSound(SoundCategory category, String sound);
	
	// Entity sound
	
	void playSound(Entity entity, Sound sound, SoundCategory category, float volume, float pitch);
	
	void playSound(Entity entity, String sound, SoundCategory category, float volume, float pitch);
	
	// Located sound
	
	default void playSound(SimpleLocation loc, Sound sound, float volume, float pitch) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		playSound(loc.getX(), loc.getY(), loc.getZ(), sound, volume, pitch);
	}
	
	default void playSound(SimpleLocation loc, Sound sound, SoundCategory category, float volume, float pitch) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		playSound(loc.getX(), loc.getY(), loc.getZ(), sound, category, volume, pitch);
	}
	
	default void playSound(double x, double y, double z, Sound sound, float volume, float pitch) {
		playSound(x, y, z, sound, SoundCategory.MASTER, volume, pitch);
	}
	
	void playSound(double x, double y, double z, Sound sound, SoundCategory category, float volume, float pitch);

	// Located named sound
	
	default void playSound(SimpleLocation loc, String sound, float volume, float pitch) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		playSound(loc.getX(), loc.getY(), loc.getZ(), sound, volume, pitch);
	}
	
	default void playSound(SimpleLocation loc, String sound, SoundCategory category, float volume, float pitch) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		playSound(loc.getX(), loc.getY(), loc.getZ(), sound, category, volume, pitch);
	}
	
	default void playSound(double x, double y, double z, String sound, float volume, float pitch) {
		playSound(x, y, z, sound, SoundCategory.MASTER, volume, pitch);
	}
	
	void playSound(double x, double y, double z, String sound, SoundCategory category, float volume, float pitch);
	
	// --- Particle ---
	
	void spawnParticle(Particle particle, double x, double y, double z, float particledata);
	
	void spawnParticle(Particle particle, double x, double y, double z, float particledata, int count);
	
	void spawnParticle(Particle particle, double x, double y, double z, float particledata, int count, Object data);
	
	void spawnParticle(Particle particle, double x, double y, double z, float offX, float offY, float offZ, float particledata, int count, Object data);
	
	// --- Inventory Stuff ---
	
	InventoryView getOpenInventory();
	
	void openInventory(Inventory inv);
	
	void closeInventory();

	void setItemOnCursor(ItemStack item);

	ItemStack getItemOnCursor();
	
	void setItemOnCursorUnsafe(ItemStack item);
	
	ItemStack getItemOnCursorUnsafe();
	
	void updateItemOnCursor();

	void disconnect(String message);
	
	DiggingHandler getDigging();
	
	void setDiggingHandller(DiggingHandler handler);
	
	/**
	 * Returns the UUID of this Player equivalent to {@link AtlasPlayer#getInternalUUID()}
	 * @return UUID
	 */
	UUID getUUID();
	
}
