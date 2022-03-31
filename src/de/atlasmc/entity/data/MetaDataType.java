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

public abstract class MetaDataType<T> {
	
	public static final MetaDataType<Byte> BYTE = new MetaDataType<Byte>(0, Number.class) {
		
		@Override
		public Byte read(ByteBuf in) {
			return in.readByte();
		}

		@Override
		public void write(Object data, ByteBuf out) {
			out.writeByte((int) data);
		}
		
	};
	public static final MetaDataType<Integer> INT = new MetaDataType<Integer>(1, Number.class) {

		@Override
		public Integer read(ByteBuf in) {
			return AbstractPacket.readVarInt(in);
		}

		@Override
		public void write(Object data, ByteBuf out) {
			AbstractPacket.writeVarInt((int) data, out);
		}
		
	};
	
	public static final MetaDataType<Float> FLOAT = new MetaDataType<Float>(2, Number.class) {

		@Override
		public Float read(ByteBuf in) {
			return in.readFloat();
		}

		@Override
		public void write(Object data, ByteBuf out) {
			out.writeFloat((float) data);
		}
		
	};
	
	public static final MetaDataType<String> STRING = new MetaDataType<String>(3, String.class) {

		@Override
		public String read(ByteBuf in) {
			return AbstractPacket.readString(in);
		}

		@Override
		public void write(Object data, ByteBuf out) {
			AbstractPacket.writeString((String) data, out);
		}
		
	};
	
	public static final MetaDataType<Chat> CHAT = new MetaDataType<Chat>(4, Chat.class) {

		@Override
		public Chat read(ByteBuf in) {
			return ChatUtil.toChat(AbstractPacket.readString(in));
		}

		@Override
		public void write(Object data, ByteBuf out) {
			Chat chat = (Chat) data;
			AbstractPacket.writeString(chat.getJsonText(), out);
		}
		
	};
	
	public static final MetaDataType<Chat> OPT_CHAT = new MetaDataType<Chat>(5, Chat.class, true) {

		@Override
		public Chat read(ByteBuf in) {
			if (!in.readBoolean()) return null;
			return ChatUtil.toChat(AbstractPacket.readString(in));
		}

		@Override
		public void write(Object data, ByteBuf out) {
			out.writeBoolean(data != null);
			if (data == null) return;
			Chat chat = (Chat) data;
			AbstractPacket.writeString(chat.getJsonText(), out);
		}
		
	};
	
	public static final MetaDataType<ItemStack> SLOT = new MetaDataType<ItemStack>(6, ItemStack.class, true) {

		@Override
		public ItemStack read(ByteBuf in) throws IOException {
			return AbstractPacket.readSlot(in);
		}

		@Override
		public void write(Object data, ByteBuf out) throws IOException {
			AbstractPacket.writeSlot((ItemStack) data, out);
		}
		
	};
	
	public static final MetaDataType<Boolean> BOOLEAN = new MetaDataType<Boolean>(7, Boolean.class) {

		@Override
		public Boolean read(ByteBuf in) {
			return in.readBoolean();
		}

		@Override
		public void write(Object data, ByteBuf out) {
			out.writeBoolean((boolean) data);
		}
		
	};
	
	public static final MetaDataType<EulerAngle> ROTATION = new MetaDataType<EulerAngle>(8, EulerAngle.class) {

		@Override
		public EulerAngle read(ByteBuf in) {
			return new EulerAngle(in.readFloat(), in.readFloat(), in.readFloat());
		}

		@Override
		public void write(Object data, ByteBuf out) {
			EulerAngle angle = (EulerAngle) data;
			out.writeFloat((float) angle.getX());
			out.writeFloat((float) angle.getY());
			out.writeFloat((float) angle.getZ());
		}
		
	};
	
	public static final MetaDataType<Long> POSISTION = new MetaDataType<Long>(9, Number.class) {

		@Override
		public Long read(ByteBuf in) {
			return in.readLong();
		}

		@Override
		public void write(Object data, ByteBuf out) {
			out.writeLong((long) data);
		}
		
	};
	
	public static final MetaDataType<Long> OPT_POSITION = new MetaDataType<Long>(10, Number.class) {

		@Override
		public Long read(ByteBuf in) {
			if (in.readBoolean()) return in.readLong();
			return null;
		}

		@Override
		public void write(Object data, ByteBuf out) {
			out.writeBoolean(data != null);
			if (data == null) return;
			long l = (long) data;
			out.writeLong(l);
		}
		
	};
	
	public static final MetaDataType<BlockFace> DIRECTION = new MetaDataType<BlockFace>(11, BlockFace.class) {

		@Override
		public BlockFace read(ByteBuf in) {
			int face = AbstractPacket.readVarInt(in);
			switch (face) {
			case 0: return BlockFace.DOWN;
			case 1: return BlockFace.UP;
			case 2: return BlockFace.NORTH;
			case 3: return BlockFace.SOUTH;
			case 4: return BlockFace.WEST;
			case 5: return BlockFace.EAST;
			default: return BlockFace.DOWN;
			}
		}

		@Override
		public void write(Object data, ByteBuf out) {
			BlockFace f = (BlockFace) data;
			int i = 0;
			switch (f) {
			case DOWN: i = 0; break;
			case UP: i = 1; break;
			case NORTH: i = 2; break;
			case SOUTH: i = 3; break;
			case WEST: i = 4; break;
			case EAST: i = 5; break;
			default: break;
			}
			AbstractPacket.writeVarInt(i, out);
		}
		
	};
	
	public static final MetaDataType<UUID> OPT_UUID = new MetaDataType<UUID>(12, UUID.class, true) {

		@Override
		public UUID read(ByteBuf in) {
			if (!in.readBoolean()) return null;
			long most = in.readLong();
			long least = in.readLong();
			return new UUID(most, least);
		}

		@Override
		public void write(Object data, ByteBuf out) {
			out.writeBoolean(data != null);
			if (data == null) return;
			UUID uid = (UUID) data;
			out.writeLong(uid.getMostSignificantBits());
			out.writeLong(uid.getLeastSignificantBits());
		}
		
	};
	
	public static final MetaDataType<Integer> OPT_BLOCKSTATE = new MetaDataType<Integer>(13, Number.class, true) {

		@Override
		public Integer read(ByteBuf in) {
			return AbstractPacket.readVarInt(in);
		}

		@Override
		public void write(Object data, ByteBuf out) {
			int i = (int) data;
			AbstractPacket.writeVarInt(data != null ? i : 0, out);
		}
		
	};
	
	public static final MetaDataType<CompoundTag> NBT_DATA = new MetaDataType<CompoundTag>(14, CompoundTag.class, true) {

		@Override
		public CompoundTag read(ByteBuf in) throws IOException {
			NBTNIOReader reader = new NBTNIOReader(in);
			CompoundTag tag = (CompoundTag) reader.readNBT();
			reader.close();
			return tag;
		}

		@Override
		public void write(Object data, ByteBuf out) throws IOException {
			NBTNIOWriter writer = new NBTNIOWriter(out);
			if (data == null)
				writer.writeEmptyCompound(null);
			else
				writer.writeNBT((NBT) data);
			writer.close();
		}
		
	};
	
	public static final ParticleMetaDataType PARTICLE = new ParticleMetaDataType();
			
	public static final class ParticleMetaDataType extends MetaDataType<ParticleObject> {

		public ParticleMetaDataType() {
			super(15, ParticleObject.class);
		}

		@Override
		public ParticleObject read(ByteBuf in) throws IOException {
			Particle p = Particle.getByID(AbstractPacket.readVarInt(in));
			return new ParticleObject(p, read(p, in));
		}
		
		public Object read(Particle particle, ByteBuf in) throws IOException {
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
				data = AbstractPacket.readSlot(in);
				break;
			default: break;
			}
			return data;
		}

		@Override
		public void write(Object data, ByteBuf out) throws IOException {
			ParticleObject obj = (ParticleObject) data;
			write(obj.getParticle(), obj.getData(), false, out);
		}
		
		public void write(Particle particle, Object data, boolean dataOnly, ByteBuf out) throws IOException {
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
				AbstractPacket.writeSlot((ItemStack) data, out);
				break;
			default: break;
			}
		}
		
	};
	
	public static final MetaDataType<VillagerData> VILLAGER_DATA = new MetaDataType<VillagerData>(16, VillagerData.class) {

		@Override
		public VillagerData read(ByteBuf in) {
			VillagerType t = VillagerType.getByID(AbstractPacket.readVarInt(in));
			VillagerProfession p = VillagerProfession.getByID(AbstractPacket.readVarInt(in));
			int lvl = AbstractPacket.readVarInt(in);
			return new VillagerData(t, p, lvl);
		}

		@Override
		public void write(Object data, ByteBuf out) {
			VillagerData v = (VillagerData) data;
			AbstractPacket.writeVarInt(v.getType().getID(), out);
			AbstractPacket.writeVarInt(v.getProfession().getID(), out);
			AbstractPacket.writeVarInt(v.getLevel(), out);
		}
		
	};
	
	public static final MetaDataType<Integer> OPT_INT = new MetaDataType<Integer>(17, Number.class, true) {

		@Override
		public Integer read(ByteBuf in) {
			int i = AbstractPacket.readVarInt(in);
			return i == 0 ? null : i-1;
		}

		@Override
		public void write(Object data, ByteBuf out) {
			if (data == null) AbstractPacket.writeVarInt(0, out);
			int i = (int) data;
			AbstractPacket.writeVarInt(i+1, out);
		}
		
	};
	
	public static final MetaDataType<Pose> POSE = new MetaDataType<Pose>(18, Pose.class) {

		@Override
		public Pose read(ByteBuf in) {
			return Pose.getByID(AbstractPacket.readVarInt(in));
		}

		@Override
		public void write(Object data, ByteBuf out) {
			Pose p = (Pose) data;
			AbstractPacket.writeVarInt(p.getID(), out);
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
	
	public int getType() {
		return type;
	}
	
	public Class<?> getTypeClass() {
		return clazz;
	}
	
	public boolean isOptional() {
		return optional;
	}
	
	public abstract T read(ByteBuf in) throws IOException;
	
	public abstract void write(Object data, ByteBuf out) throws IOException;

}
