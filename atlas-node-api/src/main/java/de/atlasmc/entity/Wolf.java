package de.atlasmc.entity;

import java.io.IOException;

import de.atlasmc.DyeColor;
import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;
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
	public static class WolfVariant extends ProtocolRegistryValueBase {

		private static final ProtocolRegistry<WolfVariant> REGISTRY;
		
		protected static final CharKey
		NBT_WILD_TEXTURE = CharKey.literal("wild_texture"),
		NBT_TAME_TEXTURE = CharKey.literal("tame_texture"),
		NBT_ANGRY_TEXTURE = CharKey.literal("angry_texture"),
		NBT_BIOMES = CharKey.literal("biomes");
		
		static {
			REGISTRY = Registries.createRegistry(WolfVariant.class);
		}
		
		private final NamespacedKey
		wildTexture,
		tameTexture,
		angryTexture;
		private final DataSet<Biome> biomes;
		
		public WolfVariant(NamespacedKey wildTexture, NamespacedKey tameTexture, NamespacedKey angryTexture, DataSet<Biome> biomes) {
			this(NamespacedKey.INLINE_DEFINITION, -1, wildTexture, tameTexture, angryTexture, biomes);
		}
		
		public WolfVariant(NamespacedKey key, int id, NamespacedKey wildTexture, NamespacedKey tameTexture, NamespacedKey angryTexture, DataSet<Biome> biomes) {
			super(key, id);
			if (wildTexture == null)
				throw new IllegalArgumentException("Wild texture can not be null!");
			if (tameTexture == null)
				throw new IllegalArgumentException("Tame texture can not be null!");
			if (angryTexture == null)
				throw new IllegalArgumentException("Angry texture can not be null!");
			if (biomes == null)
				throw new IllegalArgumentException("Biomes can not be null!");
			this.wildTexture = wildTexture;
			this.tameTexture = tameTexture;
			this.angryTexture = angryTexture;
			this.biomes = biomes;
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
		
		@Override
		public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
			writer.writeCompoundTag();
			writer.writeNamespacedKey(NBT_WILD_TEXTURE, wildTexture);
			writer.writeNamespacedKey(NBT_TAME_TEXTURE, tameTexture);
			writer.writeNamespacedKey(NBT_ANGRY_TEXTURE, angryTexture);
			DataSet.toNBT(NBT_BIOMES, biomes, writer, false);
		}
		
		public static WolfVariant get(NamespacedKey key) {
			return REGISTRY.get(key);
		}
		
		public static WolfVariant get(String key) {
			return REGISTRY.get(key);
		}
		
		public static WolfVariant getByID(int id) {
			return REGISTRY.getByID(id);
		}
		
		public static ProtocolRegistry<WolfVariant> getRegistry() {
			return REGISTRY;
		}
		
	}

}
