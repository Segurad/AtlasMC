package de.atlasmc.entity;

import java.util.UUID;

import de.atlasmc.Effect;
import de.atlasmc.Gamemode;
import de.atlasmc.Location;
import de.atlasmc.Particle;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.chat.ChatAdapter;
import de.atlasmc.chat.ChatChannel;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.scoreboard.Scoreboard;

public interface Player extends HumanEntity {

	public ChatAdapter getChat();
	
	public void sendMessage(ChatChannel channel, ChatComponent chat);
	
	/**
	 * Send a message in the SystemChannel
	 * @param chat
	 */
	public void sendMessage(ChatComponent chat);
	
	public PermissionHandler getPermissionHandler();
	
	public void setPermissionHandler(PermissionHandler handler);
	
	public boolean hasPermission(String permission);

	public void playSound(Location location, Sound sound, int i, int j);

	public int getLevel();

	public void setLevel(int level);

	public void playEffect(Location loc, Effect effect, Object data);

	public PlayerConnection getConnection();
	
	public AtlasPlayer getAtlasPlayer();

	public String getName();

	public void setScoreboard(Scoreboard board);
	
	public UUID getInternalUUID();
	
	public void setInternalUUID(UUID uuid);

	public String getLocal();

	public boolean getCanBuild();

	public void setCanBuild(boolean canBuild);

	public Gamemode getGamemode();
	
	public void setGamemode(Gamemode gamemode);
	
	/**
	 * Returns a Object stored by a {@link Plugin}<br>
	 * This method are used for storing data for e.g. a the current minigame
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

	// --- Sound ---
	
	public void playSound(Location loc, Sound sound, SoundCategory category, float volume, float pitch);

	public void playSound(Location loc, String ssound, SoundCategory category, float volume, float pitch);
	
	// --- Particle ---
	
	public void spawnParticle(Particle particle, double x, double y, double z, float particledata);
	
	public void spawnParticle(Particle particle, double x, double y, double z, float particledata, int count);
	
	public void spawnParticle(Particle particle, double x, double y, double z, float particledata, int count, Object data);
	
	public void spawnParticle(Particle particle, double x, double y, double z, float offX, float offY, float offZ, float particledata, int count, Object data);

	// --- Inventory Stuff ---
	
	public InventoryView getOpenInventory();
	
	public void openInventory(Inventory inv);
	
	public void closeInventory();

	public void setItemOnCursor(ItemStack item);

	public ItemStack getItemOnCursor();
	
	public void updateItemOnCursor();
	
}
