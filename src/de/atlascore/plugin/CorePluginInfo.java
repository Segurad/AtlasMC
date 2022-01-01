package de.atlascore.plugin;

import java.util.List;

final class CorePluginInfo {
	
	public final String name, version, author, description, main;
	public final boolean coreModule;
	public final List<String> dependencies;
	
	public CorePluginInfo() {
		this.name = "";
		this.version = "";
		this.author = "";
		this.description = "";
		this.main = "";
		this.coreModule = false;
		this.dependencies = null;
	}

}
