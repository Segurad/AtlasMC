package de.atlascore.inventory.component;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.enchantments.Enchantment;
import de.atlasmc.inventory.component.AbstractEnchantmentComponent;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

public class CoreAbstractEnchantmentComponent extends CoreAbstractTooltipComponent implements AbstractEnchantmentComponent {
	
	private Object2IntMap<Enchantment> enchantments;
	private boolean showInTooltip;
	
	public CoreAbstractEnchantmentComponent(NamespacedKey key) {
		super(key);
		this.showInTooltip = true;
	}
	
	@Override
	public CoreAbstractEnchantmentComponent clone() {
		CoreAbstractEnchantmentComponent clone = (CoreAbstractEnchantmentComponent) super.clone();
		if (enchantments != null)
			clone.enchantments = new Object2IntOpenHashMap<>(enchantments);
		return clone;
	}

	@Override
	public boolean isShowTooltip() {
		return showInTooltip;
	}

	@Override
	public void setShowTooltip(boolean show) {
		this.showInTooltip = show;
	}

	@Override
	public void addEnchant(Enchantment ench, int level) {
		if (ench == null)
			throw new IllegalArgumentException("Enchantment can not be null!");
		getStoredEnchants().put(ench, level);
	}

	@Override
	public int getEnchantLevel(Enchantment ench) {
		if (enchantments == null)
			return 0;
		return enchantments.getInt(ench);
	}

	@Override
	public Object2IntMap<Enchantment> getStoredEnchants() {
		if (enchantments == null)
			enchantments = new Object2IntOpenHashMap<>();
		return enchantments;
	}

	@Override
	public boolean hasConflictingEnchant(Enchantment ench) {
		// TODO conflicts
		return false;
	}

	@Override
	public boolean hasEnchants() {
		return enchantments != null && !enchantments.isEmpty();
	}

	@Override
	public boolean hasEnchant(Enchantment ench) {
		return enchantments != null ? enchantments.containsKey(ench) : false;
	}

	@Override
	public void removeEnchant(Enchantment ench) {
		if (enchantments == null)
			return;
		enchantments.removeInt(ench);
	}
	
	@Override
	public void write(ByteBuf buf) throws IOException {
		if (hasEnchants()) {
			final int size = enchantments.size();
			writeVarInt(size, buf);
			for (Entry<Enchantment> entry : enchantments.object2IntEntrySet()) {
				writeVarInt(entry.getKey().getID(), buf);
				writeVarInt(entry.getIntValue(), buf);
			}
		} else {
			writeVarInt(0, buf);
		}
		buf.writeBoolean(showInTooltip);
	}
	
	@Override
	public void read(ByteBuf buf) throws IOException {
		if (enchantments != null)
			enchantments.clear();
		final int count = readVarInt(buf);
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				int id = readVarInt(buf);
				int level = readVarInt(buf);
				Enchantment ench = Enchantment.getByID(id);
				if (ench == null)
					continue;
				addEnchant(ench, level);
			}
		}
		showInTooltip = buf.readBoolean();
	}

}
