package de.atlasmc.node.entity.metadata.type;

import java.io.IOException;

import de.atlasmc.nbt.io.NBTNIOReader;
import de.atlasmc.nbt.io.NBTNIOWriter;
import de.atlasmc.nbt.tag.CompoundTag;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

final class NBTMetaType extends MetaDataType<CompoundTag> {

    public NBTMetaType(int type) {
		super(type, CompoundTag.class);
	}

	@Override
    public CompoundTag read(ByteBuf in, CodecContext context) {
        NBTNIOReader reader = null;
        CompoundTag tag = null;
        try {
            reader = new NBTNIOReader(in, true);
            tag = (CompoundTag) reader.readNBT();
        } catch (IOException e) {
        	reader.close();
        	throw new IllegalStateException("Error while reading NBT meta data!", e);
        }
        reader.close();
        return tag;
    }

    @Override
    public void write(CompoundTag data, ByteBuf out, CodecContext context) {
        NBTNIOWriter writer = new NBTNIOWriter(out, true);
        try {
            if (data == null)
                writer.writeEmptyCompound(null);
            else
                writer.writeNBT(data);
        } catch (IOException e) {
        	throw new IllegalStateException("Error while writing NBT meta data!", e);
        } finally {
			try {
				writer.close();
			} catch (IOException e) {}
		}
    }

    @Override
    public CompoundTag copyData(CompoundTag data) {
        return data != null ? data.clone() : null;
    }

}