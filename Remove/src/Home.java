package lib.frame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.DimensionUIResource;
import java.awt.event.*;
import java.io.File;
import java.awt.*;

import lib.frame.FeedbackForm;
import lib.store.CRC_CheckSum;

/*
 * @author Raj
 */

public class Home extends JFrame 
{
	private JTabbedPane tabs = null;
	private JPanel navPanel = null;
	private JPanel headerPanel = null;
	private JLabel headerLabel = null;
	protected JPanel browsePanel = null;
	public JLabel scanLabel = null;
	public JLabel logLabel = null;
	private JLabel searchLabel = null;
	private JLabel feedbackLabel = null;
	private ScanPanel scanPanel = null;
	public String path;
	private JPanel tab1 = null;
	private JPanel tab2 = null;
	private JPanel tab3 = null;
	private JPanel tab4 = null;
	private int tab = 0; 

	
	public Home()
	{
		initComponents();
		setResizable(false);
		setSize(755,538);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initComponents()
	{
		navPanel = new JPanel();
		scanLabel = new JLabel();
		logLabel = new JLabel();
		searchLabel = new JLabel();
		feedbackLabel = new JLabel();
		headerPanel = new JPanel();
		headerLabel = new JLabel("  RS File Remover");
		Border border = new LineBorder(Color.BLACK,0,false);
		tabs = new JTabbedPane();
		tab1 = new JPanel();
		tab2 = new LogPanel();
		tab3 = new SearchPanel();
		tab4 = new FeedbackForm();
		browsePanel = new JPanel();
		JButton browseButton = new JButton();
	
		//-- Start Nav bar --// 
	
		navPanel.setPreferredSize(new DimensionUIResource(100, 538));
		navPanel.setBackground(new Color(0, 153, 153));
		
				
		logLabel.setFont(new java.awt.Font("Tahoma 12 Gra", 1, 14));
		logLabel.setText("Log File");
		logLabel.setHorizontalAlignment(SwingConstants.CENTER);
		// logLabel.setForeground(new Color(204,204,204));
		logLabel.setOpaque(true);
		logLabel.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent evt) 
			{
				logLabelMouseClicked(evt);
			}
		});

		scanLabel.setFont(new java.awt.Font("Century", 1, 14));
		scanLabel.setText("Scan");
		scanLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scanLabel.setOpaque(true);
		scanLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scanLabelMouseClicked(evt);
            }
        });
		
		searchLabel.setFont(new java.awt.Font("Century", 1, 14));
		searchLabel.setText("Search");
		searchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		searchLabel.setOpaque(true);
		searchLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchLabelMouseClicked(evt);
            }
        });

		feedbackLabel.setFont(new java.awt.Font("Century", 1, 14));
		feedbackLabel.setText("FeedBack");
		feedbackLabel.setOpaque(true);
		feedbackLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                feedbackLabelMouseClicked(evt);
            }
        });

		// Set bounds for scanLabel, logLabel, and searchLabel
		scanLabel.setBounds(0, 14, 100, 33);
		logLabel.setBounds(0, 62, 100, 33);
		searchLabel.setBounds(0, 110, 100, 33);

		// Add labels to navPanel
		navPanel.add(scanLabel);
		navPanel.add(logLabel);
		navPanel.add(searchLabel);

		// Set bounds for navPanel
		add(navPanel,BorderLayout.WEST);
		navPanel.setLayout(null);
				
		//-- End of Nav panel --//
				
		//-- Start of head panel --//
		
		headerPanel.setPreferredSize(new DimensionUIResource(755, 40));
		headerPanel.setBackground(Color.BLACK);
		headerLabel.setFont(new java.awt.Font("P052 Bold", 21, 24));
		headerLabel.setForeground(Color.white);
		headerLabel.setAlignmentY(LEFT_ALIGNMENT);
		headerPanel.add(headerLabel);

		feedbackLabel.setPreferredSize(new DimensionUIResource(40, 30));
		feedbackLabel.setBackground(Color.BLACK);
		feedbackLabel.setForeground(Color.white);
		feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
		feedbackLabel.setBorder(new LineBorder(Color.white));
		headerPanel.add(feedbackLabel);

		headerPanel.setLayout(null);
		headerLabel.setBounds(10, 10, 700, 20);
		feedbackLabel.setBounds(633, 0, 120, 39);
		add(headerPanel,BorderLayout.NORTH);
		
		//-- End of head Panel --//

		
		//-- Start of Body Panel --//
		
		tabs.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		tabs.setPreferredSize(new DimensionUIResource(655, 478));
		tabs.setAlignmentX(TOP_ALIGNMENT);

		tabs.setBorder(null);

		add(tabs, BorderLayout.CENTER);

		//-- End of Body Panel --//
		
		//-- Start of tabbed Panel --//
		
		tab1.setLayout(new java.awt.CardLayout());
		
		javax.swing.GroupLayout browsePanelLayout = new javax.swing.GroupLayout(browsePanel);
		browsePanel.setLayout(browsePanelLayout);
		browsePanelLayout.setHorizontalGroup(
			browsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(browsePanelLayout.createSequentialGroup()
				.addGap(253, 253, 253)
				.addComponent(browseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addContainerGap(280, Short.MAX_VALUE))
		);

		browsePanelLayout.setVerticalGroup(
			browsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(browsePanelLayout.createSequentialGroup()
				.addGap(176, 176, 176)
				.addComponent(browseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addContainerGap(220, Short.MAX_VALUE))
		);

		browseButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
		browsePanel.setBackground(new java.awt.Color(0, 0, 0));
		browseButton.setText("Browse");
		browseButton.setFont(new java.awt.Font("Brush Script MT", 1, 18));
		browseButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
				browseButtonActionPerformed(evt);
			}
		});

		tab1.add(browsePanel, "card2");
	
		tab2.setBounds(100,60,655,478);
		tab2.setBorder(border);
		tab2.setBackground(Color.WHITE);
		
		tab3.setBounds(100,60,655,478);
		tab3.setBorder(border);
		tab3.setBackground(Color.WHITE);

		tab4.setBounds(100,60,655,478);
		tab4.setBorder(border);
		tab4.setBackground(Color.WHITE);
		
		tabs.addTab("Home",tab1);
		tabs.addTab("Log",tab2);
		tabs.addTab("Search",tab3);
		tabs.addTab("Feedback",tab4);
		
		//-- End of Tabbed Pane --//

				
		final boolean showTabsHeader = false;
		tabs.setUI(new javax.swing.plaf.metal.MetalTabbedPaneUI(){
			@Override
			protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight) {
				if (showTabsHeader) {
					return super.calculateTabAreaHeight(tabPlacement, horizRunCount, maxTabHeight);
				} else {
					return 0;
				}
			}
		  protected void paintTabArea(Graphics g,int tabPlacement,int selectedIndex){}
		});

		//-- End of tabbed Panel --//
	}
	
	private void scanLabelMouseClicked(MouseEvent me)
	{
		tab = 0;
        tabs.setSelectedIndex(tab);
	}
	private void feedbackLabelMouseClicked(MouseEvent me)
	{
		tab = 3;
        tabs.setSelectedIndex(tab);
	}
	
	private void logLabelMouseClicked(MouseEvent me)
	{
		tab = 1;
        tabs.setSelectedIndex(tab);
	}
	
	private void searchLabelMouseClicked(MouseEvent me)
	{
		tab = 2;
        tabs.setSelectedIndex(tab);
	}

	public void browseButtonActionPerformed(ActionEvent ae)
	{
		JFileChooser fileChooser = new JFileChooser(".");

		fileChooser.setForeground(Color.black);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			File f = fileChooser.getSelectedFile();
			String path = f.getAbsolutePath();
			new Selectoption(path).setVisible(true);
			scanPanel = new ScanPanel(tab1,browsePanel);
			scanPanel.setVisible(true);
			tab1.removeAll();
			tab1.add(scanPanel);
			tab1.repaint();
			tab1.revalidate();
		}
	}
}