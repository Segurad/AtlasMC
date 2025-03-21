package de.atlascore.test.system.init;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.opentest4j.MultipleFailuresError;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import de.atlascore.registry.CoreRegistryHandler;
import de.atlasmc.NamespacedKey;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.BlockDataFactory;
import de.atlasmc.block.tile.TileEntityFactory;
import de.atlasmc.registry.Registries;
import de.atlascore.test.registry.RegistryTestHelper;
import de.atlastest.AtlasTest;

public class DefaultBlockStateTest { 
	
	@Test
	void testDefaultBlockStateIDs() throws Exception {
		try {
			Registries.init(new CoreRegistryHandler());
		} catch (IllegalStateException e) {}
		Registries.createRegistry(BlockType.class);
		Registries.createRegistry(BlockDataFactory.class);
		Registries.createRegistry(TileEntityFactory.class);
		RegistryTestHelper.loadBulk("/data/block_data_factories.json");
		RegistryTestHelper.loadBulk("/data/tile_entity_factories.json");
		RegistryTestHelper.loadBulk("/data/block_types.json");
		JsonReader reader = AtlasTest.getJsonResourceReader("/minecraft/blocks.json");
		reader.beginObject();
		LinkedList<Throwable> checks = new LinkedList<>();
		while (reader.peek() != JsonToken.END_OBJECT) {
			String fail = null;
			String rawMat = reader.nextName();
			NamespacedKey matKey = NamespacedKey.of(rawMat);
			BlockType type = BlockType.get(matKey);
			// handle no material found
			if (type == null) {
				reader.skipValue();
				checks.add(new AssertionFailedError("Cannot find type with name: " + rawMat));
				continue;
			}
			reader.beginObject();
			// find states
			while (reader.peek() != JsonToken.END_OBJECT) {
				String attributeKey = reader.nextName();
				if (!attributeKey.equals("states")) {
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
							defaultState = reader.nextBoolean();
							break;
						default:
							reader.skipValue();
							break;
						} 
					}
					reader.endObject();
					if (!defaultState)
						continue;
					BlockData data = type.createBlockData();
					int stateID = data.getStateID();
					if (id == stateID)
						continue;
					fail = "Internal type block id does not match default state id: " + id + "  | " + stateID + " | " + type.getNamespacedKeyRaw();
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
