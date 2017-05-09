/**
 *
 * @author Ryan Liang
 */

package com.ryanliang.inventorygui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ItemDialog extends JDialog implements ActionListener{

	private Media newItem = null;
	private boolean done = false;
	private String itemID = null;
	private String quantity = "1";
	
	private JTextField titleField = new JTextField(20);
	private JTextField genreField = new JTextField(20);
	private JTextField artistField = new JTextField(20);
	private JTextField descriptionField = new JTextField(20);
	private JTextField quantityField = new JTextField(20);
	private static final JButton doneButton = new JButton("Done"); 
	private static final JPanel panel1 = new JPanel();
	private static final JPanel panel2 = new JPanel();
	private static final JPanel panel3 = new JPanel();

	private JLabel errorLabel = new JLabel("");

	public ItemDialog(JFrame frame){
		super(frame, "New item dialog", true);

		Dimension frameSize = frame.getSize();
		int frameWidth = frameSize.width;
		int frameHeight = frameSize.height;
		setSize(frameWidth/3,frameHeight/3);

		setLayout(new GridLayout(3, 1));
		GridLayout layout = new GridLayout(0,2);

		add(panel1);
		doneButton.addActionListener(this);
		add(panel2);
		panel2.add(errorLabel);
		errorLabel.setAlignmentX(CENTER_ALIGNMENT);
		add(panel3);
		panel3.add(doneButton);
		doneButton.setAlignmentX(CENTER_ALIGNMENT);

		panel1.setLayout(layout);
		panel1.add(new JLabel("Title:"));
		panel1.add(titleField);
		panel1.add(new JLabel("Artist(s):"));
		panel1.add(artistField);
		panel1.add(new JLabel("Genre:"));
		panel1.add(genreField);
		panel1.add(new JLabel("Description:"));
		panel1.add(descriptionField);
		panel1.add(new JLabel("Quantity:"));
		panel1.add(quantityField);

		//pack();
	}

	public void actionPerformed(ActionEvent e) {

		errorLabel.setForeground(Color.RED);

		String title = titleField.getText().trim().toLowerCase();
		String genre = genreField.getText().trim().toLowerCase();
		String description = descriptionField.getText().trim().toLowerCase();
		String artist = artistField.getText().trim().toLowerCase();
		quantity = quantityField.getText().trim().toLowerCase();
		
		if (!Utility.isNumeric(quantity))
			quantity = "1";

		//Only care about length of title and artist name.  Others are ignored at this time.
		if (title.length() < 1){
			errorLabel.setText("Title may be invalid.");
		}
		else if (artist.length() < 1){
			errorLabel.setText("Artist name is too short.");
		}
		else{
			errorLabel.setText("");
			newItem = new CD(itemID, title, description, genre, artist);
			done = true;
			setVisible(false);
		}
	}
	
	public String getQuantity() {

		return quantity;
	}
	
	public Media getItem() {

		return newItem;
	}

	public void setDone(boolean b) {
		done = b;

	}

	public boolean getDone() {
		return done;
	}

	public void initializeTextFields(Media m) {
		itemID = m.getID();
		
		errorLabel.setText("");
		
		if (m instanceof CD){   
			titleField.setText(m.getTitle());
			genreField.setText(m.getGenre());
			artistField.setText(((CD) m).getArtist());
			descriptionField.setText(m.getDescription());
		}

	}

}

