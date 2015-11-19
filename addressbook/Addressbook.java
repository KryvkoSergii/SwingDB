package lesson16.addressbook;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import java.awt.event.MouseAdapter;

public class Addressbook extends JFrame implements ActionListener,
		PropertyChangeListener, ItemListener, MouseListener {

	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmOpenDb;
	private JMenuItem mntmClose;
	private JMenu mnEdit;
	private JMenuItem mntmAddRecord;
	private JMenu mnHelp;
	private JMenuItem mntmAbout;
	private JScrollPane scrollPane;
	private JTable table;
	private TableModel tm;
	private AddRecord ad;
	private boolean isConnected = false;

	private Object[] header;
	private Object[][] data;
	private JMenu mnChooseDb;
	private JRadioButtonMenuItem rdbtnmntmMysql;
	private JRadioButtonMenuItem rdbtnmntmSqlite;
	private JRadioButtonMenuItem rdbtnmntmNone;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Addressbook frame = new Addressbook();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Addressbook() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		{
			menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				mnFile = new JMenu("File");
				menuBar.add(mnFile);
				{
					mntmOpenDb = new JMenuItem("Connect to DB");
					mntmOpenDb.setActionCommand("connectToDB");
					mntmOpenDb.addActionListener(this);
					mnFile.add(mntmOpenDb);
					mntmOpenDb.setIcon(new ImageIcon("no-icon.png"));
					// mntmOpenDb.addActionListener(this);
					// mntmOpenDb.setActionCommand("opendb");

				}
				{
					ButtonGroup bgChooseDb = new ButtonGroup();
					mnChooseDb = new JMenu("Choose DB");
					mnFile.add(mnChooseDb);
					mnChooseDb.setIcon(new ImageIcon("no-icon.png"));
					{
						rdbtnmntmMysql = new JRadioButtonMenuItem("MySQL");
						bgChooseDb.add(rdbtnmntmMysql);
						rdbtnmntmMysql.addItemListener(this);
						mnChooseDb.add(rdbtnmntmMysql);
					}
					{
						rdbtnmntmSqlite = new JRadioButtonMenuItem("SQLite");
						bgChooseDb.add(rdbtnmntmSqlite);
						rdbtnmntmSqlite.addItemListener(this);
						mnChooseDb.add(rdbtnmntmSqlite);
					}
					{
						rdbtnmntmNone = new JRadioButtonMenuItem("none");
						bgChooseDb.add(rdbtnmntmNone);
						rdbtnmntmNone.addItemListener(this);
						mnChooseDb.add(rdbtnmntmNone);
					}
				}
				{
					mntmClose = new JMenuItem("Close");
					mnFile.add(mntmClose);
					mntmClose.addActionListener(this);
					mntmClose.setActionCommand("close");
				}
			}
			{
				mnEdit = new JMenu("Edit");
				menuBar.add(mnEdit);
				{
					mntmAddRecord = new JMenuItem("Add record");
					mnEdit.add(mntmAddRecord);
					mntmAddRecord.addActionListener(this);
					mntmAddRecord.setActionCommand("addrecord");
				}
			}
			{
				mnHelp = new JMenu("Help");
				menuBar.add(mnHelp);
				{
					mntmAbout = new JMenuItem("About");
					mnHelp.add(mntmAbout);
					mntmAbout.addActionListener(this);
					mntmAbout.setActionCommand("help");
				}
			}
		}
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		{
			scrollPane = new JScrollPane();
			contentPane.add(scrollPane, BorderLayout.CENTER);
			{

			}
		}
	}

	/**
	 * handling of Action Event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("opendb")) {

		} else if (e.getActionCommand().equals("close")) {
			System.exit(0);
		} else if (e.getActionCommand().equals("addrecord")) {
			ad = new AddRecord();
			ad.setVisible(true);
			ad.addPropertyChangeListener(this);

		} else if (e.getActionCommand().equals("help")) {
			myJframe mf = new myJframe();
			mf.setVisible(true);

		} else if (e.getActionCommand().equals("connectToDB")) {

			if (isConnected == true) {
				mntmOpenDb.setIcon(new ImageIcon("no-icon.png"));
				mntmOpenDb.setText("Connect to DB");
				table.setVisible(false);
				this.remove(table);
				isConnected = false;
				this.repaint();

			} else {
				tm = new TableModel();
				table = new JTable(tm);
				table.addMouseListener(this);
				scrollPane.setViewportView(table);
				tm.execureQuery(Command.showAllRecords);
				table.setAutoCreateRowSorter(true);
				tm.fireTableStructureChanged();

				/*
				 * deleting of column
				 */
				TableColumn tc = table.getColumnModel().getColumn(0);
				table.removeColumn(tc);

				this.repaint();
				mntmOpenDb.setIcon(new ImageIcon("ok-icon.png"));
				mntmOpenDb.setText("Disconnect DB");
				isConnected = true;
			}

		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		JOptionPane.showMessageDialog(table, "!!!!");
		if (evt.getPropertyName().equals("newuser")) {
			JOptionPane.showMessageDialog(table, "newuser");
			String[] fromAddDialog = (String[]) evt.getNewValue();
			int a = 0;
			try {
				tm.addRecordWithBLOB(fromAddDialog[a++], fromAddDialog[a++],
						fromAddDialog[a++], fromAddDialog[a++],
						fromAddDialog[a++]);
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * handling type of db change
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		if ((e.getStateChange() == ItemEvent.SELECTED)
				&& !(((JRadioButtonMenuItem) e.getSource()).getText())
						.equals("none")) {
			mnChooseDb.setIcon(new ImageIcon("ok-icon.png"));

			JOptionPane.showMessageDialog(menuBar,
					((JRadioButtonMenuItem) e.getSource()).getText()
							+ " data base type selected");
		} else {
			mnChooseDb.setIcon(new ImageIcon("no-icon.png"));
			JOptionPane
					.showMessageDialog(menuBar, " data base type deselected");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		JOptionPane.showMessageDialog(null, table.getSelectedColumn());

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
