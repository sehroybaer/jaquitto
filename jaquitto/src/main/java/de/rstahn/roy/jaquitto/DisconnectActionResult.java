package de.rstahn.roy.jaquitto;

public interface DisconnectActionResult {
	void disconnected();
	void disconnectedFailed(String cause);
}
