package lesson16.addressbook;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MySign extends JPanel {
	
	private String author = "";
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
		repaint();
	}

	MySign()
	{
		super();
		this.setVisible(true);
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawLine(50, 50, 100, 100);
		g.drawLine(100, 100, 150, 50);
		g.drawLine(50, 50, 150, 50);
		g.setColor(Color.red);
		g.drawString("Designed by "+getAuthor(), 110, 100);
	}

}
