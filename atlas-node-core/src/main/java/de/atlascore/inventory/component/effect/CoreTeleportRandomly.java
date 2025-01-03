package de.atlascore.inventory.component.effect;

import java.io.IOException;
import java.util.Objects;

import de.atlasmc.entity.Entity;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.component.effect.TeleportRandomly;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;

public class CoreTeleportRandomly implements TeleportRandomly {

	protected static final NBTFieldSet<CoreTeleportRandomly> NBT_FIELDS;
	
	protected static final CharKey NBT_DIAMETER = CharKey.literal("diameter");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_DIAMETER, (holder, reader) -> {
			holder.diameter = reader.readFloatTag();
		});
		NBT_FIELDS.setField(NBT_TYPE, NBTField.skip());
	}
	
	private float diameter = 16.0f;
	
	@Override
	public void apply(Entity target, ItemStack item) {
		// TODO teleport random
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(NBT_TYPE, getType().getNamespacedKeyRaw());
		if (diameter != 16.0f)
			writer.writeFloatTag(NBT_DIAMETER, diameter);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		NBTUtil.readNBT(NBT_FIELDS, this, reader);
	}

	@Override
	public void read(ByteBuf buf) throws IOException {
		diameter = buf.readFloat();
	}

	@Override
	public void write(ByteBuf buf) throws IOException {
		buf.writeFloat(diameter);
	}

	@Override
	public float getDiameter() {
		return diameter;
	}

	@Override
	public void setDiameter(float diameter) {
		this.diameter = diameter;
	}
	
	@Override
	public CoreTeleportRandomly clone() {
		try {
			return (CoreTeleportRandomly) super.clone();
		} catch (CloneNotSupportedException e) {}
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(diameter);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoreTeleportRandomly other = (CoreTeleportRandomly) obj;
		return Float.floatToIntBits(diameter) == Float.floatToIntBits(other.diameter);
	}

}
