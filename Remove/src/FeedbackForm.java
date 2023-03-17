package lib.frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;

public class FeedbackForm extends JPanel 
{
  private JLabel nameLabel;
  private JLabel emailLabel;
  private JLabel feedbackLabel;
  private JTextField nameField;
  private JTextField emailField;
  private JTextArea feedbackField;
  private JButton submitButton;

  public FeedbackForm() {
    setLayout(null);
    nameLabel = new JLabel("Name: ");
    nameLabel.setBounds(20, 20, 80, 30);
    add(nameLabel);

    emailLabel = new JLabel("Email: ");
    emailLabel.setBounds(20, 60, 80, 30);
    add(emailLabel);

    feedbackLabel = new JLabel("Feedback: ");
    feedbackLabel.setBounds(20, 100, 80, 30);
    add(feedbackLabel);

    nameField = new JTextField();
    nameField.setBorder(new LineBorder(Color.DARK_GRAY));
    nameField.setBounds(100, 20, 170, 30);
    add(nameField);
    
    emailField = new JTextField();
    emailField.setBorder(new LineBorder(Color.DARK_GRAY));
    emailField.setBounds(100, 60, 170, 30);
    add(emailField);

    feedbackField = new JTextArea();
    feedbackField.setBorder(new LineBorder(Color.DARK_GRAY));
    feedbackField.setBounds(100, 100, 350, 200);
    add(feedbackField);

    submitButton = new JButton("Submit");
    submitButton.setBounds(100, 310, 120, 30);
    add(submitButton);

    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String email = emailField.getText();
        String feedback = feedbackField.getText();
        saveToFile(name, email, feedback);
      }
    });
  }

	private void saveToFile(String name, String email, String feedback) 
	{
		try 
		{
			if (name.isEmpty() || email.isEmpty() || feedback.isEmpty() || feedback.trim().isEmpty()) 
			{
				JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			else if (name.trim().isEmpty() || email.trim().isEmpty()) 
			{
				JOptionPane.showMessageDialog(this, "Name and Email are required!");
				return;
			}
			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
			Pattern pat = Pattern.compile(emailRegex);
			if (!pat.matcher(email).matches()) 
			{
				JOptionPane.showMessageDialog(this, "Invalid Email Address!");
				return;
			}
			
			File file = new File("Feedback");
			if (!file.exists()) 
			{
				file.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
			LocalDate date = LocalDate.now();
			out.write(date + "," + name + "," + email + "," + feedback + "\n");
			out.close();
			JOptionPane.showMessageDialog(this, "Feedback saved successfully!");
			nameField.setText("");
			emailField.setText("");
			feedbackField.setText("");
		} 
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(this, "An error occurred while saving the feedback");
		}
	}
}
