package de.rstahn.roy.jaquitto;

import org.eclipse.swt.widgets.Widget;

public class ConnectionEventListener implements ConnectionEvents {

	final SignalDispatcher dispatcher;
	final Widget widget;
	
	public ConnectionEventListener(SignalDispatcher dispatcher, Widget widget) {
		this.dispatcher = dispatcher;
		this.widget = widget;
	}

	/**
	 * When a connection gets lost this method should be called.
	 * The caller may reside on a non-UI thread.
	 * @param cause
	 */
	@Override
	public void connectionLost(String cause) {
		widget.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!widget.isDisposed()) {
					final String text = "Connection lost! [" + cause + "]";
					dispatcher.addStringToListbox("Message Box", text);
					dispatcher.setEnabled("Connect Button", true);
					dispatcher.setEnabled("Disconnect Button", false);
					dispatcher.setEnabled("Publish Button", false);
					dispatcher.setEnabled("Subscribe Button", false);
					dispatcher.setEnabled("Unsubscribe Button", false);						
					dispatcher.clearListbox("Subscriptions");
				}				
			}			
		});
	}

	/**
	 * A completed delivery of a message should be announced with this method.
	 * The caller may reside on a non-UI thread.
	 * @param topic of the delivered message
	 */
	@Override
	public void deliveryComplete(String topic) {
		widget.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!widget.isDisposed()) {
					final String text = "Message to topic \"" + topic + "\" was delivered.";
					dispatcher.addStringToListbox("Message Box", text);					
				}				
			}			
		});
	}

	/**
	 * When a new message has arrived this method should be called.
	 * The caller may reside on a non-UI thread.
	 * @param topic of the new message
	 * @param message
	 */
	@Override
	public void messageArrived(String topic, String message) {
		widget.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!widget.isDisposed()) {
					final String text = "Message \"" + message + "\" arrived for topic \"" + topic + "\"";
					dispatcher.addStringToListbox("Message Box", text);			
				}				
			}			
		});
	}

}
