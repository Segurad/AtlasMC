
# AtlasMC

![Atlas](docs/images/atlas.png)

<b>IN DEVELOPEMENT</b>

```text
Atlas is currently in development and so are not all features implemented correctly yet and it is not in a useable State.
```

AtlasMC is a build from the Ground Minecraft Server Software. The goal is to provide a flexible and powerfull engine for Developers and Administrators who do not require vanilla features.

Atlas is designed to connect multiple Atlas-Nodes as Cluster or Atlas-Network. One Node will be the Master-Node managing the resources of the other Minion-Nodes.

Each node will deploy "servers" as internals servers which are simply threads sharing a JVM. A planned feature will be "deployed server" which are other third party implementations started Processes by the node.

Atlas aims for Administrative flexibility by providing powerfull configuration options, plugins and contentpacks. (Contentpacks are extensions like plugins but only provide configurations)

For Developers Atlas provides a powerfull API that is mostly based of the Bukkit API. It is not possible to direktly use Bukkit plugins but this helps to allow relatively easy port existing Plugins to Atlas. Additionally the API supports direct access to Protocol as well as some other low level access or unsafe options for a more flexible access.

For more information see the [Docs](./docs/index.md)

!!! <u>AtlasMC is <b>NOT</b> designed to give a Vanilla experience</u> !!!

# Thanks to

- Mojang for making this great game
- #mcdevs for documenting the protocol and useful snippets
- Bukkit project for creating and improving a great API
- Oracle for creating the language
- Netty project for creating a great network library
