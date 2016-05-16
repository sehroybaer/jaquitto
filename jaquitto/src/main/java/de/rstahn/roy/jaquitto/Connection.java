package de.rstahn.roy.jaquitto;

public interface Connection {
	void connect(ActionResult actionResult);
	void disconnect(ActionResult actionResult);
	void publish(String topic, String message, int qos, ActionResult actionResult);
	void subscribe(String topic, int qos, ActionResult actionResult);
	void unsubscribe(String topic, ActionResult actionResult);
	String getServerUrlAsString();
	String getName();
}
