[Doc](doc.md) > [Administration](doc.md#administration) > [Properties]()

# Properties

To configure some behavior of Atlas you can use the following properties.

| Property Name                            | Value   | Desciption                                                                | Default |
|------------------------------------------|---------|---------------------------------------------------------------------------|---------|
| de.atlasmc.map.useIntToMapColorCache     | boolean | Whether or not a full cache of rgb int values to color ids should be used | true    |
| de.atlasmc.util.map.key.literalCacheSize | int     | Defines the initial capacity of the CharKey literal cache                 | 512     |
| de.atlasmc.cache.defaultCleanupIntervall | int     | Time in ticks until a CacheHolder will be cleared                         | 6000    |
| de.atlasmc.chat.defaultLegacyPrefix      | char    | Character used to identify chat format codes in legacy format             | §       |
| atlas.config.override                    | boolean | Whether or not all configuration should be overwritten                    | false   |
| atlas.config.permission.reload           | boolean | Whether or not all permission groups should be recreated                  | false   |