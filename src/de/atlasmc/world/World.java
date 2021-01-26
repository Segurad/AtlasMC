package de.atlasmc.world;

import java.util.List;

import de.atlasmc.Effect;
import de.atlasmc.Location;
import de.atlasmc.Particle;
import de.atlasmc.SimpleLocation;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.block.Block;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.io.atlasnetwork.server.AtlasServer;

public interface World {

	public List<Entity> getEntities();
	public <T extends Entity> List<T> getEntitiesByClass(Class<T> clazz);

	@SuppressWarnings("unchecked")
	public List<Entity> getEntitesByClasses(Class<? extends Entity>... classes);

	public String getName();
	
	public AtlasServer getServer();

	public Entity spawnEntity(SimpleLocation loc, EntityType type);

	public Block getHighestBlockAt(int x, int z);

	public int getHighestBlockYAt(int x, int z);

	public long getTime();

	public void playEffect(SimpleLocation loc, Effect effect, Object data, int radius);

	public void spawnParticle(Particle particle, SimpleLocation loc, int amount);

	public Location getSpawnLocation();

	public void playSound(SimpleLocation loc, Sound sound, SoundCategory category, float volume, float pitch);

	public void playSound(SimpleLocation loc, String sound, SoundCategory category, float volume, float pitch);
	
	public boolean hasFlag(WorldFlag flag);
	public void addFlag(WorldFlag flag);
	public void removeFlag(WorldFlag flag);

	public default Block getBlock(SimpleLocation loc) {
		return getBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	public Block getBlock(int x, int y, int z);
	

}
