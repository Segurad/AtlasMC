package de.atlasmc.core.node.inventory.component;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.node.block.tile.Beehive.Occupant;
import de.atlasmc.node.entity.Bee;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.BeesComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;

public class CoreBeesComponent extends AbstractItemComponent implements BeesComponent {
	
	private static final int DEFAULT_BEES_SIZE = 5;
	
	private List<Occupant> bees;
	
	public CoreBeesComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreBeesComponent clone() {
		CoreBeesComponent clone = (CoreBeesComponent) super.clone();
		if (clone == null)
			return null;
		return clone;
	}
	
	@Override
	public boolean hasBees() {
		return bees != null && !bees.isEmpty();
	}

	@Override
	public List<Occupant> getBees() {
		if (bees == null)
			bees = new ArrayList<>(DEFAULT_BEES_SIZE);
		return bees;
	}

	@Override
	public void removeBee(Bee bee) {
		if (!hasBees())
			return;
		final List<Occupant> list = bees;
		final int size = bees.size();
		for (int i = 0; i < size; i++) {
			Occupant o = list.get(i);
			if (o.getBee().equals(bee)) {
				list.remove(i);
				return;
			}
		}
	}

	@Override
	public void addBee(Bee bee) {
		getBees().add(new Occupant(bee));
	}

	@Override
	public int getBeeCount() {
		return bees == null ? 0 : bees.size();
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		final int count = readVarInt(buf);
		if (count == 0) {
			return;
		}
		List<Occupant> bees = getBees();
		bees.clear();
		NBTReader reader = new NBTNIOReader(buf, true);
		for (int i = 0; i < count; i++) {
			NBTCodec<? extends Bee> handler = Bee.NBT_HANDLER;
			Bee bee = handler.deserialize(reader);
			int ticksInHive = readVarInt(buf);
			int minTicksInHive = readVarInt(buf);
			bees.add(new Occupant(bee, minTicksInHive, ticksInHive));
		}
		reader.close();
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		if (bees == null || bees.isEmpty()) {
			writeVarInt(0, buf);
			return;
		}
		List<Occupant> bees = getBees();
		final int count = bees.size();
		writeVarInt(count, buf);
		NBTWriter writer = new NBTNIOWriter(buf, true);
		for (int i = 0; i < count; i++) {
			writer.writeCompoundTag();
			Occupant occupant = bees.get(i);
			occupant.writeToNBT(writer, CodecContext.DEFAULT_CLIENT);
			writer.writeEndTag();
			writeVarInt(occupant.getTicksInHive(), buf);
			writeVarInt(occupant.getMinTicksInHive(), buf);
		}
		writer.close();
	}

}
