package de.atlasmc.plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.configuration.Configuration;

public interface PrototypePlugin {
	
	/**
	 * Creates the Plugin
	 * @return the Plugin
	 * @throws IOException 
	 */
	Plugin create() throws IOException;
	
	boolean isCreated();
	
	/**
	 * Returns the name if this Plugin
	 * @return name
	 */
	@NotNull
	String getName();
	
	File getFile();
	
	PluginLoader getLoader();
	
	Version getVersion();
	
	@NotNull
	Configuration getPluginInfo();
	
	@NotNull
	List<Dependency> getDependencies();
	
	@NotNull
	List<NamespacedKey> getRequiredFeatures();
	
	@NotNull
	List<NamespacedKey> getSoftRequiredFeatures();

	@Nullable
	Plugin getPlugin();
	
	/**
	 * 
	 * @return plugin information
	 * @implSpec {@link #getName()} + {@link #getVersion()}
	 */
	@Override
	String toString();

}
