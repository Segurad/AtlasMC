package de.atlasmc.node.entity;

import org.joml.Vector3f;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.util.annotation.UnsafeAPI;

public interface ArmorStand extends LivingEntity {
	
	public static final NBTCodec<ArmorStand>
	NBT_HANDLER = NBTCodec
					.builder(ArmorStand.class)
					.include(LivingEntity.NBT_HANDLER)
					.intField("DisabledSlots", ArmorStand::getSlotInteractionFlags, ArmorStand::setSlotInteractionFlags, 0)
					.boolField("Invisible", ArmorStand::isInvisible, ArmorStand::setInvisible, false)
					.boolField("Marker", ArmorStand::isMarker, ArmorStand::setMarker, false)
					.boolField("NoBasePlate", ArmorStand::hasNoBasePlate, ArmorStand::setNoBasePlate, false)
					.beginComponent("Pose")
					.codec("Body", ArmorStand::getBodyPoseUnsafe, ArmorStand::setBodyPose, NBTCodecs.VECTOR_3F)
					.codec("Head", ArmorStand::getHeadPoseUnsafe, ArmorStand::setHeadPose, NBTCodecs.VECTOR_3F)
					.codec("LeftArm", ArmorStand::getLeftArmPoseUnsafe, ArmorStand::setLeftArmPose, NBTCodecs.VECTOR_3F)
					.codec("LeftLeg", ArmorStand::getLeftLegPoseUnsafe, ArmorStand::setLeftLegPose, NBTCodecs.VECTOR_3F)
					.codec("RightArm", ArmorStand::getRightArmPoseUnsafe, ArmorStand::setRightArmPose, NBTCodecs.VECTOR_3F)
					.codec("RightLeg", ArmorStand::getRightLegPoseUnsafe, ArmorStand::setRightLegPose, NBTCodecs.VECTOR_3F)
					.endComponent()
					.boolField("ShowArms", ArmorStand::hasArms, ArmorStand::setArms, false)
					.boolField("Small", ArmorStand::isSmall, ArmorStand::setSmall, false)
					.build();

	default Vector3f getBodyPose() {
		return getBodyPose(new Vector3f());
	}

	default Vector3f getHeadPose() {
		return getHeadPose(new Vector3f());
	}

	default Vector3f getLeftArmPose() {
		return getLeftArmPose(new Vector3f());
	}

	default Vector3f getLeftLegPose() {
		return getLeftLegPose(new Vector3f());
	}

	default Vector3f getRightArmPose() {
		return getRightArmPose(new Vector3f());
	}

	default Vector3f getRightLegPose() {
		return getRightLegPose(new Vector3f());
	}
	
	@UnsafeAPI
	Vector3f getBodyPoseUnsafe();

	@UnsafeAPI
	Vector3f getHeadPoseUnsafe();

	@UnsafeAPI
	Vector3f getLeftArmPoseUnsafe();

	@UnsafeAPI
	Vector3f getLeftLegPoseUnsafe();

	@UnsafeAPI
	Vector3f getRightArmPoseUnsafe();

	@UnsafeAPI
	Vector3f getRightLegPoseUnsafe();
	
	Vector3f getBodyPose(Vector3f angle);

	Vector3f getHeadPose(Vector3f angle);

	Vector3f getLeftArmPose(Vector3f angle);

	Vector3f getLeftLegPose(Vector3f angle);

	Vector3f getRightArmPose(Vector3f angle);

	Vector3f getRightLegPose(Vector3f angle);

	void setBodyPose(Vector3f angle);

	void setBodyPose(float x, float y, float z);
	
	void setHeadPose(Vector3f angle);
	
	void setHeadPose(float x, float y, float z);

	void setLeftArmPose(Vector3f angle);

	void setLeftArmPose(float x, float y, float z);
	
	void setLeftLegPose(Vector3f angle);
	
	void setLeftLegPose(float x, float y, float z);

	void setRightArmPose(Vector3f angle);
	
	void setRightArmPose(float x, float y, float z);

	void setRightLegPose(Vector3f angle);
	
	void setRightLegPose(float x, float y, float z);
	
	default Vector3f getLimbPos(LimbType limb, Vector3f angle) {
		if (limb == null)
			throw new IllegalArgumentException("Limb can not be null!");
		switch (limb) {
		case BODY:
			return getBodyPose(angle);
		case HEAD:
			return getHeadPose(angle);
		case LEFT_ARM:
			return getLeftArmPose(angle);
		case LEFT_LEG:
			return getLeftLegPose(angle);
		case RIGHT_ARM:
			return getRightArmPose(angle);
		case RIGHT_LEG:
			return getRightLegPose(angle);
		default:
			return getHeadPose(angle);
		}
	}
	
	default void setLimbPos(LimbType limb, Vector3f angle) {
		if (limb == null)
			throw new IllegalArgumentException("Limb can not be null!");
		switch (limb) {
		case BODY: {
			setBodyPose(angle);
			break;
		}
		case HEAD: {
			setHeadPose(angle);
			break;
		}
		case LEFT_ARM: {
			setLeftArmPose(angle);
			break;
		}
		case LEFT_LEG: {
			setLeftLegPose(angle);
			break;
		}
		case RIGHT_ARM: {
			setRightArmPose(angle);
			break;
		}
		case RIGHT_LEG: {
			setRightLegPose(angle);
			break;
		}
		default:
			break;
		}
	}
	
	boolean isSmall();
	
	void setSmall(boolean small);
	
	boolean hasArms();
	
	void setArms(boolean arms);
	
	boolean hasNoBasePlate();
	
	void setNoBasePlate(boolean baseplate);
	
	/**
	 * Returns whether or not this {@link ArmorStand} is a marker. Meaning having no hitbox
	 * @return true if is a marker
	 */
	boolean isMarker();
	
	void setMarker(boolean marker);
	
	public static enum LimbType {
		HEAD, BODY, RIGHT_LEG, LEFT_LEG, RIGHT_ARM, LEFT_ARM;
	}

	/**
	 * Sets the slot interaction flags
	 * @see #getSlotInteractionFlags()
	 * @param flags the interaction flags
	 */
	void setSlotInteractionFlags(int flags);
	
	/**
	 * Returns the slot interaction flags as integer
	 * <table>
	 * <tr><th>Bitmask</th><th>Description</th></tr>
	 * <tr><td>0x000001</td><td>Disable remove items from hand</td></tr>
	 * <tr><td>0x000002</td><td>Disable remove item from foots</td></tr>
	 * <tr><td>0x000004</td><td>Disable remove item from legs</td></tr>
	 * <tr><td>0x000008</td><td>Disable remove item from body</td></tr>
	 * <tr><td>0x000010</td><td>Disable remove item from head</td></tr>
	 * <tr><td>0x000100</td><td>Disable swap items in hand</td></tr>
	 * <tr><td>0x000200</td><td>Disable swap item from foots</td></tr>
	 * <tr><td>0x000400</td><td>Disable swap item from legs</td></tr>
	 * <tr><td>0x000800</td><td>Disable swap item from body</td></tr>
	 * <tr><td>0x001000</td><td>Disable swap item from head</td></tr>
	 * <tr><td>0x010000</td><td>Disable set items in hand</td></tr>
	 * <tr><td>0x020000</td><td>Disable set item to foots</td></tr>
	 * <tr><td>0x040000</td><td>Disable set item to legs</td></tr>
	 * <tr><td>0x080000</td><td>Disable set item to body</td></tr>
	 * <tr><td>0x100000</td><td>Disable set item to head</td></tr>
	 * <tr><td></td><td></td></tr>
	 * <tr><td>0x1F1F1F</td><td>Disable all actions</td></tr>
	 * </table>
	 * @return flags
	 */
	int getSlotInteractionFlags();
	
	@Override
	default NBTCodec<? extends ArmorStand> getNBTCodec() {
		return NBT_HANDLER;
	}

}
