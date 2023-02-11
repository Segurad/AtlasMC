package test.atlascore.system.init;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import de.atlascore.system.init.MaterialLoader;
import de.atlasmc.Material;
import de.atlasmc.NamespacedKey;
import de.atlasmc.block.data.BlockData;

public class DefaultBlockStateTest { 
	
	@Test
	void testDefaultBlockStateIDs() throws IOException {
		boolean fail = false;
		MaterialLoader.loadMaterial();
		File blocksFile = new File(getClass().getResource("/test/data/blocks_1_16_5.json").getFile());
		JsonReader reader = new JsonReader(new FileReader(blocksFile));
		if (reader.peek() != JsonToken.BEGIN_OBJECT)
			throw new JsonParseException("Exspected Object but found: " + reader.peek().name());
		reader.beginObject();
		while (reader.peek() != JsonToken.END_OBJECT) {
			String rawMat = reader.nextName();
			NamespacedKey matKey = new NamespacedKey(rawMat);
			Material mat = Material.getMaterial(matKey);
			// handle no material found
			if (mat == null) {
				System.out.println("Cannot find Material with name: " + rawMat);
				reader.skipValue();
				fail = true;
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
					System.out.println("Internal Material block id does not match default state id: " + id + "  | " + stateID + " | " + mat.getNamespacedName());
					fail = true;
				}
				reader.endArray();
			}
			reader.endObject();
		}
		if (fail)
			Assertions.fail();
	}

}
