package lib.store;

import java.util.zip.CRC32;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import java.lang.*;
import java.awt.FontMetrics;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import lib.frame.ScanPanel;

public class CRC_CheckSum extends SwingWorker<Void, Void>
{
    private byte buffer[] = null;
    private CkSumStore cobj = null;
    public static int iProgress = 0;
    public static boolean stop = false;
    private String filePath;

    public CRC_CheckSum(String filePath)
    {
        this.filePath = filePath;
        cobj = new CkSumStore();
        CkSumStore.Flag = false;
    }

    public static int getFileCount(File filePath)
    {
        int iCnt = 0;

        File []files = filePath.listFiles();

        for(File file : files)
        {
            if(file.isFile())
            {
                iCnt ++;
            }
            else if(file.isDirectory())
            {
                iCnt += getFileCount(file);
            }
        }
        return iCnt;
    }
    
    @Override
    protected Void doInBackground() throws Exception 
    {
        ScanPanel.iTotal = CRC_CheckSum.getFileCount(new File(filePath));
        ScanPanel.progressBar.setMaximum(100);
        ScanPanel.progressBar.setValue(0);

        cksum(filePath);
        if(stop == false)
        {
            ScanPanel.statusLabel.setText("Complete...");
            ScanPanel.progressBar.setValue(100);
            ScanPanel.iTotal = 0;
            CRC_CheckSum.iProgress = 0;
            ScanPanel.stopBtn.setText("Done");
            CkSumStore.Flag = true;
	    CkSumStore.DeleteEmptyFolder(new File(filePath));
        }
        return null;
    }
    
    private void cksum(String FilePath)
	{
		File directoryPath = null;
		File arr[] = null;

		try
		{
			directoryPath = new File(FilePath);
			arr = directoryPath.listFiles();
			for(File filename : arr)
			{
                if(stop)
                {
                    break;
                }
				CheckSum(filename);
			}
		}
		catch(Exception obj)
		{
			System.out.println("The file is not present at given path");
		}
	}
	
	private void CheckSum(File FileName)
	{
		long len = 0;
        if(FileName == null)
        {
            return;
        }
		try 
		{
			if(FileName.isFile())
			{
				ScanPanel.stopBtn.setText("Stop");
				CRC32 crc = new CRC32();
				len = FileName.length();
                if (len == 0)
                {
                    cobj.find(0,FileName.getAbsolutePath());
                    return;
                }
                buffer = new byte[(int)len];
                FileInputStream in = new FileInputStream(FileName);
                BufferedInputStream bufIn = new BufferedInputStream(in);
                bufIn.read(buffer, 0, (int)len);
                crc.update(buffer);
                cobj.find(crc.getValue(),FileName.getAbsolutePath());
                bufIn.close();
                in.close();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run()
                    {
                        if (ScanPanel.iTotal > 0 && CRC_CheckSum.iProgress <= ScanPanel.iTotal) {
                            int percentComplete = (CRC_CheckSum.iProgress * 100) / ScanPanel.iTotal;
                            ScanPanel.progressBar.setValue(percentComplete);
                            String filePath = FileName.getPath();
                            FontMetrics fm = ScanPanel.statusLabel.getFontMetrics(ScanPanel.statusLabel.getFont());
                            int labelWidth = 529;
                            int pathWidth = fm.stringWidth(filePath);
                            if (pathWidth > labelWidth) {
                                int numChars = filePath.length() * labelWidth / pathWidth;
                                String truncatedPath = filePath.substring(0, numChars) + "...";
                                ScanPanel.statusLabel.setText(truncatedPath);
                                ScanPanel.statusLabel.setToolTipText(filePath);
                            } else {
                                ScanPanel.statusLabel.setText(filePath);
                                ScanPanel.statusLabel.setToolTipText(null);
                            }
                        }
                    }
                });
                CRC_CheckSum.iProgress++;
                int iProg = (int)((CRC_CheckSum.iProgress * 100) / ScanPanel.iTotal);
                ScanPanel.progressBar.setValue(iProg);
            }
            else if(FileName.isDirectory())
            {
            cksum(FileName.getAbsolutePath());
            }
        }
        catch(Exception obj)
        {
            System.out.println("The file is not present at given path");
        }
    }
}
