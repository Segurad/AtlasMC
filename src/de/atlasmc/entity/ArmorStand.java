package de.atlasmc.entity;

import de.atlasmc.util.EulerAngle;

public interface ArmorStand extends LivingEntity {

	public EulerAngle getBodyPose();

	public EulerAngle getHeadPose();

	public EulerAngle getLeftArmPose();

	public EulerAngle getLeftLegPose();

	public EulerAngle getRightArmPose();

	public EulerAngle getRightLegPose();

	public void setBodyPose(EulerAngle angle);

	public void setHeadPose(EulerAngle angle);

	public void setLeftArmPose(EulerAngle angle);

	public void setLeftLegPose(EulerAngle angle);

	public void setRightArmPose(EulerAngle angle);

	public void setRightLegPose(EulerAngle angle);
	
	public default EulerAngle getLimbPos(LimbType limb) {
		switch (limb) {
		case BODY:
			return getBodyPose();
		case HEAD:
			return getHeadPose();
		case LEFT_ARM:
			return getLeftArmPose();
		case LEFT_LEG:
			return getLeftLegPose();
		case RIGHT_ARM:
			return getRightArmPose();
		case RIGHT_LEG:
			return getRightLegPose();
		default:
			return getHeadPose();
		}
	}
	
	public default void setLimbPos(LimbType limb, EulerAngle angle) {
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
	
	public static enum LimbType {
		HEAD, BODY, RIGHT_LEG, LEFT_LEG, RIGHT_ARM, LEFT_ARM;
	}

}
