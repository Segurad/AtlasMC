package de.atlasmc.node.entity.metadata.type;

import static de.atlasmc.io.PacketUtil.readIdentifier;
import static de.atlasmc.io.PacketUtil.readString;
import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeIdentifier;
import static de.atlasmc.io.PacketUtil.writeString;
import static de.atlasmc.io.PacketUtil.writeVarInt;
import java.io.IOException;
import java.util.UUID;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import de.atlasmc.IDHolder;
import de.atlasmc.NamespacedKey;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.io.codec.StreamCodecs;
import de.atlasmc.node.DyeColor;
import de.atlasmc.node.WorldLocation;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.entity.AbstractVillager.VillagerData;
import de.atlasmc.node.entity.Armadillo.ArmadilloState;
import de.atlasmc.node.entity.Cat.Type;
import de.atlasmc.node.entity.Entity.Pose;
import de.atlasmc.node.entity.Frog.Variant;
import de.atlasmc.node.entity.Painting.Motive;
import de.atlasmc.node.entity.Sniffer.State;
import de.atlasmc.node.entity.Wolf.WolfVariant;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.util.MathUtil;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.tag.CompoundTag;
import io.netty.buffer.ByteBuf;

/**
 * Represents a {@link MetaData}s type
 * @param <T>
 */
public abstract class MetaDataType<T> {
	
	public static final int
	TYPE_ID_BYTE = 0,
	TYPE_ID_VAR_INT = 1,
	TYPE_ID_VAR_LONG = 2,
	TYPE_ID_FLOAT = 3,
	TYPE_ID_STRING = 4,
	TYPE_ID_CHAT = 5,
	TYPE_ID_OPT_CHAT = 6,
	TYPE_ID_SLOT = 7,
	TYPE_ID_BOOLEAN = 8,
	TYPE_ID_ROTATION = 9,
	TYPE_ID_POSITION = 10,
	TYPE_ID_OPT_POSITION = 11,
	TYPE_ID_DIRECTION = 12,
	TYPE_ID_OPT_UUID = 13,
	TYPE_ID_BLOCKSTATE = 14, // BlockID in wiki
	TYPE_ID_OPT_BLOCKSTATE = 15, // OptBlockID in wiki
	TYPE_ID_NBT = 16,
	TYPE_ID_PARTICLE = 17,
	TYPE_ID_VILLAGER_DATA = 18,
	TYPE_ID_OPT_VAR_INT = 20,
	TYPE_ID_POSE = 21,
	TYPE_ID_CAT_VARIANT = 22,
	TYPE_ID_WOLF_VARIANT = 23,
	TYPE_ID_WOLF_SOUND_VARIANT = 24,
	TYPE_ID_FROG_VARIANT = 25,
	TYPE_ID_PIG_VARIANT = 26,
	TYPE_ID_CHICKEN_VARIANT = 27,
	TYPE_ID_OPT_GLOBAL_POSITION = 28,
	TYPE_ID_PAINTING_VARIANT = 29,
	TYPE_ID_SNIFFER_STATE = 30,
	TYPE_ID_ARMADILLO_STATE = 31,
	TYPE_ID_COPPER_GOLEM_STATE = 32,
	TYPE_ID_WEATHERING_COPPER_STATE = 33,
	TYPE_ID_VECTOR_3F = 34,
	TYPE_ID_QUATERNION_F = 35,
	TYPE_ID_RESOLVED_PROFILE = 36;
	
	public static final MetaDataType<Byte> BYTE = new ByteMetaType(TYPE_ID_BYTE);
	
    public static final MetaDataType<Integer> VAR_INT = new VarIntMetaType(TYPE_ID_VAR_INT);
    
    public static final MetaDataType<Integer> OPT_VAR_INT = new OptVarIntMetaType(TYPE_ID_OPT_VAR_INT);
    
    public static final MetaDataType<Long> VAR_LONG = new VarLongMetaType(TYPE_ID_VAR_LONG);
  	
	public static final MetaDataType<Float> FLOAT = new FloatMetaType(TYPE_ID_FLOAT);
	
	public static final MetaDataType<String> STRING = new StringMetaType(TYPE_ID_STRING);
	
	public static final MetaDataType<Chat> CHAT = new MetaDataType<>(TYPE_ID_CHAT, Chat.class) {

        @Override
        public Chat read(ByteBuf in, CodecContext context) {
            return ChatUtil.toChat(readString(in));
        }

        @Override
        public void write(Chat data, ByteBuf out, CodecContext context) {
            writeString(data.toText(), out);
        }

    };
	
	public static final MetaDataType<Chat> OPT_CHAT = new MetaDataType<>(TYPE_ID_OPT_CHAT, Chat.class, true) {

        @Override
        public Chat read(ByteBuf in, CodecContext context) {
            if (!in.readBoolean()) 
            	return null;
            return ChatUtil.toChat(readString(in));
        }

        @Override
        public void write(Chat data, ByteBuf out, CodecContext context) {
            out.writeBoolean(data != null);
            if (data == null) 
            	return;
            writeString(data.toText(), out);
        }

    };
	
	public static final MetaDataType<ItemStack> SLOT = new StreamCodecMetaType<>(TYPE_ID_SLOT, false, ItemStack.STREAM_CODEC, ItemStack::clone);
	
	public static final MetaDataType<Boolean> BOOLEAN = new BooleanMetaType(TYPE_ID_BOOLEAN);
	
	public static final MetaDataType<Vector3f> ROTATION = new StreamCodecMetaType<>(TYPE_ID_ROTATION, false, StreamCodecs.VECTOR_3F, t -> { return new Vector3f(t);});;
	
	public static final MetaDataType<Long> POSITION = new LongMetaType(TYPE_ID_POSITION);
	
	public static final MetaDataType<Long> OPT_POSITION = new OptLongMetaType(TYPE_ID_OPT_POSITION);
	
	public static final MetaDataType<BlockFace> DIRECTION = new DirectionMetaType(TYPE_ID_DIRECTION);
	
	public static final MetaDataType<UUID> OPT_UUID = new MetaDataType<>(TYPE_ID_OPT_UUID, UUID.class, true) {

        @Override
        public UUID read(ByteBuf in, CodecContext context) {
            if (!in.readBoolean()) 
            	return null;
            long most = in.readLong();
            long least = in.readLong();
            return new UUID(most, least);
        }

        @Override
        public void write(UUID data, ByteBuf out, CodecContext context) {
            out.writeBoolean(data != null);
            if (data == null) return;
            out.writeLong(data.getMostSignificantBits());
            out.writeLong(data.getLeastSignificantBits());
        }

    };
    
	public static final MetaDataType<Integer> BLOCKSTATE = new VarIntMetaType(TYPE_ID_BLOCKSTATE);
	
	public static final MetaDataType<Integer> OPT_BLOCKSTATE = new MetaDataType<>(TYPE_ID_OPT_BLOCKSTATE, Number.class, true) {

        @Override
        public Integer read(ByteBuf in, CodecContext context) {
            return readVarInt(in);
        }

        @Override
        public void write(Integer data, ByteBuf out, CodecContext context) {
            writeVarInt(data != null ? data : 0, out);
        }

    };
	
	public static final MetaDataType<CompoundTag> NBT_DATA = new NBTMetaType(TYPE_ID_NBT);
	
	public static final ParticleMetaType PARTICLE = new ParticleMetaType(TYPE_ID_PARTICLE);
	
	public static final MetaDataType<VillagerData> VILLAGER_DATA = new StreamCodecMetaType<>(TYPE_ID_VILLAGER_DATA, false, VillagerData.STREAM_CODEC, VillagerData::clone);
	
	public static final MetaDataType<Pose> POSE = getVarIntEnumType(TYPE_ID_POSE, Pose.class);
    
    public static final MetaDataType<Type> CAT_VARIANT = getVarIntEnumType(TYPE_ID_CAT_VARIANT, Type.class);
	
    public static final MetaDataType<Motive> PAINTING_VARIANT = getVarIntEnumType(TYPE_ID_PAINTING_VARIANT, Motive.class);

    public static final MetaDataType<Vector3f> VECTOR_3 = new StreamCodecMetaType<>(TYPE_ID_VECTOR_3F, false, StreamCodecs.VECTOR_3F, t -> { return new Vector3f(t);});
    
    public static final MetaDataType<Quaternionf> QUATERNION = new StreamCodecMetaType<>(TYPE_ID_QUATERNION_F, false, StreamCodecs.QUATERNION_F, t -> { return new Quaternionf(t);});
    
    public static final MetaDataType<Variant> FROG_VARIANT = getVarIntEnumType(TYPE_ID_FROG_VARIANT, Variant.class);
    
    public static final MetaDataType<State> SNIFFER_STATE = getVarIntEnumType(TYPE_ID_SNIFFER_STATE, State.class);
    
    public static final MetaDataType<WorldLocation> OPT_GLOBAL_POSITION = new MetaDataType<>(TYPE_ID_OPT_GLOBAL_POSITION, WorldLocation.class, true) {

		@SuppressWarnings("unused")
		@Override
		public WorldLocation read(ByteBuf in, CodecContext context) {
			if (!in.readBoolean())
				return null;
			NamespacedKey key = readIdentifier(in);
			long pos = in.readLong();
			// TODO opt pos global to location
			return null;
		}

		@Override
		public void write(WorldLocation data, ByteBuf out, CodecContext context) {
			if (data == null) {
				out.writeBoolean(false);
			} else {
				out.writeBoolean(true);
				writeIdentifier(data.getWorld().getDimension().getNamespacedKey(), out);
				out.writeLong(MathUtil.toPosition(data));
			}
		}
	
    };
    
    public static final MetaDataType<ArmadilloState> ARMADILLO_STATE = getVarIntEnumType(TYPE_ID_ARMADILLO_STATE, ArmadilloState.class);
	
	public static final MetaDataType<WolfVariant> WOLF_VARIANT = new RegistryValueMetaType<>(TYPE_ID_WOLF_VARIANT, WolfVariant.class, WolfVariant.REGISTRY_KEY);
	
	public static final MetaDataType<DyeColor> VAR_INT_COLOR = getVarIntEnumType(DyeColor.class);
	
	private final int type;
	private final boolean optional;
	private final Class<?> clazz;
	
	public MetaDataType(int type, Class<?> typeClass) {
		this(type, typeClass, false);
	}
	
	public MetaDataType(int type, Class<?> typeClass, boolean optional) {
		this.type = type;
		this.clazz = typeClass;
		this.optional = optional;
	}
	
	public int getTypeID() {
		return type;
	}
	
	public Class<?> getTypeClass() {
		return clazz;
	}
	
	public boolean isOptional() {
		return optional;
	}
	
	/**
	 * Returns a copy of the data
	 * @param data
	 * @return copy
	 */
	public T copyData(T data) {
		return data;
	}
	
	public abstract T read(ByteBuf in, CodecContext context) throws IOException;
	
	public abstract void write(T data, ByteBuf out, CodecContext context) throws IOException;

	@SuppressWarnings("unchecked")
	public void writeRaw(Object data, ByteBuf buf, CodecContext context) throws IOException {
		write((T) data, buf, context);
	}
	
	public static MetaDataType<?> getByID(int id) {
		switch (id) {
		case 0: return BYTE;
		case 1: return VAR_INT;
		case 2: return VAR_LONG;
		case 3: return FLOAT;
		case 4: return STRING;
		case 5: return CHAT;
		case 6: return OPT_CHAT;
		case 7: return SLOT;
		case 8: return BOOLEAN;
		case 9: return ROTATION;
		case 10: return POSITION;
		case 11: return OPT_POSITION;
		case 12: return DIRECTION;
		case 13: return OPT_UUID;
		case 14: return BLOCKSTATE;
		case 15: return OPT_BLOCKSTATE;
		case 16: return NBT_DATA;
		case 17: return PARTICLE;
		case 18: return VILLAGER_DATA;
		case 19: return OPT_VAR_INT;
		case 20: return POSE;
		case 21: return CAT_VARIANT;
		case 22: return FROG_VARIANT;
		case 23: return null; //OPT_GLOBAL_POSITION;
		case 24: return PAINTING_VARIANT;
		case 25: return SNIFFER_STATE;
		case 26: return VECTOR_3;
		case 27: return QUATERNION;
		default:
			throw new IllegalArgumentException("Invalid Type ID: " + id);
		}
	}
	
	public static <T extends Enum<T> & IDHolder> MetaDataType<T> getVarIntEnumType(Class<T> clazz) {
		return getVarIntEnumType(TYPE_ID_VAR_INT, clazz);
	}
	
	public static <T extends Enum<T> & IDHolder> MetaDataType<T> getByteEnumType(Class<T> clazz) {
		return new ByteEnumMetaType<>(TYPE_ID_BYTE, clazz);
	}
	
	private static <T extends Enum<T> & IDHolder> MetaDataType<T> getVarIntEnumType(int id, Class<T> clazz) {
		return new VarIntEnumMetaType<>(id, clazz);
	}

}
