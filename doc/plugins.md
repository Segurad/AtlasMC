# Plugins

Introduction goes here

Because the APIs are loaded dynamically in Atlas a Plugin should depend on its required API Plugins.

- Atlas-Master-API
- Atlas-Network-API
- Atlas-Node-API

## atlas-plugin.yml 
```yaml
name: MyPluginName
version: v1.0.0
loader: de.atlascore.plugin.CoreJavaPluginLoader # classpath for loader class if not present treated as CoreJavaPluginLoader 
main: path.to.my.class.extends.JavaPlugin # Loader depending option e.g. classpath for main class for java plugins
description: "My Super duper Plugin does awsome stuff"
atlas-module: false # Whether or not this plugin is a atlas module and may be loaded during startup process
author: # List of String containing all the awsome people that worked on this plugin
- MeTheDeveloper
depends-on: # Plugins that must be loaded before this Plugin
- PluginNameHere
soft-depends-on: # Plugins that must be loaded before this Plugin if present
- PluginNameHere
load-before: # Plugins this Plugin should be loaded before
- PluginNameHere
```