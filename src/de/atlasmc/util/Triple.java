package de.atlasmc.util;

public final class Triple<A,B,C> {
	
	private final A value1;
	private final B value2;
	private final C value3;
	
	public Triple(A value1,B value2, C value3) {
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
	}
	
	public A getValue1() {
		return value1;
	}
	
	public B getValue2() {
		return value2;
	}
	
	public C getValue3() {
		return value3;
	}

}
