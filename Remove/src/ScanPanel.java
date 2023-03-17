package lib.frame;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import lib.store.CRC_CheckSum;
import lib.store.CkSumStore;

/**
 *
 * @author Raj
 */
public class ScanPanel extends javax.swing.JPanel 
{

    private JPanel tab1,browsePanel;
    public static int iCnt;
    public static int iTotal;
	
    public ScanPanel(JPanel tabs,JPanel browsePanel) {
        initComponents();
        this.tab1 = tabs;
        this.browsePanel =browsePanel;
    }

    private void initComponents() {

        panel1 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        scanTable = new javax.swing.JTable();
        stopBtn = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        statusLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(655, 452));

        panel1.setBackground(new java.awt.Color(235, 235, 235));
        panel1.setPreferredSize(new java.awt.Dimension(655, 452));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.setColumnIdentifiers(new String[] { "File Name", "Status", "Size" });
        scanTable.setModel(tableModel);

        scanTable.setCellSelectionEnabled(true);
        scanTable.setPreferredSize(new java.awt.Dimension(425, 400));
        scanTable.setShowGrid(true);
        jScrollPane1.setViewportView(scanTable);

        stopBtn.setText("Stop");
        stopBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stopBtnMouseClicked(evt);
            }
        });
        stopBtn.setVisible(true);
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(stopBtn)
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(stopBtn)
                .addContainerGap())
        );

        progressBar.setPreferredSize(new java.awt.Dimension(146, 10));
        progressBar.setStringPainted(true);
        statusLabel.setText("Scanning...");

        new Thread(new Runnable(){
            public void run()
            {
                while(true)
                {
                    if(CkSumStore.Flag == true)
                    {
                        stopBtn.setText("Done");
                        break;
                    }
                }
            }
        }).start();

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(statusLabel)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }

    private void stopBtnMouseClicked(java.awt.event.MouseEvent evt) 
	{  
		if(stopBtn.getText().equals("Done"))
		{
			tab1.removeAll();
			tab1.add(browsePanel);
			tab1.repaint();
			tab1.revalidate();
		}
        else if(stopBtn.getText().equals("Stop"))
        {
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to stop the file scan and discard the results", "Confirm", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                CRC_CheckSum.stop = true;
                tab1.removeAll();
                tab1.add(browsePanel);
                tab1.repaint();
                tab1.revalidate();
            }
        }
    }
	
	public static void AddRowToscanTable(Object[] dataRow)
	{
		model = (DefaultTableModel)scanTable.getModel();
		model.addRow(dataRow);
	}

    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel1;
    public static javax.swing.JProgressBar progressBar;
    private static javax.swing.JTable scanTable;
    public static javax.swing.JLabel statusLabel;
    public static javax.swing.JButton stopBtn;
    public static DefaultTableModel model;
}
