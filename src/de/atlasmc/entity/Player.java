package de.atlasmc.entity;

import java.util.UUID;

import de.atlasmc.Effect;
import de.atlasmc.Location;
import de.atlasmc.Particle;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.scoreboard.Scoreboard;

public interface Player extends AtlasPlayer, HumanEntity {
	
	public LocalServer getCurrentServer();

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

	public String getName();

	public void setScoreboard(Scoreboard board);
	
	public UUID getInternalUUID();
	public boolean hasInternalUUID();
	public void setInternalUUID(UUID uuid);

	public String getLocal();

	public InventoryView getOpenInventory();

}
