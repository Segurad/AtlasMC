package de.atlasmc.world;

import de.atlasmc.Effect;
import de.atlasmc.Location;
import de.atlasmc.Particle;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.block.Block;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.server.AtlasServer;

public interface World {

	public String getName();
	
	public AtlasServer getServer();

	public Entity spawnEntity(Location loc, EntityType type);

	public Block getHighestBlockAt(int x, int z);

	public int getHighestBlockYAt(int x, int z);

	public long getTime();

	public void playEffect(Location loc, Effect effect, Object data, int radius);

	public void spawnParticle(Particle particle, Location loc, int amount);

	public Location getSpawnLocation();

	public void playSound(Location loc, Sound sound, SoundCategory category, float volume, float pitch);

	public void playSound(Location loc, String ssound, SoundCategory category, float volume, float pitch);
	
	public boolean hasFlag(WorldFlag flag);
	public void addFlag(WorldFlag flag);
	public void removeFlag(WorldFlag flag);
	

}
