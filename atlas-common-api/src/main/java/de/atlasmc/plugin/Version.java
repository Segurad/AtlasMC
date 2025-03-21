package de.atlasmc.plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Version implements Comparable<Version> {
	
	public static final Version ZERO = new Version(0,0,0);
	// ^v?(?<major>0|[1-9]\d*)\.(?<minor>0|[1-9]\d*)\.(?<patch>0|[1-9]\d*)(?:-(?<prerelease>(?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\.(?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\+(?<buildmetadata>[0-9a-zA-Z-]+(?:\.[0-9a-zA-Z-]+)*))?$
	public static final Pattern PATTERN = Pattern.compile("^v?(?<major>0|[1-9]\\d*)\\.(?<minor>0|[1-9]\\d*)\\.(?<patch>0|[1-9]\\d*)(?:-(?<prerelease>(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\\+(?<buildmetadata>[0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?$");
	
	public final int major;
	public final int minor;
	public final int patch;
	private String raw;
	
	public Version(int major, int minor, int patch) {
		if (major < 0)
			throw new IllegalArgumentException("Major can not be negativ!");
		if (minor < 0)
			throw new IllegalArgumentException("Minor can not be negativ!");
		if (patch < 0)
			throw new IllegalArgumentException("Patch can not be negativ!");
		this.major = major;
		this.minor = minor;
		this.patch = patch;
	}
	
	public Version(String version) {
		if (version == null)
			throw new IllegalArgumentException("Version can not be null!");
		Matcher matcher = PATTERN.matcher(version);
		if (!matcher.matches())
			throw new IllegalArgumentException("Invalid version String: " + version);
		try {
			major = Integer.parseInt(matcher.group("major"));
			minor = Integer.parseInt(matcher.group("minor"));
			patch = Integer.parseInt(matcher.group("patch"));
			//matcher.group("preprelease");
			//matcher.group("build");
		} catch (NumberFormatException | NullPointerException e) {
			throw new IllegalArgumentException("Invalid version String: " + version);
		}
		this.raw = version;
	}

	/**
	 * Compares this version with the given version.
	 * @return 0 if this == other. -1 if this < other. 1 if this > other
	 */
	@Override
	public int compareTo(Version o) {
		int result = Integer.compare(major, o.major);
		if (result != 0)
			return result;
		result = Integer.compare(minor, o.minor);
		if (result != 0)
			return result;
		return Integer.compare(patch, patch);
	}
	
	@Override
	public String toString() {
		if (raw == null)
			raw = new StringBuilder()
					.append('v')
					.append(major)
					.append('.')
					.append(minor)
					.append('.')
					.append(patch)
					.toString();
		return raw;
	}

}
