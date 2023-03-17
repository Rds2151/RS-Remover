package lib.store;

import java.io.File;

import javax.lang.model.util.ElementScanner6;

import lib.frame.ScanPanel;

class Node
{
	protected long lValue;
	protected String path;
	protected Node lchild;
	protected Node rchild;
	
	public Node(long lValue,String path)
	{
		this.lValue = lValue;
		this.path = path;
		lchild = null;
		rchild = null;
	}
}

public class CkSumStore
{
	Node head;
	public static boolean Flag = false; 

	public CkSumStore()
	{
		head = null;
	}
	
	void find(long Data,String path)
	{
		Node newn = new Node(Data,path);
		if(head == null)
		{
			head = newn;
		}
		else
		{
			Node temp = head;
			while(true)
			{
				if(temp.lValue == Data && temp.path != path)
				{
					DeleteFile(path);
					break;
				}
				else if(temp.lValue == Data && temp.path == path)
				{
					break;
				}
				else
				{
					LogCreate.addDataToLogFile(path,"unique");
				}

				if(temp.lValue < Data)
				{
					if(temp.rchild == null)
					{
						temp.rchild = newn;
						break;
					}
					temp = temp.rchild;
				}
				else if(temp.lValue > Data)
				{
					if(temp.lchild == null)
					{
						temp.lchild = newn;
						break;
					}
					temp = temp.lchild;
				}
			}
		}
	}
	
	public void display(Node temp)
	{
		if(temp != null)
		{
			System.out.println(temp.path+" :: "+temp.lValue);
			display(temp.rchild);
			display(temp.lchild);
		}
	}
	
	public void DeleteFile(String path)
	{
		File file = new File(path);
		long len = file.length();
		if(file.delete())
		{
			ScanPanel.iCnt++;
			ScanPanel.AddRowToscanTable(new Object[]{
				file.getAbsolutePath(),
				"Success",
				len
			});
			LogCreate.addDataToLogFile(file.getAbsolutePath(),"success");
		}
		else
		{
			ScanPanel.AddRowToscanTable(new Object[]{
				file.getAbsolutePath(),
				"Failed",
				len
			});
			LogCreate.addDataToLogFile(file.getAbsolutePath(),"failed");
		}
	}

	public static void DeleteEmptyFile(File fileName)
	{
		Flag = true;
		File []files = fileName.listFiles();
		String status;
		for(File file : files)
		{
			status = "Failed";
			if(file.isFile() && file.length() == 0)
			{
				if(file.delete())
				{
					ScanPanel.iCnt++;
					status = "Success";
				}
				ScanPanel.AddRowToscanTable(new Object[]{
					file.getAbsolutePath(),
					status,
					0
				});
				LogCreate.addDataToLogFile(file.getAbsolutePath(),status.toLowerCase());
			}
			if(file.isDirectory())
			{
				DeleteEmptyFile(file);
			}
		}
	}

	public static void DeleteEmptyFolder(File fileName)
	{
		Flag = true;
		File []files = fileName.listFiles();
		String status;
		for(File file : files)
		{
			status = "Failed";
			if(file.isDirectory())
			{
				DeleteEmptyFolder(file);
				if(file.length() == 0) {
					if(file.delete()) {
						ScanPanel.iCnt++;
						status = "Success";
					}
					if (file.exists()) {
						ScanPanel.AddRowToscanTable(new Object[]{
							file.getAbsolutePath(),
							status,
							0
						});
						LogCreate.addDataToLogFile(file.getAbsolutePath(),status.toLowerCase());
					}
				}
			}
		}
		if(fileName.length() == 0)
		{
			status = "Failed";
			if(fileName.delete())
			{
				ScanPanel.iCnt++;
				status = "Success";
			}
			if (fileName.exists()) {
				ScanPanel.AddRowToscanTable(new Object[]{
					fileName.getAbsolutePath(),
					status,
					0
				});
				LogCreate.addDataToLogFile(fileName.getAbsolutePath(),status.toLowerCase());
			}
		}
	}
}