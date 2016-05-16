package de.rstahn.roy.jaquitto;

public interface ConnectionEvents {
	void connectionLost(String cause);
	void deliveryComplete(String topic, String message);
	void messageArrived(String topic, String message);
}
