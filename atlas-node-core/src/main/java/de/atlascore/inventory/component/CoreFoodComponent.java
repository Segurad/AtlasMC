package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.FoodComponent;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CoreFoodComponent extends AbstractItemComponent implements FoodComponent  {

	protected static final NBTFieldSet<CoreFoodComponent> NBT_FIELDS;
	
	protected static final CharKey
	NBT_NUTRITION = CharKey.literal("nutrition"),
	NBT_SATURATION = CharKey.literal("saturation"),
	NBT_CAN_ALWAYS_EAT = CharKey.literal("can_always_eat");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_NUTRITION, (holder, reader) -> {
			holder.nutrition = reader.readIntTag();
		});
		NBT_FIELDS.setField(NBT_SATURATION, (holder, reader) -> {
			holder.saturation = reader.readFloatTag();
		});
		NBT_FIELDS.setField(NBT_CAN_ALWAYS_EAT, (holder, reader) -> {
			holder.canAlwaysEat = reader.readBoolean();
		});
	}
	
	private int nutrition;
	private float saturation;
	private boolean canAlwaysEat;
	
	public CoreFoodComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreFoodComponent clone() {
		return (CoreFoodComponent) super.clone();
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeCompoundTag(key.toString());
		if (nutrition != 0)
			writer.writeIntTag(NBT_NUTRITION, nutrition);
		if (saturation != 0.0f)
			writer.writeFloatTag(NBT_SATURATION, saturation);
		if (canAlwaysEat)
			writer.writeByteTag(NBT_CAN_ALWAYS_EAT, canAlwaysEat);
		writer.writeEndTag();
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.readNextEntry();
		NBTUtil.readNBT(NBT_FIELDS, this, reader);
	}

	@Override
	public int getNutrition() {
		return nutrition;
	}

	@Override
	public void setNutrition(int nutrition) {
		this.nutrition = nutrition;
	}

	@Override
	public float getSaturation() {
		return saturation;
	}

	@Override
	public void setSaturation(float saturation) {
		this.saturation = saturation;
	}

	@Override
	public boolean isAlwaysEatable() {
		return canAlwaysEat;
	}

	@Override
	public void setAlwaysEatable(boolean eatable) {
		this.canAlwaysEat = eatable;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.FOOD;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		nutrition = readVarInt(buf);
		saturation = buf.readFloat();
		canAlwaysEat = buf.readBoolean();
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		writeVarInt(nutrition, buf);
		buf.writeFloat(saturation);
		buf.writeBoolean(canAlwaysEat);
	}

}
