package de.atlasmc.util.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class WeakReference5<T, V1, V2, V3, V4, V5> extends WeakReference<T> {
	
	public final V1 value1;
	public final V2 value2;
	public final V3 value3;
	public final V4 value4;
	public final V5 value5;
	
	public WeakReference5(T referent, ReferenceQueue<? super T> q, V1 value1, V2 value2, V3 value3, V4 value4, V5 value5) {
		super(referent, q);
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
		this.value4 = value4;
		this.value5 = value5;
	}

}
