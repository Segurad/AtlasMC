package de.atlasmc.entity.data;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.Color;
import de.atlasmc.Particle;
import de.atlasmc.Particle.DustOptions;
import de.atlasmc.block.BlockFace;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.entity.Entity.Pose;
import de.atlasmc.entity.Villager.VillagerData;
import de.atlasmc.entity.Villager.VillagerProfession;
import de.atlasmc.entity.Villager.VillagerType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.util.EulerAngle;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.util.nbt.tag.CompoundTag;
import de.atlasmc.util.nbt.tag.NBT;
import de.atlasmc.world.particle.ParticleObject;
import io.netty.buffer.ByteBuf;

/**
 * Represents a {@link MetaData}s type
 * @param <T>
 */
public abstract class MetaDataType<T> {
	
	public static final MetaDataType<Byte> BYTE = new MetaDataType<>(0, Number.class) {

        @Override
        public Byte read(ByteBuf in) {
            return in.readByte();
        }

        @Override
        public void write(Byte data, ByteBuf out) {
            out.writeByte(data & 0xFF);
        }

    };
	public static final MetaDataType<Integer> INT = new MetaDataType<>(1, Number.class) {

        @Override
        public Integer read(ByteBuf in) {
            return AbstractPacket.readVarInt(in);
        }

        @Override
        public void write(Integer data, ByteBuf out) {
            AbstractPacket.writeVarInt(data, out);
        }

    };
	
	public static final MetaDataType<Float> FLOAT = new MetaDataType<>(2, Number.class) {

        @Override
        public Float read(ByteBuf in) {
            return in.readFloat();
        }

        @Override
        public void write(Float data, ByteBuf out) {
            out.writeFloat(data);
        }

    };
	
	public static final MetaDataType<String> STRING = new MetaDataType<>(3, String.class) {

        @Override
        public String read(ByteBuf in) {
            return AbstractPacket.readString(in);
        }

        @Override
        public void write(String data, ByteBuf out) {
            AbstractPacket.writeString(data, out);
        }

    };
	
	public static final MetaDataType<Chat> CHAT = new MetaDataType<>(4, Chat.class) {

        @Override
        public Chat read(ByteBuf in) {
            return ChatUtil.toChat(AbstractPacket.readString(in));
        }

        @Override
        public void write(Chat data, ByteBuf out) {
            AbstractPacket.writeString(data.getText(), out);
        }

    };
	
	public static final MetaDataType<Chat> OPT_CHAT = new MetaDataType<>(5, Chat.class, true) {

        @Override
        public Chat read(ByteBuf in) {
            if (!in.readBoolean()) return null;
            return ChatUtil.toChat(AbstractPacket.readString(in));
        }

        @Override
        public void write(Chat data, ByteBuf out) {
            out.writeBoolean(data != null);
            if (data == null) return;
            AbstractPacket.writeString(data.getText(), out);
        }

    };
	
	public static final MetaDataType<ItemStack> SLOT = new MetaDataType<>(6, ItemStack.class, true) {

        @Override
        public ItemStack read(ByteBuf in) {
            try {
                return AbstractPacket.readSlot(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void write(ItemStack data, ByteBuf out) {
            try {
                AbstractPacket.writeSlot(data, out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public ItemStack copyData(ItemStack data) {
            return data.clone();
        }

        ;

    };
	
	public static final MetaDataType<Boolean> BOOLEAN = new MetaDataType<>(7, Boolean.class) {

        @Override
        public Boolean read(ByteBuf in) {
            return in.readBoolean();
        }

        @Override
        public void write(Boolean data, ByteBuf out) {
            out.writeBoolean(data);
        }

    };
	
	public static final MetaDataType<EulerAngle> ROTATION = new MetaDataType<>(8, EulerAngle.class) {

        @Override
        public EulerAngle read(ByteBuf in) {
            return new EulerAngle(in.readFloat(), in.readFloat(), in.readFloat());
        }

        @Override
        public void write(EulerAngle data, ByteBuf out) {
            out.writeFloat(data.getX());
            out.writeFloat(data.getY());
            out.writeFloat(data.getZ());
        }

        public EulerAngle copyData(EulerAngle data) {
            return data.clone();
        }

        ;

    };
	
	public static final MetaDataType<Long> POSITION = new MetaDataType<>(9, Number.class) {

        @Override
        public Long read(ByteBuf in) {
            return in.readLong();
        }

        @Override
        public void write(Long data, ByteBuf out) {
            out.writeLong(data);
        }

    };
	
	public static final MetaDataType<Long> OPT_POSITION = new MetaDataType<>(10, Number.class) {

        @Override
        public Long read(ByteBuf in) {
            if (in.readBoolean()) return in.readLong();
            return null;
        }

        @Override
        public void write(Long data, ByteBuf out) {
            out.writeBoolean(data != null);
            if (data == null) return;
            long l = (long) data;
            out.writeLong(l);
        }

    };
	
	public static final MetaDataType<BlockFace> DIRECTION = new MetaDataType<>(11, BlockFace.class) {

        @Override
        public BlockFace read(ByteBuf in) {
            int face = AbstractPacket.readVarInt(in);
            switch (face) {
                case 0:
                    return BlockFace.DOWN;
                case 1:
                    return BlockFace.UP;
                case 2:
                    return BlockFace.NORTH;
                case 3:
                    return BlockFace.SOUTH;
                case 4:
                    return BlockFace.WEST;
                case 5:
                    return BlockFace.EAST;
                default:
                    return BlockFace.DOWN;
            }
        }

        @Override
        public void write(BlockFace data, ByteBuf out) {
            int i = 0;
            switch (data) {
                case DOWN:
                    i = 0;
                    break;
                case UP:
                    i = 1;
                    break;
                case NORTH:
                    i = 2;
                    break;
                case SOUTH:
                    i = 3;
                    break;
                case WEST:
                    i = 4;
                    break;
                case EAST:
                    i = 5;
                    break;
                default:
                    break;
            }
            AbstractPacket.writeVarInt(i, out);
        }

    };
	
	public static final MetaDataType<UUID> OPT_UUID = new MetaDataType<>(12, UUID.class, true) {

        @Override
        public UUID read(ByteBuf in) {
            if (!in.readBoolean()) return null;
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
	
	public static final MetaDataType<Integer> OPT_BLOCKSTATE = new MetaDataType<>(13, Number.class, true) {

        @Override
        public Integer read(ByteBuf in) {
            return AbstractPacket.readVarInt(in);
        }

        @Override
        public void write(Integer data, ByteBuf out) {
            AbstractPacket.writeVarInt(data != null ? data : 0, out);
        }

    };
	
	public static final MetaDataType<CompoundTag> NBT_DATA = new MetaDataType<>(14, CompoundTag.class, true) {

        @Override
        public CompoundTag read(ByteBuf in) {
            NBTNIOReader reader = null;
            CompoundTag tag = null;
            try {
                reader = new NBTNIOReader(in);
                tag = (CompoundTag) reader.readNBT();
            } catch (IOException e) {
                e.printStackTrace();
            }
            reader.close();
            return tag;
        }

        @Override
        public void write(CompoundTag data, ByteBuf out) {
            NBTNIOWriter writer = new NBTNIOWriter(out);
            try {
                if (data == null)
                    writer.writeEmptyCompound(null);
                else
                    writer.writeNBT((NBT) data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            writer.close();
        }

        public CompoundTag copyData(CompoundTag data) {
            return data.clone();
        }

        ;

    };
	
	public static final ParticleMetaDataType PARTICLE = new ParticleMetaDataType();
			
	public static final class ParticleMetaDataType extends MetaDataType<ParticleObject> {

		public ParticleMetaDataType() {
			super(15, ParticleObject.class);
		}

		@Override
		public ParticleObject read(ByteBuf in) {
			Particle p = Particle.getByID(AbstractPacket.readVarInt(in));
			return new ParticleObject(p, read(p, in));
		}
		
		public Object read(Particle particle, ByteBuf in) {
			Object data = null;
			switch (particle) {
			case BLOCK:
			case FALLING_DUST: 
				data = AbstractPacket.readVarInt(in);
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
					data = AbstractPacket.readSlot(in);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default: break;
			}
			return data;
		}

		@Override
		public void write(ParticleObject data, ByteBuf out) {
			write(data.getParticle(), data.getData(), false, out);
		}
		
		public void write(Particle particle, Object data, boolean dataOnly, ByteBuf out) {
			if (!dataOnly) AbstractPacket.writeVarInt(particle.getID(), out);
			switch (particle) {
			case BLOCK:
			case FALLING_DUST: 
				AbstractPacket.writeVarInt(data != null ? (int) data : 0, out);
				break;
			case DUST:
				if (data != null && data instanceof DustOptions) {
					DustOptions d = (DustOptions) data;
					Color c = d.getColor();
					out.writeFloat(c.getRed() / 255.0f);
					out.writeFloat(c.getGreen() / 255.0f);
					out.writeFloat(c.getBlue() / 255.0f);
					out.writeFloat(d.getScale());
				}
				out.writeFloat(0);
				out.writeFloat(0);
				out.writeFloat(0);
				out.writeFloat(0);
				break;
			case ITEM:
				try {
					AbstractPacket.writeSlot((ItemStack) data, out);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default: break;
			}
		}
		
		@Override
		public ParticleObject copyData(ParticleObject data) {
			return data.clone();
		}
		
	};
	
	public static final MetaDataType<VillagerData> VILLAGER_DATA = new MetaDataType<>(16, VillagerData.class) {

        @Override
        public VillagerData read(ByteBuf in) {
            VillagerType t = VillagerType.getByID(AbstractPacket.readVarInt(in));
            VillagerProfession p = VillagerProfession.getByID(AbstractPacket.readVarInt(in));
            int lvl = AbstractPacket.readVarInt(in);
            return new VillagerData(t, p, lvl);
        }

        @Override
        public void write(VillagerData data, ByteBuf out) {
            AbstractPacket.writeVarInt(data.getType().getID(), out);
            AbstractPacket.writeVarInt(data.getProfession().getID(), out);
            AbstractPacket.writeVarInt(data.getLevel(), out);
        }

    };
	
	public static final MetaDataType<Integer> OPT_INT = new MetaDataType<>(17, Number.class, true) {

        @Override
        public Integer read(ByteBuf in) {
            int i = AbstractPacket.readVarInt(in);
            return i == 0 ? null : i - 1;
        }

        @Override
        public void write(Integer data, ByteBuf out) {
            if (data == null) AbstractPacket.writeVarInt(0, out);
            AbstractPacket.writeVarInt(data + 1, out);
        }

    };
	
	public static final MetaDataType<Pose> POSE = new MetaDataType<>(18, Pose.class) {

        @Override
        public Pose read(ByteBuf in) {
            return Pose.getByID(AbstractPacket.readVarInt(in));
        }

        @Override
        public void write(Pose data, ByteBuf out) {
            AbstractPacket.writeVarInt(data.getID(), out);
        }

    };
	
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
		case 1: return INT;
		case 2: return FLOAT;
		case 3: return STRING;
		case 4: return CHAT;
		case 5: return OPT_CHAT;
		case 6: return SLOT;
		case 7: return BOOLEAN;
		case 8: return ROTATION;
		case 9: return POSITION;
		case 10: return OPT_POSITION;
		case 11: return DIRECTION;
		case 12: return OPT_UUID;
		case 13: return OPT_BLOCKSTATE;
		case 14: return NBT_DATA;
		case 15: return PARTICLE;
		case 16: return VILLAGER_DATA;
		case 17: return OPT_INT;
		case 18: return POSE;
		default:
			throw new IllegalArgumentException("Invalid Type ID: " + id);
		}
	}

}
