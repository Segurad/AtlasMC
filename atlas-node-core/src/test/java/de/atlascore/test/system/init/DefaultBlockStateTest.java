package de.atlascore.test.system.init;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.opentest4j.MultipleFailuresError;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import de.atlascore.registry.CoreRegistryHandler;
import de.atlascore.system.init.MaterialLoader;
import de.atlasmc.Material;
import de.atlasmc.NamespacedKey;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.BlockDataFactory;
import de.atlasmc.block.tile.TileEntityFactory;
import de.atlasmc.inventory.meta.ItemMetaFactory;
import de.atlasmc.registry.Registries;
import de.atlastest.AtlasTest;

public class DefaultBlockStateTest { 
	
	@Test
	void testDefaultBlockStateIDs() throws Exception {
		try {
			Registries.init(new CoreRegistryHandler());
		} catch (IllegalStateException e) {}
		Registries.createInstanceRegistry(Material.class);
		Registries.createInstanceRegistry(ItemMetaFactory.class);
		Registries.createInstanceRegistry(BlockDataFactory.class);
		Registries.createInstanceRegistry(TileEntityFactory.class);
		MaterialLoader.loadMaterial();
		JsonReader reader = AtlasTest.getJsonResourceReader("blocks.json");
		reader.beginObject();
		LinkedList<Throwable> checks = new LinkedList<>();
		while (reader.peek() != JsonToken.END_OBJECT) {
			String fail = null;
			String rawMat = reader.nextName();
			NamespacedKey matKey = NamespacedKey.of(rawMat);
			Material mat = Material.getMaterial(matKey);
			// handle no material found
			if (mat == null) {
				reader.skipValue();
				checks.add(new AssertionFailedError("Cannot find Material with name: " + rawMat));
				continue;
			}
			reader.beginObject();
			// find states
			while (reader.peek() != JsonToken.END_OBJECT) {
				String attributeKey = reader.nextName();
				if (!attributeKey.equals("states")) {
					if (!attributeKey.equals("properties"))
						System.out.println("Unknown key (" + attributeKey + ") found while analysing states for : " + rawMat);
					reader.skipValue();
					continue;
				}
				reader.beginArray();
				// find default state
				while (reader.peek() != JsonToken.END_ARRAY) {
					reader.beginObject();
					int id = -1;
					boolean defaultState = false;
					while (reader.peek() != JsonToken.END_OBJECT) {
						String stateKey = reader.nextName();
						switch (stateKey) {
						case "id":
							id = reader.nextInt();
							break;
						case "default":
							defaultState = true;
						default:
							reader.skipValue();
							break;
						} 
					}
					reader.endObject();
					if (!defaultState)
						continue;
					BlockData data = mat.createBlockData();
					int stateID = data.getStateID();
					if (id == stateID)
						continue;
					fail = "Internal Material block id does not match default state id: " + id + "  | " + stateID + " | " + mat.getNamespacedKeyRaw();
				}
				reader.endArray();
			}
			reader.endObject();
			if (fail != null)
				checks.add(new AssertionFailedError(fail));
		}
		reader.close();
		if (!checks.isEmpty())
			throw new MultipleFailuresError("Failed to check default block states!", checks);
	}

}
