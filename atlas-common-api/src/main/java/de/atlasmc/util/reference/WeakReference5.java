package de.atlasmc.util.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class WeakReference5<T, A, B, C, D, E> extends WeakReference<T> {
	
	public final A value1;
	public final B value2;
	public final C value3;
	public final D value4;
	public final E value5;
	
	public WeakReference5(T referent, ReferenceQueue<? super T> q, A value1, B value2, C value3, D value4, E value5) {
		super(referent, q);
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
		this.value4 = value4;
		this.value5 = value5;
	}

}
