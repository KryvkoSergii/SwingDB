package lesson16.addressbook;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AddRecord extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtName;
	private String name;
	private JButton okButton;
	private JButton cancelButton;
	private JLabel lblName;
	private JLabel lblSurname;
	private JTextField txtSurname;
	private JLabel lblAddress;
	private JTextField txtAddress;
	private JLabel lblTel;
	private JTextField txtTelephone;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	private JLabel lblNewLabel;
	private JPanel panel_7;
	private JButton btnLoadPhoto;
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private String[] value;
	private String photoPath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddRecord dialog = new AddRecord();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddRecord() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		panel_1 = new JPanel();

		panel_6 = new JPanel();
		panel_6.setLayout(new BorderLayout(0, 0));
		scrollPane = new JScrollPane();
		panel_6.add(scrollPane);
		{
			panel = new JPanel();
			scrollPane.setViewportView(panel);

			lblNewLabel = new JLabel("");
			panel.add(lblNewLabel);
		}
		contentPanel.setLayout(new BorderLayout(0, 0));
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_1.add(panel_2);
		lblName = new JLabel("Name       ");
		panel_2.add(lblName);
		txtName = new JTextField();
		panel_2.add(txtName);
		txtName.setColumns(15);

		panel_3 = new JPanel();
		panel_1.add(panel_3);
		lblSurname = new JLabel("Surname  ");
		panel_3.add(lblSurname);
		txtSurname = new JTextField();
		panel_3.add(txtSurname);
		txtSurname.setColumns(15);

		panel_4 = new JPanel();
		panel_1.add(panel_4);
		lblAddress = new JLabel("Address   ");
		panel_4.add(lblAddress);
		txtAddress = new JTextField();
		panel_4.add(txtAddress);
		txtAddress.setText("address");
		txtAddress.setColumns(15);

		panel_5 = new JPanel();
		panel_1.add(panel_5);

		lblTel = new JLabel("Telephone");
		panel_5.add(lblTel);

		txtTelephone = new JTextField();
		panel_5.add(txtTelephone);
		txtTelephone.setText("telephone");
		txtTelephone.setColumns(15);
		contentPanel.add(panel_1, BorderLayout.CENTER);
		contentPanel.add(panel_6, BorderLayout.EAST);

		panel_7 = new JPanel();
		panel_6.add(panel_7, BorderLayout.SOUTH);

		btnLoadPhoto = new JButton("Load photo");
		btnLoadPhoto.setActionCommand("loadphoto");
		btnLoadPhoto.addActionListener(this);
		panel_7.add(btnLoadPhoto);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new LineBorder(new Color(0, 0, 0)));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(this);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public String[] getRecordDesc() {
		String[] record = new String[5];
		record[0] = new String(txtName.getText());
		record[1] = new String(txtSurname.getText());
		record[2] = new String(txtAddress.getText());
		record[3] = new String(txtTelephone.getText());
		if (photoPath != null) {
			record[4] = new String(photoPath);
		} else {
			record[4] = null;
		}

		return record;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("OK")) {
			// setName(txtName.getText());
			setValue(getRecordDesc());
			this.setVisible(false);
		} else if (e.getActionCommand().equals("loadphoto")) {
			JFileChooser loadPhotoChooser = new JFileChooser();
			loadPhotoChooser.showOpenDialog(btnLoadPhoto);
			photoPath = loadPhotoChooser.getSelectedFile().getAbsolutePath();
			lblNewLabel.setIcon(new ImageIcon(photoPath));
		} else if (e.getActionCommand().equals("Cancel")) {
			this.dispose();
		}

	}

	// Change property support using PropertyChangeSupport class

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	public String[] getValue() {
		return this.value;
	}

	public void setValue(String[] newValue) {
		String[] oldValue = this.value;
		this.value = newValue;
		this.pcs.firePropertyChange("newuser", oldValue, newValue);
	}
}
