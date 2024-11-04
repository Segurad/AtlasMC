[Doc](doc.md) > [Development](doc.md#development) > [Registries]()

# Registries

Plugin Resourcepaths: 
- [`/META-INF/atlas/registies.yml`](#registriesyml)
- [`/META-INF/atlas/registry-entries.yml`](#registry-entriesyml)

---

Registries are the main way of providing funktionality for Atlas itself and Plugins. You can simply use Registries by using the `Registries` class or using the registry annotations to automatically create registries or provide values for existing registries

## registries.yml

```yaml
<registrykey>:
  targert: INSTANE # INSTANCE, CLASS
  type: my.required.registry.type
```

## registry-entries.yml

```yaml
<registrykey>:
  my:configurationserializable:
    type: my.registry.entry 
    configuration: # <optional> uses configuration for construction on ConfigurationSerializable
      param1: value1
```

## List of atlas registries: