
package org.jas.dnd;

import java.awt.Component;

public abstract class DoInListenersAdapter<V, K> implements DoInListeners<V, K> {
	@Override
	public V doIn(K listener, Component component, V lastResult) {
		return null;
	}

	@Override
	public void done() {
	}

}
