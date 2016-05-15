package de.rstahn.roy.jaquitto;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;

public class TabDisposeListener implements DisposeListener{

	private final Connection connection;
	
	public TabDisposeListener(Connection connection) {
		this.connection = connection;
	}
	@Override
	public void widgetDisposed(DisposeEvent e) {
		connection.disconnect();
		System.out.println("Tab was disposed. " + connection.getName() + " will be disconnected.");		
	}

}
