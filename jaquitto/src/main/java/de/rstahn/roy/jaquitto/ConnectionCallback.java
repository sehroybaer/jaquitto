package de.rstahn.roy.jaquitto;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ConnectionCallback implements MqttCallback {

	private final ConnectionEvents connectionEvents;

	public ConnectionCallback(ConnectionEvents connectionEvents) {
		this.connectionEvents = connectionEvents;
	}

	@Override
	public void connectionLost(Throwable cause) {
		cause.printStackTrace();
		connectionEvents.connectionLost(cause.getMessage());		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		String topic = AsyncConnection.packTopics(token.getTopics());
		connectionEvents.deliveryComplete(topic);		
	}

	@Override
	public void messageArrived(String topic, MqttMessage msg) throws Exception {
		connectionEvents.messageArrived(topic, new String(msg.getPayload()));		
	}

}
