package de.atlasmc.node.entity.component;

import de.atlasmc.IDHolder;
import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public class PaintingMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<PaintingMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(PaintingMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("motive", PaintingMetaComponent::getMotive, PaintingMetaComponent::setMotive, EnumUtil.enumStringNBTCodec(Motive.class), Motive.KEBAB)
					.build();
	
	public static final MetaDataField<Motive> 
	META_MOTIVE = new MetaDataField<>(9, Motive.KEBAB, EntityMetaTypes.PAINTING_VARIANT);
	
	public PaintingMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_MOTIVE);
	}

	public Motive getMotive() {
		return getHolder().getMetaContainer().getData(META_MOTIVE);
	}

	public void setMotive(Motive motive) {
		getHolder().getMetaContainer().setData(META_MOTIVE, motive);
	}
	
	@Override
	public NBTCodec<? extends PaintingMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum Motive implements IDHolder, EnumName {
		
		KEBAB,
		AZTEC,
		ALBAN,
		AZTEC2,
		BOMB,
		PLANT,
		WASTELAND,
		POOL,
		COURBET,
		SEA,
		SUNSET,
		CREEBET,
		WANDERER,
		GRAHAM,
		MATCH,
		BUST,
		STAGE,
		VOID,
		SKULL_AND_ROSES,
		WITHER,
		FIGHTERS,
		POINTER,
		PIGSCENE,
		BURNING_SKULL,
		SKELETON,
		EARTH,
		WIND,
		WATER,
		FIRE,
		DONKEY_KONG;

		private final String name;
		
		private Motive() {
			String name = "minecraft:".concat(name().toLowerCase());
			this.name = name.intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}
	
}
