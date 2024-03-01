package de.atlasmc.command.argparser;

import org.joml.Vector2d;

public class ArgumentVector2d extends Vector2d {
	
	/**
	 * Whether or not the coordinate system is centered on the targets location and rotation.
	 * <ul>
	 * <li>+X will move the target right</li>
	 * <li>+Y will move the target up</li>
	 * <li>+Z will move the target forward</li>
	 * </ul>
	 */
	public boolean targetCentric;
	
	/**
	 * Whether or not the x value should be treated as relative to the target.
	 * If {@link #targetCentric} is true this should be ignored.
	 */
	public boolean relativeX;
	
	/**
	 * Whether or not the y value should be treated as relative to the target.
	 * If {@link #targetCentric} is true this should be ignored.
	 */
	public boolean relativeY;

}
