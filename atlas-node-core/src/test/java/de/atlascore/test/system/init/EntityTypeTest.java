package de.atlascore.test.system.init;

import java.io.IOException;
import java.util.LinkedList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import de.atlascore.registry.CoreRegistryHandler;
import de.atlascore.system.init.EntityTypeLoader;
import de.atlasmc.entity.EntityType;
import de.atlasmc.registry.Registries;
import de.atlastest.AtlasTest;

public class EntityTypeTest {
	
	@Test
	void testEntityTypes() throws IOException, ClassNotFoundException {
		try {
			Registries.init(new CoreRegistryHandler());
		} catch (IllegalStateException e) {}
		Registries.createInstanceRegistry(EntityType.class);
		EntityTypeLoader.loadEntityTypes();
		JsonReader reader = AtlasTest.getJsonResourceReader("/minecraft/registries/registry_minecraft_entity_type.json");
		LinkedList<Executable> checks = new LinkedList<>();
		reader.beginObject();
		while(reader.peek() == JsonToken.NAME) {
			final String name = reader.nextName();
			reader.beginObject();
			reader.nextName();
			final int protocolID = reader.nextInt();
			reader.endObject();
			checks.add(() -> {
				EntityType type = null;
				try {
					type = EntityType.getByName(name);
				} catch(Exception e) {
					Assertions.fail("Error while fetching entity type: " + name + "(" + protocolID + ")", e);
				}
				if (type == null) {
					Assertions.fail("Missing entity type: " + name + "(" + protocolID + ")");
				} else {
					if (type.getTypeID() != protocolID) {
						Assertions.fail("ID missmatch for entity type " + name + " was " + type.getTypeID() + " expected " + protocolID);
					}
				}
			});
		}
		reader.close();
		Assertions.assertAll("Failed to match all entity types to registry!", checks);
	}

}
