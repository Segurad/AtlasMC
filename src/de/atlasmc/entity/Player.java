package de.atlasmc.entity;

import de.atlasmc.Effect;
import de.atlasmc.Location;
import de.atlasmc.Particle;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.server.AtlasServer;
import de.atlasmc.server.ServerPlayer;

public interface Player extends ServerPlayer, HumanEntity {
	
	public AtlasServer getServer();

	public void openInventory(Inventory inv);

	public boolean hasPermission(String permission);

	public void playSound(Location location, Sound sound, int i, int j);

	public int getLevel();

	public void setLevel(int i);

	public void playEffect(Location loc, Effect effect, Object data);

	public void spawnParticle(Particle particle, Location loc, int amount, int i, int j, int k, int l);

	public void playSound(Location loc, Sound sound, SoundCategory category, float volume, float pitch);

	public void playSound(Location loc, String ssound, SoundCategory category, float volume, float pitch);

}
