package de.atlascore.plugin;

import java.util.List;

final class CorePluginInfo {
	
	public final String name;
	public final String version;
	public final String author;
	public final String description;
	public final String main;
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
