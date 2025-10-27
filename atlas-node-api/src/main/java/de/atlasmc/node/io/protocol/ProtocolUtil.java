package de.atlasmc.node.io.protocol;

import de.atlasmc.io.PacketUtil;
import de.atlasmc.node.FireworkExplosion;
import de.atlasmc.node.FireworkExplosion.Shape;
import de.atlasmc.util.EnumUtil;
import io.netty.buffer.ByteBuf;

public class ProtocolUtil extends PacketUtil {
	
	protected ProtocolUtil() {}
	
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
		explosion.setShape(EnumUtil.getByID(Shape.class, readVarInt(in)));
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
