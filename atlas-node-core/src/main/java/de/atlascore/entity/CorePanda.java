package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Panda;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;

public class CorePanda extends CoreAgeableMob implements Panda {

	protected static final MetaDataField<Integer>
	META_TIMER_BREED = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Integer>
	META_TIMER_SNEEZE = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Integer>
	META_TIMER_EAT = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+3, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Byte>
	META_GENE_MAIN = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+4, (byte) Gene.NORMAL.getID(), MetaDataType.BYTE);
	protected static final MetaDataField<Byte>
	META_GENE_HIDDEN = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+5, (byte) Gene.NORMAL.getID(), MetaDataType.BYTE);
	
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
		return Gene.getByID(metaContainer.getData(META_GENE_MAIN));
	}

	@Override
	public Gene getHiddenGene() {
		return Gene.getByID(metaContainer.getData(META_GENE_HIDDEN));
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
		metaContainer.get(META_TIMER_BREED).setData(time);
	}

	@Override
	public void setSneezeTimer(int time) {
		metaContainer.get(META_TIMER_SNEEZE).setData(time);	
	}

	@Override
	public void setEatTimer(int time) {
		metaContainer.get(META_TIMER_EAT).setData(time);		
	}

	@Override
	public void setMainGene(Gene gene) {
		if (gene == null)
			throw new IllegalArgumentException("Gene can not be null!");
		metaContainer.get(META_GENE_MAIN).setData((byte) gene.getID());		
	}

	@Override
	public void setHiddenGene(Gene gene) {
		if (gene == null)
			throw new IllegalArgumentException("Gene can not be null!");
		metaContainer.get(META_GENE_HIDDEN).setData((byte) gene.getID());
	}

	@Override
	public void setSneezing(boolean sneezing) {
		MetaData<Byte> data = metaContainer.get(META_PANDA_FLAGS);
		data.setData((byte) (sneezing ? data.getData() | FLAG_IS_SNEEZING : data.getData() & ~FLAG_IS_SNEEZING));
	}

	@Override
	public void setRolling(boolean rolling) {
		MetaData<Byte> data = metaContainer.get(META_PANDA_FLAGS);
		data.setData((byte) (rolling ? data.getData() | FLAG_IS_ROLLING : data.getData() & ~FLAG_IS_ROLLING));
	}

	@Override
	public void setSitting(boolean sitting) {
		MetaData<Byte> data = metaContainer.get(META_PANDA_FLAGS);
		data.setData((byte) (sitting ? data.getData() | FLAG_IS_SITTING : data.getData() & ~FLAG_IS_SITTING));
	}

	@Override
	public void setOnBack(boolean onback) {
		MetaData<Byte> data = metaContainer.get(META_PANDA_FLAGS);
		data.setData((byte) (onback ? data.getData() | FLAG_IS_ON_BACK : data.getData() & ~FLAG_IS_ON_BACK));
	}

}
