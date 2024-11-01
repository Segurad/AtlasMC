package de.atlascore.system.init;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import com.google.gson.stream.JsonReader;

import de.atlasmc.Material;
import de.atlasmc.NamespacedKey;
import de.atlasmc.block.data.BlockDataFactory;
import de.atlasmc.block.tile.TileEntityFactory;
import de.atlasmc.inventory.meta.ItemMetaFactory;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.util.FactoryException;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.file.JsonConfiguration;

public class MaterialLoader {
	
	private static volatile boolean loaded;

	public static void loadMaterial() throws IOException, ClassNotFoundException {
		if (loaded)
			return;
		load();
	}
	
	private static synchronized void load() throws IOException, ClassNotFoundException {
		if (loaded)
			return;
		// load mdf
		Registry<ItemMetaFactory> IMFs = loadFactories(ItemMetaFactory.class, "/data/item_meta_factories.json");
		// load bdf
		Registry<BlockDataFactory> BDFs = loadFactories(BlockDataFactory.class, "/data/block_data_factories.json");
		// load tef
		Registry<TileEntityFactory> TEFs = loadFactories(TileEntityFactory.class, "/data/tile_entity_factories.json");
		// load mats
		InputStream in = MaterialLoader.class.getResourceAsStream("/data/materials.json");
		JsonReader reader = new JsonReader(new InputStreamReader(in));
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			reader.beginObject();
			ItemMetaFactory IMF = null;
			BlockDataFactory BDF = null;
			TileEntityFactory TEF = null;
			String var = null;
			short itemID = -1;
			short blockID = -1;
			short blockStateID = -1;
			byte amount = 0;
			String clientName = null;
			float toughness = 0f;
			float explosionResistance = 0f;
			while (reader.hasNext()) {
				String key = reader.nextName();
				switch(key) {
				case "IMF":
					String mdfKey = reader.nextString();
					IMF = IMFs.get(mdfKey);
					break;
				case "BDF":
					String bdfKey = reader.nextString();
					BDF = BDFs.get(bdfKey);
					break;
				case "TEF":
					String tefKey = reader.nextString();
					TEF = TEFs.get(tefKey);
					break;
				case "const":
					var = reader.nextString();
					break;
				case "itemID":
					itemID = (short) reader.nextInt();
					break;
				case "blockID":
					blockID = (short) reader.nextInt();
					break;
				case "blockStateID":
					blockStateID = (short) reader.nextInt();
					break;
				case "amount":
					amount = (byte) reader.nextInt();
					break;
				case "client-name":
					clientName = reader.nextString();
					break;
				case "toughness":
					toughness = (float) reader.nextDouble();
				case "explosionResistance":
					explosionResistance = (float) reader.nextDouble();
				default:
					reader.skipValue();
				}
			}
			// set default ItemMetaFactory for Blocks
			if (IMF == null && blockID != -1) {
				if (TEF != null) {
					IMF = IMFs.get("atlas:tileentity");
				} else {
					IMF = IMFs.get("atlas:blockdata");
				}
			}
			reader.endObject();
			NamespacedKey nameKey = NamespacedKey.of(name);
			NamespacedKey clientKey = clientName == null ? nameKey : NamespacedKey.of(clientName);
			Material mat = new Material(nameKey, clientKey, itemID, blockStateID, blockID, amount, toughness, explosionResistance, IMF, BDF);
			if (TEF != null)
				mat.setTileEntityFactory(TEF);
			if (var != null) {
				try {
					Field field = Material.class.getDeclaredField(var);
					field.setAccessible(true);
				    field.set(null, mat);
				} catch (Exception e) {
					reader.close();
					throw new IllegalStateException("Error while initializing field: " + var, e);
				}
			}
		}
		reader.endObject();
		reader.close();
		loaded = true;
	}
	
	@SuppressWarnings("unchecked")
	private static <V> Registry<V> loadFactories(Class<V> clazz, String path) throws IOException, ClassNotFoundException {
		InputStream in = MaterialLoader.class.getResourceAsStream(path);
		JsonReader reader = new JsonReader(new InputStreamReader(in));
		Registry<V> registry = Registries.getInstanceRegistry(clazz);
		reader.beginArray();
		while (reader.hasNext()) {
			reader.beginObject();
			reader.nextName();
			Class<?> factoryClass = Class.forName(reader.nextString());
			Constructor<?> constructor = null;
			try {
				constructor = factoryClass.getConstructor(Configuration.class);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new FactoryException("Error while fetching constructor!", e);
			}
			reader.nextName();
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				reader.nextName();
				String name = reader.nextString();
				String key = reader.nextName();
				boolean isDefault = false;
				if (key.equals("default")) {
					isDefault = reader.nextBoolean();
					reader.nextName();
				}
				JsonConfiguration cfg = JsonConfiguration.loadConfiguration(reader);
				V factory;
				try {
					factory = (V) constructor.newInstance(cfg);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					throw new FactoryException("Error while instaciating factory!", e);
				}
				registry.register(null, name, factory);
				if (isDefault)
					registry.setDefault(factory);
				reader.endObject();
			}
			reader.endArray();
			reader.endObject();
		}
		reader.endArray();
		return registry;
	}

}
