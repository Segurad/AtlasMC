package de.atlasmc.node.entity;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Pig extends Animal {
	
	public static final NBTCodec<Pig>
	NBT_HANDLER = NBTCodec
					.builder(Pig.class)
					.include(Animal.NBT_HANDLER)
					.boolField("HasSaddle", Pig::hasSaddle, Pig::setSaddle, false)
					.intField("BoostTime", Pig::getBoostTime, Pig::setBoostTime, 0)
					.registryValue("variant", Pig::getVariant, Pig::setVariant, Registries.getRegistry(PigVariant.class))
					.build();
	
	boolean hasSaddle();
	
	int getBoostTime();
	
	void setSaddle(boolean saddle);
	
	void setBoostTime(int time);
	
	PigVariant getVariant();
	
	void setVariant(PigVariant variant);
	
	@RegistryHolder(key = "minecraft:pig_variant", target = Target.PROTOCOL)
	public static class PigVariant extends EntityVariant {
		
		public static final NBTCodec<PigVariant>
		NBT_HANDLER = NBTCodec
						.builder(PigVariant.class)
						.defaultConstructor(PigVariant::new)
						.include(PigVariant.NBT_HANDLER)
						.namespacedKey("asset_id", PigVariant::getAssetID, PigVariant::setAssetID)
						.namespacedKey("model", PigVariant::getModel, PigVariant::setModel)
						.build();
		
		private NamespacedKey assetID;
		private NamespacedKey model;
		
		public PigVariant(NamespacedKey key, NamespacedKey clientKey, int id) {
			super(key, clientKey, id);
		}
		
		public PigVariant(String key, String clientKey, int id) {
			super(key, clientKey, id);
		}
		
		public PigVariant() {
			super();
		}
		
		public NamespacedKey getAssetID() {
			return assetID;
		}
		
		private void setAssetID(NamespacedKey assetID) {
			this.assetID = assetID;
		}
		
		public NamespacedKey getModel() {
			return model;
		}
		
		private void setModel(NamespacedKey model) {
			this.model = model;
		}
		
		@Override
		public NBTCodec<? extends PigVariant> getNBTCodec() {
			return NBT_HANDLER;
		}
		
	}

}
