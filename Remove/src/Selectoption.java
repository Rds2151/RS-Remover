package lib.frame;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import lib.store.CkSumStore;
import lib.store.CRC_CheckSum;

class Selectoption extends JFrame implements ActionListener
{
	JPanel panel;
	JButton btn;
	JCheckBox cb[];
	File fileName = null;
	String path;
	
	public Selectoption(String path)
	{
		this.path = path;
		fileName = new File(path);
		setTitle("Select Option");
		panel = new JPanel();
		
		cb = new JCheckBox[3];
		setbounds();
	}
	
	private void setbounds()
	{
		cb[0] = new JCheckBox("Empty Directory");
		cb[0].setBounds(50,25,150,35);
		
		cb[1] = new JCheckBox("Empty File");
		cb[1].setBounds(50,60,150,35);
		
		cb[2] = new JCheckBox("Duplicate File",true);
		cb[2].setBounds(50,95,150,35);
		
		btn = new JButton("Proceed");
		btn.setBounds(50,140,150,35);
		add(panel);
		
		panel.add(cb[0]);
		panel.add(cb[1]);
		panel.add(cb[2]);
		panel.add(btn);
		panel.setLayout(null);
		btn.addActionListener(this);
		
		setSize(250,250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		setVisible(false);
		if(cb[0].isSelected())
		{
			CkSumStore.DeleteEmptyFolder(this.fileName);
		}
		if(cb[1].isSelected())
		{
			CkSumStore.DeleteEmptyFile(this.fileName);
		}
		if(cb[2].isSelected())
		{
			CRC_CheckSum ck = new CRC_CheckSum(this.path);
			new Thread(new Runnable() {
				public void run() {
					ck.execute();
				}
			}).start();
		}
	}
}
