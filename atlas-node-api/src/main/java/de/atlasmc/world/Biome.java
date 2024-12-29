package de.atlasmc.world;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

@RegistryHolder(key = "minecraft:worldgen/biome", target = Target.PROTOCOL)
public class Biome implements Namespaced {
	
	public static final ProtocolRegistry<Biome> REGISTRY;

	static {
		REGISTRY = Registries.createRegistry(Biome.class);
		REGISTRY.setIDSupplier(Biome::getID);
		REGISTRY.setDeserializer((id, buf, reader) -> {
			NamespacedKey key = readIdentifier(buf);
			Biome biome = new Biome(key, id);
			if (buf.readBoolean()) {
				BiomeData data = new BiomeData();
				biome.setData(data);
				reader.readNextEntry();
				data.fromNBT(reader);
			}
			return biome;
		});
		REGISTRY.setSerializer((data, buf, writer) -> {
			writeIdentifier(data.getNamespacedKey(), buf);
			if (data.data == null) {
				buf.writeBoolean(false);
			} else {
				buf.writeBoolean(true);
				writer.writeCompoundTag();
				data.data.toNBT(writer, false);
				writer.writeEndTag();
			}
		});
	}
	
	private NamespacedKey name;
	private int id;
	private BiomeData data;
	
	public Biome(NamespacedKey name) {
		this(name, -1);
	}
	
	public Biome(NamespacedKey name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public BiomeData getData() {
		return data;
	}
	
	public void setData(BiomeData data) {
		this.data = data;
	}
	
	@Override
	public NamespacedKey getNamespacedKey() {
		return name;
	}
	
	public int getID() {
		return id;
	}

}
