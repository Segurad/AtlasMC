package de.atlasmc.entity.data;

import java.io.IOException;

import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.util.nbt.tag.CompoundTag;
import io.netty.buffer.ByteBuf;

final class NBTMetaDataType extends MetaDataType<CompoundTag> {

    public NBTMetaDataType(int type) {
		super(type, CompoundTag.class);
	}

	@Override
    public CompoundTag read(ByteBuf in) {
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
    public void write(CompoundTag data, ByteBuf out) {
        NBTNIOWriter writer = new NBTNIOWriter(out, true);
        try {
            if (data == null)
                writer.writeEmptyCompound(null);
            else
                writer.writeNBT(data);
        } catch (IOException e) {
        	writer.close();
        	throw new IllegalStateException("Error while writing NBT meta data!", e);
        }
        writer.close();
    }

    @Override
    public CompoundTag copyData(CompoundTag data) {
        return data != null ? data.clone() : null;
    }

}