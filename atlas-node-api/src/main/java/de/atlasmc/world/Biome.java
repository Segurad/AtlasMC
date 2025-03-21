package de.atlasmc.world;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

@RegistryHolder(key = "minecraft:worldgen/biome", target = Target.PROTOCOL)
public class Biome extends ProtocolRegistryValueBase {
	
	public static final ProtocolRegistry<Biome> REGISTRY;

	static {
		REGISTRY = Registries.createRegistry(Biome.class);
	}
	
	private BiomeData data;
	
	public Biome(NamespacedKey key) {
		super(key, -1);
	}
	
	public Biome(NamespacedKey key, int id) {
		super(key, id);
	}
	
	public BiomeData getData() {
		return data;
	}
	
	public void setData(BiomeData data) {
		this.data = data;
	}
	
	@Override
	public boolean hasNBT() {
		return data != null;
	}
	
	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		data = new BiomeData();
		data.fromNBT(reader);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (data == null)
			return;
		data.toNBT(writer, systemData);
	}
	
	public static Biome get(NamespacedKey key) {
		return REGISTRY.get(key);
	}
	
	public static Biome get(String key) {
		return REGISTRY.get(key);
	}
	
	public static Biome getByID(int id) {
		return REGISTRY.getByID(id);
	}
	
	public static ProtocolRegistry<Biome> getRegistry() {
		return REGISTRY;
	}

}
