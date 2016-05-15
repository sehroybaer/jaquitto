package de.rstahn.roy.jaquitto;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class MainWindow {
	private Shell shell;
	private TabFolder tabFolder;
	
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
		tabFolder = new TabFolder (shell, SWT.BORDER);
		
		someButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createConnectionTab();
			}
		});
		//shell.pack();
		
	}
	
	private void createConnectionTab() {
		if(tabFolder == null) {
			return;
		}
		final TabItem item = new TabItem(tabFolder, SWT.NONE);
		item.setText("Connection B");
		Composite page = new Composite(tabFolder, SWT.NONE);
		page.setLayout(new GridLayout());
		final Label label = new Label(page, SWT.NONE);
		label.setText("Label in tab area");
		item.setControl(page);
		tabFolder.pack();		
	}

}
