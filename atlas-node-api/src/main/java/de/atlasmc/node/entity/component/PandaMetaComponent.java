package de.atlasmc.node.entity.component;

import de.atlasmc.IDHolder;
import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaData;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;

public class PandaMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<PandaMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(PandaMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("HiddenGene", PandaMetaComponent::getHiddenGene, PandaMetaComponent::setHiddenGene, EnumUtil.enumStringNBTCodec(Gene.class), Gene.NORMAL)
					.codec("MainGene", PandaMetaComponent::getMainGene, PandaMetaComponent::setMainGene, EnumUtil.enumStringNBTCodec(Gene.class), Gene.NORMAL)
					// non standard by atlas
					.boolField("IsSneezing", PandaMetaComponent::isSneezing, PandaMetaComponent::setSneezing, false)
					.boolField("IsRolling", PandaMetaComponent::isRolling, PandaMetaComponent::setRolling, false)
					.boolField("IsSitting", PandaMetaComponent::isSitting, PandaMetaComponent::setSitting, false)
					.boolField("IsOnBack", PandaMetaComponent::isOnBack, PandaMetaComponent::setOnBack, false)
					.build();
	
	protected static final MetaDataField<Integer>
	META_TIMER_BREED = new MetaDataField<>(18, 0, EntityMetaTypes.VAR_INT);
	protected static final MetaDataField<Integer>
	META_TIMER_SNEEZE = new MetaDataField<>(19, 0, EntityMetaTypes.VAR_INT);
	protected static final MetaDataField<Integer>
	META_TIMER_EAT = new MetaDataField<>(20, 0, EntityMetaTypes.VAR_INT);
	protected static final MetaDataField<Gene>
	META_GENE_MAIN = new MetaDataField<>(21, Gene.NORMAL, EntityMetaTypes.PANDA_GENE);
	protected static final MetaDataField<Gene>
	META_GENE_HIDDEN = new MetaDataField<>(22, Gene.NORMAL, EntityMetaTypes.PANDA_GENE);
	
	protected static final int
	FLAG_IS_SNEEZING = 0x02,
	FLAG_IS_ROLLING = 0x04,
	FLAG_IS_SITTING = 0x08,
	FLAG_IS_ON_BACK = 0x10;
	
	/**
	 * 0x02 - Is Sneezing<br>
	 * 0x04 - Is rolling<br>
	 * 0x08 - Is sitting<br>
	 * 0x10 - Is on back<br>
	 */
	protected static final MetaDataField<Byte>
	META_PANDA_FLAGS = new MetaDataField<>(23, (byte) 0, EntityMetaTypes.BYTE);

	public PandaMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_TIMER_BREED);
		container.set(META_TIMER_SNEEZE);
		container.set(META_TIMER_EAT);
		container.set(META_GENE_MAIN);
		container.set(META_GENE_HIDDEN);
		container.set(META_PANDA_FLAGS);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 6;
	}
	
	public int getBreedTimer() {
		return getHolder().getMetaContainer().getData(META_TIMER_BREED);
	}

	public int getSneezeTimer() {
		return getHolder().getMetaContainer().getData(META_TIMER_SNEEZE);
	}

	public int getEatTimer() {
		return getHolder().getMetaContainer().getData(META_TIMER_EAT);
	}

	public Gene getMainGene() {
		return getHolder().getMetaContainer().getData(META_GENE_MAIN);
	}

	public Gene getHiddenGene() {
		return getHolder().getMetaContainer().getData(META_GENE_HIDDEN);
	}

	public boolean isSneezing() {
		return (getHolder().getMetaContainer().getData(META_PANDA_FLAGS) & FLAG_IS_SNEEZING) == FLAG_IS_SNEEZING;
	}

	public boolean isRolling() {
		return (getHolder().getMetaContainer().getData(META_PANDA_FLAGS) & FLAG_IS_ROLLING) == FLAG_IS_ROLLING;
	}

	public boolean isSitting() {
		return (getHolder().getMetaContainer().getData(META_PANDA_FLAGS) & FLAG_IS_SITTING) == FLAG_IS_SITTING;
	}

	public boolean isOnBack() {
		return (getHolder().getMetaContainer().getData(META_PANDA_FLAGS) & FLAG_IS_ON_BACK) == FLAG_IS_ON_BACK;
	}

	public void setBreedTimer(int time) {
		getHolder().getMetaContainer().setData(META_TIMER_BREED, time);
	}

	public void setSneezeTimer(int time) {
		getHolder().getMetaContainer().setData(META_TIMER_SNEEZE, time);	
	}

	public void setEatTimer(int time) {
		getHolder().getMetaContainer().setData(META_TIMER_EAT, time);		
	}

	public void setMainGene(Gene gene) {
		getHolder().getMetaContainer().setData(META_GENE_MAIN, gene);
	}

	public void setHiddenGene(Gene gene) {
		getHolder().getMetaContainer().setData(META_GENE_HIDDEN, gene);
	}
	
	protected void setPandaFlag(int flag, boolean set) {
		var container = getHolder().getMetaContainer();
		MetaData<Byte> data = container.get(META_PANDA_FLAGS);
		var value = (byte) (set ? data.getData() | flag : data.getData() & ~flag);
		container.setData(META_PANDA_FLAGS, value);
	}

	public void setSneezing(boolean sneezing) {
		setPandaFlag(FLAG_IS_SNEEZING, sneezing);
	}

	public void setRolling(boolean rolling) {
		setPandaFlag(FLAG_IS_ROLLING, rolling);
	}

	public void setSitting(boolean sitting) {
		setPandaFlag(FLAG_IS_SITTING, sitting);
	}

	public void setOnBack(boolean onback) {
		setOnBack(onback);
	}
	
	@Override
	public NBTCodec<? extends PandaMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	public static enum Gene implements IDHolder, EnumName {
		
		NORMAL(false),
		LAZY(false),
		WORRIED(false),
		PLAYFUL(false),
		BROWN(true),
		WEAK(true),
		AGGRESSIVE(false);
		
		private final boolean receccive;
		private final String name;
		
		private Gene(boolean receccive) {
			this.receccive = receccive;
			this.name = name().toLowerCase();
		}
		
		public boolean isRececcive() {
			return receccive;
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
