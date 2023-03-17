package lib.Main;

import java.io.*;
import java.util.*;
import lib.frame.Home;

class Remover
{
	public static void main(String args[]) {
        try 
		{
			File logFile = new File("log");
			if(!logFile.exists())
            {
                logFile.createNewFile();
            }
		} catch (Exception ex) 
		{
			System.out.println(ex);
		}
			
		java.awt.EventQueue.invokeLater(new Runnable() 
		{
			public void run() {
				new Home().setVisible(true);
			}
		});
	}
}
