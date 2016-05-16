package de.rstahn.roy.jaquitto;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ConnectionCallback implements MqttCallback {
	
	private final ConnectionEvents connectionEvents;
	
	public ConnectionCallback(ConnectionEvents connectionEvents) {
		this.connectionEvents = connectionEvents;
	}

	@Override
	public void connectionLost(Throwable cause) {
		connectionEvents.connectionLost(cause.getMessage());		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		String topic = AsyncConnection.packTopics(token.getTopics());
		try {
			String message = new String(token.getMessage().getPayload());
			connectionEvents.deliveryComplete(topic, message);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connectionEvents.deliveryComplete(topic, e.getMessage());
		}		
	}

	@Override
	public void messageArrived(String topic, MqttMessage msg) throws Exception {
		connectionEvents.messageArrived(topic, new String(msg.getPayload()));		
	}

}
