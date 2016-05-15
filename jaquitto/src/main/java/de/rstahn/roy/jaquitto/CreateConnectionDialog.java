package de.rstahn.roy.jaquitto;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CreateConnectionDialog extends Dialog {

	final protected Map<String, String> result;
	protected Shell shell;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public CreateConnectionDialog(Shell parent, int style) {
		super(parent, style);
		result = new HashMap<String, String>();
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Map<String, String> open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(270, 197);
		shell.setText("Create Connection");
		
		final Label lblBrokerAddress = new Label(shell, SWT.NONE);
		lblBrokerAddress.setBounds(10, 38, 85, 15);
		lblBrokerAddress.setText("Broker address:");
		
		final Label lblPort = new Label(shell, SWT.NONE);
		lblPort.setBounds(10, 72, 55, 15);
		lblPort.setText("Port:");
		
		final Label lblName = new Label(shell, SWT.NONE);
		lblName.setBounds(10, 10, 55, 15);
		lblName.setText("Name:");
		
		final Text connectionName = new Text(shell, SWT.BORDER);
		connectionName.setBounds(100, 10, 150, 21);
		
		final Text serverAddress = new Text(shell, SWT.BORDER);
		serverAddress.setToolTipText("e.g.: 192.168.1.5 or my.server.com");
		serverAddress.setBounds(100, 38, 150, 21);
		
		final Spinner portSpinner = new Spinner(shell, SWT.BORDER);
		portSpinner.setMaximum(65000);
		portSpinner.setSelection(1883);
		portSpinner.setBounds(100, 70, 80, 22);
		
		final Shell thisShell = shell;
		Button btnOk = new Button(shell, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result.put("name",connectionName.getText());
				String urlString = "tcp://" + serverAddress.getText() + ":" + portSpinner.getText();
				result.put("url", urlString);
				thisShell.close();
			}
		});
		btnOk.setBounds(10, 122, 75, 25);
		btnOk.setText("OK");
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result.clear();
				thisShell.close();
			}
		});
		btnCancel.setBounds(175, 122, 75, 25);
		btnCancel.setText("Cancel");
	}
}
