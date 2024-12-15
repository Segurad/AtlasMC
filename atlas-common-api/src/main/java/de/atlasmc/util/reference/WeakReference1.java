package de.atlasmc.util.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class WeakReference1<T, A> extends WeakReference<T> {
	
	public final A value1;
	
	public WeakReference1(T referent, ReferenceQueue<? super T> q, A value1) {
		super(referent, q);
		this.value1 = value1;
	}

}
