package de.atlascore.command.executor;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import de.atlasmc.Atlas;
import de.atlasmc.command.CommandContext;
import de.atlasmc.command.CommandExecutor;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryEntry;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.MemoryConfigurationSection;
import de.atlasmc.util.configuration.file.YamlConfiguration;

@RegistryValue(registry="atlas:command/executor", key="atlas-core:debug_dump_registries")
public class CoreDebugDumpRegistriesCommand implements CommandExecutor {

	private static final Collection<String> KEYS = List.of("target");
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(CommandContext context) {
		String target = context.getArgument("target", String.class, false);
		YamlConfiguration dump = null;
		if (target == null) {
			dump = new YamlConfiguration();
			Registry<Registry<?>> registries = Registries.getRegistries();
			List<ConfigurationSection> dumps = new ArrayList<>(registries.size());
			dump.set("registries", dumps);
			for (Registry<?> registry : registries.values()) {
				ConfigurationSection dumpSection = new MemoryConfigurationSection(dump);
				dumps.add(dumpSection);
				dumpRegistry((Registry<Object>) registry, dumpSection);
			}
		} else {
			Registry<?> registry = Registries.getRegistry(target);
			if (registry != null) {
				dump = new YamlConfiguration();
				dumpRegistry((Registry<Object>) registry, dump);
			}
		}
		if (dump != null) {
			String pattern = "MM-dd-yyyy_HH-mm-ss";
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			Date today = Calendar.getInstance().getTime();        
			String time = df.format(today);
			File file = new File(Atlas.getWorkdir(), "registry_dump_" + time + ".yml");
			try {
				dump.save(file);
			} catch (IOException e) {
				context.getSender().sendMessage("Failed to write dump: " + e.getMessage());
			}
			context.getSender().sendMessage("Created registry dump: registry_dump_" + time + ".yml");
			return true;
		} else {
			context.getSender().sendMessage("Unable to find registry with name: " + target);
			return false;
		}
	}
	
	private void dumpRegistry(Registry<Object> registry, ConfigurationSection cfg) {
		cfg.set("key", registry.getNamespacedKeyRaw());
		cfg.set("type", registry.getType().getName());
		cfg.set("target", registry.getTarget().name());
		Object defaultEntry = registry.getDefault();
		if (defaultEntry != null) {
			Class<?> defaultClass = null;
			if (defaultEntry instanceof Class clazz) {
				defaultClass = clazz;
			} else {
				defaultClass = defaultEntry.getClass();
			}
			cfg.set("default", defaultClass.getName());
		}
		Collection<RegistryEntry<Object>> entries = registry.entries();
		if (entries.isEmpty())
			return;
		ConfigurationSection values = cfg.createSection("entries");
		for (RegistryEntry<Object> entry : entries) {
			Class<?> entryClass = null;
			Object entryValue = entry.value();
			if (entryValue instanceof Class clazz) {
				entryClass = clazz;
			} else {
				entryClass = entryValue.getClass();
			}
			values.set(entry.key().toString(), entryClass.getName());
		}
	}
	
	
	
	@Override
	public Collection<String> getKeys() {
		return KEYS;
	}

}
