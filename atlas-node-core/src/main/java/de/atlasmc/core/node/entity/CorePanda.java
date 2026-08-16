package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Panda;
import de.atlasmc.node.entity.metadata.MetaData;
import de.atlasmc.node.entity.metadata.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;

public class CorePanda extends CoreAgeableMob implements Panda {

	protected static final MetaDataType<Gene> META_TYPE_GENE = MetaDataType.getByteEnumType(Gene.class);
	
	protected static final MetaDataField<Integer>
	META_TIMER_BREED = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Integer>
	META_TIMER_SNEEZE = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Integer>
	META_TIMER_EAT = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+3, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Gene>
	META_GENE_MAIN = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+4, Gene.NORMAL, META_TYPE_GENE);
	protected static final MetaDataField<Gene>
	META_GENE_HIDDEN = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+5, Gene.NORMAL, META_TYPE_GENE);
	
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
	META_PANDA_FLAGS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+6, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+6;
	
	public CorePanda(EntityType type) {
		super(type);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_TIMER_BREED);
		metaContainer.set(META_TIMER_SNEEZE);
		metaContainer.set(META_TIMER_EAT);
		metaContainer.set(META_GENE_MAIN);
		metaContainer.set(META_GENE_HIDDEN);
		metaContainer.set(META_PANDA_FLAGS);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public int getBreedTimer() {
		return metaContainer.getData(META_TIMER_BREED);
	}

	@Override
	public int getSneezeTimer() {
		return metaContainer.getData(META_TIMER_SNEEZE);
	}

	@Override
	public int getEatTimer() {
		return metaContainer.getData(META_TIMER_EAT);
	}

	@Override
	public Gene getMainGene() {
		return metaContainer.getData(META_GENE_MAIN);
	}

	@Override
	public Gene getHiddenGene() {
		return metaContainer.getData(META_GENE_HIDDEN);
	}

	@Override
	public boolean isSneezing() {
		return (metaContainer.getData(META_PANDA_FLAGS) & FLAG_IS_SNEEZING) == FLAG_IS_SNEEZING;
	}

	@Override
	public boolean isRolling() {
		return (metaContainer.getData(META_PANDA_FLAGS) & FLAG_IS_ROLLING) == FLAG_IS_ROLLING;
	}

	@Override
	public boolean isSitting() {
		return (metaContainer.getData(META_PANDA_FLAGS) & FLAG_IS_SITTING) == FLAG_IS_SITTING;
	}

	@Override
	public boolean isOnBack() {
		return (metaContainer.getData(META_PANDA_FLAGS) & FLAG_IS_ON_BACK) == FLAG_IS_ON_BACK;
	}

	@Override
	public void setBreedTimer(int time) {
		metaContainer.setData(META_TIMER_BREED, time);
	}

	@Override
	public void setSneezeTimer(int time) {
		metaContainer.setData(META_TIMER_SNEEZE, time);	
	}

	@Override
	public void setEatTimer(int time) {
		metaContainer.setData(META_TIMER_EAT, time);		
	}

	@Override
	public void setMainGene(Gene gene) {
		metaContainer.setData(META_GENE_MAIN, gene);
	}

	@Override
	public void setHiddenGene(Gene gene) {
		metaContainer.setData(META_GENE_HIDDEN, gene);
	}
	
	protected void setPandaFlag(int flag, boolean set) {
		MetaData<Byte> data = metaContainer.get(META_PANDA_FLAGS);
		var value = (byte) (set ? data.getData() | flag : data.getData() & ~flag);
		metaContainer.setData(META_PANDA_FLAGS, value);
	}

	@Override
	public void setSneezing(boolean sneezing) {
		setPandaFlag(FLAG_IS_SNEEZING, sneezing);
	}

	@Override
	public void setRolling(boolean rolling) {
		setPandaFlag(FLAG_IS_ROLLING, rolling);
	}

	@Override
	public void setSitting(boolean sitting) {
		setPandaFlag(FLAG_IS_SITTING, sitting);
	}

	@Override
	public void setOnBack(boolean onback) {
		setOnBack(onback);
	}

}
