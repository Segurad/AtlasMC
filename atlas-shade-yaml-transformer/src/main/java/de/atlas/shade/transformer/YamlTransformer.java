package de.atlas.shade.transformer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import org.apache.maven.plugins.shade.relocation.Relocator;
import org.apache.maven.plugins.shade.resource.ReproducibleResourceTransformer;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.file.YamlConfiguration;

public class YamlTransformer implements ReproducibleResourceTransformer {
	
	private Map<String, YamlConfiguration> resources = new HashMap<>();
	private Map<String, Long> resourceTime = new HashMap<>();
	
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
		YamlConfiguration presentCfg = resources.get(resource);
		if (presentCfg == null) {			
			resources.put(resource, cfg);
			resourceTime.put(resource, time);
			return;
		}
		merge(cfg, presentCfg);
		if (time > resourceTime.get(resource))
			resourceTime.put(resource, time);
	}
	
	@SuppressWarnings("unchecked")
	private void merge(ConfigurationSection src, ConfigurationSection dest) {
		final Set<String> destKeys = dest.getKeys();
		for (String key : src.getKeys()) {
			if (destKeys.contains(key)) {
				Object destvalue = dest.get(key);
				if (destvalue instanceof ConfigurationSection section) {
					Object srcValue = src.get(key);
					if (srcValue instanceof ConfigurationSection srcSection) {
						merge(srcSection, section);
					}
				} else if (destvalue instanceof List list) {
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
				dest.set(key, src.get(key));
			}
		}
	}

	@Override
	public boolean hasTransformedResource() {
		return !resources.isEmpty();
	}

	@Override
	public void modifyOutputStream(JarOutputStream os) throws IOException {
        for (Map.Entry<String, YamlConfiguration> e : resources.entrySet()) {
            JarEntry jarEntry = new JarEntry(e.getKey());
            jarEntry.setTime(resourceTime.get(e.getKey()));
            os.putNextEntry(jarEntry);
            e.getValue().save(os);
            os.closeEntry();
            File targetFile = new File(target, e.getKey());
            if (!targetFile.exists()) {
            	targetFile.getParentFile().mkdirs();
            }
            e.getValue().save(targetFile);
        }
	}
	
	public void setTarget(String target) {
		this.target = target;
	}

}
