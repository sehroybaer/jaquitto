package de.rstahn.roy.jaquitto;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

public interface SignalDispatcher {
	void addSelectionListener(String buttonName, SelectionListener listener);
	void setEnabled(String controlName, boolean enabled);
	void addStringToListbox(String listBoxName, String item);
	void removeStringFromListbox(String listboxName, String topic);
	void clearListbox(String listBoxName);
	
	String getTextFieldContent(String textFieldName);
	String getSelectedStringFromListbox(String listboxName);
	boolean listboxIsEmpty(String listboxName);
	
	void addButton(String buttonName, Button button);
	void addControl(String controlName, Control control);
	void addListbox(String listboxName, List listBox);
	void addTextfield(String textfieldName, Text textField);
}
