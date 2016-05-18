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
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;

public class AsyncConnectionTest {
	
	AsyncConnection connection;
	TestActionResult actionResult;
	
	private class TestActionResult implements ActionResult {

		private boolean connected = false;
		private boolean subscribed = false;
		private boolean published = false;
		private String cause;
		private String topic;
		
		public boolean isConnected() {
			return connected;
		}
		
		public boolean hasSubscribed() {
			return subscribed;
		}
		
		public boolean hasPublished() {
			return published;
		}
		
		public String getCause() {
			return cause;
		}
		
		public String getTopic() {
			return topic;
		}
		
		@Override
		public void connected() {
			connected = true;			
		}

		@Override
		public void connectFailed(String cause) {
			connected = false;
			this.cause = cause;			
		}

		@Override
		public void disconnected() {
			connected = false;
			published = false;
			subscribed = false;
			cause = null;
			topic = null;			
		}

		@Override
		public void disconnectedFailed(String cause) {
			this.cause = cause;			
		}

		@Override
		public void subscribed(String topic) {
			subscribed = true;
			this.topic = topic;			
		}

		@Override
		public void subscribeFailed(String topic, String cause) {
			this.cause = cause;			
		}

		@Override
		public void unsubscribed(String topic) {
			subscribed = false;
			this.topic = topic;			
		}

		@Override
		public void unsubscribeFailed(String topic, String cause) {
			this.topic = topic;
			this.cause = cause;			
		}

		@Override
		public void published(String topic) {
			published = true;
			this.topic = topic;			
		}

		@Override
		public void publishFailed(String topic, String cause) {
			this.topic = topic;
			this.cause = cause;			
		}		
	}
	
	private class TestConnectionEvents implements ConnectionEvents {

		private String causeOfLostConnection;
		private String topic;
		private String message;
		
		public String getCause() {
			return causeOfLostConnection;
		}
		
		public String getTopic() {
			return topic;
		}
		
		public String getMessage() {
			return message;
		}
		
		@Override
		public void connectionLost(String cause) {
			this.causeOfLostConnection = cause;			
		}

		@Override
		public void deliveryComplete(String topic) {
			this.topic = topic;			
		}

		@Override
		public void messageArrived(String topic, String message) {
			this.topic = topic;
			this.message = message;			
		}
		
	}
	
	private class TestToken implements IMqttToken {
		private final String topic;
		private final String message;
		
		public TestToken(String topic, String message) {
			this.topic = topic;
			this.message = message;
		}

		@Override
		public void waitForCompletion() throws MqttException {
			throw new UnsupportedOperationException();			
		}

		@Override
		public void waitForCompletion(long timeout) throws MqttException {
			throw new UnsupportedOperationException();			
		}

		@Override
		public boolean isComplete() {
			return true;
		}

		@Override
		public MqttException getException() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setActionCallback(IMqttActionListener listener) {
			throw new UnsupportedOperationException();			
		}

		@Override
		public IMqttActionListener getActionCallback() {
			throw new UnsupportedOperationException();
		}

		@Override
		public IMqttAsyncClient getClient() {
			throw new UnsupportedOperationException();
		}

		@Override
		public String[] getTopics() {
			String topics[] = {topic};
			return topics;
		}

		@Override
		public void setUserContext(Object userContext) {
			throw new UnsupportedOperationException();		
		}

		@Override
		public Object getUserContext() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getMessageId() {
			return 0;
		}

		@Override
		public int[] getGrantedQos() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean getSessionPresent() {
			return false;
		}

		@Override
		public MqttWireMessage getResponse() {
			throw new UnsupportedOperationException();
		}
		
	}
	
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
		private MqttCallback callback;

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
			connected = true;
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
			// not interested in
			throw new UnsupportedOperationException();
		}

		@Override
		public IMqttDeliveryToken publish(String topic, byte[] payload, int qos, boolean retained, Object userContext,
				IMqttActionListener callback) throws MqttException, MqttPersistenceException {
			callback.onSuccess(new TestToken(topic, new String(payload)));
			return null;
		}

		@Override
		public IMqttDeliveryToken publish(String topic, MqttMessage message)
				throws MqttException, MqttPersistenceException {
			// not interested in
			throw new UnsupportedOperationException();
		}

		@Override
		public IMqttDeliveryToken publish(String topic, MqttMessage message, Object userContext,
				IMqttActionListener callback) throws MqttException, MqttPersistenceException { 
			callback.onSuccess(new TestToken(topic, new String(message.getPayload())));
			return null;
		}

		@Override
		public IMqttToken subscribe(String topicFilter, int qos) throws MqttException {
			// will not be used
			throw new UnsupportedOperationException();
		}

		@Override
		public IMqttToken subscribe(String topicFilter, int qos, Object userContext, IMqttActionListener callback)
				throws MqttException {
			callback.onSuccess(new TestToken(topicFilter, "some message"));
			return null;
		}

		@Override
		public IMqttToken subscribe(String[] topicFilters, int[] qos) throws MqttException {
			// will not be used
			throw new UnsupportedOperationException();
		}

		@Override
		public IMqttToken subscribe(String[] topicFilters, int[] qos, Object userContext, IMqttActionListener callback)
				throws MqttException {
			// will not be used
			throw new UnsupportedOperationException();
		}

		@Override
		public IMqttToken unsubscribe(String topicFilter) throws MqttException {
			// will not be used, throw
			throw new UnsupportedOperationException();
		}

		@Override
		public IMqttToken unsubscribe(String[] topicFilters) throws MqttException {
			throw new UnsupportedOperationException();
		}

		@Override
		public IMqttToken unsubscribe(String topicFilter, Object userContext, IMqttActionListener callback)
				throws MqttException {
			callback.onSuccess(new TestToken(topicFilter, "testmessage"));
			return null;
		}

		@Override
		public IMqttToken unsubscribe(String[] topicFilters, Object userContext, IMqttActionListener callback)
				throws MqttException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setCallback(MqttCallback callback) {
			this.callback = callback;
			
		}

		@Override
		public IMqttDeliveryToken[] getPendingDeliveryTokens() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void close() throws MqttException {
			throw new UnsupportedOperationException();
		}
		
	}

	@Before
	public void setUp() {
		MqttConnectOptions conOpts = new MqttConnectOptions();
		TestClient client = new TestClient();		
		connection = new AsyncConnection(client, conOpts);
		actionResult = new TestActionResult();
	}
	
	@Test
	public void testPackTopics() {		
		String nullTopic = AsyncConnection.packTopics(null);
		assertNull(nullTopic);
		
		String emptyTopics[] = {};
		String emptyTopic = AsyncConnection.packTopics(emptyTopics);
		assertThat(emptyTopic, is("unknown"));
		
		String topics[] = {"one", "two" , "three"};
		String expectedTopic = "one;two;three";
		String actualTopic = AsyncConnection.packTopics(topics);
		assertThat(actualTopic, is(expectedTopic));
	}

	@Test
	public void testConnect() {
		connection.connect(actionResult);
		assertThat(actionResult.isConnected(), is(true));
	}

	@Test
	public void testDisconnect() {
		connection.connect(actionResult);
		assertThat(actionResult.isConnected(), is(true));
		connection.disconnect(actionResult);
		assertThat(actionResult.isConnected(), is(false));
	}

	@Test
	public void testPublish() {
		connection.connect(actionResult);
		assertThat(actionResult.isConnected(), is(true));
		connection.publish("test_", "testmessage_", 1, actionResult);
		assertThat(actionResult.hasPublished(), is(true));
		assertThat(actionResult.getTopic(), is("test_"));
		connection.disconnect(actionResult);
		assertThat(actionResult.isConnected(), is(false));
	}

	@Test
	public void testSubscribe() {
		connection.connect(actionResult);
		assertThat(actionResult.isConnected(), is(true));
		connection.subscribe("subscribe_test", 1, actionResult);
		assertThat(actionResult.hasSubscribed(), is(true));
		assertThat(actionResult.getTopic(), is("subscribe_test"));
		connection.disconnect(actionResult);
		assertThat(actionResult.isConnected(), is(false));
	}

	@Test
	public void testUnsubscribe() {
		connection.connect(actionResult);
		assertThat(actionResult.isConnected(), is(true));
		connection.subscribe("unsubscribe test", 1, actionResult);
		assertThat(actionResult.hasSubscribed(), is(true));
		connection.unsubscribe("unsubscribe test", actionResult);
		assertThat(actionResult.hasSubscribed(), is(false));
		assertThat(actionResult.getTopic(), is("unsubscribe test"));
		connection.disconnect(actionResult);
		assertThat(actionResult.isConnected(), is(false));
	}

	@Test
	public void testGetServerUrlAsString() {
		String actualServerUrl = connection.getServerUrlAsString();
		String expectedServerUrl = "tcp://localhost:1883";
		assertThat(actualServerUrl, is(expectedServerUrl));
	}

	@Test
	public void testGetName() {
		String actualConnectionName = connection.getName();
		String expectedConnectionName = "Test Client";
		assertThat(actualConnectionName, is(expectedConnectionName));
	}

}
