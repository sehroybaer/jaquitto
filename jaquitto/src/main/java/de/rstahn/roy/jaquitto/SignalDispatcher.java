package de.rstahn.roy.jaquitto;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;

public interface SignalDispatcher {
	void addSelectionListener(String buttonName, SelectionListener listener);
	void setEnabled(String controlName, boolean enabled);
	void addStringToListbox(String listBoxName, String item);
	void removeStringFromListbox(String listboxName, String topic);
	void clearListbox(String listBoxName);
	
	String getTextFieldContent(String textFieldName);
	String getSelectedStringFromListbox(String listboxName);
	boolean listboxIsEmpty(String listboxName);
	
	void addButton(Button button);
	void addControl(Control control);
	void addListbox(List listBox);

}
