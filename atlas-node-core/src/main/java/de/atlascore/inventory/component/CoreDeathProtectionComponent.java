package de.atlascore.inventory.component;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.DeathProtectionComponent;
import de.atlasmc.inventory.component.effect.ComponentEffect;
import de.atlasmc.inventory.component.effect.ComponentEffectType;
import io.netty.buffer.ByteBuf;

public class CoreDeathProtectionComponent extends AbstractItemComponent implements DeathProtectionComponent {
	
	private List<ComponentEffect> effects;
	
	public CoreDeathProtectionComponent(ComponentType type) {
		super(type);
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
	public void read(ByteBuf buf) throws IOException {
		if (effects != null)
			effects.clear();
		final int count = readVarInt(buf);
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				ComponentEffectType type = ComponentEffectType.getByID(readVarInt(buf));
				addEffect(type.createEffect());
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
