package de.rstahn.roy.jaquitto;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Widget;

public class ConnectionController {
	
	final ConnectionActor actions;
	
	public ConnectionController(Connection connection, SignalDispatcher dispatcher, Widget widget) {
		this.actions = new ConnectionActor(dispatcher, widget);
		setupButtonPressedListener(connection, dispatcher);
	}

	private void setupButtonPressedListener(final Connection connection, final SignalDispatcher dispatcher) {
		dispatcher.addSelectionListener("Connect Button", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				connection.connect(actions);
			}			
		});		
	}
}
