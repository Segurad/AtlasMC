package de.atlasmc.node.entity;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.DyeColor;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.codec.NBTSerializable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Wolf extends Tameable, AngerableMob {
	
	public static final NBTCodec<Wolf>
	NBT_HANDLER = NBTCodec
					.builder(Wolf.class)
					.include(Tameable.NBT_HANDLER)
					.include(AngerableMob.NBT_HANDLER)
					.enumByteField("CollarColor", Wolf::getCollarColor, Wolf::setCollarColor, DyeColor.class, DyeColor.RED)
					.registryValue("variant", Wolf::getVariant, Wolf::setVariant, WolfVariant.REGISTRY_KEY)
					// sound_variant
					.build();
	
	boolean isBegging();
	
	void setBegging(boolean begging);
	
	DyeColor getCollarColor();
	
	void setCollarColor(DyeColor color);
	
	WolfVariant getVariant();
	
	void setVariant(WolfVariant variant);
	
	@Override
	default NBTCodec<? extends Wolf> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	@RegistryHolder(key = "minecraft:wolf_variant", target = Target.PROTOCOL)
	public static class WolfVariant extends ProtocolRegistryValueBase implements NBTSerializable {

		public static final RegistryKey<WolfVariant> REGISTRY_KEY = Registries.getRegistryKey(WolfVariant.class);
		
		public static final NBTCodec<WolfVariant>
		NBT_HANDLER = NBTCodec
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
			return getRegistry().get(key);
		}
		
		public static WolfVariant get(String key) {
			return getRegistry().get(key);
		}
		
		public static WolfVariant getByID(int id) {
			return getRegistry().getByID(id);
		}
		
		public static ProtocolRegistry<WolfVariant> getRegistry() {
			return REGISTRY_KEY.getRegistry();
		}
		
		@Override
		public NBTCodec<? extends WolfVariant> getNBTCodec() {
			return NBT_HANDLER;
		}
		
	}

}
