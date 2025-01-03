package de.atlascore.inventory.component.effect;

import java.io.IOException;
import java.util.Objects;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.component.effect.RemoveEffects;
import de.atlasmc.potion.PotionEffectType;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.NBTUtil;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CoreRemoveEffects implements RemoveEffects {
	
	protected static final NBTFieldSet<CoreRemoveEffects> NBT_FIELDS;
	
	protected static final CharKey NBT_EFFECTS = CharKey.literal("effects");
	
	static {
		NBT_FIELDS = NBTFieldSet.newSet();
		NBT_FIELDS.setField(NBT_EFFECTS, (holder, reader) -> {
			holder.effects = DataSet.getFromNBT(PotionEffectType.REGISTRY, reader);
		});
		NBT_FIELDS.setField(NBT_TYPE, NBTField.skip());
	}

	private DataSet<PotionEffectType> effects;
	
	public CoreRemoveEffects() {
		effects = DataSet.of();
	}
	
	@Override
	public void apply(Entity target, ItemStack item) {
		if (target instanceof LivingEntity entity && hasEffects()) {
			for (PotionEffectType type : effects.values()) {
				entity.removePotionEffect(type);
			}
		}
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(NBT_TYPE, getType().getNamespacedKeyRaw());
		if (hasEffects())
			DataSet.toNBT(NBT_EFFECTS, effects, writer, systemData);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		NBTUtil.readNBT(NBT_FIELDS, this, reader);
	}

	@Override
	public void read(ByteBuf buf) throws IOException {
		effects = readDataSet(PotionEffectType.REGISTRY, buf);
	}

	@Override
	public void write(ByteBuf buf) throws IOException {
		writeDataSet(effects, PotionEffectType.REGISTRY, buf);
	}

	@Override
	public DataSet<PotionEffectType> getEffects() {
		return effects;
	}

	@Override
	public void setEffects(DataSet<PotionEffectType> effects) {
		if (effects == null)
			this.effects = DataSet.of();
		else
			this.effects = effects;
	}

	@Override
	public boolean hasEffects() {
		return !effects.isEmpty();
	}
	
	@Override
	public CoreRemoveEffects clone() {
		try {
			return (CoreRemoveEffects) super.clone();
		} catch (CloneNotSupportedException e) {
		}
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(effects);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoreRemoveEffects other = (CoreRemoveEffects) obj;
		return Objects.equals(effects, other.effects);
	}

}
