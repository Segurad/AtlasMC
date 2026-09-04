package de.atlasmc.node.entity.component;

import java.util.Objects;

import de.atlasmc.NamespacedKey;
import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.node.DyeColor;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.registry.RegistryValueKey;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumUtil;
import static de.atlasmc.registry.RegistryValueKey.ofLiteral;

public class WolfMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<WolfMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(WolfMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("CollarColor", WolfMetaComponent::getCollarColor, WolfMetaComponent::setCollarColor, EnumUtil.enumByteNBTCodec(DyeColor.class), DyeColor.RED)
					.codec("variant", WolfMetaComponent::getVariant, WolfMetaComponent::setVariant, Registries.registryValueNBTCodec(WolfVariant.REGISTRY_KEY))
					.codec("sound_variant", WolfMetaComponent::getSoundVariant, WolfMetaComponent::setSoundVariant, Registries.registryValueNBTCodec(WolfSoundVariant.REGISTRY_KEY))
					.build();
	
	public static final MetaDataField<Boolean>
	META_IS_BEGGING = new MetaDataField<>(20, false, EntityMetaTypes.BOOLEAN);
	public static final MetaDataField<Integer>
	META_COLAR_COLOR = new MetaDataField<>(21, DyeColor.RED.getID(), EntityMetaTypes.VAR_INT);
	public static final MetaDataField<Integer>
	META_ANGER_TIME = new MetaDataField<>(22, 0, EntityMetaTypes.VAR_INT);
	public static final MetaDataField<WolfVariant>
	META_WOLF_VARIANT = new MetaDataField<>(23, WolfVariant.PALE.get(), EntityMetaTypes.WOLF_VARIANT);
	public static final MetaDataField<WolfSoundVariant>
	META_WOLF_SOUND_VARIANT = new MetaDataField<>(24, WolfSoundVariant.CLASSIC.get(), EntityMetaTypes.WOLF_SOUND_VARIANT);
	
	public WolfMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_IS_BEGGING);
		container.set(META_COLAR_COLOR);
		container.set(META_ANGER_TIME);
		container.set(META_WOLF_VARIANT);
		container.set(META_WOLF_SOUND_VARIANT);
	}

	@Override
	public int getMetaFieldCount() {
		return 5;
	}
	
	public boolean isBegging() {
		return getHolder().getMetaContainer().getData(META_IS_BEGGING);
	}

	public void setBegging(boolean begging) {
		getHolder().getMetaContainer().setData(META_IS_BEGGING, begging);		
	}

	public DyeColor getCollarColor() {
		return EnumUtil.getByID(DyeColor.class, getHolder().getMetaContainer().getData(META_COLAR_COLOR));
	}

	public void setCollarColor(DyeColor color) {
		getHolder().getMetaContainer().setData(META_COLAR_COLOR, color.getID());
	}

	public WolfVariant getVariant() {
		return getHolder().getMetaContainer().getData(META_WOLF_VARIANT);
	}

	public void setVariant(WolfVariant variant) {
		getHolder().getMetaContainer().setData(META_WOLF_VARIANT, variant);
	}
	
	public WolfSoundVariant getSoundVariant() {
		return getHolder().getMetaContainer().getData(META_WOLF_SOUND_VARIANT);
	}
	
	public void setSoundVariant(WolfSoundVariant sound) {
		getHolder().getMetaContainer().setData(META_WOLF_SOUND_VARIANT, sound);
	}
	
	@Override
	public NBTCodec<? extends WolfMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@RegistryHolder(key = "minecraft:wolf_variant", target = Target.PROTOCOL)
	public static class WolfVariant extends ProtocolRegistryValueBase implements NBTSerializable {

		public static final RegistryKey<WolfVariant> REGISTRY_KEY = Registries.getRegistryKey(WolfVariant.class);
		
		public static final RegistryValueKey<WolfVariant> 
		PALE = ofLiteral(REGISTRY_KEY, "minecraft:pale");
		
		@NotNull
		public static final NBTCodec<WolfVariant>
		NBT_CODEC = NBTCodec
						.builder(WolfVariant.class)
						.defaultConstructor(WolfVariant::new)
						.codec("assets", WolfVariant::getAssets, WolfVariant::setAssets, WolfAssets.NBT_CODEC)
						.codec("baby_assets", WolfVariant::getBabyAssets, WolfVariant::setBabyAssets, WolfAssets.NBT_CODEC)
						.build();
		
		private WolfAssets
		assets,
		babyAssets;
		
		private WolfVariant() {
			super();
			// for construction via codec
		}
		
		public WolfVariant(NamespacedKey key, int id, WolfAssets assets, WolfAssets babyAssets) {
			super(key, id);
			this.assets = Objects.requireNonNull(assets, "assets");
			this.babyAssets = Objects.requireNonNull(babyAssets, "babyAssets");
		}
		
		public WolfAssets getAssets() {
			return assets;
		}
		
		private void setAssets(WolfAssets assets) {
			this.assets = assets;
		}
		
		public WolfAssets getBabyAssets() {
			return babyAssets;
		}
		
		private void setBabyAssets(WolfAssets babyAssets) {
			this.babyAssets = babyAssets;
		}
		
		public static ProtocolRegistry<WolfVariant> getRegistry() {
			return REGISTRY_KEY.getRegistry();
		}
		
		@Override
		public NBTCodec<? extends WolfVariant> getNBTCodec() {
			return NBT_CODEC;
		}
		
		@Override
		public boolean hasNBT() {
			return true;
		}
		
	}
	
	public static class WolfAssets implements NBTSerializable {
		
		public static final NBTCodec<WolfAssets>
		NBT_CODEC = NBTCodec
						.builder(WolfAssets.class)
						.codec("angry", WolfAssets::getAngryTexture, WolfAssets::setAngryTexture, NamespacedKey.NBT_CODEC)
						.codec("wild", WolfAssets::getWildTexture, WolfAssets::setWildTexture, NamespacedKey.NBT_CODEC)
						.codec("tame", WolfAssets::getTameTexture, WolfAssets::setTameTexture, NamespacedKey.NBT_CODEC)
						.build();

		private NamespacedKey
		wildTexture,
		tameTexture,
		angryTexture;
	
		public NamespacedKey getAngryTexture() {
			return angryTexture;
		}
		
		public void setAngryTexture(NamespacedKey angryTexture) {
			this.angryTexture = angryTexture;
		}
		
		public NamespacedKey getWildTexture() {
			return wildTexture;
		}
		
		public void setWildTexture(NamespacedKey wildTexture) {
			this.wildTexture = wildTexture;
		}
		
		public NamespacedKey getTameTexture() {
			return tameTexture;
		}
		
		public void setTameTexture(NamespacedKey tameTexture) {
			this.tameTexture = tameTexture;
		}
		
		@Override
		public NBTCodec<? extends NBTSerializable> getNBTCodec() {
			return NBT_CODEC;
		}
		
	}
	
	@RegistryHolder(key = "minecraft:wolf_sound_variant", target = Target.PROTOCOL)
	public static class WolfSoundVariant extends ProtocolRegistryValueBase implements NBTSerializable {

		public static final RegistryKey<WolfSoundVariant> REGISTRY_KEY = Registries.getRegistryKey(WolfSoundVariant.class);
		
		public static final RegistryValueKey<WolfSoundVariant> 
		CLASSIC = ofLiteral(REGISTRY_KEY, "minecraft:classic");
		
		@NotNull
		public static final NBTCodec<WolfSoundVariant>
		NBT_CODEC = NBTCodec
						.builder(WolfSoundVariant.class)
						.defaultConstructor(WolfSoundVariant::new)
						.codec("adult_sounds", WolfSoundVariant::getAdult, WolfSoundVariant::setAdult, WolfSounds.NBT_CODEC)
						.codec("baby_sounds", WolfSoundVariant::getBaby, WolfSoundVariant::setBaby, WolfSounds.NBT_CODEC)
						.build();
		
		private WolfSounds
		adult,
		baby;
		
		private WolfSoundVariant() {}
		
		public WolfSoundVariant(NamespacedKey key, int id, WolfSounds adult, WolfSounds baby) {
			super(key, id);
			this.adult = adult;
			this.baby = baby;
		}
	
		public WolfSounds getAdult() {
			return adult;
		}
		
		private void setAdult(WolfSounds adult) {
			this.adult = adult;
		}
		
		public WolfSounds getBaby() {
			return baby;
		}
		
		private void setBaby(WolfSounds baby) {
			this.baby = baby;
		}
		
		@Override
		public NBTCodec<? extends WolfSoundVariant> getNBTCodec() {
			return NBT_CODEC;
		}
		
		@Override
		public boolean hasNBT() {
			return true;
		}
		
	}
	
	public static final class WolfSounds implements NBTSerializable {

		@NotNull
		public static final NBTCodec<WolfSounds>
		NBT_CODEC = NBTCodec
						.builder(WolfSounds.class)
						.defaultConstructor(WolfSounds::new)
						.codec("ambient_sound", WolfSounds::getAmbient, WolfSounds::setAmbient, Sound.NBT_CODEC)
						.codec("death_sound", WolfSounds::getDeath, WolfSounds::setDeath, Sound.NBT_CODEC)
						.codec("growl_sound", WolfSounds::getGrowl, WolfSounds::setGrowl, Sound.NBT_CODEC)
						.codec("hurt_sound", WolfSounds::getHurt, WolfSounds::setHurt, Sound.NBT_CODEC)
						.codec("pant_sound", WolfSounds::getPant, WolfSounds::setPant, Sound.NBT_CODEC)
						.codec("whine", WolfSounds::getWhine, WolfSounds::setWhine, Sound.NBT_CODEC)
						.build();
		
		private Sound
		ambient,
		death,
		growl,
		hurt,
		pant,
		whine;
		
		public Sound getAmbient() {
			return ambient;
		}
		
		public Sound getDeath() {
			return death;
		}
		
		public Sound getGrowl() {
			return growl;
		}
		
		public Sound getHurt() {
			return hurt;
		}
		
		public Sound getWhine() {
			return whine;
		}
		
		
		public Sound getPant() {
			return pant;
		}
		
		public void setAmbient(Sound ambient) {
			this.ambient = ambient;
		}
		
		public void setDeath(Sound death) {
			this.death = death;
		}
		
		public void setGrowl(Sound growl) {
			this.growl = growl;
		}
		
		public void setHurt(Sound hurt) {
			this.hurt = hurt;
		}
		
		public void setPant(Sound pant) {
			this.pant = pant;
		}
		
		public void setWhine(Sound whine) {
			this.whine = whine;
		}
		
		@Override
		public NBTCodec<? extends WolfSounds> getNBTCodec() {
			return NBT_CODEC;
		}
		
	}
	
}
