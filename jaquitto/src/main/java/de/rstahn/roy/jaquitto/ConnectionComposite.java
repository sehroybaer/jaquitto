package de.rstahn.roy.jaquitto;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;


public class ConnectionComposite extends Composite {
	private Text txtSubscibeTopic;
	private Text txtPublishTopic;
	private Text txtPublishPayload;
	private final List messages;
	private final Button btnConnect;
	private final Button btnDisconnect;
	private final Button btnPublish;
	private final Button btnSubscribe;
	private final Button btnUnsubscribe;
	private final List subscriptions;
	private final SignalDispatcher controller;
	private final ConnectionEvents connectionEventListener;

	public ConnectionComposite(Composite parent, int style, Connection connection) {
		super(parent, style);
		controller = new ConnectionController(this);
		connectionEventListener = new ConnectionEventListener(controller, this);

		final Label lblUrl = new Label(this, SWT.NONE);
		lblUrl.setBounds(10, 10, 300, 15);
		lblUrl.setText(connection.getServerUrlAsString());

		btnConnect = new Button(this, SWT.NONE);

		btnConnect.setBounds(10, 38, 75, 25);
		btnConnect.setText("Connect");
		controller.addButton("Connect Button", btnConnect);

		btnDisconnect = new Button(this, SWT.NONE);
		btnDisconnect.setEnabled(false);

		btnDisconnect.setBounds(102, 38, 75, 25);
		btnDisconnect.setText("Disconnect");
		controller.addButton("Disconnect Button", btnDisconnect);

		btnSubscribe = new Button(this, SWT.NONE);

		btnSubscribe.setEnabled(false);
		btnSubscribe.setBounds(237, 38, 75, 25);
		btnSubscribe.setText("Subscribe");
		controller.addButton("Subscribe Button", btnSubscribe);

		btnUnsubscribe = new Button(this, SWT.NONE);

		btnUnsubscribe.setEnabled(false);
		btnUnsubscribe.setBounds(237, 69, 75, 25);
		btnUnsubscribe.setText("Unsubscribe");
		controller.addButton("Unsubscribe Button", btnUnsubscribe);

		btnPublish = new Button(this, SWT.NONE);

		btnPublish.setEnabled(false);
		btnPublish.setBounds(237, 117, 75, 25);
		btnPublish.setText("Publish");
		controller.addButton("Publish Button", btnPublish);

		txtSubscibeTopic = new Text(this, SWT.BORDER);
		txtSubscibeTopic.setBounds(321, 40, 119, 21);
		controller.addTextfield("Subscribe Topic", txtSubscibeTopic);

		final Label lblSubscribeTopic = new Label(this, SWT.NONE);
		lblSubscribeTopic.setBounds(321, 20, 55, 15);
		lblSubscribeTopic.setText("Topic:");

		subscriptions = new List(this, SWT.BORDER | SWT.V_SCROLL);
		subscriptions.setBounds(321, 67, 119, 228);
		controller.addListbox("Subscriptions", subscriptions);

		messages = new List(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		messages.setBounds(10, 163, 302, 132);
		controller.addListbox("Message Box", messages);

		final Label lblMessages = new Label(this, SWT.NONE);
		lblMessages.setBounds(10, 146, 55, 15);
		lblMessages.setText("Messages:");

		txtPublishTopic = new Text(this, SWT.BORDER);
		txtPublishTopic.setBounds(10, 119, 76, 21);
		controller.addTextfield("Publish Topic", txtPublishTopic);

		txtPublishPayload = new Text(this, SWT.BORDER);
		txtPublishPayload.setBounds(102, 119, 129, 21);
		controller.addTextfield("Publish Payload", txtPublishPayload);

		final Label lblPublishTopic = new Label(this, SWT.NONE);
		lblPublishTopic.setBounds(10, 98, 55, 15);
		lblPublishTopic.setText("Topic:");

		final Label lblPayload = new Label(this, SWT.NONE);
		lblPayload.setBounds(102, 98, 55, 15);
		lblPayload.setText("Payload:");
		
		controller.setupButtonPressedListener(connection);
		connection.setConnectionCallback(connectionEventListener);

		/*
		final Connection con = connection;
		final ConnectionComposite instance = this;
		btnConnect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				con.connect(instance);
			}
		});

		btnDisconnect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				con.disconnect(instance);
			}
		});

		btnSubscribe.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final String topic = txtSubscibeTopic.getText();
				if(!topic.isEmpty()) {
					con.subscribe(topic, 1, instance);
				}
			}
		});

		btnUnsubscribe.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(subscriptions.getItemCount() == 0 
						|| subscriptions.getSelectionIndex() < 0) {
					return;
				}
				int selection = subscriptions.getSelectionIndex();
				final String topic = subscriptions.getItem(selection);
				con.unsubscribe(topic, instance);
			}
		});

		btnPublish.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final String topic = txtPublishTopic.getText();
				final String message = txtPublishPayload.getText();
				if(!topic.isEmpty() && !message.isEmpty()) {
					con.publish(topic, message, 1, instance);
				}
			}
		});

		 */
	}

	// ActionResult interface implementation
	// all methods may be called from a non UI thread
	

//	/**
//	 * After the connection is successfully established
//	 * this method should be called.
//	 * The caller may reside on a different thread.
//	 */
//	@Override
//	public void connected() {
//		final ConnectionComposite instance = this;
//		getDisplay().asyncExec(new Runnable() {
//			@Override
//			public void run() {
//				if (!instance.isDisposed()) {
//					messages.add(".Connected.");
//					btnConnect.setEnabled(false);
//					btnDisconnect.setEnabled(true);
//					btnPublish.setEnabled(true);
//					btnSubscribe.setEnabled(true);
//				}				
//			}			
//		});			
//	}
//
//	/**
//	 * When a connection can't be established this method should be called. 
//	 * The caller may reside on a different thread.
//	 */
//	@Override
//	public void connectFailed(String cause) {
//		final ConnectionComposite instance = this;
//		getDisplay().asyncExec(new Runnable () {
//			@Override
//			public void run () {
//				if (!instance.isDisposed()) {
//					messages.add(".Connecting failed: " + cause);
//				}
//			}
//		});		
//	}
//
//	/**
//	 * When the connection was separated this method should be called.
//	 * The caller may reside on a different thread.
//	 */
//	@Override
//	public void disconnected() {
//		final ConnectionComposite instance = this;
//		getDisplay().asyncExec(new Runnable() {
//			@Override
//			public void run() {
//				if (!instance.isDisposed()) {
//					messages.add(".Disconnected.");
//					btnConnect.setEnabled(true);
//					btnDisconnect.setEnabled(false);
//					btnPublish.setEnabled(false);
//					btnSubscribe.setEnabled(false);
//					btnUnsubscribe.setEnabled(false);
//					subscriptions.removeAll();
//				}				
//			}			
//		});		
//	}
//
//	/**
//	 * When the connection couldn't be separated this method should be called.
//	 * The caller may reside on a different thread.
//	 * @param cause of the failure.
//	 */
//	@Override
//	public void disconnectedFailed(String cause) {
//		final ConnectionComposite instance = this;
//		getDisplay().asyncExec(new Runnable() {
//			@Override
//			public void run() {
//				if (!instance.isDisposed()) {
//					messages.add(".Disconnecting failed: " + cause);				
//				}				
//			}			
//		});			
//	}
//
//	/**
//	 * When the subscription to a topic was successful this method should be called.
//	 * The caller may reside on a different thread.
//	 * @param topic of the subscription
//	 */
//	@Override
//	public void subscribed(String topic) {
//		final ConnectionComposite instance = this;
//		getDisplay().asyncExec(new Runnable() {
//			@Override
//			public void run() {
//				if (!instance.isDisposed()) {
//					messages.add(".Subscribed to topic \"" + topic + "\"");
//					subscriptions.add(topic);
//					btnUnsubscribe.setEnabled(true);
//				}				
//			}			
//		});	
//	}
//
//	/**
//	 * When the subscription to a topic failed this method should be called.
//	 * The caller may reside on a different thread.
//	 * @param topic of subscription
//	 * @param cause of failure
//	 */
//	@Override
//	public void subscribeFailed(String topic, String cause) {
//		final ConnectionComposite instance = this;
//		getDisplay().asyncExec(new Runnable() {
//			@Override
//			public void run() {
//				if (!instance.isDisposed()) {
//					messages.add(".Subscribing to topic \"" + topic + "\" failed! [" + cause + "]");				
//				}				
//			}			
//		});		
//	}
//
//	/**
//	 * When unsubscribing from a topic was successful this method should be called.
//	 * The caller may reside on a different thread. 
//	 * @param topic to unsubscribe from
//	 */
//	@Override
//	public void unsubscribed(String topic) {
//		final ConnectionComposite instance = this;
//		getDisplay().asyncExec(new Runnable() {
//			@Override
//			public void run() {
//				if (!instance.isDisposed()) {
//					messages.add(".Topic \"" + topic + "\" unsubscribed.");
//					subscriptions.remove(topic);
//					if(subscriptions.getItemCount() == 0) {
//						btnUnsubscribe.setEnabled(false);
//					}
//				}				
//			}			
//		});		
//	}
//
//	/**
//	 * When unsubscribing from a topic has failed this method should be called.
//	 * The caller may reside on a different thread.
//	 * @param topic to unsubscribe from
//	 * @param cause of failure
//	 */
//	@Override
//	public void unsubscribeFailed(String topic, String cause) {
//		final ConnectionComposite instance = this;
//		getDisplay().asyncExec(new Runnable() {
//			@Override
//			public void run() {
//				if (!instance.isDisposed()) {
//					messages.add(".Unsubscribing from topic \"" + topic + "\" failed! [" + cause + "]");				
//				}				
//			}			
//		});		
//	}
//
//	/**
//	 * When publishing to a topic was successful this method should be called.
//	 * The caller may reside on a different thread.
//	 * @param topic to publish
//	 */
//	@Override
//	public void published(String topic) {
//		final ConnectionComposite instance = this;
//		getDisplay().asyncExec(new Runnable() {
//			@Override
//			public void run() {
//				if (!instance.isDisposed()) {
//					messages.add(".Published to topic \"" + topic + "\"");			
//				}				
//			}			
//		});		
//	}
//
//	/**
//	 * When publishing to a topic has failed this method should be called.
//	 * The caller may reside on a different thread.
//	 * @param topic to publish
//	 * @param cause of failure
//	 */
//	@Override
//	public void publishFailed(String topic, String cause) {
//		final ConnectionComposite instance = this;
//		getDisplay().asyncExec(new Runnable() {
//			@Override
//			public void run() {
//				if (!instance.isDisposed()) {
//					messages.add(".Publishing to topic \"" + topic + "\" failed! [" + cause + "]");			
//				}				
//			}			
//		});		
//	}
//
//	// ConnectionEvents implementation
//	// all methods may be called from a non UI thread
//
//	/**
//	 * When a connection gets lost this method should be called.
//	 * The caller may reside on a different thread.
//	 * @param cause
//	 */
//	@Override
//	public void connectionLost(String cause) {
//		final ConnectionComposite instance = this;
//		getDisplay().asyncExec(new Runnable() {
//			@Override
//			public void run() {
//				if (!instance.isDisposed()) {
//					messages.add(".Connection lost! [" + cause + "]");
//					btnConnect.setEnabled(true);
//					btnDisconnect.setEnabled(false);
//					btnPublish.setEnabled(false);
//					btnSubscribe.setEnabled(false);
//					btnUnsubscribe.setEnabled(false);
//					subscriptions.removeAll();
//				}				
//			}			
//		});		
//	}
//
//	/**
//	 * A completed delivery of a message should be announced with this method.
//	 * The caller may reside on a different thread.
//	 * @param topic of the delivered message
//	 */
//	@Override
//	public void deliveryComplete(String topic) {
//		final ConnectionComposite instance = this;
//		getDisplay().asyncExec(new Runnable() {
//			@Override
//			public void run() {
//				if (!instance.isDisposed()) {
//					messages.add(".Message to topic \"" + topic + "\" was delivered.");				
//				}				
//			}			
//		});		
//	}
//
//	/**
//	 * When a new message has arrived this method should be called.
//	 * The caller may reside on a different thread.
//	 * @param topic of the new message
//	 * @param message
//	 */
//	@Override
//	public void messageArrived(String topic, String message) {
//		final ConnectionComposite instance = this;
//		getDisplay().asyncExec(new Runnable() {
//			@Override
//			public void run() {
//				if (!instance.isDisposed()) {
//					messages.add(".Message \"" + message + "\" arrived for topic \"" + topic + "\"");			
//				}				
//			}			
//		});		
//	}
}
