package de.atlasmc.block.data.property;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.Axis;
import de.atlasmc.Instrument;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.Bisected.Half;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.FaceAttachable.AttachedFace;
import de.atlasmc.block.data.Orientable.Orientation;
import de.atlasmc.block.data.type.Bamboo.Leaves;
import de.atlasmc.block.data.type.Bed.Part;
import de.atlasmc.block.data.type.Bell.Attachment;
import de.atlasmc.block.data.type.BigDripleaf.Tilt;
import de.atlasmc.block.data.type.Door.Hinge;
import de.atlasmc.block.data.type.PointedDripstone.Thickness;
import de.atlasmc.block.data.type.PointedDripstone.VerticalDirection;
import de.atlasmc.block.data.type.RedstoneWire.Connection;
import de.atlasmc.block.data.type.SculkSensor.Phase;
import de.atlasmc.block.data.type.TrialSpawner.TrialSpawnerState;
import de.atlasmc.block.data.type.Wall.Height;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public abstract class BlockDataProperty<T> {
	
	private static final Map<CharSequence, Map<TagType, BlockDataProperty<?>>> properties = new ConcurrentHashMap<>();
	
	public static final BlockDataProperty<BlockFace> FACING = new FacingProperty();
	public static final BlockDataProperty<Boolean> WATERLOGGED = new WaterloggedProperty();
	public static final BlockDataProperty<Leaves> LEAVES = new LeavesProperty();
	public static final BlockDataProperty<Integer> STAGE = new StageProperty();
	public static final BlockDataProperty<BlockFace> ROTATION = new RotationProperty();
	public static final BlockDataProperty<Boolean> OPEN = new OpenProperty();
	public static final BlockDataProperty<Axis> AXIS = new AxisProperty();
	public static final BlockDataProperty<Boolean> OCCUPIED = new OccupiedProperty();
	public static final BlockDataProperty<Part> PART = new PartProperty();
	public static final BlockDataProperty<Integer> HONEY_LEVEL = new HoneyLevelProperty();
	public static final BlockDataProperty<Attachment> ATTACHEMENT = new AttachementProperty();
	public static final BlockDataProperty<Boolean> POWERED = new PoweredProperty();
	public static final BlockDataProperty<Tilt> TILT = new TiltProperty();
	public static final BlockDataProperty<Boolean> LIT = new LitProperty();
	public static final BlockDataProperty<Boolean> HAS_BOTTLE_0 = new HasBottleProperty("has_bottle_0", 0);
	public static final BlockDataProperty<Boolean> HAS_BOTTLE_1 = new HasBottleProperty("has_bottle_1", 1);
	public static final BlockDataProperty<Boolean> HAS_BOOTLE_2 = new HasBottleProperty("has_bottle_2", 2);
	public static final BlockDataProperty<Boolean> DRAG = new DragProperty();
	public static final BlockDataProperty<AttachedFace> FACE = new FaceProperty();
	public static final BlockDataProperty<Integer> BITES = new BitesProperty();
	public static final BlockDataProperty<Boolean> SIGNAL_FIRE = new SignalFireProperty();
	public static final BlockDataProperty<Integer> CANDLES = new CandlesProperty();
	public static final BlockDataProperty<Integer> LEVEL = new LevelProperty();
	public static final BlockDataProperty<Boolean> BERRIES = new BerriesProperty();
	/**
	 * Used for because reasons:
	 * <ul>
	 * <li>{@link de.atlasmc.block.data.type.TechnicalPiston.Type}</li>
	 * <li>{@link de.atlasmc.block.data.type.Chest.Type}</li>
	 * <li>{@link de.atlasmc.block.data.type.Slab.Type}</li>
	 * </ul>
	 */
	public static final BlockDataProperty<Enum<?>> TYPE = new TypeProperty();
	public static final BlockDataProperty<Boolean> SLOT_0_OCCUPIED = new SlotOccupiedProperty("slot_0_occupied", 0);
	public static final BlockDataProperty<Boolean> SLOT_1_OCCUPIED = new SlotOccupiedProperty("slot_1_occupied", 1);
	public static final BlockDataProperty<Boolean> SLOT_2_OCCUPIED = new SlotOccupiedProperty("slot_2_occupied", 2);
	public static final BlockDataProperty<Boolean> SLOT_3_OCCUPIED = new SlotOccupiedProperty("slot_3_occupied", 3);
	public static final BlockDataProperty<Boolean> SLOT_4_OCCUPIED = new SlotOccupiedProperty("slot_4_occupied", 4);
	public static final BlockDataProperty<Boolean> SLOT_5_OCCUPIED = new SlotOccupiedProperty("slot_5_occupied", 5);
	public static final BlockDataProperty<Boolean> DOWN = new MultipleFacingProperty(BlockFace.DOWN);
	public static final BlockDataProperty<Boolean> EAST = new MultipleFacingProperty(BlockFace.EAST);
	public static final BlockDataProperty<Boolean> NORTH = new MultipleFacingProperty(BlockFace.NORTH);
	public static final BlockDataProperty<Boolean> UP = new MultipleFacingProperty(BlockFace.UP);
	public static final BlockDataProperty<Boolean> WEST = new MultipleFacingProperty(BlockFace.WEST);
	public static final BlockDataProperty<Boolean> SOUTH = new MultipleFacingProperty(BlockFace.SOUTH);
	public static final BlockDataProperty<Boolean> CONDITIONAL = new ConditionalProperty();
	public static final BlockDataProperty<Boolean> CRAFTING = new CraftingProperty();
	public static final BlockDataProperty<Orientation> ORIENTATION = new OrientationProperty();
	public static final BlockDataProperty<Boolean> TRIGGERED = new TriggeredProperty();
	public static final BlockDataProperty<Boolean> ACTIVE = new ActiveProperty();
	public static final BlockDataProperty<Boolean> NATURAL = new NaturalProperty();
	public static final BlockDataProperty<Boolean> INVERTED = new InvertedProperty();
	public static final BlockDataProperty<Integer> POWER = new PowerProperty();
	public static final BlockDataProperty<Boolean> CRACKED = new CrackedProperty();
	public static final BlockDataProperty<Half> HALF = new HalfProperty();
	public static final BlockDataProperty<Hinge> HINGE = new HingeProperty();
	public static final BlockDataProperty<Boolean> EYE = new EyeProperty();
	public static final BlockDataProperty<Integer> MOISTURE = new MoistureProperty();
	public static final BlockDataProperty<Boolean> IN_WALL = new InWallProperty();
	public static final BlockDataProperty<Boolean> SNOWY = new SnowyProperty();
	public static final BlockDataProperty<Boolean> ENABLED = new EnabledProperty();
	public static final BlockDataProperty<Boolean> HAS_RECORD = new HasRecordProperty();
	public static final BlockDataProperty<Boolean> HANGING = new HangingProperty();
	public static final BlockDataProperty<Integer> DISTANCE = new DistanceProperty();
	public static final BlockDataProperty<Boolean> PERSISTENT = new PersistentProperty();
	public static final BlockDataProperty<Boolean> HAS_BOOK = new HasBookProperty();
	public static final BlockDataProperty<Instrument> INSTRUMENT = new InstrumentProperty();
	public static final BlockDataProperty<Integer> NOTE = new NoteProperty();
	public static final BlockDataProperty<Integer> FLOWER_AMOUNT = new FlowerAmountProperty();
	public static final BlockDataProperty<Boolean> EXTENDED = new ExtendedProperty();
	public static final BlockDataProperty<Boolean> SHORT = new ShortProperty();
	public static final BlockDataProperty<Thickness> THICKNESS = new ThicknessProperty();
	/**
	 * Used for because reasons:
	 * <ul>
	 * <li>{@link de.atlasmc.block.data.Rail.Shape}</li>
	 * <li>{@link de.atlasmc.block.data.type.Stairs.Shape}</li>
	 * </ul>
	 */
	public static final BlockDataProperty<Enum<?>> SHAPE = new ShapeProperty();
	/**
	 * Used for because reasons:
	 * <ul>
	 * <li>{@link de.atlasmc.block.data.type.Comparator.Mode}</li> 
	 * <li>{@link de.atlasmc.block.data.type.StructureBlock.Mode}</li>
	 * </ul>
	 */
	public static final BlockDataProperty<Enum<?>> MODE = new ModeProperty();
	/**
	 * Used for because reasons:
	 * <ul>
	 * <li>{@link Connection}</li>
	 * <li>{@link Height}</li>
	 * </ul>
	 */
	public static final BlockDataProperty<Enum<?>> CON_EAST = new ConnectionProperty(BlockFace.EAST);
	/**
	 * Used for because reasons:
	 * <ul>
	 * <li>{@link Connection}</li>
	 * <li>{@link Height}</li>
	 * </ul>
	 */
	public static final BlockDataProperty<Enum<?>> CON_NORTH = new ConnectionProperty(BlockFace.NORTH);
	/**
	 * Used for because reasons:
	 * <ul>
	 * <li>{@link Connection}</li>
	 * <li>{@link Height}</li>
	 * </ul>
	 */
	public static final BlockDataProperty<Enum<?>> CON_SOUTH = new ConnectionProperty(BlockFace.SOUTH);
	/**
	 * Used for because reasons:
	 * <ul>
	 * <li>{@link Connection}</li>
	 * <li>{@link Height}</li>
	 * </ul>
	 */
	public static final BlockDataProperty<Enum<?>> CON_WEST = new ConnectionProperty(BlockFace.WEST);
	public static final BlockDataProperty<Integer> DELAY = new DelayProperty();
	public static final BlockDataProperty<Boolean> LOCKED = new LockedProperty();
	public static final BlockDataProperty<Integer> CHARGES = new ChargesProperty();
	public static final BlockDataProperty<Boolean> BOTTOM = new BottomProperty();
	public static final BlockDataProperty<Boolean> BLOOM = new BloomProperty();
	public static final BlockDataProperty<Phase> SCULK_SENSOR_PHASE = new SculkSensorPhaseProperty();
	public static final BlockDataProperty<Boolean> CAN_SUMMON = new CanSummonProperty();
	public static final BlockDataProperty<Boolean> SHRIEKING = new ShriekingProperty();
	public static final BlockDataProperty<Integer> PICKLES = new PicklesProperty();
	public static final BlockDataProperty<Integer> HATCH = new HatchProperty();
	public static final BlockDataProperty<Integer> LAYERS = new LayersProperty();
	public static final BlockDataProperty<Integer> DUSTED = new DustedProperty();
	public static final BlockDataProperty<Boolean> UNSTABLE = new UnstableProperty();
	public static final BlockDataProperty<Boolean> OMINOUS = new OminousProperty();
	public static final BlockDataProperty<TrialSpawnerState> TRIAL_SPAWNER_STATE = new TrialSpawnerStateProperty();
	public static final BlockDataProperty<Boolean> DISARMED = new DisarmedProperty();
	public static final BlockDataProperty<Boolean> ATTACHED = new AttachedProperty();
	public static final BlockDataProperty<Integer> EGGS = new EggsProperty();
	public static final BlockDataProperty<Integer> AGE = new AgeProperty();
	public static final BlockDataProperty<VerticalDirection> VERTICAL_DIRECTION = new VerticalDirectionProperty();
	
	protected final CharKey key;
	
	public BlockDataProperty(String key) {
		this(CharKey.literal(key));
	}
	
	public BlockDataProperty(CharKey key) {
		this.key = key;
		synchronized (properties) {
			Map<TagType, BlockDataProperty<?>> typeProperties = properties.get(key);
			if (typeProperties == null) {
				properties.put(key, typeProperties = new ConcurrentHashMap<>(3));
			}
			TagType type = getType();
			if (typeProperties.containsKey(type))
				throw new IllegalArgumentException("Property with key: " + key + " and type: " + type + " already exists!");
			typeProperties.put(type, this);
		}
	}
	
	public CharKey getKey() {
		return key;
	}
	
	public abstract TagType getType();
	
	public abstract T fromNBT(NBTReader reader) throws IOException;
	
	public abstract void toNBT(T value, NBTWriter writer, boolean systemData) throws IOException;

	public abstract void set(BlockData data, T value);
	
	public abstract T get(BlockData data);
	
	public boolean match(BlockData data, T value) {
		T present = get(data);
		if (present == value)
			return true;
		if (present != null)
			return present.equals(value);
		return false;
	}
	
	public abstract boolean supports(BlockData data);
	
	public void dataFromNBT(BlockData data, NBTReader reader) throws IOException {
		T value = fromNBT(reader);
		if (value == null)
			return;
		set(data, value);
	}
	
	public void dataToNBT(BlockData data, NBTWriter writer, boolean systemData) throws IOException {
		T value = get(data);
		if (value == null)
			return;
		toNBT(value, writer, systemData);
	}
	
	public static BlockDataProperty<?> getProperty(CharSequence key, TagType type) {
		Map<TagType, BlockDataProperty<?>> typeProperties = properties.get(key);
		if (typeProperties == null)
			return null;
		return typeProperties.get(type);
	}
	
	public static Map<BlockDataProperty<?>, Object> readProperties(NBTReader reader) throws IOException {
		Map<BlockDataProperty<?>, Object> properties = null;
		while (reader.getType() != TagType.TAG_END) {
			CharSequence key = reader.getFieldName();
			TagType type = reader.getType();
			BlockDataProperty<?> property = getProperty(key, type);
			if (property == null) {
				reader.skipTag();
				continue;
			}
			if (properties == null)
				properties = new HashMap<>();
			properties.put(property, property.fromNBT(reader));
		}
		reader.readNextEntry();
		if (properties == null)
			return Map.of();
		return properties;
	}
	
	public static void writeProperties(Map<BlockDataProperty<?>, ?> properties, NBTWriter writer, boolean systemData) throws IOException {
		for (Entry<BlockDataProperty<?>, ?> entry : properties.entrySet()) {
			@SuppressWarnings("unchecked")
			BlockDataProperty<Object> property = (BlockDataProperty<Object>) entry.getKey();
			property.toNBT(entry.getValue(), writer, systemData);
		}
	}
	
}
