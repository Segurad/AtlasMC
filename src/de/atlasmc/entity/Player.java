package de.atlasmc.entity;

import de.atlasmc.Effect;
import de.atlasmc.Location;
import de.atlasmc.Particle;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.atlasnetwork.server.AtlasPlayer;
import de.atlasmc.io.atlasnetwork.server.AtlasServer;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.scoreboard.Scoreboard;

public interface Player extends AtlasPlayer, HumanEntity {
	
	public AtlasServer getServer();

	public void openInventory(Inventory inv);

	public boolean hasPermission(String permission);

	public void playSound(Location location, Sound sound, int i, int j);

	public int getLevel();

	public void setLevel(int level);

	public void playEffect(Location loc, Effect effect, Object data);

	public void spawnParticle(Particle particle, Location loc, int amount, int i, int j, int k, int l);

	public void playSound(Location loc, Sound sound, SoundCategory category, float volume, float pitch);

	public void playSound(Location loc, String ssound, SoundCategory category, float volume, float pitch);

	public void closeInventory();

	public void setItemOnCursor(ItemStack item);

	public ItemStack getItemOnCursor();

	public PlayerConnection getConnection();

	/**
	 * 
	 * @param inventory
	 * @return the current windowID or -1 if the Inventory does not belong to the Player
	 */
	public byte getWindowID(Inventory inventory);

	public String getName();

	public void setScoreboard(Scoreboard board);

}
