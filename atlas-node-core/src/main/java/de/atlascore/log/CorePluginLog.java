package de.atlascore.log;

import org.apache.logging.log4j.core.Logger;

import de.atlasmc.log.PluginLog;
import de.atlasmc.plugin.Plugin;

public class CorePluginLog extends CoreLog implements PluginLog {

	private final Plugin plugin;
	
	public CorePluginLog(Plugin plugin, Logger log) {
		super(plugin.getName(), log);
		this.plugin = plugin;
	}

	@Override
	public Plugin getPlugin() {
		return plugin;
	}

}
