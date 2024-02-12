package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.PinkPetals;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CorePinkPetals extends CoreDirectional4Faces implements PinkPetals {

	protected static final ChildNBTFieldContainer<CorePinkPetals> NBT_FIELDS;
	
	private static final CharKey FLOWER_AMOUNT = CharKey.literal("flower_amount");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreDirectional4Faces.NBT_FIELDS);
		NBT_FIELDS.setField(FLOWER_AMOUNT, (holder, reader) -> {
			holder.setFlowerAmount(reader.readIntTag());
		});
	}
	
	private int flowerAmount;
	
	public CorePinkPetals(Material material) {
		super(material);
		flowerAmount = 1;
	}
	
	@Override
	public CorePinkPetals clone() {
		return (CorePinkPetals) super.clone();
	}

	@Override
	public int getFlowerAmount() {
		return flowerAmount;
	}

	@Override
	public void setFlowerAmount(int amount) {
		if (amount < 1 || amount > 4)
			throw new IllegalArgumentException("Amount must be between 1 and 4: " + amount);
		this.flowerAmount = amount;
	}

	@Override
	public int getMaxFlowerAmount() {
		return 4;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID() + (flowerAmount-1) + getFaceValue()*4;
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreBlockData> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (flowerAmount != 1)
			writer.writeIntTag(FLOWER_AMOUNT, flowerAmount);
	}

}
