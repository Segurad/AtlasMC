package de.atlasmc.node.entity;

import java.util.UUID;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3i;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.codec.StreamCodecs;
import de.atlasmc.io.codec.StringCodec;
import de.atlasmc.io.codec.UUIDCodec;
import de.atlasmc.io.metadata.BooleanMetaType;
import de.atlasmc.io.metadata.ByteMetaType;
import de.atlasmc.io.metadata.FloatMetaType;
import de.atlasmc.io.metadata.MetaDataType;
import de.atlasmc.io.metadata.NBTMetaType;
import de.atlasmc.io.metadata.OptVarIntMetaType;
import de.atlasmc.io.metadata.RegistryValueMetaType;
import de.atlasmc.io.metadata.StreamCodecMetaType;
import de.atlasmc.io.metadata.VarIntEnumMetaType;
import de.atlasmc.io.metadata.VarIntMetaType;
import de.atlasmc.io.metadata.VarLongMetaType;
import de.atlasmc.nbt.tag.CompoundTag;
import de.atlasmc.node.DyeColor;
import de.atlasmc.node.WorldLocation;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.entity.AbstractVillager.VillagerData;
import de.atlasmc.node.entity.Entity.Pose;
import de.atlasmc.node.entity.component.ArmadilloMetaComponent;
import de.atlasmc.node.entity.component.CatMetaComponent;
import de.atlasmc.node.entity.component.FrogMetaComponent;
import de.atlasmc.node.entity.component.PaintingMetaComponent;
import de.atlasmc.node.entity.component.PandaMetaComponent;
import de.atlasmc.node.entity.component.SnifferMetaComponent;
import de.atlasmc.node.entity.component.WolfMetaComponent.WolfSoundVariant;
import de.atlasmc.node.entity.component.WolfMetaComponent.WolfVariant;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.io.metadata.DirectionMetaType;
import de.atlasmc.node.io.metadata.OptPositionMetaType;
import de.atlasmc.node.io.metadata.PositionMetaType;
import de.atlasmc.node.io.metadata.WorldLocationMetaType;
import de.atlasmc.node.world.particle.Particle;

public class EntityMetaTypes {

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
	
	public static final MetaDataType<String> STRING = new StreamCodecMetaType<>(TYPE_ID_STRING, false, StringCodec.MAX_LENGTH_CODEC);
	
	public static final MetaDataType<Chat> CHAT = new StreamCodecMetaType<>(TYPE_ID_CHAT, false, Chat.STREAM_CODEC);
	
	public static final MetaDataType<Chat> OPT_CHAT = new StreamCodecMetaType<>(TYPE_ID_OPT_CHAT, true, StreamCodec.optNullable(Chat.STREAM_CODEC));
	
	public static final MetaDataType<ItemStack> SLOT = new StreamCodecMetaType<>(TYPE_ID_SLOT, false, ItemStack.STREAM_CODEC, ItemStack::clone);
	
	public static final MetaDataType<Boolean> BOOLEAN = new BooleanMetaType(TYPE_ID_BOOLEAN);
	
	public static final MetaDataType<Vector3f> ROTATION = new StreamCodecMetaType<>(TYPE_ID_ROTATION, false, StreamCodecs.VECTOR_3F, t -> { return new Vector3f(t);});;
	
	public static final MetaDataType<Vector3i> POSITION = new PositionMetaType(TYPE_ID_POSITION);
	
	public static final MetaDataType<Vector3i> OPT_POSITION = new OptPositionMetaType(TYPE_ID_OPT_POSITION);
	
	public static final MetaDataType<BlockFace> DIRECTION = new DirectionMetaType(TYPE_ID_DIRECTION);
	
	public static final MetaDataType<UUID> OPT_UUID = new StreamCodecMetaType<>(TYPE_ID_OPT_UUID, true, StreamCodec.optNullable(UUIDCodec.STREAM_CODEC));
    
	public static final MetaDataType<Integer> BLOCKSTATE = new VarIntMetaType(TYPE_ID_BLOCKSTATE);
	
	public static final MetaDataType<Integer> OPT_BLOCKSTATE = new VarIntMetaType(TYPE_ID_OPT_BLOCKSTATE, true); 
	
	public static final MetaDataType<CompoundTag> NBT_DATA = new NBTMetaType(TYPE_ID_NBT);
	
	public static final MetaDataType<Particle> PARTICLE = new StreamCodecMetaType<>(TYPE_ID_PARTICLE, false, Particle.STREAM_CODEC);
	
	public static final MetaDataType<VillagerData> VILLAGER_DATA = new StreamCodecMetaType<>(TYPE_ID_VILLAGER_DATA, false, VillagerData.STREAM_CODEC, VillagerData::clone);
	
	public static final MetaDataType<Pose> POSE = new VarIntEnumMetaType<>(TYPE_ID_POSE, Pose.class);
    
    public static final MetaDataType<CatMetaComponent.Type> CAT_VARIANT = new VarIntEnumMetaType<>(TYPE_ID_CAT_VARIANT, CatMetaComponent.Type.class);
	
    public static final MetaDataType<PaintingMetaComponent.Motive> PAINTING_VARIANT = new VarIntEnumMetaType<>(TYPE_ID_PAINTING_VARIANT, PaintingMetaComponent.Motive.class);

    public static final MetaDataType<Vector3f> VECTOR_3F = new StreamCodecMetaType<>(TYPE_ID_VECTOR_3F, false, StreamCodecs.VECTOR_3F, t -> { return new Vector3f(t);});
    
    public static final MetaDataType<Quaternionf> QUATERNION_F = new StreamCodecMetaType<>(TYPE_ID_QUATERNION_F, false, StreamCodecs.QUATERNION_F, t -> { return new Quaternionf(t);});
    
    public static final MetaDataType<FrogMetaComponent.Variant> FROG_VARIANT = new VarIntEnumMetaType<>(TYPE_ID_FROG_VARIANT, FrogMetaComponent.Variant.class);
    
    public static final MetaDataType<SnifferMetaComponent.State> SNIFFER_STATE = new VarIntEnumMetaType<>(TYPE_ID_SNIFFER_STATE, SnifferMetaComponent.State.class);
    
    public static final MetaDataType<WorldLocation> OPT_GLOBAL_POSITION = new WorldLocationMetaType(TYPE_ID_OPT_GLOBAL_POSITION);
    
    public static final MetaDataType<ArmadilloMetaComponent.ArmadilloState> ARMADILLO_STATE = new VarIntEnumMetaType<>(TYPE_ID_ARMADILLO_STATE, ArmadilloMetaComponent.ArmadilloState.class);
	
	public static final MetaDataType<WolfVariant> WOLF_VARIANT = new RegistryValueMetaType<>(TYPE_ID_WOLF_VARIANT, WolfVariant.class, WolfVariant.REGISTRY_KEY);
	
	public static final MetaDataType<WolfSoundVariant> WOLF_SOUND_VARIANT = new RegistryValueMetaType<>(TYPE_ID_WOLF_SOUND_VARIANT, WolfSoundVariant.class, WolfSoundVariant.REGISTRY_KEY);
	
	public static final MetaDataType<DyeColor> VAR_INT_COLOR = new VarIntEnumMetaType<>(TYPE_ID_VAR_INT, DyeColor.class);
	
	public static final MetaDataType<PandaMetaComponent.Gene> PANDA_GENE = new VarIntEnumMetaType<>(TYPE_ID_ARMADILLO_STATE, PandaMetaComponent.Gene.class);
	
	private static final MetaDataType<?>[] TYPES = new MetaDataType<?>[] {
		BYTE,
		VAR_INT,
		VAR_LONG,
		FLOAT,
		STRING,
		CHAT,
		OPT_CHAT,
		SLOT,
		BOOLEAN,
		ROTATION,
		POSITION,
		OPT_POSITION,
		DIRECTION,
		OPT_UUID,
		BLOCKSTATE, // BlockID in wiki
		OPT_BLOCKSTATE, // OptBlockID in wiki
		NBT_DATA,
		PARTICLE,
		VILLAGER_DATA,
		OPT_VAR_INT,
		POSE,
		CAT_VARIANT,
		WOLF_VARIANT,
		WOLF_SOUND_VARIANT,
		FROG_VARIANT,
		//PIG_VARIANT,
		//CHICKEN_VARIANT,
		OPT_GLOBAL_POSITION,
		PAINTING_VARIANT,
		SNIFFER_STATE,
		ARMADILLO_STATE,
		//COPPER_GOLEM_STATE,
		//WEATHERING_COPPER_STATE,
		VECTOR_3F,
		QUATERNION_F,
		//RESOLVED_PROFILE
	};
	
	public static MetaDataType<?> getByID(int id) {
		return TYPES[id];
	}
	
}
