package de.rstahn.roy.jaquitto;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

public class ConnectionController implements SignalDispatcher {

	final ConnectionActor actions;
	final private Map<String, Button> buttons = new HashMap<String, Button>();
	final private Map<String, Control> controls = new HashMap<String, Control>();
	final private Map<String, List> listboxes = new HashMap<String, List>();
	final private Map<String, Text> textfields = new HashMap<String, Text>();

	public ConnectionController(Widget widget) {
		this.actions = new ConnectionActor(this, widget);
	}

	@Override
	public void setupButtonPressedListener(final Connection connection) {
		final SignalDispatcher dispatcher = this;
		dispatcher.addSelectionListener("Connect Button", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				connection.connect(actions);
			}			
		});

		dispatcher.addSelectionListener("Disconnect Button", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				connection.disconnect(actions);
			}
		});

		dispatcher.addSelectionListener("Publish Button", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final String topic = dispatcher.getTextFieldContent("Publish Topic");
				final String payload = dispatcher.getTextFieldContent("Publish Payload");
				final int qos = 1;
				if(isNotNullOrEmpty(topic) && isNotNullOrEmpty(payload)) {
					connection.publish(topic, payload, qos, actions);
				}
			}
		});

		dispatcher.addSelectionListener("Subscribe Button", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final String topic = dispatcher.getTextFieldContent("Subscribe Topic");
				final int qos = 1;
				if(isNotNullOrEmpty(topic)) {
					connection.subscribe(topic, qos, actions);
				}
			}
		});

		dispatcher.addSelectionListener("Unsubscribe Button", new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final String topic = dispatcher.getSelectedStringFromListbox("Subscriptions");
				if(isNotNullOrEmpty(topic)) { 
					connection.unsubscribe(topic, actions);
				}
			}
		});

	}

	public static boolean isNotNullOrEmpty(String text) {
		return text != null && !text.isEmpty();
	}
	
	@Override
	public void addSelectionListener(String buttonName, SelectionListener listener) {
		Button button = buttons.get(buttonName);
		if(button != null) {
			button.addSelectionListener(listener);
		} else {
			throw new NoSuchElementException(buttonName);
		}
	}

	@Override
	public void setEnabled(String controlName, boolean enabled) {
		Control control = controls.get(controlName);
		if(control != null) {
			control.setEnabled(enabled);
		} else {
			throw new NoSuchElementException(controlName);
		}
	}

	@Override
	public void addStringToListbox(String listboxName, String item) {
		List listbox = listboxes.get(listboxName);
		if(listbox != null) {
			listbox.add(item);
		} else {
			throw new NoSuchElementException(listboxName);
		}
	}

	@Override
	public void removeStringFromListbox(String listboxName, String item) {
		List listbox = listboxes.get(listboxName);
		if(listbox != null) {
			listbox.remove(item);
		} else {
			throw new NoSuchElementException(listboxName);
		}
	}

	@Override
	public void clearListbox(String listboxName) {
		List listbox = listboxes.get(listboxName);
		if(listbox != null) {
			listbox.removeAll();
		} else {
			throw new NoSuchElementException(listboxName);			
		}
	}

	@Override
	public String getTextFieldContent(String textfieldName) {
		Text textfield = textfields.get(textfieldName);
		if(textfield != null) {
			return textfield.getText();
		} else {
			throw new NoSuchElementException(textfieldName);
		}
	}

	@Override
	public String getSelectedStringFromListbox(String listboxName) {
		List listbox = listboxes.get(listboxName);
		if(listbox != null) {
			if(listbox.getItemCount() != 0) { 
				final int selection = listbox.getSelectionIndex();
				if(selection > -1) {
					return listbox.getItem(selection);
				}
			}
		} else {
			throw new NoSuchElementException(listboxName);
		}
		return null;
	}

	@Override
	public boolean listboxIsEmpty(String listboxName) {
		List listbox = listboxes.get(listboxName);
		if(listbox != null) {
			return listbox.getItemCount() == 0;
		} else {
			throw new NoSuchElementException(listboxName);
		}
	}

	@Override
	public void addButton(String buttonName, Button button) {
		buttons.put(buttonName, button);
		controls.put(buttonName, button);
	}

	@Override
	public void addControl(String controlName, Control control) {
		controls.put(controlName, control);
	}

	@Override
	public void addListbox(String listboxName, List listBox) {
		listboxes.put(listboxName, listBox);
		controls.put(listboxName, listBox);
	}

	@Override
	public void addTextfield(String textfieldName, Text textField) {
		textfields.put(textfieldName, textField);
		controls.put(textfieldName, textField);
	}
}
