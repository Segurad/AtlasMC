package de.atlasmc.entity.data;

import static de.atlasmc.io.PacketUtil.readIdentifier;
import static de.atlasmc.io.PacketUtil.readString;
import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.readVarLong;
import static de.atlasmc.io.PacketUtil.writeIdentifier;
import static de.atlasmc.io.PacketUtil.writeString;
import static de.atlasmc.io.PacketUtil.writeVarInt;
import static de.atlasmc.io.PacketUtil.writeVarLong;

import java.io.IOException;
import java.util.UUID;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import de.atlasmc.Location;
import de.atlasmc.NamespacedKey;
import de.atlasmc.block.BlockFace;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.entity.AbstractVillager.VillagerData;
import de.atlasmc.entity.AbstractVillager.VillagerProfession;
import de.atlasmc.entity.AbstractVillager.VillagerType;
import de.atlasmc.entity.Armadillo.ArmadilloState;
import de.atlasmc.entity.Cat.Type;
import de.atlasmc.entity.Entity.Pose;
import de.atlasmc.entity.Frog.Variant;
import de.atlasmc.entity.Painting.Motive;
import de.atlasmc.entity.Sniffer.State;
import de.atlasmc.entity.Wolf.WolfVariant;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.protocol.ProtocolUtil;
import de.atlasmc.util.MathUtil;
import de.atlasmc.util.nbt.tag.CompoundTag;
import io.netty.buffer.ByteBuf;

/**
 * Represents a {@link MetaData}s type
 * @param <T>
 */
public abstract class MetaDataType<T> {
	
	private static final int
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
	TYPE_ID_FROG_VARIANT = 24,
	TYPE_ID_OPT_GLOBAL_POSITION = 25,
	TYPE_ID_PAINTING_VARIANT = 26,
	TYPE_ID_SNIFFER_STATE = 27,
	TYPE_ID_ARMADILLO_STATE = 28,
	TYPE_ID_VECTOR_3 = 29,
	TYPE_ID_QUATERNION = 30;
	
	
	public static final MetaDataType<Byte> BYTE = new MetaDataType<>(TYPE_ID_BYTE, Number.class) {

        @Override
        public Byte read(ByteBuf in) {
            return in.readByte();
        }

        @Override
        public void write(Byte data, ByteBuf out) {
            out.writeByte(data & 0xFF);
        }

    };
	
    public static final MetaDataType<Integer> VAR_INT = new VarIntMetaDataType(TYPE_ID_VAR_INT);
    
    public static final MetaDataType<Long> VAR_LONG = new MetaDataType<>(TYPE_ID_VAR_LONG, Number.class) {
    	
    	@Override
    	public Long read(ByteBuf in) {
    		return readVarLong(in);
    	}
    	
    	public void write(Long data, ByteBuf out) {
			writeVarLong(data, out);
    	};
    	
    };
  	
	public static final MetaDataType<Float> FLOAT = new MetaDataType<>(TYPE_ID_FLOAT, Number.class) {

        @Override
        public Float read(ByteBuf in) {
            return in.readFloat();
        }

        @Override
        public void write(Float data, ByteBuf out) {
            out.writeFloat(data);
        }

    };
	
	public static final MetaDataType<String> STRING = new MetaDataType<>(TYPE_ID_STRING, String.class) {

        @Override
        public String read(ByteBuf in) {
            return readString(in);
        }

        @Override
        public void write(String data, ByteBuf out) {
            writeString(data, out);
        }

    };
	
	public static final MetaDataType<Chat> CHAT = new MetaDataType<>(TYPE_ID_CHAT, Chat.class) {

        @Override
        public Chat read(ByteBuf in) {
            return ChatUtil.toChat(readString(in));
        }

        @Override
        public void write(Chat data, ByteBuf out) {
            writeString(data.toText(), out);
        }

    };
	
	public static final MetaDataType<Chat> OPT_CHAT = new MetaDataType<>(TYPE_ID_OPT_CHAT, Chat.class, true) {

        @Override
        public Chat read(ByteBuf in) {
            if (!in.readBoolean()) 
            	return null;
            return ChatUtil.toChat(readString(in));
        }

        @Override
        public void write(Chat data, ByteBuf out) {
            out.writeBoolean(data != null);
            if (data == null) 
            	return;
            writeString(data.toText(), out);
        }

    };
	
	public static final MetaDataType<ItemStack> SLOT = new MetaDataType<>(TYPE_ID_SLOT, ItemStack.class, true) {

        @Override
        public ItemStack read(ByteBuf in) {
            try {
                return ProtocolUtil.readSlot(in);
            } catch (IOException e) {
                throw new IllegalStateException("Error while reading slot meta data!", e);
            }
        }

        @Override
        public void write(ItemStack data, ByteBuf out) {
            try {
                ProtocolUtil.writeSlot(data, out);
            } catch (IOException e) {
            	throw new IllegalStateException("Error while writing slot meta data!", e);
            }
        }

        public ItemStack copyData(ItemStack data) {
            return data != null ? data.clone() : null;
        }

    };
	
	public static final MetaDataType<Boolean> BOOLEAN = new MetaDataType<>(TYPE_ID_BOOLEAN, Boolean.class) {

        @Override
        public Boolean read(ByteBuf in) {
            return in.readBoolean();
        }

        @Override
        public void write(Boolean data, ByteBuf out) {
            out.writeBoolean(data);
        }

    };
	
	public static final MetaDataType<Vector3f> ROTATION = new MetaDataType<>(TYPE_ID_ROTATION, Vector3f.class) {

        @Override
        public Vector3f read(ByteBuf in) {
            return new Vector3f(in.readFloat(), in.readFloat(), in.readFloat());
        }

        @Override
        public void write(Vector3f data, ByteBuf out) {
            out.writeFloat(data.x);
            out.writeFloat(data.y);
            out.writeFloat(data.z);
        }

        public Vector3f copyData(Vector3f data) {
            return new Vector3f(data);
        }

    };
	
	public static final MetaDataType<Long> POSITION = new MetaDataType<>(TYPE_ID_POSITION, Number.class) {

        @Override
        public Long read(ByteBuf in) {
            return in.readLong();
        }

        @Override
        public void write(Long data, ByteBuf out) {
            out.writeLong(data);
        }

    };
	
	public static final MetaDataType<Long> OPT_POSITION = new MetaDataType<>(TYPE_ID_OPT_POSITION, Number.class) {

        @Override
        public Long read(ByteBuf in) {
            if (in.readBoolean()) 
            	return in.readLong();
            return null;
        }

        @Override
        public void write(Long data, ByteBuf out) {
            out.writeBoolean(data != null);
            if (data == null) 
            	return;
            long l = data;
            out.writeLong(l);
        }

    };
	
	public static final MetaDataType<BlockFace> DIRECTION = new DirectionMetaDataType(TYPE_ID_DIRECTION);
	
	public static final MetaDataType<UUID> OPT_UUID = new MetaDataType<>(TYPE_ID_OPT_UUID, UUID.class, true) {

        @Override
        public UUID read(ByteBuf in) {
            if (!in.readBoolean()) 
            	return null;
            long most = in.readLong();
            long least = in.readLong();
            return new UUID(most, least);
        }

        @Override
        public void write(UUID data, ByteBuf out) {
            out.writeBoolean(data != null);
            if (data == null) return;
            out.writeLong(data.getMostSignificantBits());
            out.writeLong(data.getLeastSignificantBits());
        }

    };
    
	public static final MetaDataType<Integer> BLOCKSTATE = new VarIntMetaDataType(TYPE_ID_BLOCKSTATE);
	
	public static final MetaDataType<Integer> OPT_BLOCKSTATE = new MetaDataType<>(TYPE_ID_OPT_BLOCKSTATE, Number.class, true) {

        @Override
        public Integer read(ByteBuf in) {
            return readVarInt(in);
        }

        @Override
        public void write(Integer data, ByteBuf out) {
            writeVarInt(data != null ? data : 0, out);
        }

    };
	
	public static final MetaDataType<CompoundTag> NBT_DATA = new NBTMetaDataType(TYPE_ID_NBT);
	
	public static final ParticleMetaDataType PARTICLE = new ParticleMetaDataType(TYPE_ID_PARTICLE);
	
	public static final MetaDataType<VillagerData> VILLAGER_DATA = new MetaDataType<>(TYPE_ID_VILLAGER_DATA, VillagerData.class) {

        @Override
        public VillagerData read(ByteBuf in) {
            VillagerType t = VillagerType.getByID(readVarInt(in));
            VillagerProfession p = VillagerProfession.getByID(readVarInt(in));
            int lvl = readVarInt(in);
            return new VillagerData(t, p, lvl);
        }

        @Override
        public void write(VillagerData data, ByteBuf out) {
            writeVarInt(data.getType().getID(), out);
            writeVarInt(data.getProfession().getID(), out);
            writeVarInt(data.getLevel(), out);
        }

    };
	
	public static final MetaDataType<Pose> POSE = new EnumMetaDataType<>(TYPE_ID_POSE, Pose.class);
    
    public static final MetaDataType<Type> CAT_VARIANT = new EnumMetaDataType<>(TYPE_ID_CAT_VARIANT, Type.class);
	
    public static final MetaDataType<Motive> PAINTING_VARIANT = new EnumMetaDataType<>(TYPE_ID_PAINTING_VARIANT, Motive.class);

    public static final MetaDataType<Vector3f> VECTOR_3 = new MetaDataType<>(TYPE_ID_VECTOR_3, Vector3f.class) {

		@Override
		public Vector3f read(ByteBuf in) {
			return new Vector3f(in.readFloat(), in.readFloat(), in.readFloat());
		}

		@Override
		public void write(Vector3f data, ByteBuf out) {
			out.writeFloat(data.x);
			out.writeFloat(data.y);
			out.writeFloat(data.z);
		}
		
		public Vector3f copyData(Vector3f data) {
			return new Vector3f(data);
		};
	
    };
    
    public static final MetaDataType<Quaternionf> QUATERNION = new MetaDataType<>(TYPE_ID_QUATERNION, Quaternionf.class) {

		@Override
		public Quaternionf read(ByteBuf in) {
			return new Quaternionf(in.readFloat(), in.readFloat(), in.readFloat(), in.readFloat());
		}

		@Override
		public void write(Quaternionf data, ByteBuf out) {
			out.writeFloat(data.x);
			out.writeFloat(data.y);
			out.writeFloat(data.z);
			out.writeFloat(data.w);
		}
	
		public Quaternionf copyData(Quaternionf data) {
			return new Quaternionf(data);
		};
		
    };
    
    public static final MetaDataType<Variant> FROG_VARIANT = new EnumMetaDataType<>(TYPE_ID_FROG_VARIANT, Variant.class);
    
    public static final MetaDataType<State> SNIFFER_STATE = new EnumMetaDataType<>(TYPE_ID_SNIFFER_STATE, State.class);
    
    public static final MetaDataType<Location> OPT_GLOBAL_POSITION = new MetaDataType<>(TYPE_ID_OPT_GLOBAL_POSITION, Location.class, true) {

		@SuppressWarnings("unused")
		@Override
		public Location read(ByteBuf in) {
			if (!in.readBoolean())
				return null;
			NamespacedKey key = readIdentifier(in);
			long pos = in.readLong();
			// TODO opt pos global to location
			return null;
		}

		@Override
		public void write(Location data, ByteBuf out) {
			if (data == null) {
				out.writeBoolean(false);
			} else {
				out.writeBoolean(true);
				writeIdentifier(data.getWorld().getDimension().getNamespacedKey(), out);
				out.writeLong(MathUtil.toPosition(data));
			}
		}
	
    };
    
    public static final MetaDataType<ArmadilloState> ARMADILLO_STATE = new EnumMetaDataType<>(TYPE_ID_ARMADILLO_STATE, ArmadilloState.class);
	
	public static final MetaDataType<WolfVariant> WOLF_VARIANT = new MetaDataType<>(TYPE_ID_WOLF_VARIANT, WolfVariant.class) {

		@Override
		public WolfVariant read(ByteBuf in) {
			int id = readVarInt(in);
			if (id == 0) {
				NamespacedKey wild = readIdentifier(in);
				NamespacedKey tame = readIdentifier(in);
				NamespacedKey angry = readIdentifier(in);
				return new WolfVariant(wild, tame, angry);
			}
			return WolfVariant.getByID(id-1);
		}

		@Override
		public void write(WolfVariant data, ByteBuf out) {
			int id = data.getID();
			if (id < 0) {
				writeIdentifier(data.getWildTexture(), out);
				writeIdentifier(data.getTameTexture(), out);
				writeIdentifier(data.getAngryTexture(), out);
			} else {
				writeVarInt(id+1, out);
			}
		}
		
	};
	
	public static final MetaDataType<Integer> OPT_VAR_INT = new OptVarIntMetaDataType(TYPE_ID_OPT_VAR_INT);
    
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
	
	public abstract T read(ByteBuf in);
	
	public abstract void write(T data, ByteBuf out);

	@SuppressWarnings("unchecked")
	public void writeRaw(Object data, ByteBuf buf) {
		write((T) data, buf);
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

}
