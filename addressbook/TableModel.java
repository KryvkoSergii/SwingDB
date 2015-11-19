package lesson16.addressbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


public class TableModel extends AbstractTableModel {
	private Connection connection;
	private List<Object[]> data = new ArrayList<>();
	private List<Object> header = new ArrayList<>();
	private String table = "USERS";
	private String dbPath = "mydb.sqlite";

	public TableModel() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:"+dbPath);
			Statement createStatement = connection.createStatement();
			String createUserTable = "CREATE TABLE IF NOT EXISTS "+table+" (id int auto_increment primary key, name VARCHAR(30), surname VARCHAR(30), address VARCHAR(100), telephone VARCHAR(20), photo LONGBLOB)";
			data.add(new Object[1]);
			header.add(new Object());
			createStatement.executeUpdate(createUserTable);
			createStatement.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Executes query 'query' and adds result to List<> data structures.
	 * @param query
	 */
	public void execureQuery(String query)
	{
		Statement createStatement;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		header.clear();
		data.clear();
		try {
			createStatement = connection.createStatement();
			rs = createStatement.executeQuery(query);
			rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			
			for(int i=0; i<columnCount;i++)
			{
				header.add(rsmd.getColumnName(i+1));
			}
			
			while(rs.next())
			{
				Object[] object = new Object[columnCount];
				for(int i=0; i<object.length;i++)
				{
					object[i] = rs.getObject(i+1);
				}
				data.add(object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.fireTableStructureChanged();

	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return (data.get(0).length);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex)[columnIndex];
	}
	
	@Override
	public String getColumnName(int column)
	{	
		return header.get(column).toString();
	}
	
	/**
	 * Writes a new record with LONGBLOB direct stream to database 
	 * @param name
	 * @param surname
	 * @param address
	 * @param telephone
	 * @param photoPath
	 * @throws SQLException
	 * @throws IOException
	 */
	public void addRecordWithBLOB(String name, String surname, String address, String telephone, String photoPath) throws SQLException, IOException
	{
		FileInputStream fis = null;
	    PreparedStatement ps = null;
	   
	    if (photoPath != null)
	    {
			try {
				connection.setAutoCommit(false);
				File file = new File(photoPath);
				fis = new FileInputStream(file);
				ps = connection.prepareStatement("INSERT INTO ? (name, surname, address, telephone, photo) VALUES(?,?,?,?,?)");
				ps.setString(1, table);
				ps.setString(2, name);
				ps.setString(3, surname);
				ps.setString(4, address);
				ps.setString(5, telephone);
//				System.out.println("Size"+file.length());
				ps.setBinaryStream(6, fis, file.length());
				ps.executeUpdate();
				connection.commit();
			} finally
			{
				ps.close();
				fis.close();
			}
	    } else
	    {
			try {
				connection.setAutoCommit(false);
				File file = new File(photoPath);
				fis = new FileInputStream(file);
				ps = connection.prepareStatement("INSERT INTO ? (name, surname, address, telephone) VALUES(?,?,?,?)");
				ps.setString(1, table);
				ps.setString(2, name);
				ps.setString(3, surname);
				ps.setString(4, address);
				ps.setString(5, telephone);
//				System.out.println("Size"+file.length());
				ps.executeUpdate();
				connection.commit();
			} finally
			{
				ps.close();
				fis.close();
			}
	    }
	    this.fireTableDataChanged();
	}

}
