package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreAgeable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Bamboo;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBamboo extends CoreSapling implements Bamboo {

	protected static final String
	LEAVES = "leaves";
	
	static {
		NBT_FIELDS.setField(LEAVES, (holder, reader) -> {
			if (holder instanceof Bamboo)
			((Bamboo) holder).setLeaves(Leaves.getByName(reader.readStringTag()));
			else reader.skipTag();
		});
	}
	
	private int age;
	private Leaves leaves;
	
	public CoreBamboo(Material material) {
		super(material, 1);
		leaves = Leaves.NONE;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public int getMaxAge() {
		return 1;
	}

	@Override
	public void setAge(int age) {
		if (age > 1 || age < 0) throw new IllegalArgumentException("Age is not between 0 and 1: " + age);
		this.age = age;
	}

	@Override
	public Leaves getLeaves() {
		return leaves;
	}

	@Override
	public void setLeaves(Leaves leaves) {
		if (leaves == null) throw new IllegalArgumentException("Leaves can not be null!");
		this.leaves = leaves;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(CoreAgeable.AGE, getAge());
		writer.writeStringTag(LEAVES, getLeaves().name().toLowerCase());
	}

}
