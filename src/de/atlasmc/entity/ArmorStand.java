package de.atlasmc.entity;

import de.atlasmc.util.EulerAngle;

public interface ArmorStand extends LivingEntity {

	public default EulerAngle getBodyPose() {
		return getBodyPose(new EulerAngle());
	}

	public default EulerAngle getHeadPose() {
		return getHeadPose(new EulerAngle());
	}

	public default EulerAngle getLeftArmPose() {
		return getLeftArmPose(new EulerAngle());
	}

	public default EulerAngle getLeftLegPose() {
		return getLeftLegPose(new EulerAngle());
	}

	public default EulerAngle getRightArmPose() {
		return getRightArmPose(new EulerAngle());
	}

	public default EulerAngle getRightLegPose() {
		return getRightLegPose(new EulerAngle());
	}
	
	public EulerAngle getBodyPose(EulerAngle angle);

	public EulerAngle getHeadPose(EulerAngle angle);

	public EulerAngle getLeftArmPose(EulerAngle angle);

	public EulerAngle getLeftLegPose(EulerAngle angle);

	public EulerAngle getRightArmPose(EulerAngle angle);

	public EulerAngle getRightLegPose(EulerAngle angle);

	public void setBodyPose(EulerAngle angle);

	public void setBodyPose(float x, float y, float z);
	
	public void setHeadPose(EulerAngle angle);
	
	public void setHeadPose(float x, float y, float z);

	public void setLeftArmPose(EulerAngle angle);

	public void setLeftArmPose(float x, float y, float z);
	
	public void setLeftLegPose(EulerAngle angle);
	
	public void setLeftLegPose(float x, float y, float z);

	public void setRightArmPose(EulerAngle angle);
	
	public void setRightArmPose(float x, float y, float z);

	public void setRightLegPose(EulerAngle angle);
	
	public void setRightLegPose(float x, float y, float z);
	
	public default EulerAngle getLimbPos(LimbType limb, EulerAngle angle) {
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
	
	public default void setLimbPos(LimbType limb, EulerAngle angle) {
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
	
	public boolean isSmall();
	
	public void setSmall(boolean small);
	
	public boolean hasArms();
	
	public void setArms(boolean arms);
	
	public boolean hasBasePlate();
	
	public void setBasePlate(boolean baseplate);
	
	/**
	 * Returns whether or not this {@link ArmorStand} is a marker. Meaning having no hitbox
	 * @return true if is a marker
	 */
	public boolean isMarker();
	
	public void setMarker(boolean marker);
	
	public static enum LimbType {
		HEAD, BODY, RIGHT_LEG, LEFT_LEG, RIGHT_ARM, LEFT_ARM;
	}

	/**
	 * Sets the slot interaction flags
	 * @see #getSlotInteractionFlags()
	 * @param flags
	 */
	public void setSlotInteractionFlags(int flags);
	
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
	 * <tr></tr>
	 * <tr><td>0x1F1F1F</td><td>Disable all actions</td></tr>
	 * </table>
	 * @return flags
	 */
	public int getSlotInteractionFlags();

}
