package de.atlasmc.node.block.data.property;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.node.Axis;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.Bisected.Half;
import de.atlasmc.node.block.data.FaceAttachable.AttachedFace;
import de.atlasmc.node.block.data.HightConnectable.Height;
import de.atlasmc.node.block.data.Orientable.Orientation;
import de.atlasmc.node.block.data.type.Instrument;
import de.atlasmc.node.block.data.type.Bamboo.Leaves;
import de.atlasmc.node.block.data.type.Bed.Part;
import de.atlasmc.node.block.data.type.Bell.Attachment;
import de.atlasmc.node.block.data.type.BigDripleaf.Tilt;
import de.atlasmc.node.block.data.type.Door.Hinge;
import de.atlasmc.node.block.data.type.PointedDripstone.Thickness;
import de.atlasmc.node.block.data.type.PointedDripstone.VerticalDirection;
import de.atlasmc.node.block.data.type.RedstoneWire.Connection;
import de.atlasmc.node.block.data.type.SculkSensor.Phase;
import de.atlasmc.node.block.data.type.TrialSpawner.TrialSpawnerState;
import de.atlasmc.node.block.data.type.Vault.VaultState;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.map.key.CharKey;

public abstract class PropertyType<T> {
	
	private static final Map<CharSequence, Map<TagType, PropertyType<?>>> properties = new ConcurrentHashMap<>();

	public static final PropertyType<BlockFace> FACING = new FacingProperty();
	public static final PropertyType<Boolean> WATERLOGGED = new WaterloggedProperty();
	public static final PropertyType<Leaves> LEAVES = new LeavesProperty();
	public static final PropertyType<Integer> STAGE = new StageProperty();
	public static final PropertyType<BlockFace> ROTATION = new RotationProperty();
	public static final PropertyType<Boolean> OPEN = new OpenProperty();
	public static final PropertyType<Axis> AXIS = new AxisProperty();
	public static final PropertyType<Boolean> OCCUPIED = new OccupiedProperty();
	public static final PropertyType<Part> PART = new PartProperty();
	public static final PropertyType<Integer> HONEY_LEVEL = new HoneyLevelProperty();
	public static final PropertyType<Attachment> ATTACHEMENT = new AttachementProperty();
	public static final PropertyType<Boolean> POWERED = new PoweredProperty();
	public static final PropertyType<Tilt> TILT = new TiltProperty();
	public static final PropertyType<Boolean> LIT = new LitProperty();
	public static final PropertyType<Boolean> HAS_BOTTLE_0 = new HasBottleProperty("has_bottle_0", 0);
	public static final PropertyType<Boolean> HAS_BOTTLE_1 = new HasBottleProperty("has_bottle_1", 1);
	public static final PropertyType<Boolean> HAS_BOOTLE_2 = new HasBottleProperty("has_bottle_2", 2);
	public static final PropertyType<Boolean> DRAG = new DragProperty();
	public static final PropertyType<AttachedFace> FACE = new FaceProperty();
	public static final PropertyType<Integer> BITES = new BitesProperty();
	public static final PropertyType<Boolean> SIGNAL_FIRE = new SignalFireProperty();
	public static final PropertyType<Integer> CANDLES = new CandlesProperty();
	public static final PropertyType<Integer> LEVEL = new LevelProperty();
	public static final PropertyType<Boolean> BERRIES = new BerriesProperty();
	/**
	 * Used for because reasons:
	 * <ul>
	 * <li>{@link de.atlasmc.node.block.data.type.TechnicalPiston.Type}</li>
	 * <li>{@link de.atlasmc.node.block.data.type.Chest.Type}</li>
	 * <li>{@link de.atlasmc.node.block.data.type.Slab.Type}</li>
	 * </ul>
	 */
	public static final PropertyType<Enum<?>> TYPE = new TypeProperty();
	public static final PropertyType<Boolean> SLOT_0_OCCUPIED = new SlotOccupiedProperty("slot_0_occupied", 0);
	public static final PropertyType<Boolean> SLOT_1_OCCUPIED = new SlotOccupiedProperty("slot_1_occupied", 1);
	public static final PropertyType<Boolean> SLOT_2_OCCUPIED = new SlotOccupiedProperty("slot_2_occupied", 2);
	public static final PropertyType<Boolean> SLOT_3_OCCUPIED = new SlotOccupiedProperty("slot_3_occupied", 3);
	public static final PropertyType<Boolean> SLOT_4_OCCUPIED = new SlotOccupiedProperty("slot_4_occupied", 4);
	public static final PropertyType<Boolean> SLOT_5_OCCUPIED = new SlotOccupiedProperty("slot_5_occupied", 5);
	public static final PropertyType<Boolean> DOWN = new MultipleFacingProperty(BlockFace.DOWN);
	public static final PropertyType<Boolean> EAST = new MultipleFacingProperty(BlockFace.EAST);
	public static final PropertyType<Boolean> NORTH = new MultipleFacingProperty(BlockFace.NORTH);
	public static final PropertyType<Boolean> UP = new MultipleFacingProperty(BlockFace.UP);
	public static final PropertyType<Boolean> WEST = new MultipleFacingProperty(BlockFace.WEST);
	public static final PropertyType<Boolean> SOUTH = new MultipleFacingProperty(BlockFace.SOUTH);
	public static final PropertyType<Boolean> CONDITIONAL = new ConditionalProperty();
	public static final PropertyType<Boolean> CRAFTING = new CraftingProperty();
	public static final PropertyType<Orientation> ORIENTATION = new OrientationProperty();
	public static final PropertyType<Boolean> TRIGGERED = new TriggeredProperty();
	public static final PropertyType<Boolean> ACTIVE = new ActiveProperty();
	public static final PropertyType<Boolean> NATURAL = new NaturalProperty();
	public static final PropertyType<Boolean> INVERTED = new InvertedProperty();
	public static final PropertyType<Integer> POWER = new PowerProperty();
	public static final PropertyType<Boolean> CRACKED = new CrackedProperty();
	public static final PropertyType<Half> HALF = new HalfProperty();
	public static final PropertyType<Hinge> HINGE = new HingeProperty();
	public static final PropertyType<Boolean> EYE = new EyeProperty();
	public static final PropertyType<Integer> MOISTURE = new MoistureProperty();
	public static final PropertyType<Boolean> IN_WALL = new InWallProperty();
	public static final PropertyType<Boolean> SNOWY = new SnowyProperty();
	public static final PropertyType<Boolean> ENABLED = new EnabledProperty();
	public static final PropertyType<Boolean> HAS_RECORD = new HasRecordProperty();
	public static final PropertyType<Boolean> HANGING = new HangingProperty();
	public static final PropertyType<Integer> DISTANCE = new DistanceProperty();
	public static final PropertyType<Boolean> PERSISTENT = new PersistentProperty();
	public static final PropertyType<Boolean> HAS_BOOK = new HasBookProperty();
	public static final PropertyType<Instrument> INSTRUMENT = new InstrumentProperty();
	public static final PropertyType<Integer> NOTE = new NoteProperty();
	public static final PropertyType<Integer> FLOWER_AMOUNT = new FlowerAmountProperty();
	public static final PropertyType<Boolean> EXTENDED = new ExtendedProperty();
	public static final PropertyType<Boolean> SHORT = new ShortProperty();
	public static final PropertyType<Thickness> THICKNESS = new ThicknessProperty();
	public static final PropertyType<VaultState> VAULT_STATE = new VaultStateProperty();
	public static final PropertyType<Boolean> TIP = new TipProperty();
	public static final PropertyType<Integer> HYDRATION = new HydrationProperty();

	/**
	 * Used for because reasons:
	 * <ul>
	 * <li>{@link de.atlasmc.node.block.data.Rail.Shape}</li>
	 * <li>{@link de.atlasmc.node.block.data.type.Stairs.Shape}</li>
	 * </ul>
	 */
	public static final PropertyType<Enum<?>> SHAPE = new ShapeProperty();
	/**
	 * Used for because reasons:
	 * <ul>
	 * <li>{@link de.atlasmc.node.block.data.type.Comparator.Mode}</li>
	 * <li>{@link de.atlasmc.node.block.data.type.StructureBlock.Mode}</li>
	 * <li>{@link de.atlasmc.node.block.data.type.TestBlock.Mode}</li>
	 * </ul>
	 */
	public static final PropertyType<Enum<?>> MODE = new ModeProperty();
	/**
	 * Used for because reasons:
	 * <ul>
	 * <li>{@link Connection}</li>
	 * <li>{@link Height}</li>
	 * </ul>
	 */
	public static final PropertyType<Enum<?>> CON_EAST = new ConnectionProperty(BlockFace.EAST);
	/**
	 * Used for because reasons:
	 * <ul>
	 * <li>{@link Connection}</li>
	 * <li>{@link Height}</li>
	 * </ul>
	 */
	public static final PropertyType<Enum<?>> CON_NORTH = new ConnectionProperty(BlockFace.NORTH);
	/**
	 * Used for because reasons:
	 * <ul>
	 * <li>{@link Connection}</li>
	 * <li>{@link Height}</li>
	 * </ul>
	 */
	public static final PropertyType<Enum<?>> CON_SOUTH = new ConnectionProperty(BlockFace.SOUTH);
	/**
	 * Used for because reasons:
	 * <ul>
	 * <li>{@link Connection}</li>
	 * <li>{@link Height}</li>
	 * </ul>
	 */
	public static final PropertyType<Enum<?>> CON_WEST = new ConnectionProperty(BlockFace.WEST);
	public static final PropertyType<Integer> DELAY = new DelayProperty();
	public static final PropertyType<Boolean> LOCKED = new LockedProperty();
	public static final PropertyType<Integer> CHARGES = new ChargesProperty();
	public static final PropertyType<Boolean> BOTTOM = new BottomProperty();
	public static final PropertyType<Boolean> BLOOM = new BloomProperty();
	public static final PropertyType<Phase> SCULK_SENSOR_PHASE = new SculkSensorPhaseProperty();
	public static final PropertyType<Boolean> CAN_SUMMON = new CanSummonProperty();
	public static final PropertyType<Boolean> SHRIEKING = new ShriekingProperty();
	public static final PropertyType<Integer> PICKLES = new PicklesProperty();
	public static final PropertyType<Integer> HATCH = new HatchProperty();
	public static final PropertyType<Integer> LAYERS = new LayersProperty();
	public static final PropertyType<Integer> DUSTED = new DustedProperty();
	public static final PropertyType<Boolean> UNSTABLE = new UnstableProperty();
	public static final PropertyType<Boolean> OMINOUS = new OminousProperty();
	public static final PropertyType<TrialSpawnerState> TRIAL_SPAWNER_STATE = new TrialSpawnerStateProperty();
	public static final PropertyType<Boolean> DISARMED = new DisarmedProperty();
	public static final PropertyType<Boolean> ATTACHED = new AttachedProperty();
	public static final PropertyType<Integer> EGGS = new EggsProperty();
	public static final PropertyType<Integer> AGE = new AgeProperty();
	public static final PropertyType<VerticalDirection> VERTICAL_DIRECTION = new VerticalDirectionProperty();
	public static final PropertyType<Integer> SEGMENT_AMOUNT = new SegmentAmountProperty();
	
	protected final CharKey key;

	public PropertyType(String key) {
		this(CharKey.literal(key));
	}

	public PropertyType(CharKey key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		this.key = key;
		synchronized (properties) {
			Map<TagType, PropertyType<?>> typeProperties = properties.get(key);
			if (typeProperties == null) {
				properties.put(key, typeProperties = new ConcurrentHashMap<>(3));
			}
			TagType type = getType();
			if (typeProperties.containsKey(type))
				throw new IllegalArgumentException(
						"Property with key: " + key + " and type: " + type + " already exists!");
			typeProperties.put(type, this);
		}
	}

	@NotNull
	public CharKey getKey() {
		return key;
	}

	/**
	 * Returns the {@link TagType} of this property
	 * @return tag type
	 */
	@NotNull
	public abstract TagType getType();

	/**
	 * Reads the property from a NBT steam
	 * @param reader to read from
	 * @return value
	 * @throws IOException
	 */
	@Nullable
	public abstract T fromNBT(NBTReader reader) throws IOException;

	public abstract void toNBT(T value, NBTWriter writer, boolean systemData) throws IOException;

	public abstract void set(BlockData data, T value);

	/**
	 * Returns the properties value of the given BlockData or null of not valid
	 * @param data to fetch from
	 * @return value or null if invalid
	 */
	@Nullable
	public abstract T get(BlockData data);

	/**
	 * Returns the value represented by the string or null if invalid
	 * @param value
	 * @return value or null
	 */
	@Nullable
	public abstract T fromString(String value);

	public abstract String toString(T value);

	public boolean match(BlockData data, T value) {
		T present = get(data);
		if (present == value)
			return true;
		if (present != null)
			return present.equals(value);
		return false;
	}

	public abstract boolean supports(BlockData data);

	/**
	 * Unofficial format reads property as TagType specified by {@link #getType()}.
	 * <b>This is not vanilla!</b>
	 * @param data
	 * @param reader
	 * @throws IOException
	 */
	public void dataFromNBT(BlockData data, NBTReader reader) throws IOException {
		T value = fromNBT(reader);
		if (value == null)
			return;
		set(data, value);
	}
	
	/**
	 * Reads the property data as string field.
	 * @param data
	 * @param reader
	 * @throws IOException
	 */
	public void dataFromNBTString(BlockData data, NBTReader reader) throws IOException {
		T value = fromString(reader.readStringTag());
		if (value == null)
			return;
		set(data, value);
	}

	/**
	 * Unofficial format writes property as TagType specified by {@link #getType()}.
	 * <b>This is not vanilla!</b>
	 * @param data
	 * @param writer
	 * @param systemData
	 * @throws IOException
	 */
	public void dataToNBT(BlockData data, NBTWriter writer, boolean systemData) throws IOException {
		T value = get(data);
		if (value == null)
			return;
		toNBT(value, writer, systemData);
	}
	
	/**
	 * Writes the property data as string field.
	 * @param data
	 * @param writer
	 * @throws IOException
	 */
	public void dataToNBTString(BlockData data, NBTWriter writer, boolean systemData) throws IOException {
		T value = get(data);
		if (value == null)
			return;
		writer.writeStringTag(key, toString(value));
	}

	@Nullable
	public static PropertyType<?> getProperty(CharSequence key, TagType type) {
		Map<TagType, PropertyType<?>> typeProperties = properties.get(key);
		if (typeProperties == null)
			return null;
		return typeProperties.get(type);
	}

	@Nullable
	public static Collection<PropertyType<?>> getProperties(CharSequence key) {
		Map<TagType, PropertyType<?>> typeProperties = properties.get(key);
		if (typeProperties == null)
			return null;
		return typeProperties.values();
	}

}
