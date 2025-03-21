package de.atlascore.inventory.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.DeathProtectionComponent;
import de.atlasmc.inventory.component.effect.ComponentEffect;
import de.atlasmc.inventory.component.effect.ComponentEffectFactory;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CoreDeathProtectionComponent extends AbstractItemComponent implements DeathProtectionComponent {

	protected static final NBTFieldSet<CoreDeathProtectionComponent> NBT_FIELDS;
	
	protected static final CharKey
	NBT_DEATH_EFFECTS = CharKey.literal("death_effects");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_DEATH_EFFECTS, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				reader.readNextEntry();
				ComponentEffect effect = ComponentEffect.getFromNBT(reader);
				if (effect == null)
					continue;
				holder.addEffect(effect);
			}
			reader.readNextEntry();
		});
	}
	
	private List<ComponentEffect> effects;
	
	public CoreDeathProtectionComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreDeathProtectionComponent clone() {
		CoreDeathProtectionComponent clone = (CoreDeathProtectionComponent) super.clone();
		if (effects != null) {
			clone.effects = new ArrayList<>();
			final int size = effects.size();
			for (int i = 0; i < size; i++) {
				ComponentEffect effect = effects.get(i);
				clone.effects.add(effect.clone());
			}
		}
		return clone;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeCompoundTag(key.toString());
		if (hasEffects()) {
			final int size = effects.size();
			writer.writeListTag(null, null, size);
		}
		writer.writeEndTag();
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.readNextEntry();
		NBTUtil.readNBT(NBT_FIELDS, this, reader);
	}

	@Override
	public List<ComponentEffect> getEffects() {
		if (effects == null)
			effects = new ArrayList<>();
		return effects;
	}

	@Override
	public boolean hasEffects() {
		return effects != null && !effects.isEmpty();
	}

	@Override
	public void addEffect(ComponentEffect effect) {
		getEffects().add(effect);
	}

	@Override
	public void removeEffect(ComponentEffect effect) {
		if (effects == null)
			return;
		effects.remove(effect);
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.DEATH_PROTECTION;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		if (effects != null)
			effects.clear();
		final int count = readVarInt(buf);
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				ComponentType type = ComponentType.getByID(readVarInt(buf));
				ComponentEffectFactory factory = ComponentEffectFactory.REGISTRY.get(type.getKey());
				ComponentEffect effect = factory.createEffect();
				addEffect(effect);
			}
		}
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		if (hasEffects()) {
			final int size = effects.size();
			writeVarInt(size, buf);
			for (int i = 0; i < size; i++) {
				ComponentEffect effect = effects.get(i);
				writeVarInt(effect.getType().getID(), buf);
				effect.write(buf);
			}
		} else {
			writeVarInt(0, buf);
		}
	}

}
