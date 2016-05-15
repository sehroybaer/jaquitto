package de.rstahn.roy.jaquitto;

public class DebugConnection implements Connection {

	@Override
	public void connect() {
		System.out.println("Connected.");
	}

	@Override
	public void disconnect() {
		System.out.println("Disconnected.");
	}

	@Override
	public void publish(String topic, String message) {
		System.out.println(message + " published to "  + topic);
	}

	@Override
	public void subscribe(String topic) {
		System.out.println("Topic " + topic + " subscribed.");
	}

	@Override
	public void unsubscribe(String topic) {
		System.out.println("Topic " + topic + " unsubscribed.");
	}

}
