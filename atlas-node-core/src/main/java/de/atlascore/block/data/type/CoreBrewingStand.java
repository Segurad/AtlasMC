package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.BrewingStand;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBrewingStand extends CoreBlockData implements BrewingStand {
	
	protected static final CharKey
	HAS_BOTTLE_0 = CharKey.literal("has_bottle_0"),
	HAS_BOTTLE_1 = CharKey.literal("has_bottle_1"),
	HAS_BOTTLE_2 = CharKey.literal("has_bottle_2");
	
	static {
		NBT_FIELDS.setField(HAS_BOTTLE_0, (holder, reader) -> {
			if (holder instanceof BrewingStand) {
				((BrewingStand) holder).setBottle0(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(HAS_BOTTLE_1, (holder, reader) -> {
			if (holder instanceof BrewingStand) {
				((BrewingStand) holder).setBottle1(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(HAS_BOTTLE_2, (holder, reader) -> {
			if (holder instanceof BrewingStand) {
				((BrewingStand) holder).setBottle2(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	private boolean bottle0;
	private boolean bottle1;
	private boolean bottle2;
	
	public CoreBrewingStand(Material material) {
		super(material);
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				(bottle0?0:1)+
				(bottle1?0:2)+
				(bottle2?0:4);
	}

	@Override
	public boolean hasBottle0() {
		return bottle0;
	}

	@Override
	public boolean hasBottle1() {
		return bottle1;
	}

	@Override
	public boolean hasBottle2() {
		return bottle2;
	}

	@Override
	public void setBottle0(boolean bottle0) {
		this.bottle0 = bottle0;
	}

	@Override
	public void setBottle1(boolean bottle1) {
		this.bottle1 = bottle1;
	}

	@Override
	public void setBottle2(boolean bottle2) {
		this.bottle2 = bottle2;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (hasBottle0()) writer.writeByteTag(HAS_BOTTLE_0, true);
		if (hasBottle1()) writer.writeByteTag(HAS_BOTTLE_1, true);
		if (hasBottle2()) writer.writeByteTag(HAS_BOTTLE_2, true);
	}

}
