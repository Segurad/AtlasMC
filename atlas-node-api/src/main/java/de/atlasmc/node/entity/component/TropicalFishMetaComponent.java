package de.atlasmc.node.entity.component;

import de.atlasmc.IDHolder;
import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaData;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.DyeColor;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumUtil;

public class TropicalFishMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<TropicalFishMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(TropicalFishMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.intField("Variant", TropicalFishMetaComponent::getVariantID, TropicalFishMetaComponent::setVariantID, 0)
					.build();
	
	/**
	 * 0xFF000000 - Pattern Color<br>
	 * 0x00FF0000 - Base Color<br>
	 * 0x0000FF00 - Pattern<br>
	 * 0x000000FF - Size<br>
	 */
	public static final MetaDataField<Integer>
	META_TROPICAL_VARIANT = new MetaDataField<>(17, 0, EntityMetaTypes.VAR_INT);
	
	public TropicalFishMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_TROPICAL_VARIANT);
	}

	public Pattern getPattern() {
		return Pattern.getByDataID(getHolder().getMetaContainer().getData(META_TROPICAL_VARIANT));
	}

	public void setPattern(Pattern pattern) {
		var container = getHolder().getMetaContainer();
		MetaData<Integer> data = container.get(META_TROPICAL_VARIANT);
		container.setData(META_TROPICAL_VARIANT, (data.getData() & 0xFFFF) | pattern.getDataID());
	}

	public DyeColor getBaseColor() {
		return EnumUtil.getByID(DyeColor.class, ((getHolder().getMetaContainer().getData(META_TROPICAL_VARIANT) >> 16) & 0xFF));
	}

	public void setBaseColor(DyeColor color) {
		var container = getHolder().getMetaContainer();
		MetaData<Integer> data = container.get(META_TROPICAL_VARIANT);
		container.setData(META_TROPICAL_VARIANT, (data.getData() & 0xFF0000) | (color.getID() << 16));
	}

	public DyeColor getPatternColor() {
		return EnumUtil.getByID(DyeColor.class, ((getHolder().getMetaContainer().getData(META_TROPICAL_VARIANT) >> 24) & 0xFF));
	}

	public void setPatternColor(DyeColor color) {
		var container = getHolder().getMetaContainer();
		MetaData<Integer> data = container.get(META_TROPICAL_VARIANT);
		container.setData(META_TROPICAL_VARIANT, (data.getData() & 0xFF000000) | (color.getID() << 24));
	}

	public int getVariantID() {
		return getHolder().getMetaContainer().getData(META_TROPICAL_VARIANT);
	}

	public void setVariantID(int id) {
		getHolder().getMetaContainer().setData(META_TROPICAL_VARIANT, id);
	}
	
	@Override
	public NBTCodec<? extends TropicalFishMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum Pattern implements IDHolder {
		KOB(0, false),
		FLOPPER(0, true),
		SUNSTREAK(1, false),
		STRIPEY(1, true),
		SNOPPER(2, false),
		GLITTER(2, true),
		DASHER(3, false),
		BLOCKFISH(3, true),
		BRINELY(4, false),
		BETTY(4, true),
		SPOTTY(5, false),
		CLAYFISH(5, true);

		private final int variant;
		private final boolean large;
		
		private Pattern(int variant, boolean large) {
			this.variant = variant;
			this.large = large;
		}
		
		public int getDataID() {
			return (large?0:1) | variant<<8;
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		public static Pattern getByDataID(int id) {
			int index = (id & 0xFF) | (id & 0xFF00) >> 7;
			if (index < 0 && index > 12) throw new IllegalArgumentException("Invalid DataID: " + id);
			return EnumUtil.getByID(Pattern.class, index);
		}
		
	}
	
}
