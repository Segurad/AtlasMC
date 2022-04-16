package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Panda;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CorePanda extends CoreAgeableMob implements Panda {

	protected static final MetaDataField<Integer>
	META_TIMER_BREED = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+1, 0, MetaDataType.INT);
	protected static final MetaDataField<Integer>
	META_TIMER_SNEEZE = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+2, 0, MetaDataType.INT);
	protected static final MetaDataField<Integer>
	META_TIMER_EAT = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+3, 0, MetaDataType.INT);
	protected static final MetaDataField<Byte>
	META_GENE_MAIN = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+4, (byte) 0, MetaDataType.BYTE);
	protected static final MetaDataField<Byte>
	META_GENE_HIDDEN = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+5, (byte) 0, MetaDataType.BYTE);
	/**
	 * 0x02 - Is Sneezing<br>
	 * 0x04 - Is rolling<br>
	 * 0x08 - Is sitting<br>
	 * 0x10 - Is on back<br>
	 */
	protected static final MetaDataField<Byte>
	META_PANDA_FLAGS = new MetaDataField<>(CoreAgeableMob.LAST_META_INDEX+6, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreAgeableMob.LAST_META_INDEX+6;
	
	protected static final String
	NBT_MAIN_GENE = "MainGene",
	NBT_HIDDEN_GENE = "HiddenGene";
	
	static {
		NBT_FIELDS.setField(NBT_MAIN_GENE, (holder, reader) -> {
			if (holder instanceof Panda) {
				((Panda) holder).setMainGene(Gene.getByNameID(reader.readStringTag()));
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_HIDDEN_GENE, (holder, reader) -> {
			if (holder instanceof Panda) {
				((Panda) holder).setHiddenGene(Gene.getByNameID(reader.readStringTag()));
			} else reader.skipTag();
		});
	}
	
	public CorePanda(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
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
		return (metaContainer.getData(META_PANDA_FLAGS) & 0x02) == 0x02;
	}

	@Override
	public boolean isRolling() {
		return (metaContainer.getData(META_PANDA_FLAGS) & 0x04) == 0x04;
	}

	@Override
	public boolean isSitting() {
		return (metaContainer.getData(META_PANDA_FLAGS) & 0x08) == 0x08;
	}

	@Override
	public boolean isOnBack() {
		return (metaContainer.getData(META_PANDA_FLAGS) & 0x10) == 0x10;
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
		data.setData((byte) (sneezing ? data.getData() | 0x02 : data.getData() & 0xFD));
	}

	@Override
	public void setRolling(boolean rolling) {
		MetaData<Byte> data = metaContainer.get(META_PANDA_FLAGS);
		data.setData((byte) (rolling ? data.getData() | 0x04 : data.getData() & 0xFB));
	}

	@Override
	public void setSitting(boolean sitting) {
		MetaData<Byte> data = metaContainer.get(META_PANDA_FLAGS);
		data.setData((byte) (sitting ? data.getData() | 0x08 : data.getData() & 0xF7));
	}

	@Override
	public void setOnBack(boolean onback) {
		MetaData<Byte> data = metaContainer.get(META_PANDA_FLAGS);
		data.setData((byte) (onback ? data.getData() | 0x10 : data.getData() & 0xEF));
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeStringTag(NBT_MAIN_GENE, getMainGene().getNameID());
		writer.writeStringTag(NBT_HIDDEN_GENE, getHiddenGene().getNameID());
	}

}
