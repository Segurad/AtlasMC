package de.atlascore.inventory.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.FireworkExplosion;
import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.FireworksComponent;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CoreFireworksComponent extends AbstractItemComponent implements FireworksComponent {

	protected static final NBTFieldSet<CoreFireworksComponent> NBT_FIELDS;
	
	protected static final CharKey
	NBT_EXPLOSIONS = CharKey.literal("explosions"),
	NBT_FLIGHT_DURATION = CharKey.literal("flight_duration");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_EXPLOSIONS, (holder ,reader) -> {
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				reader.readNextEntry();
				FireworkExplosion explosion = new FireworkExplosion();
				explosion.fromNBT(reader);
				holder.addExplosion(explosion);
			}
			reader.readNextEntry();
		});
		NBT_FIELDS.setField(NBT_FLIGHT_DURATION, (holder, reader) -> {
			holder.flightDuration = reader.readByteTag() & 0xFF;
		});
	}
	
	private List<FireworkExplosion> explosions; 
	private int flightDuration;
	
	public CoreFireworksComponent(NamespacedKey key) {
		super(key);
		flightDuration = 1;
	}
	
	@Override
	public CoreFireworksComponent clone() {
		CoreFireworksComponent clone = (CoreFireworksComponent) super.clone();
		if (explosions != null) {
			clone.explosions = new ArrayList<>();
			final int size = explosions.size();
			for (int i = 0; i < size; i++) {
				FireworkExplosion explosion = explosions.get(i);
				clone.explosions.add(explosion.clone());
			}
		}
		return clone;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeCompoundTag(key.toString());
		if (hasExplosions()) {
			final int size = explosions.size();
			writer.writeListTag(NBT_EXPLOSIONS, TagType.COMPOUND, size);
			for (int i = 0; i < size; i++) {
				FireworkExplosion explosion = explosions.get(i);
				writer.writeCompoundTag();
				explosion.toNBT(writer, systemData);
				writer.writeEndTag();
			}
		}
		if (flightDuration != 1)
			writer.writeByteTag(NBT_FLIGHT_DURATION, flightDuration);
		writer.writeEndTag();
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.readNextEntry();
		NBTUtil.readNBT(NBT_FIELDS, this, reader);
	}

	@Override
	public List<FireworkExplosion> getExplosions() {
		if (explosions == null)
			explosions = new ArrayList<>();
		return explosions;
	}

	@Override
	public boolean hasExplosions() {
		return explosions != null && !explosions.isEmpty();
	}

	@Override
	public void addExplosion(FireworkExplosion explosion) {
		getExplosions().add(explosion);
	}

	@Override
	public void removeExplosions(FireworkExplosion explosion) {
		if (explosions != null)
			explosions.remove(explosion);
	}

	@Override
	public int getFlightDuration() {
		return flightDuration;
	}

	@Override
	public void setFlightDuration(int duration) {
		this.flightDuration = duration;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.FIREWORKS;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		flightDuration = readVarInt(buf);
		final int count = readVarInt(buf);
		if (explosions != null)
			explosions.clear();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				addExplosion(readFireworkExplosion(buf));
			}
		}
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		writeVarInt(flightDuration, buf);
		if (hasExplosions()) {
			final int size = explosions.size();
			writeVarInt(size, buf);
			for (int i = 0; i < size; i++) {
				FireworkExplosion explosion = explosions.get(i);
				writeFireworkExplosion(explosion, buf);
			}
		} else {
			writeVarInt(0, buf);
		}
	}

}
