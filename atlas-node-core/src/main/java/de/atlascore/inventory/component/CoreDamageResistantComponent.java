package de.atlascore.inventory.component;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.DamageType;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.DamageResistantComponent;
import de.atlasmc.tag.Tag;
import de.atlasmc.tag.Tags;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CoreDamageResistantComponent extends AbstractItemComponent implements DamageResistantComponent {

	protected static final NBTFieldSet<CoreDamageResistantComponent> NBT_FIELDS;
	
	protected static final CharKey
	NBT_TYPES = CharKey.literal("types");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_TYPES, (holder, reader) -> {
			String value = reader.readStringTag();
			if (value.charAt(0) != '#')
				return;
			holder.types = Tags.getTag(NamespacedKey.of(value.substring(1)));
		});
	}
	
	private Tag<DamageType> types;
	
	public CoreDamageResistantComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreDamageResistantComponent clone() {
		return (CoreDamageResistantComponent) super.clone();
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		if (types == null)
			return;
		writer.writeCompoundTag(key.toString());
		writer.writeStringTag(NBT_TYPES, "#" + types.getNamespacedKeyRaw());
		writer.writeEndTag();
	}	

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.readNextEntry();
		NBTUtil.readNBT(NBT_FIELDS, this, reader);
	}

	@Override
	public Tag<DamageType> getDamageTypes() {
		return types;
	}

	@Override
	public void setDamageTypes(Tag<DamageType> types) {
		this.types = types;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.DAMAGE_RESISTANT;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		NamespacedKey key = readIdentifier(buf);
		if (key == null)
			return;
		types = Tags.getTag(key);
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		if (types == null)
			writeIdentifier(null, buf);
		else
			writeIdentifier(types.getNamespacedKey(), buf);
	}

}
