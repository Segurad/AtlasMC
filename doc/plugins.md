# Plugins

atlas-plugin.yml

```yaml
name: MyPluginName
version: v1.0.0
loader: de.atlascore.plugin.CoreJavaPluginLoader # classpath for loader class if not present treated as CoreJavaPluginLoader 
main: path.to.my.class.extends.JavaPlugin # Loader depending option e.g. classpath for main class for java plugins
description: "My Super duper Plugin does awsome stuff"
author:
- MeTheDeveloper
depends-on: # Plugins that must be loaded before this Plugin
- PluginNameHere
soft-depends-on: # Plugins that must be loaded before this Plugin if present
- PluginNameHere
load-before: # Plugins this Plugin should be loaded before
- PluginNameHere
```