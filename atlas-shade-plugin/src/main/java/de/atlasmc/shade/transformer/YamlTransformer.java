package de.atlasmc.shade.transformer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import org.apache.maven.plugins.shade.relocation.Relocator;
import org.apache.maven.plugins.shade.resource.ReproducibleResourceTransformer;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.file.YamlConfiguration;

public class YamlTransformer implements ReproducibleResourceTransformer {
	
	private Map<String, ResourceFile> resources = new HashMap<>();
	
	private String target;

	@Override
	public boolean canTransformResource(String resource) {
		return resource.endsWith(".yml") || resource.endsWith(".yaml");
	}

	@Override
	public void processResource(String resource, InputStream is, List<Relocator> relocators) throws IOException {
		processResource(resource, is, relocators, 0);
	}
	
	@Override
	public void processResource(String resource, InputStream is, List<Relocator> relocators, long time) throws IOException {
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(is);
		ResourceFile presentCfg = resources.get(resource);
		if (presentCfg == null) {			
			resources.put(resource, new ResourceFile(cfg, time));
			return;
		}
		merge(cfg, presentCfg.config);
		presentCfg.changed = true;
		if (time > presentCfg.time)
			presentCfg.time = time;
	}
	
	private void merge(ConfigurationSection src, ConfigurationSection cfg) {
		final Set<String> destKeys = cfg.getKeys();
		for (String key : src.getKeys()) {
			if (destKeys.contains(key)) {
				Object destvalue = cfg.get(key);
				if (destvalue instanceof ConfigurationSection section) {
					Object srcValue = src.get(key);
					if (srcValue instanceof ConfigurationSection srcSection) {
						merge(srcSection, section);
					}
				} else if (destvalue instanceof List) {
					@SuppressWarnings("unchecked")
					List<Object> list = (List<Object>) destvalue;
					Object srcValue = src.get(key);
					if (srcValue instanceof List srcList) {
						for (Object srcEntry : srcList) {
							if (list.contains(srcEntry))
								continue;
							list.add(srcEntry);
						}
					}
				}
			} else {
				cfg.set(key, src.get(key));
			}
		}
	}

	@Override
	public boolean hasTransformedResource() {
		return !resources.isEmpty();
	}

	@Override
	public void modifyOutputStream(JarOutputStream os) throws IOException {
        for (Entry<String, ResourceFile> e : resources.entrySet()) {
        	ResourceFile resource = e.getValue();
            JarEntry jarEntry = new JarEntry(e.getKey());
            jarEntry.setTime(resource.time);
            os.putNextEntry(jarEntry);
            resource.config.save(os);
            os.closeEntry();
            File targetFile = new File(target, e.getKey());
            if (!targetFile.exists()) {
            	targetFile.getParentFile().mkdirs();
            }
            resource.config.save(targetFile);
            if (resource.changed)
            	System.out.println("Merged Yaml: " + e.getKey());
        }
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	private static final class ResourceFile {
		
		public final YamlConfiguration config;
		public long time;
		public boolean changed;
		
		public ResourceFile(YamlConfiguration config, long time) {
			this.config = config;
			this.time = time;
		}
		
	}

}
