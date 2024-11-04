package de.atlascore.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import de.atlasmc.plugin.JavaPlugin;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginLoader;
import de.atlasmc.plugin.PreparedPlugin;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.file.YamlConfiguration;

/**
 * Default PluginLoader only capable of loading {@link JavaPlugin}
 */
public class CoreJavaPluginLoader implements PluginLoader {
	
	private final CopyOnWriteArrayList<CoreJavaClassLoader> loaders;
	
	public CoreJavaPluginLoader() {
		loaders = new CopyOnWriteArrayList<>();
	};

	@Override
	public boolean canLoad(File file) {
		if (!file.exists() || file.isDirectory()) 
			return false;
		if (!file.getPath().endsWith(".jar")) 
			return false;
		Configuration info = null;
		try {
			info = getInfo(file);
		} catch (IOException e) {}
		if (info == null)
			return false;
		String loader = info.getString("loader");
		return loader == null || loader.equals(getClass().getName());
	}

	@Override
	public Plugin load(File file) throws IOException {
		if (file == null)
			throw new IllegalArgumentException("File can not be null!");
		if (!file.exists())
			throw new FileNotFoundException("File does not exist: " + file.getPath());
		if (!file.isFile())
			throw new IllegalArgumentException("The file is not a valid file: " + file.getPath());
		Configuration info = getInfo(file);
		if (info == null)
			return null;
		String loaderName = info.getString("loader");
		if (loaderName != null && !loaderName.equals(getClass().getName()))
			return null;
		CoreJavaClassLoader loader = new CoreJavaClassLoader(this, file, getClass().getClassLoader(), info);
		loaders.add(loader);
		return loader.getPlugin();
	}
	

	@Override
	public PreparedPlugin preparePlugin(File file) throws IOException {
		if (file == null)
			throw new IllegalArgumentException("File can not be null!");
		if (!file.exists())
			throw new FileNotFoundException("File does not exist: " + file.getPath());
		if (!file.isFile())
			throw new IllegalArgumentException("The file is not a valid file: " + file.getPath());
		Configuration info = getInfo(file);
		if (info == null)
			return null;
		String loaderName = info.getString("loader");
		if (loaderName != null && !loaderName.equals(getClass().getName()))
			return null;
		return new CorePreparedJavaPlugin(info, file, this);
	}

	/**
	 * Returns the atlas-plugin info of a jar file
	 * @param file the jar file
	 * @return info as {@link Configuration} or null if not present
	 * @throws IOException
	 */
	@NotNull
	private Configuration getInfo(File file) throws IOException {
		JarFile jar = new JarFile(file);
		JarEntry entry = jar.getJarEntry("atlas-plugin.yml");
		if (entry == null) { 
			jar.close();
			return null;
		}
		InputStream in = jar.getInputStream(entry);
		YamlConfiguration pluginyml = YamlConfiguration.loadConfiguration(in);
		in.close();
		jar.close();
		return pluginyml;
	}

	Class<?> getClassByName(String name, CoreJavaClassLoader source) throws ClassNotFoundException {
		Class<?> clazz = null;
		for (CoreJavaClassLoader loader : loaders) {
			if (loader == source) 
				continue;
			clazz = loader.findClass(name, false);
			if (clazz != null) 
				return clazz;
		}
		return null;
	}

	Plugin loadPreparedPlugin(CorePreparedJavaPlugin prepared) throws IOException {
		CoreJavaClassLoader loader = new CoreJavaClassLoader(this, prepared.getFile(), getClass().getClassLoader(), prepared.getPluginInfo());
		loaders.add(loader);
		return loader.getPlugin();
	}

	@Override
	public void unload(Plugin plugin) {
		if (plugin.getPluginLoader() != this)
			return;
		loaders.remove(plugin.getClass().getClassLoader());
	}

}
