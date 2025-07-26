
# Plugins

[Doc](../index.md) > [Development](../index.md#development) > [Plugins](#plugins)

---

- [altas-plugin.yml](#atlas-pluginyml)
- [Dependencies](#dependencies)

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
version: v1.0.0 # must be a valid semver format may be prefixed with "v"
loader: de.atlascore.plugin.CoreJavaPluginLoader # classpath for loader class if not present treated as CoreJavaPluginLoader 
main: path.to.my.class.extends.JavaPlugin # Loader depending option e.g. classpath for main class for java plugins
description: "My Super duper Plugin does awsome stuff"
author: # List of String containing all the awsome people that worked on this plugin
- MeTheDeveloper
dependencies:
- "Someplugin"
- "Someplugin >= 1.0.0"
- "Someplugin 1.0.0 - 2.0.0"
required-features: # Features that must present
- some:feature
- some:other_feature
soft-required-features: # Features only one must be present
- some:soft_feature
```

## Dependencies

Dependencies are other Plugins required for the plugin. They may be Specified in the following format.

```text
<order><type> <name> <operation> <version>
<order><type> <name> <from> - <to>
<Name>
```

For explanation read below. In case not Version or Range is defined any Version is valid

### Order

Defines the order in which Plugins are loaded.

- `+` loads the Plugin after the dependency.
- `-` loads the Plugin befor the dependency.
- `~` does not affect the load order.

It is not required to define a order value. If not defined it defaults to `+` loading the Plugin after the dependency.

### Type

Defines the type of the dependency.

- `!` is a incompatiple dependency. If the dependecy is present it will prevent this plugin from loading.
- `*` is a required dependency. If the dependency is not present it will prevent this plugin from loading.
- `?` is a optional dependency. This dependency is not required for this plugin but if it is the load order can be modifier with this type.

It is not required to define a type value. If not defined it defaults to `*` a required dependency

#### Name

Defines the name of the dependency. This value is always required.

### Operation & Version

Defines a required Version for the dependency. Valid comparators are `<` `<=` `==` `>=` `>`. The must be a valid version format e.g. `1.0.0` or `v1.0.0`

### Range

Defines a Version range that is required for the Dependency.
the `from` and `to` value must be a valid version format e.g. `1.0.0 - 1.5.0` or `v1.0.0 - v1.5.0`

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
-  resource/Path:target/Path
```
