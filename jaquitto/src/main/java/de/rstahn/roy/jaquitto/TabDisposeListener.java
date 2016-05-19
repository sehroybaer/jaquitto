package de.rstahn.roy.jaquitto;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;

public class TabDisposeListener implements DisposeListener{

	private final Connection connection;
	private final DisconnectActionResult actionResult;
	
	public TabDisposeListener(Connection connection, DisconnectActionResult actionResult) {
		this.connection = connection;
		this.actionResult = actionResult;
	}
	@Override
	public void widgetDisposed(DisposeEvent e) {
		connection.disconnect(actionResult);
		System.out.println("Tab was disposed. " + connection.getName() + " will be disconnected.");		
	}

}
