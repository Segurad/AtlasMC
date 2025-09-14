package de.atlasmc.node.io.protocol;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.PacketUtil;
import de.atlasmc.io.ProtocolException;
import de.atlasmc.node.FireworkExplosion;
import de.atlasmc.node.FireworkExplosion.Shape;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.ItemType;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.ItemComponent;
import de.atlasmc.node.potion.PotionEffect;
import de.atlasmc.node.potion.PotionEffectType;
import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.node.sound.ResourceSound;
import de.atlasmc.node.sound.Sound;
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
		writeVarInt(item.getType().getID(), out);
		final int ptrCompCount = out.readerIndex();
		writeVarInt(0, out);
		final int ptrIgnoredCount = out.readerIndex();
		writeVarInt(0, out);
		if (item.hasComponents()) {
			int count = 0;
			Map<ComponentType, ItemComponent> components = item.getComponents();
			for (ItemComponent comp : components.values()) {
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
		ItemType itemType = ItemType.getByID(readVarInt(in));
		ItemStack item = new ItemStack(itemType, amount);
		final int compCount = readVarInt(in);
		final int ignoredCount = readVarInt(in);
		for (int i = 0; i < compCount; i++) {
			ComponentType type = ComponentType.getByID(readVarInt(in));
			ItemComponent comp = item.getComponent(type);
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
	
	public static void writeFireworkExplosion(FireworkExplosion explosion, ByteBuf out) {
		writeVarInt(explosion.getShape().getID(), out);
		int[] colors = explosion.getColors();
		if (colors == null) {
			writeVarInt(0, out);
		} else {
			writeVarInt(colors.length, out);
			for (int i : colors)
				out.writeInt(i);
		}
		int[] fadeColors = explosion.getFadeColors();
		if (fadeColors == null) {
			writeVarInt(0, out);
		} else {
			writeVarInt(fadeColors.length, out);
			for (int i : fadeColors)
				out.writeInt(i);
		}
		out.writeBoolean(explosion.hasTrail());
		out.writeBoolean(explosion.hasTwinkel());
	}
	
	public static FireworkExplosion readFireworkExplosion(ByteBuf in) {
		FireworkExplosion explosion = new FireworkExplosion();
		explosion.setShape(Shape.getByID(readVarInt(in)));
		final int colorCount = readVarInt(in);
		if (colorCount > 0) {
			int[] colors = new int[colorCount];
			for (int i = 0; i < colorCount; i++) {
				colors[i] = in.readInt();
			}
			explosion.setColors(colors);
		}
		final int fadeColorCount = readVarInt(in);
		if (fadeColorCount > 0) {
			int[] fadeColors = new int[fadeColorCount];
			for (int i = 0; i < fadeColorCount; i++) {
				fadeColors[i] = in.readInt();
			}
			explosion.setFadeColors(fadeColors);
		}
		explosion.setTrail(in.readBoolean());
		explosion.setTwinkel(in.readBoolean());
		return explosion;
	}
	
}
