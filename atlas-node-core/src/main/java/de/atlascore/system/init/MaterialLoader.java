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
import de.atlasmc.factory.FactoryException;
import de.atlasmc.factory.MetaDataFactory;
import de.atlasmc.factory.TileEntityFactory;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
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
		InputStream in = MaterialLoader.class.getResourceAsStream("/data/material_factory.json");
		JsonReader reader = new JsonReader(new InputStreamReader(in));
		reader.beginObject();
		// load mdf
		Registry<MetaDataFactory> MDFs = loadFactories(reader, MetaDataFactory.class);
		// load tef
		Registry<TileEntityFactory> TEFs = loadFactories(reader, TileEntityFactory.class);
		// load mats
		reader.nextName();
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			reader.beginObject();
			MetaDataFactory MDF = null;
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
				case "MDF":
					String mdfKey = reader.nextString();
					MDF = MDFs.get(mdfKey);
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
			reader.endObject();
			NamespacedKey nameKey = NamespacedKey.of(name);
			NamespacedKey clientKey = clientName == null ? nameKey : NamespacedKey.of(clientName);
			Material mat = new Material(nameKey, clientKey, itemID, blockStateID, blockID, amount, toughness, explosionResistance, MDF);
			if (TEF != null)
				mat.setTileEntityFactory(TEF);
			if (var != null) {
				try {
					Field field = Material.class.getDeclaredField(var);
					field.setAccessible(true);
				    field.set(null, mat);
				} catch (Exception e) {
					throw new IllegalStateException("Error while initializing field: " + var, e);
				}
			}
		}
		reader.endObject();
		reader.close();
		loaded = true;
	}
	
	@SuppressWarnings("unchecked")
	private static <V> Registry<V> loadFactories(JsonReader reader, Class<V> clazz) throws IOException, ClassNotFoundException {
		Registry<V> registry = Registries.getInstanceRegistry(clazz);
		reader.nextName();
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
				registry.register(name, factory);
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
