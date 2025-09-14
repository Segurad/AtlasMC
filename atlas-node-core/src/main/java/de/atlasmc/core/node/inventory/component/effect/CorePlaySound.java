package de.atlasmc.core.node.inventory.component.effect;

import static de.atlasmc.node.io.protocol.ProtocolUtil.readSound;
import static de.atlasmc.node.io.protocol.ProtocolUtil.writeSound;

import java.io.IOException;
import java.util.Objects;

import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.component.effect.ComponentEffectType;
import de.atlasmc.node.inventory.component.effect.PlaySound;
import de.atlasmc.node.sound.Sound;
import io.netty.buffer.ByteBuf;

public class CorePlaySound extends CoreAbstractEffect implements PlaySound {

	private Sound sound;
	
	public CorePlaySound(ComponentEffectType type) {
		super(type);
	}
	
	@Override
	public void apply(Entity target, ItemStack item) {
		target.causeSound(sound);
	}

	@Override
	public void read(ByteBuf buf) throws IOException {
		sound = readSound(buf);
	}

	@Override
	public void write(ByteBuf buf) throws IOException {
		writeSound(sound, buf);
	}

	@Override
	public Sound getSound() {
		return sound;
	}

	@Override
	public void setSound(Sound sound) {
		this.sound = sound;
	}
	
	@Override
	public CorePlaySound clone() {
		return (CorePlaySound) super.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(sound);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CorePlaySound other = (CorePlaySound) obj;
		return Objects.equals(sound, other.sound);
	}

}
