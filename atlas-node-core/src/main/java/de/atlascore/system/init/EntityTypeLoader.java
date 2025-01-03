package de.atlascore.system.init;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.stream.JsonReader;

import de.atlascore.plugin.CorePluginManager;
import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;

public class EntityTypeLoader {
	
	private static volatile boolean loaded = false;
	
	public static synchronized void loadEntityTypes() throws IOException, ClassNotFoundException {
		if (loaded)
			return;
		try (
			InputStream in = EntityTypeLoader.class.getResourceAsStream("/data/entity_factory.json");
			JsonReader reader = new JsonReader(new InputStreamReader(in))
			) {
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				String rawClass = null;
				int id = -1;
				reader.beginObject();
				while (reader.hasNext()) {
					String key = reader.nextName();
					switch (key) {
					case "id":
						id = reader.nextInt();
						break;
					case "entityClass":
						rawClass = reader.nextString();
						break;
					default:
						reader.skipValue();
					}
				}
				reader.endObject();
				if (rawClass == null)
					throw new IOException("Missing field \"entityClass\" for: " + name);
				if (id == -1)
					throw new IOException("Missing field \"id\" for: " + name);
				Class<?> entityClass = Class.forName(rawClass);
				if (!Entity.class.isAssignableFrom(entityClass))
					throw new IllegalArgumentException("Entity class is not assignable from de.atlasmc.entity.Entity: " + entityClass.getName());
				@SuppressWarnings("unchecked")
				EntityType type = new EntityType(NamespacedKey.of(name), id, (Class<? extends Entity>) entityClass);
				EntityType.REGISTRY.register(CorePluginManager.SYSTEM, type.getNamespacedKey(), type);
			}
			reader.endObject();
			reader.close();
			loaded = true;
		}
	}

}
