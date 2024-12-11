package de.atlasmc.entity.data;

import java.io.IOException;

import de.atlasmc.Color;
import de.atlasmc.Particle;
import de.atlasmc.Particle.DustOptions;
import de.atlasmc.inventory.ItemStack;
import static de.atlasmc.io.protocol.ProtocolUtil.*;
import de.atlasmc.io.protocol.ProtocolUtil;
import de.atlasmc.world.particle.ParticleObject;
import io.netty.buffer.ByteBuf;

public final class ParticleMetaDataType extends MetaDataType<ParticleObject> {

	public ParticleMetaDataType(int type) {
		super(type, ParticleObject.class);
	}

	@Override
	public ParticleObject read(ByteBuf in) {
		Particle p = Particle.getByID(readVarInt(in));
		return new ParticleObject(p, read(p, in));
	}

	public Object read(Particle particle, ByteBuf in) {
		Object data = null;
		switch (particle) {
		case BLOCK:
		case FALLING_DUST:
			data = readVarInt(in);
			break;
		case DUST:
			float r = in.readFloat();
			float g = in.readFloat();
			float b = in.readFloat();
			float scale = in.readFloat();
			data = new DustOptions(new Color(r, g, b), scale);
			break;
		case ITEM:
			try {
				data = ProtocolUtil.readSlot(in);
			} catch (IOException e) {
				throw new IllegalStateException("Error while reading particle meta data!", e);
			}
			break;
		default:
			break;
		}
		return data;
	}
	
	@Override
	public void write(ParticleObject data, ByteBuf out) {
		write(data.getParticle(), data.getData(), false, out);
	}
	
	public void write(Particle particle, Object data, boolean dataOnly, ByteBuf out) {
		if (!dataOnly) 
			writeVarInt(particle.getID(), out);
		switch (particle) {
		case BLOCK:
		case FALLING_DUST: 
			writeVarInt(data != null ? (int) data : 0, out);
			break;
		case DUST:
			if (data != null && data instanceof DustOptions) {
				DustOptions d = (DustOptions) data;
				Color c = d.getColor();
				out.writeFloat(c.r / 255.0f);
				out.writeFloat(c.g / 255.0f);
				out.writeFloat(c.b / 255.0f);
				out.writeFloat(d.getScale());
			}
			out.writeFloat(0);
			out.writeFloat(0);
			out.writeFloat(0);
			out.writeFloat(0);
			break;
		case ITEM:
			try {
				ProtocolUtil.writeSlot((ItemStack) data, out);
			} catch (IOException e) {
				throw new IllegalStateException("Error while writing particle meta data!", e);
			}
			break;
		default: break;
		}
	}
	
	@Override
	public ParticleObject copyData(ParticleObject data) {
		return data.clone();
	}

}
