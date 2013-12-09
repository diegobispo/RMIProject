package src;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

/**
 * This class controls the components of main frame.
 * 
 * @author Diego Bispo
 */

public class MainFrame extends JFrame {

	public static final String APP_NAME = "RMI Project";

	private TextAnalyzer textAnalyser;

	protected JTextArea textEditor;
	protected JTextArea reportArea;

	JScrollPane spText;
	JScrollPane spReport;
	private JSplitPane splitPane;

	// component to choose File
	protected JFileChooser fileChooser;
	protected File currentFile;

	public MainFrame(TextAnalyzer textAnalyzer) {
		
		this.setTitle(APP_NAME);
		
		this.textAnalyser = textAnalyzer;

		textEditor = new JTextArea();
		textEditor.append(MessageI18NUtil.getMessage("type_here"));
		//Text Line Wrap
		textEditor.setLineWrap(true); 
		
		reportArea = new JTextArea();
		//reportArea.setVisible(false);
		reportArea.setEditable(false);
		reportArea.append(MessageI18NUtil.getMessage("report"));
		
		spText= new JScrollPane(textEditor);
		spReport = new JScrollPane(reportArea);
		spReport.setVisible(false);

		createFrame();

		JMenuBar menuBar = createMenuBar();
		setJMenuBar(menuBar);

		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("."));
	}

	protected void createFrame() {
		setSize(600, 600);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(spText);

	}

	protected void createFrameWithReportArea() {
		setSize(600, 600);

		spReport.setVisible(true);
		getContentPane().add(spReport);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, spText,
				reportArea);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		splitPane.setContinuousLayout(true);
	}

	protected JMenuBar createMenuBar() {
		final JMenuBar menuBar = new JMenuBar();

		// File Menu creation
		JMenu mFile = new JMenu(MessageI18NUtil.getMessage("file"));
		// set mnemonics to File menu
		mFile.setMnemonic(MessageI18NUtil.getMessage("file_mnemonics").charAt(0));

		// New Item Creator
		ImageIcon iconNew = new ImageIcon("image/New.gif");
		Action actionNew = new AbstractAction(MessageI18NUtil.getMessage("new_menu_item"), iconNew) {
			public void actionPerformed(ActionEvent e) {
				setNewDocument();
			}
		};

		JMenuItem item = new JMenuItem(actionNew);
		item.setIcon(iconNew);
		item.setMnemonic(MessageI18NUtil.getMessage("new_mnemonics").charAt(0));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_MASK));
		mFile.add(item);

		// Opem Item Creator
		ImageIcon iconOpen = new ImageIcon("image/Open.gif");
		Action actionOpen = new AbstractAction(MessageI18NUtil.getMessage("open_menu_item"), iconOpen) {
			public void actionPerformed(ActionEvent e) {
				opemFile();
			}
		};

		item = new JMenuItem(actionOpen);
		item.setMnemonic(MessageI18NUtil.getMessage("open_mnemonics").charAt(0));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				InputEvent.CTRL_MASK));
		mFile.add(item);

		// Save Item Creator
		ImageIcon iconSave = new ImageIcon("image/Save.gif");
		Action actionSave = new AbstractAction(MessageI18NUtil.getMessage("save_menu_item"), iconSave) {
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		};

		item = new JMenuItem(actionSave);
		item.setMnemonic(MessageI18NUtil.getMessage("save_mnemonics").charAt(0));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK));
		mFile.add(item);

		// Separator
		mFile.addSeparator();

		// Exit Item Creator
		Action actionExit = new AbstractAction(MessageI18NUtil.getMessage("exit_menu_item")) {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		item = new JMenuItem(actionExit);
		item.setMnemonic(MessageI18NUtil.getMessage("exit_mnemonics").charAt(0));
		mFile.add(item);

		// Add File Menu to Menubar
		menuBar.add(mFile);

		// Report Menu creation
		JMenu mReport = new JMenu(MessageI18NUtil.getMessage("report_menu"));
		// set mnemonics to Report menu
		mReport.setMnemonic(MessageI18NUtil.getMessage("report_mnemonics").charAt(0));

		// Show Report Item Creator
		ImageIcon iconShowReport = new ImageIcon("image/ShowReport.gif");
		Action actionShowHideReport = new AbstractAction(MessageI18NUtil.getMessage("show_report_menu_item"),
				iconShowReport) {
			public void actionPerformed(ActionEvent e) {
				showHideReport();
			}
		};

		item = new JMenuItem(actionShowHideReport);
		item.setMnemonic(MessageI18NUtil.getMessage("show_report_mnemonics").charAt(0));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				InputEvent.CTRL_MASK));
		mReport.add(item);

		// Clear Report Item Creator
		ImageIcon iconClearReport = new ImageIcon("image/ClearReport.gif");
		Action actionClearReport = new AbstractAction(MessageI18NUtil.getMessage("clear_menu_item"), iconClearReport) {
			public void actionPerformed(ActionEvent e) {
				clearReport();
			}
		};

		item = new JMenuItem(actionClearReport);
		item.setMnemonic(MessageI18NUtil.getMessage("clear_mnemonics").charAt(0));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				InputEvent.CTRL_MASK));
		mReport.add(item);

		// Separator
		mReport.addSeparator();

		// Nunber Words Report Item Creator
		ImageIcon iconNunberWordsReport = new ImageIcon("image/NunberWords.gif");
		Action actionNunberWordsReport = new AbstractAction(MessageI18NUtil.getMessage("nunber_words_menu_item"),
				iconNunberWordsReport) {
			public void actionPerformed(ActionEvent e) {
				showNunberWordsReport();
			}
		};

		item = new JMenuItem(actionNunberWordsReport);
		item.setMnemonic(MessageI18NUtil.getMessage("nunber_words_mnemonics").charAt(0));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
				InputEvent.CTRL_MASK));
		mReport.add(item);

		// Unique Nunber Words Report
		ImageIcon iconUniqueWordsReport = new ImageIcon("image/NunberWords.gif");
		Action actionUniqueWordsReport = new AbstractAction(MessageI18NUtil.getMessage("unique_words_menu_item"),
				iconUniqueWordsReport) {
			public void actionPerformed(ActionEvent e) {
				showUniqueWordsReport();
			}
		};

		item = new JMenuItem(actionUniqueWordsReport);
		item.setMnemonic(MessageI18NUtil.getMessage("unique_words_mnemonics").charAt(0));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				InputEvent.CTRL_MASK));
		mReport.add(item);

		// Usage Words Report
		ImageIcon iconUsageWordsReport = new ImageIcon("image/UsageWords.gif");
		Action actionUsageWordsReport = new AbstractAction(MessageI18NUtil.getMessage("usage_words_menu_item"),
				iconUsageWordsReport) {
			public void actionPerformed(ActionEvent e) {
				showUsageWordsReport();
			}
		};

		item = new JMenuItem(actionUsageWordsReport);
		item.setMnemonic(MessageI18NUtil.getMessage("usage_words_mnemonics").charAt(0));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
				InputEvent.CTRL_MASK));
		mReport.add(item);

		// Add Report Menu to Menubar
		menuBar.add(mReport);

		return menuBar;
	}

	public void showError(Exception ex, String message) {
		ex.printStackTrace();
		JOptionPane.showMessageDialog(this, message, APP_NAME,
				JOptionPane.WARNING_MESSAGE);
	}

	public void setNewDocument() {
		textEditor.setText("");
		currentFile = null;
		setTitle(APP_NAME + " [Untitled]");
	}

	private void opemFile() {
		int result = fileChooser.showOpenDialog(MainFrame.this);
		repaint();
		if (result != JFileChooser.APPROVE_OPTION)
			return;
		File f = fileChooser.getSelectedFile();
		if (f == null || !f.isFile())
			return;
		currentFile = f;
		try {
			FileReader in = new FileReader(currentFile);
			textEditor.read(in, null);
			in.close();
			setTitle(APP_NAME + " [" + currentFile.getName() + "]");
		} catch (IOException ex) {
			showError(ex, MessageI18NUtil.getMessage("error_reading_file") + currentFile);
		}
	}

	private void saveFile() {
		if (currentFile == null) {
			int result = fileChooser.showSaveDialog(MainFrame.this);
			repaint();
			if (result != JFileChooser.APPROVE_OPTION)
				return;
			File f = fileChooser.getSelectedFile();
			if (f == null)
				return;
			currentFile = f;
			setTitle(APP_NAME + " [" + currentFile.getName() + "]");
		}

		try {
			FileWriter out = new FileWriter(currentFile);
			textEditor.write(out);
			out.close();
		} catch (IOException ex) {
			showError(ex, MessageI18NUtil.getMessage("error_saving_file") + currentFile);
		}
	}

	private void showHideReport() {

		boolean isVisible = spReport.isVisible();
		Container contentPane = getContentPane();
		if (isVisible) {
			contentPane.remove(splitPane);
			contentPane.remove(spText);
			contentPane.remove(spReport);

			createFrame();
		} else {
			createFrameWithReportArea();
		}

		contentPane.validate();
		contentPane.repaint();

		spReport.setVisible(!isVisible);
	}

	public void showReport() {
		boolean isVisible = spReport.isVisible();
		if (!isVisible) {
			showHideReport();
		}
	}

	private void showUniqueWordsReport() {
		clearReport();
		String txt = textEditor.getText();
		long textID;
		try {
			textID = textAnalyser.storeText(txt);

			int uniqueWords = textAnalyser.countUniqueWords(textID);

			reportArea.append(MessageI18NUtil.getMessage("unique_words")+ uniqueWords);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		showReport();
	}

	private void showUsageWordsReport() {
		clearReport();
		String txt = textEditor.getText();
		try {
			long textID = textAnalyser.storeText(txt);
			List<WordCount> listWord = textAnalyser.wordUsage(textID);

			reportArea.append(MessageI18NUtil.getMessage("usage_words"));

			for (WordCount wordCount : listWord) {
				reportArea.append(" * " + wordCount.word + ": " + wordCount.count
						+ "\n");
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		showReport();
	}

	private void showNunberWordsReport() {
		clearReport();
		String txt = textEditor.getText();
		long textID;
		try {
			textID = textAnalyser.storeText(txt);

			int nunberWords = textAnalyser.countWords(textID);

			reportArea.append(MessageI18NUtil.getMessage("nunber_words") + nunberWords);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		showReport();
	}

	protected void clearReport() {
		reportArea.setText("");
		reportArea.append(MessageI18NUtil.getMessage("report"));
	}

	public void sowFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}