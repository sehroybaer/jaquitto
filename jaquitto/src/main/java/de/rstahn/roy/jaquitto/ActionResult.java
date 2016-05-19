package de.rstahn.roy.jaquitto;

public interface ActionResult extends DisconnectActionResult{
	void connected();
	void connectFailed(String cause);
	void disconnected();
	void disconnectedFailed(String cause);
	void subscribed(String topic);
	void subscribeFailed(String topic, String cause);
	void unsubscribed(String topic);
	void unsubscribeFailed(String topic, String cause);
	void published(String topic);
	void publishFailed(String topic, String cause);
}
