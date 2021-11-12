package de.atlasmc.entity;

import java.util.UUID;

import de.atlasmc.Effect;
import de.atlasmc.Gamemode;
import de.atlasmc.Location;
import de.atlasmc.Particle;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryView;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.scoreboard.Scoreboard;

public interface Player extends HumanEntity {

	public void openInventory(Inventory inv);

	public boolean hasPermission(String permission);

	public void playSound(Location location, Sound sound, int i, int j);

	public int getLevel();

	public void setLevel(int level);

	public void playEffect(Location loc, Effect effect, Object data);

	public void spawnParticle(Particle particle, double x, double y, double z, float particledata);
	
	public void spawnParticle(Particle particle, double x, double y, double z, float particledata, int count);
	
	public void spawnParticle(Particle particle, double x, double y, double z, float particledata, int count, Object data);
	
	public void spawnParticle(Particle particle, double x, double y, double z, float offX, float offY, float offZ, float particledata, int count, Object data);

	public void playSound(Location loc, Sound sound, SoundCategory category, float volume, float pitch);

	public void playSound(Location loc, String ssound, SoundCategory category, float volume, float pitch);

	public void closeInventory();

	public void setItemOnCursor(ItemStack item);

	public ItemStack getItemOnCursor();
	
	public void updateItemOnCursor();

	public PlayerConnection getConnection();
	
	public AtlasPlayer getAtlasPlayer();

	public String getName();

	public void setScoreboard(Scoreboard board);
	
	public UUID getInternalUUID();
	
	public boolean hasInternalUUID();
	
	public void setInternalUUID(UUID uuid);

	public String getLocal();

	public InventoryView getOpenInventory();

	public Gamemode getGamemode();

	public boolean getCanBuild();

	public void setCanBuild(boolean canBuild);

	public void setGamemode(Gamemode gamemode);

}
