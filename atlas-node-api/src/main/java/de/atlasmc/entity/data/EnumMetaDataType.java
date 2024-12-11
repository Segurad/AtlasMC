package de.atlasmc.entity.data;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.netty.buffer.ByteBuf;

final class EnumMetaDataType<T extends Enum<T>> extends MetaDataType<T> {

	private final Method getByID;
	private final Method getID;
	
	public EnumMetaDataType(int type, Class<T> typeClass) {
		super(type, typeClass);
		try {
			getByID = typeClass.getDeclaredMethod("getByID", int.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new IllegalStateException("Error while fetching getByID method for: " + typeClass.getName(), e);
		}
		try {
			getID = typeClass.getDeclaredMethod("getID");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new IllegalStateException("Error while fetching getID method for: " + typeClass.getName(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T read(ByteBuf in) {
		try {
			return (T) getByID.invoke(null, readVarInt(in));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new IllegalStateException("Error while reading enum meta data type: " + getTypeClass().getName(), e);
		}
	}

	@Override
	public void write(T data, ByteBuf out) {
		try {
			writeVarInt((int) getID.invoke(data), out);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new IllegalStateException("Error while writing enum meta data type: " + getTypeClass().getName(), e);
		}
	}

}
