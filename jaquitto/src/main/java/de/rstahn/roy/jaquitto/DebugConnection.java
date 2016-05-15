package de.rstahn.roy.jaquitto;

public class DebugConnection implements Connection {
	final private String name;
	final private String urlString;
	
	public DebugConnection(String name, String urlString) {
		this.name = name;
		this.urlString = urlString;
	}

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

	@Override
	public String getServerUrlAsString() {
		return urlString;
	}

	@Override
	public String getName() {
		return name;
	}

}
