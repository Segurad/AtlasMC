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
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.entity.EntitySpawnEvent;
import de.atlasmc.io.protocol.play.PacketOutEffect;
import de.atlasmc.io.protocol.play.PacketOutEntitySoundEffect;
import de.atlasmc.io.protocol.play.PacketOutNamedSoundEffect;
import de.atlasmc.io.protocol.play.PacketOutParticle;
import de.atlasmc.io.protocol.play.PacketOutSoundEffect;
import de.atlasmc.util.MathUtil;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkListener;
import de.atlasmc.world.ChunkProvider;
import de.atlasmc.world.PlayerChunkListener;
import de.atlasmc.world.World;
import de.atlasmc.world.WorldFlag;

public class CoreWorld implements World {
	
	private final ChunkProvider chunks;
	private final String name;
	private final LocalServer server;
	private long time;
	private boolean timeCycle;
	private long age;
	private int nextEntityID;
	private Location spawn;
	
	public CoreWorld(LocalServer server, String name) {
		chunks = new CoreBufferedChunkProvider(this, null, null); // TODO use worldcreator
		this.name = name;
		this.server = server;
	}

	@Override
	public List<Entity> getEntities() {
		return chunks.getEntities();
	}

	@Override
	public <T extends Entity> List<T> getEntitiesByClass(Class<T> clazz) {
		return chunks.getEntitiesByClass(clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Entity> getEntitesByClasses(Class<? extends Entity>... classes) {
		return chunks.getEntitesByClasses(classes);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public LocalServer getServer() {
		return server;
	}

	@Override
	public Block getHighestBlockAt(int x, int z) {
		return getBlock(x, getHighestBlockYAt(x, z), z);
	}

	@Override
	public int getHighestBlockYAt(int x, int z) {
		return chunks.getHighestBlockYAt(x, z);
	}

	@Override
	public long getTime() {
		return time;
	}

	@Override
	public void playEffect(SimpleLocation loc, Effect effect, Object data, int radius) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		if (effect == null)
			throw new IllegalArgumentException("Effect can not be null!");
		Chunk chunk = getChunk(loc, false);
		if (chunk == null)
			return;
		PacketOutEffect packet = null;
		for (ChunkListener listener : chunk.getViewers()) {
			if (!(listener instanceof PlayerChunkListener))
				continue;
			PlayerChunkListener player = (PlayerChunkListener) listener;
			if (packet == null) {
				packet = new PacketOutEffect();
				packet.setEffect(effect);
				packet.setPosition(MathUtil.toPosition(loc));
				packet.setData(effect.getDataValueByObject(data));
			}
			player.getConnection().sendPacked(packet);
		}
	}

	@Override
	public void spawnParticle(Particle particle, SimpleLocation loc, int amount) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		if (particle == null)
			throw new IllegalArgumentException("Particle can not be null!");
		PacketOutParticle packet = null;
		Chunk chunk = getChunk(loc, false);
		if (chunk == null)
			return;
		for (ChunkListener listener : chunk.getViewers()) {
			if (!(listener instanceof PlayerChunkListener))
				continue;
			PlayerChunkListener player = (PlayerChunkListener) listener;
			if (packet == null) {
				packet = new PacketOutParticle();
				packet.setParticle(particle);
				packet.setLocation(loc.getX(), loc.getY(), loc.getZ());
				packet.setCount(amount);
			}
			player.getConnection().sendPacked(packet);
		}
	}

	@Override
	public Location getSpawnLocation() {
		return spawn.clone();
	}
	
	@Override
	public Location getSpawnLocation(Location loc) {
		return spawn.copyTo(loc);
	}

	@Override
	public void playSound(SimpleLocation loc, Sound sound, SoundCategory category, float volume, float pitch) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		if (sound == null)
			throw new IllegalArgumentException("Sound can not be null!");
		if (category == null)
			throw new IllegalAccessError("Category can not be null!");
		PacketOutSoundEffect packet = null;
		Chunk chunk = getChunk(loc, false);
		if (chunk == null)
			return;
		for (ChunkListener listener : chunk.getViewers()) {
			if (!(listener instanceof PlayerChunkListener))
				continue;
			PlayerChunkListener player = (PlayerChunkListener) listener;
			if (packet == null) {
				packet = new PacketOutSoundEffect();
				packet.setSound(sound);
				packet.setCategory(category);
				packet.setPitch(pitch);
				packet.setVolume(volume);
				packet.setLocation(loc.getX(), loc.getY(), loc.getZ());
			}
			player.getConnection().sendPacked(packet);
		}
	}

	@Override
	public void playSound(SimpleLocation loc, String sound, SoundCategory category, float volume, float pitch) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		if (sound == null)
			throw new IllegalArgumentException("Sound can not be null!");
		if (category == null)
			throw new IllegalAccessError("Category can not be null!");
		PacketOutNamedSoundEffect packet = null;
		Chunk chunk = getChunk(loc, false);
		if (chunk == null)
			return;
		for (ChunkListener listener : chunk.getViewers()) {
			if (!(listener instanceof PlayerChunkListener))
				continue;
			PlayerChunkListener player = (PlayerChunkListener) listener;
			if (packet == null) {
				packet = new PacketOutNamedSoundEffect();
				packet.setIdentifier(sound);
				packet.setCategory(category);
				packet.setPitch(pitch);
				packet.setVolume(volume);
				packet.setLocation(loc.getX(), loc.getY(), loc.getZ());
			}
			player.getConnection().sendPacked(packet);
		}
	}
	
	@Override
	public void playSound(Entity entity, Sound sound, SoundCategory category, float volume, float pitch) {
		if (entity == null)
			throw new IllegalArgumentException("Entity can not be null!");
		if (sound == null)
			throw new IllegalArgumentException("Sound can not be null!");
		if (category == null)
			throw new IllegalAccessError("Category can not be null!");
		PacketOutEntitySoundEffect packet = null;
		Chunk chunk = entity.getChunk();
		if (chunk == null)
			return;
		for (ChunkListener listener : chunk.getViewers()) {
			if (!(listener instanceof PlayerChunkListener))
				continue;
			PlayerChunkListener player = (PlayerChunkListener) listener;
			if (packet == null) {
				packet = new PacketOutEntitySoundEffect();
				packet.setSound(sound);
				packet.setCategory(category);
				packet.setPitch(pitch);
				packet.setVolume(volume);
				packet.setEntityID(entity.getID());
			}
			player.getConnection().sendPacked(packet);
		}
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
		return chunks.getBlock(x, y, z);
	}

	@Override
	public BlockData getBlockData(int x, int y, int z) {
		return chunks.getBlockData(x, y ,z);
	}

	@Override
	public void tick() {
		age++;
		if (timeCycle) {
			if (time >= 24000) {
				time = 0;
			} else
			time++;
		}
		chunks.tick();
	}

	@Override
	public long getAge() {
		return age;
	}

	@Override
	public Entity getEntity(int entityID) {
		return chunks.getEntity(entityID);
	}

	@Override
	public Chunk getChunk(int x, int z) {
		return getChunk(x, z, true);
	}
	
	@Override
	public Chunk getChunk(int x, int z, boolean load) {
		return chunks.getChunk(x, z, load);
	}

	@Override
	public Chunk getChunk(SimpleLocation loc) {
		return getChunk(loc, true);
	}
	
	@Override
	public Chunk getChunk(SimpleLocation loc, boolean load) {
		return getChunk(loc.getBlockX() >> 4, loc.getBlockZ() >> 4, load);
	}

	@Override
	public boolean spawnEntity(Entity entity, double x, double y, double z, float pitch, float yaw) {
		if (!entity.asyncIsRemoved())
			return false;
		EntitySpawnEvent event = new EntitySpawnEvent(entity, this, x, y, z, pitch, yaw);
		HandlerList.callEvent(event);
		if (event.isCancelled())
			return false;
		entity.spawn(nextEntityID++, this, event.getX(), event.getY(), event.getZ(), event.getPitch(), event.getYaw());
		return true;
	}

	@Override
	public Entity spawnEntity(EntityType type, double x, double y, double z, float pitch, float yaw) {
		Entity ent = type.create(this);
		return spawnEntity(ent, x, y, z, pitch, yaw) ? ent : null;
	}

	@Override
	public int getEntityID() {
		return nextEntityID++;
	}

}