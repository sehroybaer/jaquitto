package de.rstahn.roy.jaquitto;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MainWindow {
	private Shell shell;
	private CTabFolder tabFolder;
	
	public void open() {
		Display display = new Display(); //Display.getDefault();
		createShell();
		createContent();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void createShell() {
		shell = new Shell();
		shell.setMinimumSize(new Point(300, 39));
		shell.setSize(450, 300);
		shell.setText("Jaquitto, a mqqt client in java");		
	}
	

	private void createContent() {
		if(shell == null) {
			createShell();
		}
		shell.setLayout(new GridLayout());
		Button someButton = new Button(shell, SWT.PUSH);
		someButton.setText("New Connection");
		tabFolder = new CTabFolder (shell, SWT.BORDER);
		
		someButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createConnectionTab();
			}
		});		
	}
	
	private void createConnectionTab() {
		if(tabFolder == null) {
			return;
		}
		CreateConnectionDialog dialog = new CreateConnectionDialog(shell, SWT.APPLICATION_MODAL);
		Map<String, String> result = dialog.open();
		if(result.isEmpty()) {
			System.out.println("Result was emtpy. Dialog canceled.");
			return;
		} else {
			System.out.println("Connection created.");
			System.out.println("  name = " + result.get("name"));
			System.out.println("  url  = " + result.get("url"));
		}
		DebugConnection connection = new DebugConnection(result.get("name"), result.get("url"));
		final CTabItem item = new CTabItem(tabFolder, SWT.CLOSE);
		item.setText(connection.getName());
		Composite page = new ConnectionComposite(tabFolder, SWT.NONE, connection);
		page.pack();
		item.setControl(page);
		item.addDisposeListener(new TabDisposeListener(connection, (ActionResult) page));
		tabFolder.setSelection(item);
		tabFolder.pack();
		shell.pack();
	}

}
