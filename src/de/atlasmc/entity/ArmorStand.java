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

	public void setHeadPose(EulerAngle angle);

	public void setLeftArmPose(EulerAngle angle);

	public void setLeftLegPose(EulerAngle angle);

	public void setRightArmPose(EulerAngle angle);

	public void setRightLegPose(EulerAngle angle);
	
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
	
	public boolean isMarker();
	
	public void setMarker(boolean marker);
	
	public static enum LimbType {
		HEAD, BODY, RIGHT_LEG, LEFT_LEG, RIGHT_ARM, LEFT_ARM;
	}

}
