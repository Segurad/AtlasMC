package de.atlasmc.node.world;

import java.io.File;

import de.atlasmc.node.server.LocalServer;
import de.atlasmc.node.world.entitytracker.EntityTrackerFactory;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.util.Builder;
import de.atlasmc.util.Pair;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationException;

public class WorldBuilder implements Builder<World> {

	private LocalServer server;
	private String name;
	private File worldDir;
	private ConfigurationSection chunkGenConfig;
	private ChunkGeneratorFactory chunkGenerator;
	private ConfigurationSection chunkLoaderConfig;
	private ChunkLoaderFactory chunkLoader;
	private ConfigurationSection chunkProviderConfig;
	private ChunkProviderFactory chunkProviderFactory;
	private Dimension dimension;
	private WorldFactory worldFactory;
	private ChunkFactory chunkFactory;
	private EntityTrackerFactory entityTracker;
	
	public WorldBuilder() {}
	
	public WorldBuilder(ConfigurationSection config) {
		setConfiguration(config);
	}
	
	/**
	 * Retrieves the factory and config within a {@link ConfigurationSection} with the given key.
	 * If the section of the key is null a empty pair will be returned.
	 * @param <T> type of factory
	 * @param clazz of factory
	 * @param key section key
	 * @param config the config used
	 * @return pair of factory and config or empty
	 * @throws ConfigurationException if the defined factory is not found
	 */
	@NotNull
	private <T> Pair<T, ConfigurationSection> getOptionalFactory(Class<T> clazz, String key, ConfigurationSection config) {
		ConfigurationSection optionalCfg = config.getConfigurationSection(key);
		if (optionalCfg != null) {
			String optinalFactoryKey = optionalCfg.getString("factory");
			if (optinalFactoryKey == null)
				return Pair.of();
			Registry<T> registry = Registries.getRegistry(clazz);
 			T factory = registry.get(optinalFactoryKey);
			if (factory == null)
				throw new ConfigurationException("Unable to find the defined \"" + key + ".factory\": " + optinalFactoryKey);
			ConfigurationSection factoryCfg = optionalCfg.getConfigurationSection("config");
			return Pair.of(factory, factoryCfg);
		}
		return Pair.of();
	}
	
	private <T> T getRequiredOptional(Class<T> clazz, String key, ConfigurationSection config) {
		Registry<T> registry = Registries.getRegistry(clazz);
		String raw = config.getString(key);
		T value = null;
		if (raw == null) {
			value = registry.getDefault();
		} else {
			value = registry.get(key);
			if (value == null)
				throw new ConfigurationException("Unable to find the defined \"" + key + "\": " + raw);
		}
		return value;
	}
	
	public void setConfiguration(ConfigurationSection config) {
		if (config == null)
			throw new IllegalArgumentException("Config can not be null!");
		this.name = config.getString("name");
		this.worldFactory = getRequiredOptional(WorldFactory.class, "world-factory", config);
		this.chunkFactory = getRequiredOptional(ChunkFactory.class, "chunk-factory", config);
		this.dimension = getRequiredOptional(Dimension.class, "dimension", config);
		Pair<ChunkGeneratorFactory, ConfigurationSection> chunkGen =
				getOptionalFactory(ChunkGeneratorFactory.class, "chunk-generator", config);
		this.chunkGenerator = chunkGen.getValue1();
		this.chunkGenConfig = chunkGen.getValue2();
		Pair<ChunkLoaderFactory, ConfigurationSection> loader =
				getOptionalFactory(ChunkLoaderFactory.class, "chunk-loader", config);
		this.chunkLoader = loader.getValue1();
		this.chunkLoaderConfig = loader.getValue2();
		Pair<ChunkProviderFactory, ConfigurationSection> provider = 
				getOptionalFactory(ChunkProviderFactory.class, "chunk-provider", config);
		this.chunkProviderFactory = provider.getValue1();
		this.chunkProviderConfig = provider.getValue2();
	}
	
	public LocalServer getServer() {
		return server;
	}

	public String getName() {
		return name;
	}

	public ChunkGeneratorFactory getChunkGenerator() {
		return chunkGenerator;
	}

	public ChunkLoaderFactory getChunkLoader() {
		return chunkLoader;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public ChunkProviderFactory getProviderFactory() {
		return chunkProviderFactory;
	}

	public WorldFactory getWorldFactory() {
		return worldFactory;
	}

	public ConfigurationSection getChunkProviderConfig() {
		return chunkProviderConfig;
	}
	
	/**
	 * Returns the file or directory of the world
	 * @return
	 */
	public File getWorldDir() {
		return worldDir;
	}
	
	public WorldBuilder setWorldDir(File worldDir) {
		this.worldDir = worldDir;
		return this;
	}

	public WorldBuilder setServer(LocalServer server) {
		this.server = server;
		return this;
	}

	public WorldBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public WorldBuilder setChunkGenerator(ChunkGeneratorFactory chunkGenerator) {
		this.chunkGenerator = chunkGenerator;
		return this;
	}

	public WorldBuilder setChunkLoader(ChunkLoaderFactory chunkLoader) {
		this.chunkLoader = chunkLoader;
		return this;
	}

	public WorldBuilder setDimension(Dimension dimension) {
		this.dimension = dimension;
		return this;
	}

	public WorldBuilder setProviderFactory(ChunkProviderFactory providerFactory) {
		this.chunkProviderFactory = providerFactory;
		return this;
	}

	public WorldBuilder setWorldFactory(WorldFactory worldFactory) {
		this.worldFactory = worldFactory;
		return this;
	}

	public WorldBuilder setChunkProviderConfig(ConfigurationSection chunkProviderConfig) {
		this.chunkProviderConfig = chunkProviderConfig;
		return this;
	}
	
	@Override
	public World build() {
		validate();
		World world = worldFactory.createWorld(this);
		return world;
	}
	
	protected void validate() {
		if (server == null)
			throw new IllegalStateException("Server can not be null!");
		if (name == null)
			throw new IllegalStateException("Name can not be null!");
		if (worldFactory == null)
			throw new IllegalStateException("World factory can not be null!");
	}

	public ChunkFactory getChunkFactory() {
		return chunkFactory;
	}
	
	public WorldBuilder setChunkFactory(ChunkFactory chunkFactory) {
		this.chunkFactory = chunkFactory;
		return this;
	}

	public ConfigurationSection getChunkGeneratorConfig() {
		return chunkGenConfig;
	}
	
	public WorldBuilder setChunkGeneratorConfig(ConfigurationSection config) {
		this.chunkGenConfig = config;
		return this;
	}

	public ChunkProviderFactory getChunkProviderFactory() {
		return chunkProviderFactory;
	}
	
	public WorldBuilder setChunkProviderFactory(ChunkProviderFactory chunkProviderFactory) {
		this.chunkProviderFactory = chunkProviderFactory;
		return this;
	}
	
	public ConfigurationSection getChunkLoaderConfig() {
		return chunkLoaderConfig;
	}
	
	public WorldBuilder setChunkLoaderConfig(ConfigurationSection chunkLoaderConfig) {
		this.chunkLoaderConfig = chunkLoaderConfig;
		return this;
	}
	
	public EntityTrackerFactory getEntityTracker() {
		return entityTracker;
	}
	
	public WorldBuilder setEntityTracker(EntityTrackerFactory entityTracker) {
		this.entityTracker = entityTracker;
		return this;
	}

	@Override
	public void clear() {
		server = null;
		name = null;
		worldDir = null;
		chunkGenConfig = null;
		chunkGenerator = null;
		chunkLoaderConfig = null;
		chunkLoader = null;
		chunkProviderConfig = null;
		chunkProviderFactory = null;
		dimension = null;
		worldFactory = null;
		chunkFactory = null;
		entityTracker = null;
	}

}
