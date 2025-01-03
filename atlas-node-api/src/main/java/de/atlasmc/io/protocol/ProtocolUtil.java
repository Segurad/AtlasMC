package de.atlasmc.io.protocol;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.ItemComponent;
import de.atlasmc.io.PacketUtil;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;
import de.atlasmc.sound.EnumSound;
import de.atlasmc.sound.ResourceSound;
import de.atlasmc.sound.Sound;
import io.netty.buffer.ByteBuf;

public class ProtocolUtil extends PacketUtil {
	
	protected ProtocolUtil() {}
	
	/**
	 * Writes the given ItemStack to the given buffer
	 * @param item to write
	 * @param out to write to
	 * @throws IOException
	 */
	public static void writeSlot(ItemStack item, ByteBuf out) throws IOException {
		if (item == null || item.getAmount() == 0) {
			writeVarInt(0, out);
			return;
		}
		writeVarInt(item.getAmount(), out);
		writeVarInt(item.getType().getItemID(), out);
		final int ptrCompCount = out.readerIndex();
		writeVarInt(0, out);
		final int ptrIgnoredCount = out.readerIndex();
		writeVarInt(0, out);
		if (item.hasComponents()) {
			int count = 0;
			Map<NamespacedKey, ItemComponent> components = item.getComponents();
			for (ItemComponent comp : components.values()) {
				if (comp.isServerOnly())
					continue;
				count++;
				writeVarInt(comp.getType().getID(), out);
				comp.write(out);
			}
			if (count > 127) {
				/*
				 * Varints up to 127 only take one byte. To prevent data corruption throw exception
				 */
				throw new IllegalStateException("More than 127 ItemComponent written!");
			} else if (count > 0) {
				final int  ptrEnd = out.writerIndex();
				out.writerIndex(ptrCompCount);
				writeVarInt(count, out);
				out.writerIndex(ptrEnd);
			}
		}
		if (item.hasIgnoredComponents()) {
			Set<ComponentType> ignored = item.getIgnoredComponents();
			for (ComponentType type : ignored)
				writeVarInt(type.getID(), out);
			final int  ptrEnd = out.writerIndex();
			out.writerIndex(ptrIgnoredCount);
			writeVarInt(ignored.size(), out);
			out.writerIndex(ptrEnd);
		}
	}
	
	/**
	 * Reads a ItemStack from the buffer
	 * @param in to read from
	 * @return item or null
	 */
	public static ItemStack readSlot(ByteBuf in) throws IOException {
		final int amount = readVarInt(in);
		if (amount == 0)
			return null;
		Material material = Material.getByItemID(readVarInt(in));
		ItemStack item = new ItemStack(material, amount);
		final int compCount = readVarInt(in);
		final int ignoredCount = readVarInt(in);
		for (int i = 0; i < compCount; i++) {
			ComponentType type = ComponentType.getByID(readVarInt(in));
			ItemComponent comp = item.getComponent(type.getKey());
			comp.read(in);
		}
		for (int i = 0; i < ignoredCount; i++) {
			ComponentType type = ComponentType.getByID(readVarInt(in));
			item.addIgnoredComponent(type);
		}
		return item;
	}

	public static Sound readSound(ByteBuf in) {
		int soundID = readVarInt(in);
		if (soundID > 0) {
			return EnumSound.getByID(soundID-1);
		} else {
			NamespacedKey key = readIdentifier(in);
			float fixedRange = Float.NaN;
			if (in.readBoolean())
				fixedRange = in.readFloat();
			return new ResourceSound(key, fixedRange);
		}
	}
	
	public static void writeSound(Sound sound, ByteBuf out) {
		if (sound instanceof EnumSound enumSound) {
			writeVarInt(enumSound.getID()+1, out);
		} else {
			writeVarInt(0, out);
			writeString(sound.getNamespacedKeyRaw(), out);
			float fixedRange = sound.getFixedRange();
			if (fixedRange != fixedRange) {
				out.writeBoolean(false);
			} else {
				out.writeBoolean(true);
				out.writeFloat(fixedRange);
			}
		}
	}
	
	public static void writePotionEffect(PotionEffect effect, ByteBuf out) {
		writeVarInt(effect.getType().getID(), out);
		writeVarInt(effect.getAmplifier(), out);
		writeVarInt(effect.getDuration(), out);
		out.writeBoolean(effect.hasReducedAmbient());
		out.writeBoolean(effect.hasParticels());
		out.writeBoolean(effect.isShowingIcon());
		out.writeBoolean(false); // hidden details
	}
	
	public static PotionEffect readPotionEffect(ByteBuf in) {
		final int id = readVarInt(in);
		PotionEffectType type = PotionEffectType.getByID(id);
		if (type == null)
			throw new ProtocolException("Unable to find PotionEffectType with id: " + id);
		int amplifier = readVarInt(in);
		int duration = readVarInt(in);
		boolean ambient = in.readBoolean();
		boolean particles = in.readBoolean();
		boolean icon = in.readBoolean();
		while (in.readBoolean())  { // skip hidden details
			readVarInt(in);
			readVarInt(in);
			in.readBoolean();
			in.readBoolean();
		}
		return type.createEffect(amplifier, duration, ambient, particles, icon);
	}
	
}
