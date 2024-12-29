package de.atlasmc.entity;

import de.atlasmc.DyeColor;
import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.world.Biome;

public interface Wolf extends Tameable {
	
	boolean isBegging();
	
	void setBegging(boolean begging);
	
	DyeColor getCollarColor();
	
	void setCollarColor(DyeColor color);
	
	/**
	 * Returns the time in ticks until the angry state of this Wolf will be reset or -1 if no time
	 * @return ticks or -1
	 */
	int getAngerTime();
	
	void setAngerTime(int ticks);
	
	void setAngry(boolean angry);
	
	boolean isAngry();
	
	WolfVariant getVariant();
	
	void setVariant(WolfVariant variant);
	
	@RegistryHolder(key = "minecraft:wolf_variant", target = Target.PROTOCOL)
	public static class WolfVariant implements Namespaced {

		public static final ProtocolRegistry<WolfVariant> REGISTRY;
		
		static {
			REGISTRY = Registries.createRegistry(WolfVariant.class);
			REGISTRY.setIDSupplier(WolfVariant::getID);
		}
		
		private final NamespacedKey
		key,
		wildTexture,
		tameTexture,
		angryTexture;
		private final DataSet<Biome> biomes;
		private final int id;
		
		public WolfVariant(NamespacedKey wildTexture, NamespacedKey tameTexture, NamespacedKey angryTexture, DataSet<Biome> biomes) {
			this(null, -1, wildTexture, tameTexture, angryTexture, biomes);
		}
		
		public WolfVariant(NamespacedKey key, int id, NamespacedKey wildTexture, NamespacedKey tameTexture, NamespacedKey angryTexture, DataSet<Biome> biomes) {
			if (wildTexture == null)
				throw new IllegalArgumentException("Wild texture can not be null!");
			if (tameTexture == null)
				throw new IllegalArgumentException("Tame texture can not be null!");
			if (angryTexture == null)
				throw new IllegalArgumentException("Angry texture can not be null!");
			if (biomes == null)
				throw new IllegalArgumentException("Biomes can not be null!");
			this.id = id;
			this.key = key;
			this.wildTexture = wildTexture;
			this.tameTexture = tameTexture;
			this.angryTexture = angryTexture;
			this.biomes = biomes;
		}
		
		@Override
		public NamespacedKey getNamespacedKey() {
			return key;
		}
		
		public NamespacedKey getAngryTexture() {
			return angryTexture;
		}
		
		public DataSet<Biome> getBiomes() {
			return biomes;
		}
		
		public NamespacedKey getWildTexture() {
			return wildTexture;
		}
		
		public NamespacedKey getTameTexture() {
			return tameTexture;
		}
		
		public int getID() {
			return id;
		}
		
	}

}
