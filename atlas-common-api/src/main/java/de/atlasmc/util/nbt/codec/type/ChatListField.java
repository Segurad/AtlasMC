package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class ChatListField<T> extends AbstractCollectionField<T, List<Chat>> {

	public ChatListField(CharSequence key, ToBooleanFunction<T> has, Function<T, List<Chat>> getCollection, boolean optional) {
		super(key, LIST, has, getCollection, optional);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		if (has != null && !has.applyAsBoolean(value)) {
			if (!useDefault)
				writer.writeListTag(key, TagType.COMPOUND, 0);
			return true;
		}
		final List<Chat> list = get.apply(value);
		final int size = list.size();
		if (size == 0) {
			if (!useDefault)
				writer.writeListTag(key, TagType.COMPOUND, 0);
			return true;
		}
		if (list.get(0).isComponent()) {
			writer.writeListTag(key, TagType.COMPOUND, size);
			for (int i = 0; i < size; i++) {
				Chat v = list.get(i);
				ChatComponent comp = v.toComponent();
				@SuppressWarnings("unchecked")
				NBTCodec<ChatComponent> handler = (NBTCodec<ChatComponent>) comp.getNBTCodec();
				writer.writeCompoundTag();
				handler.serialize(comp, writer, context);
				writer.writeEndTag();
			}
		} else {
			writer.writeListTag(key, TagType.STRING, size);
			for (int i = 0; i < size; i++) {
				Chat v = list.get(i);
				writer.writeStringTag(null, v.toText());
			}
		}
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
		final TagType listType = reader.getListType();
		if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
			reader.readNextEntry();
			reader.readNextEntry();
			return;
		}
		final List<Chat> list = get.apply(value);
		switch (listType) {
		case COMPOUND:
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				reader.readNextEntry();
				Chat v = ChatComponent.NBT_HANDLER.deserialize(reader, context);
				list.add(v);
			}
			reader.readNextEntry();
		case STRING:
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				String raw = reader.readStringTag();
				Chat v = ChatUtil.toChat(raw);
				list.add(v);
			}
		default:
			throw new NBTException("Expected list of type COMPOUND or STRING but was: " + listType);
		}
	}

}
