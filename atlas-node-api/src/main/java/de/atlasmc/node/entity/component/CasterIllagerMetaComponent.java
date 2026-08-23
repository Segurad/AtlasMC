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

public class CasterIllagerMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<CasterIllagerMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(CasterIllagerMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("Spell", CasterIllagerMetaComponent::getSpell, CasterIllagerMetaComponent::setSpell, EnumUtil.enumStringNBTCodec(Spell.class), Spell.NONE) // non standard
					.build();
	
	public static final MetaDataField<Byte>
	META_SPELL = new MetaDataField<>(17, (byte) Spell.NONE.getID(), EntityMetaTypes.BYTE);

	public CasterIllagerMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_SPELL);
	}

	public Spell getSpell() {
		return EnumUtil.getByID(Spell.class, getHolder().getMetaContainer().getData(META_SPELL));
	}

	public void setSpell(Spell spell) {
		getHolder().getMetaContainer().setData(META_SPELL, (byte) spell.getID());
	}
	
	@Override
	public NBTCodec<? extends CasterIllagerMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum Spell implements IDHolder, EnumName {
		
		NONE,
		SUMMON_VEX,
		ATTACK,
		WOLOLO,
		DISAPEAR,
		BLINDNESS;
		
		private final String name;
		
		private Spell() {
			this.name = name().toLowerCase();
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}

}
