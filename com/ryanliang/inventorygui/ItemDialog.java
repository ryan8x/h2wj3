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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ItemDialog extends JDialog implements ActionListener{

	private Media item = null;
	private boolean done = false;
	private String itemID = null;
	private String quantity = "1";
	
	private JTextField CDTitleField = new JTextField(20);
	private JTextField CDGenreField = new JTextField(20);
	private JTextField CDArtistField = new JTextField(20);
	private JTextField CDDescriptionField = new JTextField(20);
	private JTextField CDQuantityField = new JTextField(20);
	
	private JTextField DVDTitleField = new JTextField(20);
	private JTextField DVDGenreField = new JTextField(20);
	private JTextField DVDCastField = new JTextField(20);
	private JTextField DVDDescriptionField = new JTextField(20);
	private JTextField DVDQuantityField = new JTextField(20);
	
	private static final JButton doneButton = new JButton("Done"); 
	private static final JPanel radioButtonPanel = new JPanel();
	private static final JPanel CDTextFieldPanel = new JPanel();
	private static final JPanel DVDTextFieldPanel = new JPanel();
	private static final JPanel errorPanel = new JPanel();
	private static final JPanel buttonPanel = new JPanel();
	
	private static final ButtonGroup radioGroup = new ButtonGroup();
	private static final JRadioButton CDRadioButton = new JRadioButton("CD", false);
	private static final JRadioButton DVDRadioButton = new JRadioButton("DVD", false);
	private static final JRadioButton bookRadioButton = new JRadioButton("Book", false);
	

	private JLabel errorLabel = new JLabel("");

	public ItemDialog(JFrame frame){
		super(frame, "New item dialog", true);

		Dimension frameSize = frame.getSize();
		int frameWidth = frameSize.width;
		int frameHeight = frameSize.height;
		setSize(frameWidth/3,frameHeight/3);

		setLayout(new GridLayout(4, 1));
		GridLayout textFieldLayout = new GridLayout(0,2);

		//add(radioButtonPanel);
		//add(textFieldPanel);

		radioGroup.add(CDRadioButton);
		radioGroup.add(DVDRadioButton);
		radioGroup.add(bookRadioButton);
		radioButtonPanel.add(CDRadioButton);
		radioButtonPanel.add(DVDRadioButton);
		radioButtonPanel.add(bookRadioButton);
		
		CDRadioButton.addActionListener(event -> {
			remove(CDTextFieldPanel);
			remove(DVDTextFieldPanel);
			remove(errorPanel);
			remove(buttonPanel);
			add(CDTextFieldPanel);
			add(errorPanel);
			add(buttonPanel);
			validate();
		});
		
		DVDRadioButton.addActionListener(event -> {
			remove(CDTextFieldPanel);
			remove(DVDTextFieldPanel);
			remove(errorPanel);
			remove(buttonPanel);
			add(DVDTextFieldPanel);
			add(errorPanel);
			add(buttonPanel);
			validate();
		});
		
		doneButton.addActionListener(this);
		//add(errorPanel);
		errorPanel.add(errorLabel);
		errorLabel.setAlignmentX(CENTER_ALIGNMENT);
		//add(buttonPanel);
		buttonPanel.add(doneButton);
		doneButton.setAlignmentX(CENTER_ALIGNMENT);

		CDTextFieldPanel.setLayout(textFieldLayout);
		DVDTextFieldPanel.setLayout(textFieldLayout);

		CDTextFieldPanel.add(new JLabel("Title:"));
		CDTextFieldPanel.add(CDTitleField);
		CDTextFieldPanel.add(new JLabel("Artist(s):"));
		CDTextFieldPanel.add(CDArtistField);
		CDTextFieldPanel.add(new JLabel("Genre:"));
		CDTextFieldPanel.add(CDGenreField);
		CDTextFieldPanel.add(new JLabel("Description:"));
		CDTextFieldPanel.add(CDDescriptionField);
		CDTextFieldPanel.add(new JLabel("Quantity:"));
		CDTextFieldPanel.add(CDQuantityField);
		
		DVDTextFieldPanel.add(new JLabel("Title:"));
		DVDTextFieldPanel.add(DVDTitleField);
		DVDTextFieldPanel.add(new JLabel("Cast(s):"));
		DVDTextFieldPanel.add(DVDCastField);
		DVDTextFieldPanel.add(new JLabel("Genre:"));
		DVDTextFieldPanel.add(DVDGenreField);
		DVDTextFieldPanel.add(new JLabel("Description:"));
		DVDTextFieldPanel.add(DVDDescriptionField);
		DVDTextFieldPanel.add(new JLabel("Quantity:"));
		DVDTextFieldPanel.add(DVDQuantityField);


		//pack();
	}

	public void actionPerformed(ActionEvent e) {

		errorLabel.setForeground(Color.RED);

		String title = CDTitleField.getText().trim().toLowerCase();
		String genre = CDGenreField.getText().trim().toLowerCase();
		String description = CDDescriptionField.getText().trim().toLowerCase();
		String artist = CDArtistField.getText().trim().toLowerCase();
		quantity = CDQuantityField.getText().trim().toLowerCase();
		
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
			item = new CD(itemID, title, description, genre, artist);
			done = true;
			setVisible(false);
		}
	}
	
	public String getQuantity() {

		return quantity;
	}
	
	public Media getItem() {

		return item;
	}

	public void setDone(boolean b) {
		done = b;

	}

	public boolean getDone() {
		return done;
	}

	public void initializeTextFields(Media m, String quantity) {
		itemID = m.getID();
		
		errorLabel.setText("");
		
		remove(CDTextFieldPanel);
		remove(DVDTextFieldPanel);
		remove(radioButtonPanel);
		remove(errorPanel);
		remove(buttonPanel);

		add(CDTextFieldPanel);
		add(errorPanel);
		add(buttonPanel);
		if (m instanceof CD){   
						
			CDTitleField.setText(m.getTitle());
			CDGenreField.setText(m.getGenre());
			CDArtistField.setText(((CD) m).getArtist());
			CDDescriptionField.setText(m.getDescription());
			CDQuantityField.setText(quantity);
		}

	}

	public void inputItemDetails() {
		remove(CDTextFieldPanel);
		remove(DVDTextFieldPanel);
		remove(radioButtonPanel);
		remove(errorPanel);
		remove(buttonPanel);
		add(radioButtonPanel);
		
	}

	public void resetRadioButtonGroup() {
		radioGroup.clearSelection();
	}

}

