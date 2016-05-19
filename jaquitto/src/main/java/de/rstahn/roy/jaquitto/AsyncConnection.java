package de.rstahn.roy.jaquitto;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class AsyncConnection implements Connection {
	private MqttConnectOptions opt;
	private final IMqttAsyncClient client;

	// Inner ActionListener classes
	public class ConnectActionListener implements IMqttActionListener {

		private final ActionResult actionResult;

		public ConnectActionListener(ActionResult actionResult) {
			this.actionResult = actionResult;
		}

		@Override
		public void onFailure(IMqttToken token, Throwable cause) {
			actionResult.connectFailed(cause.getMessage());			
		}

		@Override
		public void onSuccess(IMqttToken token) {
			actionResult.connected();
		}		
	}

	public class DisconnectActionListener implements IMqttActionListener {
		private final DisconnectActionResult actionResult;

		public DisconnectActionListener(DisconnectActionResult actionResult) {
			this.actionResult = actionResult;
		}

		@Override
		public void onFailure(IMqttToken token, Throwable cause) {
			actionResult.disconnectedFailed(cause.getMessage());
		}

		@Override
		public void onSuccess(IMqttToken arg0) {
			actionResult.disconnected();			
		}		
	}

	public class PublishActionListener implements IMqttActionListener {

		private final ActionResult actionResult;

		public PublishActionListener(ActionResult actionResult) {
			this.actionResult = actionResult;
		}

		@Override
		public void onFailure(IMqttToken token, Throwable cause) {			
			String topic = packTopics(token.getTopics());
			actionResult.publishFailed(topic, cause.getMessage());						
		}

		@Override
		public void onSuccess(IMqttToken token) {
			String topic = packTopics(token.getTopics());
			actionResult.published(topic);		
		}		
	}

	public class SubscribeActionListener implements IMqttActionListener {

		private final ActionResult actionResult;

		public SubscribeActionListener(ActionResult actionResult) {
			this.actionResult = actionResult;
		}

		@Override
		public void onFailure(IMqttToken token, Throwable cause) {
			String topic = packTopics(token.getTopics());
			actionResult.subscribeFailed(topic, cause.getMessage());			
		}

		@Override
		public void onSuccess(IMqttToken token) {
			String topic = packTopics(token.getTopics());
			actionResult.subscribed(topic);			
		}		
	}

	public class UnsubscribeActionListener implements IMqttActionListener {

		private final ActionResult actionResult;

		public UnsubscribeActionListener(ActionResult actionResult) {
			this.actionResult = actionResult;
		}

		@Override
		public void onFailure(IMqttToken token, Throwable cause) {
			String topic = packTopics(token.getTopics());
			actionResult.subscribeFailed(topic, cause.getMessage());			
		}

		@Override
		public void onSuccess(IMqttToken token) {
			String topic = packTopics(token.getTopics());
			actionResult.unsubscribed(topic);			
		}		
	}

	// Definition of AsyncConnection

	public AsyncConnection(IMqttAsyncClient client, MqttConnectOptions conOpts) {
		this.client = client;
		if(conOpts == null) {
			this.opt = new MqttConnectOptions();
		} else {
			this.opt = conOpts;
		}
	}

	public static String packTopics(String[] topics) {
		StringBuilder builder = new StringBuilder();
		if(topics == null) {
			return null;
		}
		if(topics.length == 0) {
			return "unknown";
		}
		for(int i=0; i<topics.length; ++i) {
			if(i > 0) {
				builder.append(";");
			}
			builder.append(topics[i]);
		}
		return builder.toString();			
	}


	// Implementation of Connection interface

	@Override
	public void setConnectionCallback(ConnectionEvents connectionEvents) {
		ConnectionCallback callback = new ConnectionCallback(connectionEvents);
		client.setCallback(callback);
	}

	@Override
	public void connect(ActionResult actionResult) {
		try {
			client.connect(opt, null, new ConnectActionListener(actionResult));
		} catch (MqttSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void disconnect(DisconnectActionResult actionResult) {
		try {
			if(client.isConnected()) {
				client.disconnect(null, new DisconnectActionListener(actionResult));
			}
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void publish(String topic, String message, int qos, ActionResult actionResult) {
		MqttMessage msg = new MqttMessage(message.getBytes());
		msg.setQos(qos);
		try {
			client.publish(topic, msg, null, new PublishActionListener(actionResult));
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
	}

	@Override
	public void subscribe(String topic, int qos, ActionResult actionResult) {
		try {
			client.subscribe(topic, qos, null, new SubscribeActionListener(actionResult));
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void unsubscribe(String topic, ActionResult actionResult) {
		try {
			client.unsubscribe(topic, null, new UnsubscribeActionListener(actionResult));
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public String getServerUrlAsString() {
		return client.getServerURI();
	}

	@Override
	public String getName() {
		return client.getClientId();
	}

	public static AsyncConnection createDefaultAsyncConnection(String server, String clientId) {
		AsyncConnection connection = null;
		try {
			MqttAsyncClient client = new MqttAsyncClient(server, clientId);
			MqttConnectOptions options = new MqttConnectOptions();
			connection = new AsyncConnection(client, options);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connection;
	}

}
