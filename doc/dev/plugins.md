# Plugins

[Doc](../doc.md) > [Development](../doc.md#development) > [Plugins](#plugins)

---

Plugin Resourcepaths:

- [`/atlas-plugin.yml`](#atlas-pluginyml)
- [`/setup.yml`](#setupyml) (only for  AtlasModules)
- [`/server-setup.yml`](#setupyml)

---

Introduction goes here

---

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
author: # List of String containing all the awsome people that worked on this plugin
- MeTheDeveloper
depends-on: # Plugins that must be loaded before this Plugin
- PluginNameHere
soft-depends-on: # Plugins that must be loaded before this Plugin if present
- PluginNameHere
load-before: # Plugins this Plugin should be loaded before
- PluginNameHere
required-features: # Features that must present
- some:feature
- some:other_feature
soft-required-features: # Features only one must be present
- some:soft_feature
```

## PluginHandle

A plugin handles represent plugins for registration of new functionality or values. Handles allow you to register and unregister those bound to a context like a server or servergroup. For example if a servergroup requires a specific configuration of your minigame you just create a new plugin handle and register all listeners for this minigame with the newly created handle. If the servergroup is no longer required you can simply remove every listener with the removeAll function without the need of tracking the listeners yourself. If your implementation does not require this sort of registration you can simply use your plugin instance.

## setup.yml

Setup files are helpers for creating directories and extract resources of your plugin. The external paths have to be within the workdir of atlas for `setup.yml` or the workdir of the server for `server-setup.yml`.

`server-setup.yml` and `setup.yml` share the same structure.

```yaml
directories: # creates directories
- some/Path
- some/Other/Path
extract: # copies resources to the given path
-  "resource/Path:target/Path"
```
