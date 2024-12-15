package de.atlasmc.util.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class WeakReference2<T, A, B> extends WeakReference<T> {
	
	public final A value1;
	public final B value2;
	
	public WeakReference2(T referent, ReferenceQueue<? super T> q, A value1, B value2) {
		super(referent, q);
		this.value1 = value1;
		this.value2 = value2;
	}

}
