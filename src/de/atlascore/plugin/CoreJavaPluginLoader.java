package de.atlascore.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import de.atlasmc.plugin.JavaPlugin;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginLoader;

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
		if (!file.getPath().endsWith(".jar")) return false;
		if (!file.exists() || file.isDirectory()) return false;
		return true;
	}

	@Override
	public Plugin load(File file) throws IOException {
		if (!canLoad(file)) return null;
		CorePluginInfo info = getInfo(file);
		CoreJavaClassLoader loader = new CoreJavaClassLoader(this, file, getClass().getClassLoader(), info);
		loaders.add(loader);
		return loader.getPlugin();
	}

	private CorePluginInfo getInfo(File file) {
		JarFile jar = null;
		CorePluginInfo info = null;
		try {
			jar = new JarFile(file);
			JarEntry entry = jar.getJarEntry("plugin.json");
			if (entry == null) throw new FileNotFoundException("Jar does not contain plugin.json");
			InputStream in = jar.getInputStream(entry);
			Gson gson = new Gson();
			JsonReader reader = new JsonReader(new InputStreamReader(in));
			info = gson.fromJson(reader, CorePluginInfo.class);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (jar != null)
			try {
				jar.close();
			} catch (IOException e) {}
		}
		return info;
	}

	Class<?> getClassByName(String name, CoreJavaClassLoader source) throws ClassNotFoundException {
		Class<?> clazz;
		for (CoreJavaClassLoader loader : loaders) {
			if (loader == source) continue;
			clazz = loader.findClass(name, false);
			if (clazz != null) return clazz;
		}
		return null;
	}

}
