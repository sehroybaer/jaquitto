package de.rstahn.roy.jaquitto;

import static org.junit.Assert.*;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.junit.Before;
import org.junit.Test;

public class AsyncConnectionTest {
	
	AsyncConnection connection;
	
	private class TestActionListener implements IMqttActionListener {
		
		private boolean success = false;
		
		public boolean wasSuccessful() {
			return success;
		}

		@Override
		public void onSuccess(IMqttToken asyncActionToken) {
			success = true;			
		}

		@Override
		public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
			success = false;			
		}
		
	}
	
	
	private class TestClient implements IMqttAsyncClient {
		
		private boolean connected = false;
		private String serverUrl = "tcp://localhost:1883";
		private String clientId = "Test Client";

		@Override
		public IMqttToken connect() throws MqttException, MqttSecurityException {
			connected = true;
			return null;
		}

		@Override
		public IMqttToken connect(MqttConnectOptions options) throws MqttException, MqttSecurityException {
			connected = true;
			return null;
		}

		@Override
		public IMqttToken connect(Object userContext, IMqttActionListener callback)
				throws MqttException, MqttSecurityException {
			connected = true;
			return null;
		}

		@Override
		public IMqttToken connect(MqttConnectOptions options, Object userContext, IMqttActionListener callback)
				throws MqttException, MqttSecurityException {
			callback.onSuccess(null);
			return null;
		}

		@Override
		public IMqttToken disconnect() throws MqttException {
			connected = false;
			return null;
		}

		@Override
		public IMqttToken disconnect(long quiesceTimeout) throws MqttException {
			connected = false;
			return null;
		}

		@Override
		public IMqttToken disconnect(Object userContext, IMqttActionListener callback) throws MqttException {
			connected = false;
			callback.onSuccess(null);
			return null;
		}

		@Override
		public IMqttToken disconnect(long quiesceTimeout, Object userContext, IMqttActionListener callback)
				throws MqttException {
			connected = false;
			callback.onSuccess(null);
			return null;
		}

		@Override
		public void disconnectForcibly() throws MqttException {
			connected = false;			
		}

		@Override
		public void disconnectForcibly(long disconnectTimeout) throws MqttException {
			connected = false;			
		}

		@Override
		public void disconnectForcibly(long quiesceTimeout, long disconnectTimeout) throws MqttException {
			connected = false;			
		}

		@Override
		public boolean isConnected() {
			return connected;
		}

		@Override
		public String getClientId() {
			return clientId;
		}

		@Override
		public String getServerURI() {
			return serverUrl;
		}

		@Override
		public IMqttDeliveryToken publish(String topic, byte[] payload, int qos, boolean retained)
				throws MqttException, MqttPersistenceException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IMqttDeliveryToken publish(String topic, byte[] payload, int qos, boolean retained, Object userContext,
				IMqttActionListener callback) throws MqttException, MqttPersistenceException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IMqttDeliveryToken publish(String topic, MqttMessage message)
				throws MqttException, MqttPersistenceException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IMqttDeliveryToken publish(String topic, MqttMessage message, Object userContext,
				IMqttActionListener callback) throws MqttException, MqttPersistenceException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IMqttToken subscribe(String topicFilter, int qos) throws MqttException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IMqttToken subscribe(String topicFilter, int qos, Object userContext, IMqttActionListener callback)
				throws MqttException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IMqttToken subscribe(String[] topicFilters, int[] qos) throws MqttException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IMqttToken subscribe(String[] topicFilters, int[] qos, Object userContext, IMqttActionListener callback)
				throws MqttException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IMqttToken unsubscribe(String topicFilter) throws MqttException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IMqttToken unsubscribe(String[] topicFilters) throws MqttException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IMqttToken unsubscribe(String topicFilter, Object userContext, IMqttActionListener callback)
				throws MqttException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IMqttToken unsubscribe(String[] topicFilters, Object userContext, IMqttActionListener callback)
				throws MqttException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setCallback(MqttCallback callback) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public IMqttDeliveryToken[] getPendingDeliveryTokens() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void close() throws MqttException {
			// TODO Auto-generated method stub
			
		}
		
	}

	@Before
	public void setUp() {
		MqttConnectOptions conOpts = new MqttConnectOptions();
		TestClient client = new TestClient();		
		connection = new AsyncConnection(client, conOpts);
	}
	
	@Test
	public void testPackTopics() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetConnectionCallback() {
		fail("Not yet implemented");
	}

	@Test
	public void testConnect() {
		fail("Not yet implemented");
	}

	@Test
	public void testDisconnect() {
		fail("Not yet implemented");
	}

	@Test
	public void testPublish() {
		fail("Not yet implemented");
	}

	@Test
	public void testSubscribe() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnsubscribe() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetServerUrlAsString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

}
