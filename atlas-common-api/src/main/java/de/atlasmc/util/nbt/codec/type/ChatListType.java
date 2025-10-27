package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

@Singleton
public class ChatListType extends ObjectType<List<Chat>> {

	private static final ChatListType INSTANCE = new ChatListType();
	
	public static ChatListType getInstance() {
		return INSTANCE;
	}
	
	private ChatListType() {
		// singleton
	}

	@Override
	public boolean serialize(CharSequence key, List<Chat> value, NBTWriter writer, CodecContext context) throws IOException {
		final int size = value.size();
		if (value.get(0).isComponent()) {
			writer.writeListTag(key, TagType.COMPOUND, size);
			for (int i = 0; i < size; i++) {
				Chat v = value.get(i);
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
				Chat v = value.get(i);
				writer.writeStringTag(null, v.toText());
			}
		}
		return true;
	}

	@Override
	public List<Chat> deserialize(List<Chat> value, NBTReader reader, CodecContext context) throws IOException {
		final TagType listType = reader.getListType();
		if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
			reader.readNextEntry();
			reader.readNextEntry();
			return value;
		}
		switch (listType) {
		case COMPOUND:
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				reader.readNextEntry();
				Chat v = ChatComponent.NBT_CODEC.deserialize(reader, context);
				value.add(v);
			}
			reader.readNextEntry();
			break;
		case STRING:
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				String raw = reader.readStringTag();
				Chat v = ChatUtil.toChat(raw);
				value.add(v);
			}
			break;
		default:
			throw new NBTException("Expected list of type COMPOUND or STRING but was: " + listType);
		}
		return value;
	}

	@Override
	public List<TagType> getTypes() {
		return LIST;
	}

}
