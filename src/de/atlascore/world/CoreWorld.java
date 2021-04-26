package de.atlascore.world;

import java.util.List;

import de.atlasmc.Effect;
import de.atlasmc.Location;
import de.atlasmc.Particle;
import de.atlasmc.SimpleLocation;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.block.Block;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.world.World;
import de.atlasmc.world.WorldFlag;

public class CoreWorld implements World {

	@Override
	public List<Entity> getEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Entity> List<T> getEntitiesByClass(Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Entity> getEntitesByClasses(Class<? extends Entity>... classes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalServer getServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity spawnEntity(SimpleLocation loc, EntityType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Block getHighestBlockAt(int x, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHighestBlockYAt(int x, int z) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void playEffect(SimpleLocation loc, Effect effect, Object data, int radius) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spawnParticle(Particle particle, SimpleLocation loc, int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Location getSpawnLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void playSound(SimpleLocation loc, Sound sound, SoundCategory category, float volume, float pitch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playSound(SimpleLocation loc, String sound, SoundCategory category, float volume, float pitch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasFlag(WorldFlag flag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addFlag(WorldFlag flag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFlag(WorldFlag flag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Block getBlock(int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockData getBlockData(int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

}
