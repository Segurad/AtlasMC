package de.atlasmc.entity.data;

import java.io.IOException;

import de.atlasmc.Color;
import de.atlasmc.SimpleLocation;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.inventory.ItemStack;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.util.MathUtil;
import de.atlasmc.world.particle.Particle;
import de.atlasmc.world.particle.ParticleObject;
import de.atlasmc.world.particle.ParticleType;
import de.atlasmc.world.particle.ParticleType.DustColor;
import de.atlasmc.world.particle.ParticleType.DustColorTransition;
import de.atlasmc.world.particle.ParticleType.VibrationData;
import io.netty.buffer.ByteBuf;

public final class ParticleMetaDataType extends MetaDataType<Particle> {

	public ParticleMetaDataType(int type) {
		super(type, ParticleObject.class);
	}

	@Override
	public Particle read(ByteBuf in) {
		ParticleType p = ParticleType.getByID(readVarInt(in));
		// TODO read
		return null;
	}

	public Object read(ParticleType particle, ByteBuf in) {
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
			try {
				data = readSlot(in);
			} catch (IOException e) {
				throw new IllegalStateException("Error while reading particle meta data!", e);
			}
			break;
		case VIBRATION: {
			int type = readVarInt(in);
			switch (type) {
			case 0:
				SimpleLocation pos = MathUtil.getLocation(in.readLong());
				int ticks = readVarInt(in);
				data = new VibrationData(pos, ticks);
				break;
			case 1:
				int entityID = readVarInt(in);
				float eyeHeight = readVarInt(in);
				ticks = readVarInt(in);
				data = new VibrationData(entityID, eyeHeight, ticks);
				break;
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
			break;
		}
		return data;
	}
	
	@Override
	public void write(Particle data, ByteBuf out) {
		// TODO write
	}
	
	public void write(ParticleType particle, Object data, boolean dataOnly, ByteBuf out) {
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
				try {
					writeSlot(item, out);
				} catch (IOException e) {
					throw new IllegalStateException("Error while writing particle meta data!", e);
				}
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
			break;
		}
		
	}
	
	@Override
	public Particle copyData(Particle data) {
		return data.clone();
	}

}
