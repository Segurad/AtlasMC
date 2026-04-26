package de.atlasmc.util.configuration;

public class ConfigurationSettings  {

	private boolean preserveComments = true;
	private boolean beautify = false;
	private boolean autoSerialize = false;
	private ClassLoader laoder;
	
	public boolean isPreserveComments() {
		return preserveComments;
	}
	
	public void setPreserveComments(boolean preserveComments) {
		this.preserveComments = preserveComments;
	}
	
	public boolean isBeautify() {
		return beautify;
	}
	
	public void setBeautify(boolean beautify) {
		this.beautify = beautify;
	}
	
	public boolean isAutoSerialize() {
		return autoSerialize;
	}
	
	public void setAutoSerialize(boolean autoSerialize) {
		this.autoSerialize = autoSerialize;
	}
	
	public void setLaoder(ClassLoader laoder) {
		this.laoder = laoder;
	}
	
	public ClassLoader getLaoder() {
		return laoder;
	}
	
	public ClassLoader getEffectiveLoader() {
		var loader = this.laoder;
		return loader == null ? ClassLoader.getSystemClassLoader() : loader;
	}
	
}
