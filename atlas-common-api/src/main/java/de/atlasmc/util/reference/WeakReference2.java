package de.atlasmc.util.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class WeakReference2<T, V1, V2> extends WeakReference<T> {
	
	public final V1 value1;
	public final V2 value2;
	
	public WeakReference2(T referent, ReferenceQueue<? super T> q, V1 value1, V2 value2) {
		super(referent, q);
		this.value1 = value1;
		this.value2 = value2;
	}

}
