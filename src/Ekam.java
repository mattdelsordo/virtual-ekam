/*
 * simulates my boy ekam
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Ekam extends JFrame implements ActionListener
{
	private JButton btnEkamsHead;
	private JLabel lblBitches;
	private HashTable asoiafReference;
	private GridBagConstraints c;
	private BufferedImage ekam;
	private URL bitchPath;
	
	public Ekam(String picPath, String dictionaryPath, String audioPath)
	{
		//initialize hash table
		asoiafReference = new HashTable(getClass().getResourceAsStream(dictionaryPath), 31);
		
		//create window
		this.setSize(400,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Virtual Ekam");
		
		//create sound clip
		bitchPath = getClass().getResource(audioPath);
		
		//constructor creates window
				
		//set layout
		GridBagLayout grid = new GridBagLayout();
		c = new GridBagConstraints();
		grid.columnWidths = new int[]{0};
		grid.rowHeights = new int[]{0, 0};
		this.setLayout(grid);
		
		//add elements
		try //get image for button
		{
			ekam = ImageIO.read(getClass().getResourceAsStream(picPath));
		}
		catch(Exception e)
		{
			System.out.println("Error reading image.");
			System.exit(1);
		}
		this.setIconImage(ekam);
		
		btnEkamsHead = new JButton(new ImageIcon(ekam));
		btnEkamsHead.addActionListener(this);
		c.gridx = 0;
		c.gridy = 0;
		this.add(btnEkamsHead, c);
		this.getRootPane().setDefaultButton(btnEkamsHead);
		
		lblBitches = new JLabel(asoiafReference.randomEntry() + ", bitches.");
		c.gridx = 0;
		c.gridy = 1;
		this.add(lblBitches, c);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		bitches();
		
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if (btnEkamsHead == ae.getSource())
		{
			bitches();
		}
	}
	
	private void bitches()
	{
		lblBitches.setText(asoiafReference.randomEntry() + ", bitches.");
		
		try
		{
			Clip bitches = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(bitchPath);
			bitches.open(ais);
			bitches.start();
		}
		catch(Exception e)
		{
			System.out.println("Error reading audio file.");
			System.exit(1);
		}
	}
}
