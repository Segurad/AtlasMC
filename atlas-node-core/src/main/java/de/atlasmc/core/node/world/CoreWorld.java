package de.atlasmc.core.node.world;

import java.io.File;
import java.util.Collection;

import de.atlasmc.event.HandlerList;
import de.atlasmc.node.WorldLocation;
import de.atlasmc.node.Location;
import de.atlasmc.node.SoundCategory;
import de.atlasmc.node.block.Block;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.event.entity.EntitySpawnEvent;
import de.atlasmc.node.io.protocol.play.PacketOutEntitySoundEffect;
import de.atlasmc.node.io.protocol.play.PacketOutParticle;
import de.atlasmc.node.io.protocol.play.PacketOutSoundEffect;
import de.atlasmc.node.io.protocol.play.PacketOutWorldEvent;
import de.atlasmc.node.server.LocalServer;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.node.util.MathUtil;
import de.atlasmc.node.world.Chunk;
import de.atlasmc.node.world.ChunkFactory;
import de.atlasmc.node.world.ChunkGenerator;
import de.atlasmc.node.world.ChunkGeneratorFactory;
import de.atlasmc.node.world.ChunkLoader;
import de.atlasmc.node.world.ChunkLoaderFactory;
import de.atlasmc.node.world.ChunkProvider;
import de.atlasmc.node.world.ChunkProviderFactory;
import de.atlasmc.node.world.ChunkViewer;
import de.atlasmc.node.world.Dimension;
import de.atlasmc.node.world.PlayerChunkListener;
import de.atlasmc.node.world.World;
import de.atlasmc.node.world.WorldBuilder;
import de.atlasmc.node.world.WorldEvent;
import de.atlasmc.node.world.WorldFlag;
import de.atlasmc.node.world.entitytracker.EntityTracker;
import de.atlasmc.node.world.entitytracker.EntityTrackerFactory;
import de.atlasmc.node.world.particle.ParticleType;
import de.atlasmc.util.configuration.ConfigurationSection;

public class CoreWorld implements World {
	
	private final ChunkProvider chunks;
	private final String name;
	private final LocalServer server;
	private final Dimension dimension;
	private final EntityTracker entityTracker;
	private long time;
	private boolean timeCycle;
	private long age;
	private WorldLocation spawn;
	
	public CoreWorld(WorldBuilder builder) {
		if (builder == null)
			throw new IllegalArgumentException("Builder can not be null!");
		this.name = builder.getName();
		this.server = builder.getServer();
		this.dimension = builder.getDimension();
		if (server == null)
			throw new IllegalStateException("Server can not be null!");
		if (name == null)
			throw new IllegalStateException("Name can not be null!");
		if (dimension == null)
			throw new IllegalStateException("Dimension can not be null!");
		this.chunks = buildChunkProvider(builder);
		if (chunks == null)
			throw new IllegalStateException("No chunk provider initialized!");
		EntityTrackerFactory entityTrackerFactory = builder.getEntityTracker();
		if (entityTrackerFactory == null)
			throw new IllegalArgumentException("EntityTrackerFactory can not be null!");
		this.entityTracker = entityTrackerFactory.createEntityTracker(this);
	}
	
	private ChunkProvider buildChunkProvider(WorldBuilder builder) {
		final ChunkProviderFactory chunkProviderFactory = builder.getChunkProviderFactory();
		if (chunkProviderFactory == null)
			throw new IllegalStateException("ChunkProviderFactoy can not be null!");
		final ChunkFactory chunkFactory = builder.getChunkFactory();
		ChunkGeneratorFactory chunkGenFactory = builder.getChunkGenerator();
		ChunkGenerator chunkGen = null; // may be null in case no chunks need to be generated
		if (chunkGenFactory != null)
			 chunkGenFactory.createChunkGenerator(this, chunkFactory, builder.getChunkGeneratorConfig());
		final File worldDir = builder.getWorldDir();
		ChunkLoaderFactory chunkLoaderFactory = builder.getChunkLoader();
		ChunkLoader chunkLoader = null; // may be null i case no world needs to be loaded or saved
		if (chunkLoaderFactory != null)
			chunkLoader = chunkLoaderFactory.createChunkLoader(this, worldDir, chunkFactory, builder.getChunkLoaderConfig());
		ConfigurationSection providerCfg = builder.getChunkProviderConfig();
		return chunkProviderFactory.createProvider(this, chunkGen, chunkLoader, providerCfg);
	}

	@Override
	public Collection<Entity> getEntities() {
		return entityTracker.getEntities();
	}

	@Override
	public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> clazz) {
		return entityTracker.getEntitiesByClass(clazz);
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
	public void playEffect(Location loc, WorldEvent effect, Object data, int radius) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		if (effect == null)
			throw new IllegalArgumentException("Effect can not be null!");
		Chunk chunk = getChunk(loc, false);
		if (chunk == null)
			return;
		PacketOutWorldEvent packet = null;
		for (ChunkViewer listener : chunk.getViewers()) {
			if (!(listener instanceof PlayerChunkListener))
				continue;
			PlayerChunkListener player = (PlayerChunkListener) listener;
			if (packet == null) {
				packet = new PacketOutWorldEvent();
				packet.event = effect;
				packet.position = MathUtil.toPosition(loc);
				packet.data = effect.getDataValueByObject(data);
			}
			player.getConnection().sendPacked(packet);
		}
	}

	@Override
	public void spawnParticle(ParticleType particle, Location loc, int amount) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		if (particle == null)
			throw new IllegalArgumentException("Particle can not be null!");
		PacketOutParticle packet = null;
		Chunk chunk = getChunk(loc, false);
		if (chunk == null)
			return;
		for (ChunkViewer listener : chunk.getViewers()) {
			if (!(listener instanceof PlayerChunkListener))
				continue;
			PlayerChunkListener player = (PlayerChunkListener) listener;
			if (packet == null) {
				packet = new PacketOutParticle();
				packet.particle = particle;
				packet.x = loc.x;
				packet.y = loc.y;
				packet.z = loc.z;
				packet.count = amount;
			}
			player.getConnection().sendPacked(packet);
		}
	}

	@Override
	public WorldLocation getSpawnLocation() {
		return spawn.clone();
	}
	
	@Override
	public WorldLocation getSpawnLocation(WorldLocation loc) {
		return spawn.copyTo(loc);
	}
	
	@Override
	public void playSound(double x, double y, double z, Sound sound, SoundCategory category, float volume, float pitch, long seed) {
		if (sound == null)
			throw new IllegalArgumentException("Sound can not be null!");
		if (category == null)
			throw new IllegalAccessError("Category can not be null!");
		PacketOutSoundEffect packet = null;
		Chunk chunk = getChunk((int) x, (int) z, false);
		if (chunk == null)
			return;
		for (ChunkViewer listener : chunk.getViewers()) {
			if (!(listener instanceof PlayerChunkListener))
				continue;
			PlayerChunkListener player = (PlayerChunkListener) listener;
			if (packet == null) {
				packet = new PacketOutSoundEffect();
				packet.x = x;
				packet.y = y;
				packet.z = z;
				packet.sound = sound;
				packet.category = category;
				packet.volume = volume;
				packet.pitch = pitch;
				packet.seed = seed;
			}
			player.getConnection().sendPacked(packet);
		}
	}
	
	@Override
	public void playSound(Entity entity, Sound sound, SoundCategory category, float volume, float pitch, long seed) {
		if (entity == null)
			throw new IllegalArgumentException("Entity can not be null!");
		if (sound == null)
			throw new IllegalArgumentException("Sound can not be null!");
		if (category == null)
			throw new IllegalAccessError("Category can not be null!");
		PacketOutEntitySoundEffect packet = null;
		for (Player player : entity.getViewers()) {
			if (packet == null) {
				packet = new PacketOutEntitySoundEffect();
				packet.entityID = entity.getID();
				packet.sound = sound;
				packet.category = category;
				packet.volume = volume;
				packet.pitch = pitch;
				packet.seed = seed;
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
		return entityTracker.getEntity(entityID);
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
	public Chunk getChunk(Location loc) {
		return getChunk(loc, true);
	}
	
	@Override
	public Chunk getChunk(Location loc, boolean load) {
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
		entity.spawn(this, event.getX(), event.getY(), event.getZ(), event.getPitch(), event.getYaw());
		return true;
	}

	@Override
	public Entity spawnEntity(EntityType type, double x, double y, double z, float pitch, float yaw) {
		Entity ent = type.createEntity();
		return spawnEntity(ent, x, y, z, pitch, yaw) ? ent : null;
	}

	@Override
	public Dimension getDimension() {
		return dimension;
	}

	@Override
	public EntityTracker getEntityTracker() {
		return entityTracker;
	}

}