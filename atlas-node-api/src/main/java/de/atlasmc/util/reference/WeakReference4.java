package de.atlasmc.util.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class WeakReference4<T, V1, V2, V3, V4> extends WeakReference<T> {
	
	public final V1 value1;
	public final V2 value2;
	public final V3 value3;
	public final V4 value4;
	
	public WeakReference4(T referent, ReferenceQueue<? super T> q, V1 value1, V2 value2, V3 value3, V4 value4) {
		super(referent, q);
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
		this.value4 = value4;
	}

}
