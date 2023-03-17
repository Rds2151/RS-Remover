package lib.frame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.event.*;
import javax.swing.*;


public class SearchPanel extends JPanel 
{
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JComboBox<String> comboBox;
    private JTextField searchBar;
	
    public SearchPanel() 
    {
        initComponents();
    }

    private void initComponents() {
        
        String str[] = {"All", "Unique", "Success"};
        comboBox = new JComboBox<String>(str);
        scrollPane = new JScrollPane();
        textArea = new JTextArea();
        searchBar = new JTextField();

        textArea.setRows(20);
        textArea.setColumns(5);

        searchBar.setText("Search...");
        searchBar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals("Search...")) {
                    searchBar.setText("");
                }
            }
        
            @Override
            public void focusLost(FocusEvent e) {
                if (searchBar.getText().isEmpty()) {
                    searchBar.setText("Search...");
                }
            }
        });

        searchBar.setBounds(30,10,299,25);
        searchBar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) 
            {
                searchBarActionPerformed(evt);
            }
        });

        comboBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                searchBarActionPerformed(ae);
            }
        });

        setPreferredSize(new java.awt.Dimension(655, 452));
        setLayout(null);

        comboBox.setBounds(500, 10, 120, 20);       
        
        scrollPane.setViewportView(textArea);
        scrollPane.setBounds(10, 40, 633, 412);

        add(comboBox);
        add(searchBar);
        add(scrollPane);
    }

    private void searchBarActionPerformed(ActionEvent evt) 
    {  
        String searchValue = searchBar.getText();
        String text = "";
        try 
        {
            String line = "";
            BufferedReader bReader = new BufferedReader(new FileReader("log"));
            while ((line = bReader.readLine()) != null) 
            {
                int posFound = line.indexOf(searchValue);
                if (posFound > -1) 
                {
                    String arr[] = line.split(",");
                        
                    if (comboBox.getSelectedItem().equals("Success") && arr[1].equals("success"))
                    {
                        text += arr[2] + "\n";
                    }
                    else if (comboBox.getSelectedItem().equals("Unique") && arr[1].equals("unique"))
                    {
                        text += arr[2] + "\n";
                    }
                    else
                    {
                        text += arr[2] + "\n";
                    }
                }
            }
            bReader.close();
            textArea.setText(text);
        }
        catch (Exception e) 
        {
            System.out.println("Error: " + e.toString());
        }
    }
}