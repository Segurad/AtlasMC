package de.atlasmc.entity.data;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.Particle;
import de.atlasmc.block.BlockFace;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.chat.component.FinalComponent;
import de.atlasmc.entity.Entity.Pose;
import de.atlasmc.entity.Villager.VillagerData;
import de.atlasmc.entity.Villager.VillagerProfession;
import de.atlasmc.entity.Villager.VillagerType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.util.EulerAngle;
import de.atlasmc.util.nbt.CompoundTag;
import de.atlasmc.util.nbt.NBT;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import de.atlasmc.world.particle.ParticleObject;
import io.netty.buffer.ByteBuf;

public abstract class MetaDataType<T> {
	
	public static final MetaDataType<Byte> BYTE = new MetaDataType<Byte>(0) {
		
		@Override
		public Byte read(ByteBuf in) {
			return in.readByte();
		}

		@Override
		public void write(Object data, ByteBuf out) {
			out.writeByte((int) data);
		}
		
	};
	public static final MetaDataType<Integer> INT = new MetaDataType<Integer>(1) {

		@Override
		public Integer read(ByteBuf in) {
			return AbstractPacket.readVarInt(in);
		}

		@Override
		public void write(Object data, ByteBuf out) {
			AbstractPacket.writeVarInt((int) data, out);
		}
		
	};
	
	public static final MetaDataType<Float> FLOAT = new MetaDataType<Float>(2) {

		@Override
		public Float read(ByteBuf in) {
			return in.readFloat();
		}

		@Override
		public void write(Object data, ByteBuf out) {
			out.writeFloat((float) data);
		}
		
	};
	
	public static final MetaDataType<String> STRING = new MetaDataType<String>(3) {

		@Override
		public String read(ByteBuf in) {
			return AbstractPacket.readString(in);
		}

		@Override
		public void write(Object data, ByteBuf out) {
			AbstractPacket.writeString((String) data, out);
		}
		
	};
	
	public static final MetaDataType<ChatComponent> CHAT = new MetaDataType<ChatComponent>(4) {

		@Override
		public ChatComponent read(ByteBuf in) {
			return new FinalComponent(AbstractPacket.readString(in));
		}

		@Override
		public void write(Object data, ByteBuf out) {
			ChatComponent chat = (ChatComponent) data;
			AbstractPacket.writeString(chat.getJsonText(), out);
		}
		
	};
	
	public static final MetaDataType<ChatComponent> OPT_CHAT = new MetaDataType<ChatComponent>(5) {

		@Override
		public ChatComponent read(ByteBuf in) {
			if (!in.readBoolean()) return null;
			return new FinalComponent(AbstractPacket.readString(in));
		}

		@Override
		public void write(Object data, ByteBuf out) {
			out.writeBoolean(data != null);
			if (data == null) return;
			ChatComponent chat = (ChatComponent) data;
			AbstractPacket.writeString(chat.getJsonText(), out);
		}
		
	};
	
	public static final MetaDataType<ItemStack> SLOT = new MetaDataType<ItemStack>(6) {

		@Override
		public ItemStack read(ByteBuf in) throws IOException {
			return AbstractPacket.readSlot(in);
		}

		@Override
		public void write(Object data, ByteBuf out) throws IOException {
			AbstractPacket.writeSlot((ItemStack) data, out);
		}
		
	};
	
	public static final MetaDataType<Boolean> BOOLEAN = new MetaDataType<Boolean>(7) {

		@Override
		public Boolean read(ByteBuf in) {
			return in.readBoolean();
		}

		@Override
		public void write(Object data, ByteBuf out) {
			out.writeBoolean((boolean) data);
		}
		
	};
	
	public static final MetaDataType<EulerAngle> ROTATION = new MetaDataType<EulerAngle>(8) {

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
	
	public static final MetaDataType<Long> POSISTION = new MetaDataType<Long>(9) {

		@Override
		public Long read(ByteBuf in) {
			return in.readLong();
		}

		@Override
		public void write(Object data, ByteBuf out) {
			out.writeLong((long) data);
		}
		
	};
	
	public static final MetaDataType<Long> OPT_POSITION = new MetaDataType<Long>(10) {

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
	
	public static final MetaDataType<BlockFace> direction = new MetaDataType<BlockFace>(11) {

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
	
	public static final MetaDataType<UUID> OPT_UUID = new MetaDataType<UUID>(12) {

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
	
	public static final MetaDataType<Integer> OPT_BLOCKSTATE = new MetaDataType<Integer>(13) {

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
	
	public static final MetaDataType<CompoundTag> NBT_DATA = new MetaDataType<CompoundTag>(14) {

		@Override
		public CompoundTag read(ByteBuf in) throws IOException {
			return (CompoundTag) new NBTNIOReader(in).readNBT();
		}

		@Override
		public void write(Object data, ByteBuf out) throws IOException {
			new NBTNIOWriter(out).writeNBT((NBT) data);
		}
		
	};

	
	public static final MetaDataType<ParticleObject> PARTICLE = new MetaDataType<ParticleObject>(15) {

		@Override
		public ParticleObject read(ByteBuf in) throws IOException {
			Particle p = Particle.getByID(AbstractPacket.readVarInt(in));
			Object data = null;
			switch (p) {
			case BLOCK:
			case FALLING_DUST: 
				data = AbstractPacket.readVarInt(in);
				break;
			case DUST:
				// TODO
				in.readFloat();
				in.readFloat();
				in.readFloat();
				in.readFloat();
				break;
			case ITEM:
				data = AbstractPacket.readSlot(in);
				break;
			default: break;
			}
			return new ParticleObject(p, 1, data);
		}

		@Override
		public void write(Object data, ByteBuf out) throws IOException {
			ParticleObject o = (ParticleObject) data;
			Particle p = o.getParticle();
			AbstractPacket.writeVarInt(p.getID(), out);
			switch (p) {
			case BLOCK:
			case FALLING_DUST: 
				AbstractPacket.writeVarInt(o.getData() != null ? (int) o.getData() : 0, out);
				break;
			case DUST:
				// TODO
				out.writeFloat(0);
				out.writeFloat(0);
				out.writeFloat(0);
				out.writeFloat(0);
				break;
			case ITEM:
				AbstractPacket.writeSlot((ItemStack) o.getData(), out);
				break;
			default: break;
			}
		}
		
	};
	
	public static final MetaDataType<VillagerData> VILLAGER_DATA = new MetaDataType<VillagerData>(16) {

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
	
	public static final MetaDataType<Integer> OPT_INT = new MetaDataType<Integer>(17) {

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
	
	public static final MetaDataType<Pose> POSE = new MetaDataType<Pose>(18) {

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
	
	public MetaDataType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	
	public abstract T read(ByteBuf in) throws IOException;
	
	public abstract void write(Object data, ByteBuf out) throws IOException;

}
