package de.atlasmc.node.inventory.component;

import java.util.Map;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.chat.Chat;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface TrimComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<TrimComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(TrimComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.TRIM.getNamespacedKey())
					.registryValue("pattern", TrimComponent::getTrimMaterial, TrimComponent::setTrimMaterial, TrimMaterial.REGISTRY_KEY)
					.registryValue("material", TrimComponent::getTrimPattern, TrimComponent::setTrimPattern, TrimPattern.REGISTRY_KEY)
					.endComponent()
					.build();
	
	TrimMaterial getTrimMaterial();
	
	void setTrimMaterial(TrimMaterial material);
	
	TrimPattern getTrimPattern();
	
	void setTrimPattern(TrimPattern pattern);
	
	TrimComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends TrimComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	@RegistryHolder(key="atlas:trim_material")
	public static class TrimMaterial implements Namespaced {
		
		public static final RegistryKey<TrimMaterial> REGISTRY_KEY = Registries.getRegistryKey(TrimMaterial.class);

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
		
		public static final RegistryKey<TrimPattern> REGISTRY_KEY = Registries.getRegistryKey(TrimPattern.class);
		
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
