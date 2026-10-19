package de.atlasmc.util.configuration;

public class ConfigurationSettings  {

	private boolean preserveComments = true;
	private boolean beautify = true;
	private boolean autoSerialize = false;
	private ClassLoader loader;
	
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
	
	public void setLoader(ClassLoader laoder) {
		this.loader = laoder;
	}
	
	public ClassLoader getLoader() {
		return loader;
	}
	
	public ClassLoader getEffectiveLoader() {
		var loader = this.loader;
		return loader == null ? ClassLoader.getSystemClassLoader() : loader;
	}
	
}
