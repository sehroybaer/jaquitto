package de.rstahn.roy.jaquitto;

public class DebugConnection implements Connection {
	final private String name;
	final private String urlString;
	
	public DebugConnection(String name, String urlString) {
		this.name = name;
		this.urlString = urlString;
	}

	@Override
	public void connect(ActionResult actionResult) {
		System.out.println("Connected.");
	}

	@Override
	public void disconnect(ActionResult actionResult) {
		System.out.println("Disconnected.");
	}

	@Override
	public void publish(String topic, String message, int qos, ActionResult actionResult) {
		System.out.println(message + " published to "  + topic + "[qos=" + qos + "]");
	}

	@Override
	public void subscribe(String topic, int qos, ActionResult actionResult) {
		System.out.println("Topic " + topic + " subscribed." + "[qos=" + qos + "]");
	}

	@Override
	public void unsubscribe(String topic, ActionResult actionResult) {
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
