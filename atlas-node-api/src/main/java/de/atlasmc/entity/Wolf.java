package de.atlasmc.entity;

import de.atlasmc.DyeColor;
import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

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
	public static class WolfVariant extends ProtocolRegistryValueBase implements NBTSerializable {

		private static final ProtocolRegistry<WolfVariant> REGISTRY;
		
		public static final NBTSerializationHandler<WolfVariant>
		NBT_HANDLER = NBTSerializationHandler
						.builder(WolfVariant.class)
						.defaultConstructor(WolfVariant::new)
						.beginComponent("assets")
						.namespacedKey("angry", WolfVariant::getAngryTexture, WolfVariant::setAngryTexture)
						.namespacedKey("wild", WolfVariant::getWildTexture, WolfVariant::setWildTexture)
						.namespacedKey("tame", WolfVariant::getTameTexture, WolfVariant::setTameTexture)
						.endComponent()
						.build();
		
		protected static final CharKey
		NBT_WILD_TEXTURE = CharKey.literal("wild_texture"),
		NBT_TAME_TEXTURE = CharKey.literal("tame_texture"),
		NBT_ANGRY_TEXTURE = CharKey.literal("angry_texture"),
		NBT_BIOMES = CharKey.literal("biomes");
		
		static {
			REGISTRY = Registries.createRegistry(WolfVariant.class);
		}
		
		private NamespacedKey
		wildTexture,
		tameTexture,
		angryTexture;
		
		private WolfVariant() {}
		
		public WolfVariant(NamespacedKey wildTexture, NamespacedKey tameTexture, NamespacedKey angryTexture) {
			this(NamespacedKey.INLINE_DEFINITION, -1, wildTexture, tameTexture, angryTexture);
		}
		
		public WolfVariant(NamespacedKey key, int id, NamespacedKey wildTexture, NamespacedKey tameTexture, NamespacedKey angryTexture) {
			super(key, id);
			if (wildTexture == null)
				throw new IllegalArgumentException("Wild texture can not be null!");
			if (tameTexture == null)
				throw new IllegalArgumentException("Tame texture can not be null!");
			if (angryTexture == null)
				throw new IllegalArgumentException("Angry texture can not be null!");
			this.wildTexture = wildTexture;
			this.tameTexture = tameTexture;
			this.angryTexture = angryTexture;
		}
	
		public NamespacedKey getAngryTexture() {
			return angryTexture;
		}
		
		private void setAngryTexture(NamespacedKey angryTexture) {
			this.angryTexture = angryTexture;
		}
		
		public NamespacedKey getWildTexture() {
			return wildTexture;
		}
		
		private void setWildTexture(NamespacedKey wildTexture) {
			this.wildTexture = wildTexture;
		}
		
		public NamespacedKey getTameTexture() {
			return tameTexture;
		}
		
		private void setTameTexture(NamespacedKey tameTexture) {
			this.tameTexture = tameTexture;
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
		
		@Override
		public NBTSerializationHandler<? extends WolfVariant> getNBTHandler() {
			return NBT_HANDLER;
		}
		
	}

}
