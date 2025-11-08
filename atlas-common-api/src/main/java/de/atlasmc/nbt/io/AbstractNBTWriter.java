package de.atlasmc.nbt.io;

import java.io.IOException;

import de.atlasmc.nbt.tag.NBT;

public abstract class AbstractNBTWriter implements NBTWriter {
	
	private boolean closed;
	
	@Override
	public void writeNBT(NBT nbt) throws IOException {
		ensureOpen();
		if (nbt == null) 
			throw new IllegalArgumentException("NBT can not be null!");
		nbt.toNBT(this, true);
	}
	
	@Override
	public void writeNBT(CharSequence name, NBT nbt) throws IOException {
		ensureOpen();
		if (nbt == null) 
			throw new IllegalArgumentException("NBT can not be null!");
		nbt.toNBT(name, this, true);
	}
	
	@Override
	public void close() throws IOException {
		this.closed = true;
	}
	
	protected final void ensureOpen() throws IOException {
		if (closed)
			throw new IOException("Stream closed!");
	}

}
