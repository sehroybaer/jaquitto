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
		
		dispatcher.addSelectionListener("Disconnect Button", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				connection.disconnect(actions);
			}
		});

		dispatcher.addSelectionListener("Publish Button", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final String topic = dispatcher.getTextFieldContent("Publish Topic");
				final String payload = dispatcher.getTextFieldContent("Publish Payload");
				final int qos = 1;
				if(isNotNullOrEmpty(topic) && isNotNullOrEmpty(payload)) {
					connection.publish(topic, payload, qos, actions);
				}
			}
		});

		dispatcher.addSelectionListener("Subscribe Button", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final String topic = dispatcher.getSelectedStringFromListbox("Subscriptions");
				final int qos = 1;
				if(isNotNullOrEmpty(topic)) {
					connection.subscribe(topic, qos, actions);
				}
			}
		});

		dispatcher.addSelectionListener("Unsubscribe Button", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final String topic = dispatcher.getSelectedStringFromListbox("Subscriptions");
				if(isNotNullOrEmpty(topic)) { 
					connection.unsubscribe(topic, actions);
				}
			}
		});

	}
	
	public static boolean isNotNullOrEmpty(String text) {
		return text != null && !text.isEmpty();
	}
}
