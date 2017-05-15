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
	private MediaCategory media = null;
	
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
	
	private JTextField bookTitleField = new JTextField(20);
	private JTextField bookGenreField = new JTextField(20);
	private JTextField bookAuthorField = new JTextField(20);
	private JTextField bookISBNField = new JTextField(20);
	private JTextField bookDescriptionField = new JTextField(20);
	private JTextField bookQuantityField = new JTextField(20);
	
	private static final JButton doneButton = new JButton("Done"); 
	private static final JPanel radioButtonPanel = new JPanel();
	private static final JPanel CDTextFieldPanel = new JPanel();
	private static final JPanel DVDTextFieldPanel = new JPanel();
	private static final JPanel bookTextFieldPanel = new JPanel();
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

		radioGroup.add(CDRadioButton);
		radioGroup.add(DVDRadioButton);
		radioGroup.add(bookRadioButton);
		radioButtonPanel.add(CDRadioButton);
		radioButtonPanel.add(DVDRadioButton);
		radioButtonPanel.add(bookRadioButton);
		
		CDRadioButton.addActionListener(event -> {
			addJPanel(CDTextFieldPanel);
			media = MediaCategory.CD;
			
			CDTitleField.setText("");
			CDGenreField.setText("");
			CDArtistField.setText("");
			CDDescriptionField.setText("");
			CDQuantityField.setText("");
		});
		
		DVDRadioButton.addActionListener(event -> {
			addJPanel(DVDTextFieldPanel);
			media = MediaCategory.DVD;
			
			DVDTitleField.setText("");
			DVDGenreField.setText("");
			DVDCastField.setText("");
			DVDDescriptionField.setText("");
			DVDQuantityField.setText("");
		});
		
		bookRadioButton.addActionListener(event -> {
			addJPanel(bookTextFieldPanel);
			media = MediaCategory.BOOK;
			
			bookTitleField.setText("");
			bookGenreField.setText("");
			bookAuthorField.setText("");
			bookISBNField.setText("");
			bookDescriptionField.setText("");
			bookQuantityField.setText("");
		});
		
		doneButton.addActionListener(this);
		errorPanel.add(errorLabel);
		errorLabel.setAlignmentX(CENTER_ALIGNMENT);
		buttonPanel.add(doneButton);
		doneButton.setAlignmentX(CENTER_ALIGNMENT);

		CDTextFieldPanel.setLayout(textFieldLayout);
		DVDTextFieldPanel.setLayout(textFieldLayout);
		bookTextFieldPanel.setLayout(textFieldLayout);


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
		
		bookTextFieldPanel.add(new JLabel("Title:"));
		bookTextFieldPanel.add(bookTitleField);
		bookTextFieldPanel.add(new JLabel("Author(s):"));
		bookTextFieldPanel.add(bookAuthorField);
		bookTextFieldPanel.add(new JLabel("ISBN:"));
		bookTextFieldPanel.add(bookISBNField);
		bookTextFieldPanel.add(new JLabel("Genre:"));
		bookTextFieldPanel.add(bookGenreField);
		bookTextFieldPanel.add(new JLabel("Description:"));
		bookTextFieldPanel.add(bookDescriptionField);
		bookTextFieldPanel.add(new JLabel("Quantity:"));
		bookTextFieldPanel.add(bookQuantityField);

	}

	private void addJPanel(JPanel panel) {
		clearDialog();
		
		add(panel);
		add(errorPanel);
		add(buttonPanel);

		validate();
		repaint();	
	}

	public void actionPerformed(ActionEvent e) {
		String title = "";
		String genre = "";
		String description = "";
		
		errorLabel.setForeground(Color.RED);

		if (media ==  MediaCategory.CD){
			title = CDTitleField.getText().trim().toLowerCase();
			genre = CDGenreField.getText().trim().toLowerCase();
			description = CDDescriptionField.getText().trim().toLowerCase();
			String artist = CDArtistField.getText().trim().toLowerCase();
			quantity = CDQuantityField.getText().trim().toLowerCase();

			if (!Utility.isNumeric(quantity))
				quantity = "1";
			
			if (title.length() < 1)
				errorLabel.setText("Title may be invalid.");
			else{
				item = new CD(itemID, title, description, genre, artist);
				
				resetDialog();
			}
		}
		else if (media ==  MediaCategory.DVD){
			title = DVDTitleField.getText().trim().toLowerCase();
			genre = DVDGenreField.getText().trim().toLowerCase();
			description = DVDDescriptionField.getText().trim().toLowerCase();
			String cast = DVDCastField.getText().trim().toLowerCase();
			quantity = DVDQuantityField.getText().trim().toLowerCase();

			if (!Utility.isNumeric(quantity))
				quantity = "1";
			
			if (title.length() < 1)
				errorLabel.setText("Title may be invalid.");
			else{
				item = new DVD(itemID, title, description, genre, cast);
				
				resetDialog();
			}
		}
		else if (media ==  MediaCategory.BOOK){
			title = bookTitleField.getText().trim().toLowerCase();
			genre = bookGenreField.getText().trim().toLowerCase();
			description = bookDescriptionField.getText().trim().toLowerCase();
			String author = bookAuthorField.getText().trim().toLowerCase();
			String ISBN = bookISBNField.getText().trim().toLowerCase();
			quantity = bookQuantityField.getText().trim().toLowerCase();

			if (!Utility.isNumeric(quantity))
				quantity = "1";
			
			if (title.length() < 1)
				errorLabel.setText("Title may be invalid.");
			else{
				item = new Book(itemID, title, description, genre, author, ISBN);
				
				resetDialog();
			}
		}
	}
	
	private void resetDialog() {
		errorLabel.setText("");
		done = true;
		setVisible(false);
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

	public void initializeTextFields(Media m, String quant) {
		itemID = m.getID();
		
		errorLabel.setText("");
		
		remove(radioButtonPanel);
		clearDialog();

		if (m instanceof CD){   
			add(CDTextFieldPanel);
			
			CDTitleField.setText(m.getTitle());
			CDGenreField.setText(m.getGenre());
			CDArtistField.setText(((CD) m).getArtist());
			CDDescriptionField.setText(m.getDescription());
			CDQuantityField.setText(quant);
		}
		else if (m instanceof DVD){   
			add(DVDTextFieldPanel);
			
			DVDTitleField.setText(m.getTitle());
			DVDGenreField.setText(m.getGenre());
			DVDCastField.setText(((DVD) m).getCast());
			DVDDescriptionField.setText(m.getDescription());
			DVDQuantityField.setText(quant);
		}
		else if (m instanceof Book){   
			add(bookTextFieldPanel);
			
			bookTitleField.setText(m.getTitle());
			bookGenreField.setText(m.getGenre());
			bookAuthorField.setText(((Book) m).getAuthor());
			bookISBNField.setText(((Book) m).getISBN());
			bookDescriptionField.setText(m.getDescription());
			bookQuantityField.setText(quant);
		}
		
		add(errorPanel);
		add(buttonPanel);

	}

	public void inputItemDetails(String itemID) {
		this.itemID = itemID;
		
		remove(radioButtonPanel);
		clearDialog();

		add(radioButtonPanel);
		
	}

	private void clearDialog() {
		remove(CDTextFieldPanel);
		remove(DVDTextFieldPanel);
		remove(bookTextFieldPanel);

		remove(errorPanel);
		remove(buttonPanel);
		
	}

	public void resetRadioButtonGroup() {
		radioGroup.clearSelection();
	}
}

