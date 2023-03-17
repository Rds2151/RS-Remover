package lib.frame;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.awt.event.*;

public class LogPanel extends JPanel 
{
    private JScrollPane scrollPane;
    private JTable logTable;
    private  DefaultTableModel model;
    private JComboBox<String> cb;
    private File file;
	
    public LogPanel() 
    {
        initComponents();
        file = new File("log");
        try
        {
            if(!file.exists())
            {
                file.createNewFile();
            }
        }
        catch(IOException ie)
        {
            System.out.println(ie);
        }
    }

    private void initComponents() {
        
        String str[] = {"Today", "This Week", "This Month", "This Year"};
        cb = new JComboBox<String>(str);
        logTable = new JTable();
        model = new DefaultTableModel();
        
        setPreferredSize(new java.awt.Dimension(655, 452));
        setLayout(null);

        cb.setBounds(10, 10, 120, 20);
        cb.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                comboBoxActionPerformed(ae);
            }
        });
        
        model.addColumn("Date");
        model.addColumn("File Name");
        model.addColumn("Status");
        
        logTable.setModel(model);
        
        scrollPane = new JScrollPane(logTable);
        scrollPane.setBounds(10, 40, 633, 412);

        logTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        logTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        logTable.getColumnModel().getColumn(2).setPreferredWidth(30);
        logTable.repaint();


        add(cb);
        add(scrollPane);
    }

    private void comboBoxActionPerformed(ActionEvent evt) {
    try {
        clearlogTable();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currLocalDate = LocalDate.now();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        switch(cb.getSelectedIndex()) {
            case 0:
                // Today
                String currLocalDateString = currLocalDate.format(formatter);
                    while ((line = reader.readLine()) != null) {
                        String[] data = line.split(",");
                        if (data[0].equals(currLocalDateString)) {
                            model.addRow(new Object[]{data[0], data[2], data[1]});
                        }
                    }
                break;
            case 1:
                // Last week
                LocalDate weekAgo = currLocalDate.minusWeeks(1);
                String weekAgoString = weekAgo.format(formatter);
                currLocalDateString = currLocalDate.format(formatter);
                
                    while ((line = reader.readLine()) != null) {
                        String[] data = line.split(",");
                        LocalDate date = LocalDate.parse(data[0]);
                        if (date.isAfter(weekAgo.minusDays(1)) && date.isBefore(currLocalDate.plusDays(1))) {
                            model.addRow(new Object[]{data[0], data[2], data[1]});
                        }
                    }
                break;
            case 2:
                // This month
                int year = currLocalDate.getYear();
                int month = currLocalDate.getMonthValue();
                
                    while ((line = reader.readLine()) != null) {
                        String[] data = line.split(",");
                        LocalDate date = LocalDate.parse(data[0]);
                        if (date.getYear() == year && date.getMonthValue() == month) {
                            model.addRow(new Object[]{data[0], data[2], data[1]});
                        }
                    }
                
                break;
            case 3:
                // This year
                year = currLocalDate.getYear();
                    while ((line = reader.readLine()) != null) {
                        String[] data = line.split(",");
                        LocalDate date = LocalDate.parse(data[0]);
                        if (date.getYear() == year) {
                            model.addRow(new Object[]{data[0], data[2], data[1]});
                        }
                    }
               
                break;
        }
    } catch(Exception e) {
        System.out.println(e);
    }
}

    private void clearlogTable()
    {
        model.setRowCount(0);
    }
}
