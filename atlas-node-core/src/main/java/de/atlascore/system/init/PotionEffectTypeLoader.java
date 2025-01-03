package de.atlascore.system.init;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.stream.JsonReader;

import de.atlascore.plugin.CorePluginManager;
import de.atlascore.potion.CoreClassPotionEffectType;
import de.atlasmc.NamespacedKey;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;
import de.atlasmc.util.configuration.InvalidConfigurationException;

public class PotionEffectTypeLoader {
	
	private static volatile boolean loaded;

	public static void loadPotionEffects() throws IOException, ClassNotFoundException {
		if (loaded)
			return;
		load();
	}
	
	@SuppressWarnings({ "unchecked", "resource" })
	private static synchronized void load() throws IOException, ClassNotFoundException {
		if (loaded)
			return;
		InputStream in = MaterialLoader.class.getResourceAsStream("/data/potion_effect_type.json");
		JsonReader reader = new JsonReader(new InputStreamReader(in));
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			int id = -1;
			int color = 0;
			Class<? extends PotionEffect> effectClass = null;
			reader.beginObject();
			while (reader.hasNext()) {
				String key = reader.nextName();
				switch(key) {
				case "id":
					id = reader.nextInt();
				case "effectClass":
					effectClass = (Class<? extends PotionEffect>) Class.forName(reader.nextString());
				case "color":
					color = reader.nextInt();
				default:
					reader.skipValue();
				}
			}
			reader.endObject();
			if (id == -1)
				throw new InvalidConfigurationException("Missing id definition!");
			if (effectClass == null)
				throw new InvalidConfigurationException("Missing effectClass definition!");
			NamespacedKey nameKey = NamespacedKey.of(name);
			PotionEffectType type = new CoreClassPotionEffectType(nameKey, id, color, effectClass);
			PotionEffectType.REGISTRY.register(CorePluginManager.SYSTEM, nameKey, type);
		}
		reader.endObject();
		reader.close();
		loaded = true;
	}

}
