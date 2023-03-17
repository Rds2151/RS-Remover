package lib.store;

import java.io.*;
import java.time.LocalDate;

class LogCreate
{
	public static void addDataToLogFile(String Path,String status)
	{
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("log",true));
			LocalDate date = LocalDate.now();
			out.write(date.toString());
			out.write(","+status+","+Path+"\n");
			out.close();
		}
		catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
}
