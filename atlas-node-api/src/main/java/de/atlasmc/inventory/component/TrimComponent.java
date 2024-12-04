package de.atlasmc.inventory.component;

import java.util.Map;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.chat.Chat;
import de.atlasmc.registry.RegistryHolder;

public interface TrimComponent extends AbstractTooltipComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:trim");
	
	TrimMaterial getMaterial();
	
	TrimPattern getTrimPattern();
	
	TrimComponent clone();
	
	@RegistryHolder(key="atlas:trim_material")
	public static class TrimMaterial implements Namespaced {

		private final NamespacedKey key;
		private final NamespacedKey asset;
		private final NamespacedKey ingredient;
		private final float itemModelIndex;
		private final Map<String, NamespacedKey> overrideArmorMaterials;
		private final Chat description;
		
		public TrimMaterial(NamespacedKey key, NamespacedKey asset, NamespacedKey ingredient, float itemModelIndex, Map<String, NamespacedKey> overrideArmorMaterials, Chat description) {
			this.key = key;
			this.asset = asset;
			this.ingredient = ingredient;
			this.itemModelIndex = itemModelIndex;
			this.overrideArmorMaterials = Map.copyOf(overrideArmorMaterials);
			this.description = description;
		}
		
		public NamespacedKey getAsset() {
			return asset;
		}
		
		public NamespacedKey getIngredient() {
			return ingredient;
		}
		
		public float getItemModelIndex() {
			return itemModelIndex;
		}
		
		public Map<String, NamespacedKey> getOverrideArmorMaterials() {
			return overrideArmorMaterials;
		}
		
		public Chat getDescription() {
			return description;
		}
		
		@Override
		public NamespacedKey getNamespacedKey() {
			return key;
		}
		
	}
	
	@RegistryHolder(key="atlas:trim_pattern")
	public static class TrimPattern implements Namespaced {
		
		private final NamespacedKey key;
		private final NamespacedKey asset;
		private final NamespacedKey template;
		private final Chat description;
		private final boolean decal;
		
		public TrimPattern(NamespacedKey key,  NamespacedKey asset, NamespacedKey template, Chat description, boolean decal) {
			this.key = key;
			this.asset = asset;
			this.template = template;
			this.description = description;
			this.decal = decal;
		}
		
		public Chat getDescription() {
			return description;
		}
		
		public boolean isDecal() {
			return decal;
		}
		
		public NamespacedKey getAsset() {
			return asset;
		}
		
		public NamespacedKey getTemplate() {
			return template;
		}

		@Override
		public NamespacedKey getNamespacedKey() {
			return key;
		}
		
	}

}
