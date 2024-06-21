package de.atlasmc.util.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class WeakReference1<T, V1> extends WeakReference<T> {
	
	public final V1 value1;
	
	public WeakReference1(T referent, ReferenceQueue<? super T> q, V1 value1) {
		super(referent, q);
		this.value1 = value1;
	}

}
