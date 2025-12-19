package de.atlasmc.node.entity.metadata.type;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.Color;
import de.atlasmc.node.Location;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.util.MathUtil;
import de.atlasmc.node.world.particle.Particle;
import de.atlasmc.node.world.particle.ParticleType;
import de.atlasmc.node.world.particle.ParticleType.VibrationData;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.enums.EnumUtil;
import io.netty.buffer.ByteBuf;

public final class ParticleMetaType extends MetaDataType<Particle> {

	public ParticleMetaType(int type) {
		super(type, Particle.class);
	}

	@Override
	public Particle read(ByteBuf in, CodecContext context) {
		ParticleType p = EnumUtil.getByID(ParticleType.class, readVarInt(in));
		// TODO read
		return null;
	}

	public Object read(ParticleType particle, ByteBuf in, CodecContext context) throws IOException {
		Object data = null;
		switch (particle) {
		case BLOCK:
		case FALLING_DUST:
		case BLOCK_MARKER:
		case DUST_PILLAR:
			data = readVarInt(in);
			break;
		case DUST: {
			int rgb = readVarInt(in);
			float scale = in.readFloat();
			data = new DustColor(Color.fromRGB(rgb), scale);
			break;
		}
		case DUST_COLOR_TRANSITION: {
			int rgbFrom = readVarInt(in);
			int rgbTo = readVarInt(in);
			float scale = in.readFloat();
			data = new DustColorTransition(Color.fromRGB(rgbFrom), Color.fromRGB(rgbTo), scale);
			break;
		}
		case ENTITY_EFFECT:
			data = Color.fromRGB(readVarInt(in));
			break;
		case ITEM:
			data = ItemStack.STREAM_CODEC.deserialize(in, context);
			break;
		case VIBRATION: {
			int type = readVarInt(in);
			switch (type) {
			case 0:
				Location pos = MathUtil.getLocation(in.readLong());
				int ticks = readVarInt(in);
				data = new VibrationData(pos, ticks);
				break;
			case 1:
				int entityID = readVarInt(in);
				float eyeHeight = readVarInt(in);
				ticks = readVarInt(in);
				data = new VibrationData(entityID, eyeHeight, ticks);
				break;
			default:
				throw new IllegalArgumentException("Unknown type: " + type);
			}
			break;
		}
		case SHRIEK:
			data = readVarInt(in);
			break;
		case SCULK_CHARGE:
			data = in.readFloat();
			break;
		default:
			break; // no data for other types
		}
		return data;
	}
	
	@Override
	public void write(Particle data, ByteBuf out, CodecContext context) {
		// TODO write
	}
	
	public void write(ParticleType particle, Object data, boolean dataOnly, ByteBuf out, CodecContext context) throws IOException {
		if (!dataOnly) 
			writeVarInt(particle.getID(), out);
		switch (particle) {
		case BLOCK:
		case FALLING_DUST:
		case BLOCK_MARKER:
		case DUST_PILLAR:
			if (data instanceof BlockData block) {
				writeVarInt(block.getStateID(), out);
			} else if (data instanceof Integer raw) {
				writeVarInt(raw, out);
			} else if (data != null) {
				throw new IllegalArgumentException("Invalid data type: " + data.getClass().getName());
			} else {
				throw new IllegalArgumentException("Data can not be null!");
			}
			break;
		case DUST:
			if (data instanceof DustColor color) {
				writeVarInt(color.getColor().asRGB(), out);
				out.writeFloat(color.getScale());
			} else if (data != null) {
				throw new IllegalArgumentException("Invalid data type: " + data.getClass().getName());
			} else {
				throw new IllegalArgumentException("Data can not be null!");
			}
			break;
		case DUST_COLOR_TRANSITION:
			if (data instanceof DustColorTransition color) {
				writeVarInt(color.getColorFrom().asRGB(), out);
				writeVarInt(color.getColorTo().asRGB(), out);
				out.writeFloat(color.getScale());
			} else if (data != null) {
				throw new IllegalArgumentException("Invalid data type: " + data.getClass().getName());
			} else {
				throw new IllegalArgumentException("Data can not be null!");
			}
			break;
		case ENTITY_EFFECT:
			if (data instanceof Color color) {
				writeVarInt(color.asRGB(), out);
			} else if (data != null) {
				throw new IllegalArgumentException("Invalid data type: " + data.getClass().getName());
			} else {
				throw new IllegalArgumentException("Data can not be null!");
			}
			break;
		case ITEM:
			if (data instanceof ItemStack item) {
				ItemStack.STREAM_CODEC.serialize(item, out, context);
			} else if (data != null) {
				throw new IllegalArgumentException("Invalid data type: " + data.getClass().getName());
			} else {
				throw new IllegalArgumentException("Data can not be null!");
			}
			break;
		case VIBRATION:
			if (data instanceof VibrationData vibration) {
				if (vibration.isEntity()) {
					writeVarInt(vibration.getSourceEntity(), out);
					out.writeFloat(vibration.getEyeHeight());
				} else {
					out.writeLong(MathUtil.toPosition(vibration.getSourceLocation()));
				}
				writeVarInt(vibration.getTravelTicks(), out);
			} else if (data != null) {
				throw new IllegalArgumentException("Invalid data type: " + data.getClass().getName());
			} else {
				throw new IllegalArgumentException("Data can not be null!");
			}
			break;
		case SHRIEK:
			if (data instanceof Integer delay) {
				writeVarInt(delay, out);
			} else if (data != null) {
				throw new IllegalArgumentException("Invalid data type: " + data.getClass().getName());
			} else {
				throw new IllegalArgumentException("Data can not be null!");
			}
			break;
		case SCULK_CHARGE:
			if (data instanceof Float roll) {
				out.writeFloat(roll);
			} else if (data != null) {
				throw new IllegalArgumentException("Invalid data type: " + data.getClass().getName());
			} else {
				throw new IllegalArgumentException("Data can not be null!");
			}
			break;
		default: 
			break; // no data for other types
		}
		
	}
	
	@Override
	public Particle copyData(Particle data) {
		return data.clone();
	}

}
