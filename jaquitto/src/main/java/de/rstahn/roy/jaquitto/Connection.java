package de.rstahn.roy.jaquitto;

public interface Connection {
	void connect();
	void disconnect();
	void publish(String topic, String message);
	void subscribe(String topic);
	void unsubscribe(String topic);
}
