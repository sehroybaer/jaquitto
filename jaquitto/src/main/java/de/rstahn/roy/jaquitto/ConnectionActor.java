package de.rstahn.roy.jaquitto;

import org.eclipse.swt.widgets.Widget;

public class ConnectionActor implements ActionResult {

	final SignalDispatcher dispatcher;
	final Widget widget;

	public ConnectionActor(SignalDispatcher dispatcher, Widget widget) {
		this.dispatcher = dispatcher;
		this.widget = widget;
	}

	/**
	 * After the connection is successfully established this method should be called.
	 * The caller may reside on a different thread.
	 */
	@Override
	public void connected() {
		widget.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!widget.isDisposed()) {
					dispatcher.addStringToListbox("Message Box", "Connected.");
					dispatcher.setEnabled("Connect Button", false);
					dispatcher.setEnabled("Disconnect Button", true);
					dispatcher.setEnabled("Publish Button", true);
					dispatcher.setEnabled("Subscribe Button", true);
					dispatcher.setEnabled("Unsubscribe Button", false);
				}				
			}			
		});			
	}

	/**
	 * When a connection can't be established this method should be called. 
	 * The caller may reside on a different thread.
	 */
	@Override
	public void connectFailed(String cause) {
		widget.getDisplay().asyncExec(new Runnable () {
			@Override
			public void run () {
				if (!widget.isDisposed()) {
					dispatcher.addStringToListbox("Message Box", "Connecting failed: " + cause);
				}
			}
		});			
	}

	/**
	 * When the connection was separated this method should be called.
	 * The caller may reside on a different thread.
	 */
	@Override
	public void disconnected() {
		widget.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!widget.isDisposed()) {
					dispatcher.addStringToListbox("Message Box", "Disconnected.");
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
	 * When the connection couldn't be separated this method should be called.
	 * The caller may reside on a different thread.
	 * @param cause of the failure.
	 */
	@Override
	public void disconnectedFailed(String cause) {
		widget.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!widget.isDisposed()) {
					dispatcher.addStringToListbox("Message Box", "Disconnecting failed: " + cause);				
				}				
			}			
		});			
	}

	/**
	 * When the subscription to a topic was successful this method should be called.
	 * The caller may reside on a different thread.
	 * @param topic of the subscription
	 */
	@Override
	public void subscribed(String topic) {
		widget.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!widget.isDisposed()) {
					dispatcher.addStringToListbox("Message Box", "Subscribed to topic \"" + topic + "\"");
					dispatcher.addStringToListbox("Subscriptions", topic);
					dispatcher.setEnabled("Unsubscribe Button", false);
				}				
			}			
		});			
	}

	/**
	 * When the subscription to a topic failed this method should be called.
	 * The caller may reside on a different thread.
	 * @param topic of subscription
	 * @param cause of failure
	 */
	@Override
	public void subscribeFailed(String topic, String cause) {
		widget.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!widget.isDisposed()) {
					dispatcher.addStringToListbox("Message Box", 
							"Subscribing to topic \"" + topic + "\" failed! [" + cause + "]");				
				}				
			}			
		});			
	}

	/**
	 * When unsubscribing from a topic was successful this method should be called.
	 * The caller may reside on a different thread. 
	 * @param topic to unsubscribe from
	 */
	@Override
	public void unsubscribed(String topic) {
		widget.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!widget.isDisposed()) {
					dispatcher.addStringToListbox("Message Box", "Topic \"" + topic + "\" unsubscribed.");
					dispatcher.removeStringFromListbox("Subscriptions", topic);
					if(dispatcher.listboxIsEmpty("Subscriptions")) {
						dispatcher.setEnabled("Unsubscribe Button", false);
					}
				}				
			}			
		});			
	}

	/**
	 * When unsubscribing from a topic has failed this method should be called.
	 * The caller may reside on a different thread.
	 * @param topic to unsubscribe from
	 * @param cause of failure
	 */
	@Override
	public void unsubscribeFailed(String topic, String cause) {
		widget.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!widget.isDisposed()) {
					dispatcher.addStringToListbox("Message Box", 
							"Unsubscribing from topic \"" + topic + "\" failed! [" + cause + "]");				
				}				
			}			
		});				
	}

	/**
	 * When publishing to a topic was successful this method should be called.
	 * The caller may reside on a different thread.
	 * @param topic to publish
	 */
	@Override
	public void published(String topic) {
		widget.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!widget.isDisposed()) {
					dispatcher.addStringToListbox("Message Box", 
							"Published to topic \"" + topic + "\"");			
				}				
			}			
		});			
	}

	/**
	 * When publishing to a topic has failed this method should be called.
	 * The caller may reside on a different thread.
	 * @param topic to publish
	 * @param cause of failure
	 */
	@Override
	public void publishFailed(String topic, String cause) {
		widget.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!widget.isDisposed()) {
					dispatcher.addStringToListbox("Message Box", 
							"Publishing to topic \"" + topic + "\" failed! [" + cause + "]");			
				}				
			}			
		});			
	}

}
