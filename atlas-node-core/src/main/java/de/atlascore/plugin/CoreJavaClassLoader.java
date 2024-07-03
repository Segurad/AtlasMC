package de.atlascore.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import de.atlasmc.log.Log;
import de.atlasmc.log.Logging;
import de.atlasmc.plugin.JavaPlugin;
import de.atlasmc.plugin.PluginException;
import de.atlasmc.util.ByteDataBuffer;
import de.atlasmc.util.configuration.Configuration;

public class CoreJavaClassLoader extends URLClassLoader {
	
	private final URL url;
	private final JarFile jar;
	private final Manifest manifest;
	private final JavaPlugin plugin;
	private final Map<String, Class<?>> classCache;
	private final CoreJavaPluginLoader loader;
	
	public CoreJavaClassLoader(CoreJavaPluginLoader loader, File file, ClassLoader parent, Configuration info) throws IOException {
		super(new URL[] { file.toURI().toURL() }, parent);
		this.url = file.toURI().toURL();
		this.jar = new JarFile(file);
		this.manifest = jar.getManifest();
		this.classCache = new ConcurrentHashMap<>();
		this.loader = loader;
		
		Class<?> mainClass = null;
		try {
			mainClass = Class.forName(info.getString("main"), true, this);
		} catch (ClassNotFoundException e) {
			throw new PluginException("Main class not found: " + info.getString("main") +"!");
		}
		if (!mainClass.isAssignableFrom(JavaPlugin.class))
			throw new PluginException("Main class is not assignable from JavaPlugin!");
		try {
			plugin = (JavaPlugin) mainClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new PluginException("Unable to instanciate Plugin!", e);
		}
		String name = info.getString("name");
		String version = info.getString("version");
		List<String> author = List.copyOf(info.getStringList("author", List.of()));
		String description = info.getString("description");
		Log logger = Logging.getLogger(plugin);
		plugin.init(file, loader, logger, name, version, author, description);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		return findClass(name, true);
	}
	
	protected Class<?> findClass(String name, boolean checkGlobal) throws ClassNotFoundException {
		Class<?> clazz = classCache.get(name);
		if (clazz != null) 
			return clazz; // this cache contained the class
		if (checkGlobal)
			clazz = loader.getClassByName(name, this); // try find in global cache 
		if (clazz != null) 
			return clazz; // global cache contained the class
		// try find in jar
		String path = name.replace('.', '/').concat(".class");
		JarEntry entry = jar.getJarEntry(path);
		if (entry != null) { // present try to load class
			byte[] classData;
			try {
				InputStream in = jar.getInputStream(entry);
				ByteDataBuffer buf = new ByteDataBuffer(in.available());
				buf.copyAllFromInput(in);
				classData = buf.toByteArray();
				
				int index = name.lastIndexOf('.');
				if (index != -1) { // define packet
					String packetName = name.substring(0, index);
					if (getDefinedPackage(packetName) == null) {
						if (this.manifest != null)
							definePackage(packetName, manifest, url);
						else
							definePackage(packetName, null, null, null, null, null, null, null);
					}
				}
				// define class
				CodeSigner[] signer = entry.getCodeSigners();
				CodeSource source = new CodeSource(url, signer);
				defineClass(name, classData, 0, classData.length, source);
			} catch (IOException e) {
				throw new ClassNotFoundException(name, e);
			}
		} else {
			clazz = super.findClass(name); // not found in jar try with super and exit with exception if not found
		}
		classCache.put(name, clazz); // add class to cache for further use
		return clazz;
	}
	
	public JavaPlugin getPlugin() {
		return plugin;
	}

}
