package de.atlasmc.node.entity.component;

import static de.atlasmc.registry.RegistryValueKey.ofLiteral;

import java.util.Objects;

import de.atlasmc.NamespacedKey;
import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.registry.ProtocolRegistryValueBase;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.registry.RegistryValueKey;
import de.atlasmc.util.annotation.NotNull;

public class PigMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	public static final NBTCodec<PigMetaComponent>
	NBT_HANDLER = NBTCodec
					.builder(PigMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.intField("BoostTime", PigMetaComponent::getBoostTime, PigMetaComponent::setBoostTime, 0)
					.codec("variant", PigMetaComponent::getVariant, PigMetaComponent::setVariant, Registries.registryValueNBTCodec(PigVariant.REGISTRY_KEY))
					.build();
	
	public static final MetaDataField<Integer>
	META_BOOST_TIME = new MetaDataField<>(18, 0, EntityMetaTypes.VAR_INT);
	public static final MetaDataField<PigVariant>
	META_VARIANT = new MetaDataField<>(19, PigVariant.TEMPERATE.get(), EntityMetaTypes.PIG_VARIANT);
	public static final MetaDataField<PigSoundVariant>
	META_SOUND_VARIANT = new MetaDataField<>(20, PigSoundVariant.CLASSIC.get(), EntityMetaTypes.PIG_SOUND_VARIANT);
	
	public PigMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 3;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_BOOST_TIME);
		container.set(META_VARIANT);
		container.set(META_SOUND_VARIANT);
	}

	public int getBoostTime() {
		return getHolder().getMetaContainer().getData(META_BOOST_TIME);
	}

	public void setBoostTime(int time) {
		getHolder().getMetaContainer().setData(META_BOOST_TIME, time);		
	}

	public PigVariant getVariant() {
		return getHolder().getMetaContainer().getData(META_VARIANT);
	}

	public void setVariant(PigVariant variant) {
		getHolder().getMetaContainer().setData(META_VARIANT, variant);
	}

	
	@RegistryHolder(key = "minecraft:pig_variant", target = Target.PROTOCOL)
	public static class PigVariant extends ProtocolRegistryValueBase implements NBTSerializable {
		
		public static final RegistryKey<PigVariant> REGISTRY_KEY = Registries.getRegistryKey(PigVariant.class);
		
		public static final RegistryValueKey<PigVariant>
		TEMPERATE = ofLiteral(REGISTRY_KEY, "minecraft:temperate");
		
		@NotNull
		public static final NBTCodec<PigVariant>
		NBT_CODEC = NBTCodec
						.builder(PigVariant.class)
						.defaultConstructor(PigVariant::new)
						.codec("asset_id", PigVariant::getAsset, PigVariant::setAsset, NamespacedKey.NBT_CODEC)
						.codec("baby_asset_id", PigVariant::getBabyAsset, PigVariant::setBabyAsset, NamespacedKey.NBT_CODEC)
						.codec("model", PigVariant::getModel, PigVariant::setModel, NamespacedKey.NBT_CODEC)
						.build();
		
		private NamespacedKey 
		asset,
		babyAsset,
		model;
		
		private PigVariant() {
			super();
			// for construction via codec
		}
		
		public PigVariant(NamespacedKey key, int id, NamespacedKey asset, NamespacedKey babyAsset, NamespacedKey model) {
			super(key, id);
			this.asset = Objects.requireNonNull(asset, "asset");
			this.babyAsset = Objects.requireNonNull(babyAsset, "babyAsset");
			this.model = Objects.requireNonNull(model, "model");
		}
		
		public NamespacedKey getAsset() {
			return asset;
		}
		
		private void setAsset(NamespacedKey asset) {
			this.asset = asset;
		}
		
		public NamespacedKey getBabyAsset() {
			return babyAsset;
		}
		
		private void setBabyAsset(NamespacedKey babyAsset) {
			this.babyAsset = babyAsset;
		}
		
		public NamespacedKey getModel() {
			return model;
		}
		
		private void setModel(NamespacedKey model) {
			this.model = model;
		}
		
		@Override
		public boolean hasNBT() {
			return true;
		}
		
		@Override
		public NBTCodec<? extends PigVariant> getNBTCodec() {
			return NBT_CODEC;
		}
		
	}
	
	@RegistryHolder(key = "minecraft:pig_sound_variant", target = Target.PROTOCOL)
	public static class PigSoundVariant extends ProtocolRegistryValueBase implements NBTSerializable {

		public static final RegistryKey<PigSoundVariant> REGISTRY_KEY = Registries.getRegistryKey(PigSoundVariant.class);
		
		public static final RegistryValueKey<PigSoundVariant> 
		CLASSIC = ofLiteral(REGISTRY_KEY, "minecraft:classic");
		
		@NotNull
		public static final NBTCodec<PigSoundVariant>
		NBT_CODEC = NBTCodec
						.builder(PigSoundVariant.class)
						.defaultConstructor(PigSoundVariant::new)
						.codec("adult_sounds", PigSoundVariant::getAdult, PigSoundVariant::setAdult, PigSounds.NBT_CODEC)
						.codec("baby_sounds", PigSoundVariant::getBaby, PigSoundVariant::setBaby, PigSounds.NBT_CODEC)
						.build();
		
		private PigSounds
		adult,
		baby;
		
		private PigSoundVariant() {}
		
		public PigSoundVariant(NamespacedKey key, int id, PigSounds adult, PigSounds baby) {
			super(key, id);
			this.adult = adult;
			this.baby = baby;
		}
	
		public PigSounds getAdult() {
			return adult;
		}
		
		private void setAdult(PigSounds adult) {
			this.adult = adult;
		}
		
		public PigSounds getBaby() {
			return baby;
		}
		
		private void setBaby(PigSounds baby) {
			this.baby = baby;
		}
		
		@Override
		public NBTCodec<? extends PigSoundVariant> getNBTCodec() {
			return NBT_CODEC;
		}
		
		@Override
		public boolean hasNBT() {
			return true;
		}
		
	}
	
	public static final class PigSounds implements NBTSerializable {

		@NotNull
		public static final NBTCodec<PigSounds>
		NBT_CODEC = NBTCodec
						.builder(PigSounds.class)
						.defaultConstructor(PigSounds::new)
						.codec("ambient_sound", PigSounds::getAmbient, PigSounds::setAmbient, Sound.NBT_CODEC)
						.codec("death_sound", PigSounds::getDeath, PigSounds::setDeath, Sound.NBT_CODEC)
						.codec("eat_sound", PigSounds::getEat, PigSounds::setEat, Sound.NBT_CODEC)
						.codec("hurt_sound", PigSounds::getHurt, PigSounds::setHurt, Sound.NBT_CODEC)
						.codec("step_sound", PigSounds::getStep, PigSounds::setStep, Sound.NBT_CODEC)
						.build();
		
		private Sound
		ambient,
		death,
		eat,
		hurt,
		step;
		
		public Sound getAmbient() {
			return ambient;
		}
		
		public Sound getDeath() {
			return death;
		}
		
		public Sound getEat() {
			return eat;
		}
		
		public Sound getHurt() {
			return hurt;
		}		
		
		public Sound getStep() {
			return step;
		}
		
		public void setAmbient(Sound ambient) {
			this.ambient = ambient;
		}
		
		public void setDeath(Sound death) {
			this.death = death;
		}
		
		public void setEat(Sound growl) {
			this.eat = growl;
		}
		
		public void setHurt(Sound hurt) {
			this.hurt = hurt;
		}
		
		public void setStep(Sound pant) {
			this.step = pant;
		}

		@Override
		public NBTCodec<? extends PigSounds> getNBTCodec() {
			return NBT_CODEC;
		}
		
	}
	
}
