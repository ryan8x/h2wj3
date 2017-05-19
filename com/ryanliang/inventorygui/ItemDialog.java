/**
 *
 * @author Ryan Liang
 */

package com.ryanliang.inventorygui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class ItemDialog extends JDialog implements ActionListener{

	private Media item = null;
	private boolean done = false;
	private String itemID = null;
	private String quantity = "1";
	private MediaCategory media = null;
	
	private final JTextField CDTitleField = new JTextField(20);
	private final JTextField CDGenreField = new JTextField(20);
	private final JTextField CDArtistField = new JTextField(20);
	private final JTextField CDDescriptionField = new JTextField(20);
	private final JTextField CDQuantityField = new JTextField(20);
	
	private final JTextField DVDTitleField = new JTextField(20);
	private final JTextField DVDGenreField = new JTextField(20);
	private final JTextField DVDCastField = new JTextField(20);
	private final JTextField DVDDescriptionField = new JTextField(20);
	private final JTextField DVDQuantityField = new JTextField(20);
	
	private final JTextField bookTitleField = new JTextField(20);
	private final JTextField bookGenreField = new JTextField(20);
	private final JTextField bookAuthorField = new JTextField(20);
	private final JTextField bookISBNField = new JTextField(20);
	private final JTextField bookDescriptionField = new JTextField(20);
	private final JTextField bookQuantityField = new JTextField(20);
		
	private final JButton doneButton = new JButton("Done"); 
	
	private final JPanel CDMainPanel = new JPanel(new BorderLayout());
	private final JPanel DVDMainPanel = new JPanel(new BorderLayout());
	private final JPanel bookMainPanel = new JPanel(new BorderLayout());
	
	private final JPanel radioButtonPanel = new JPanel();
	private final JPanel CDTextFieldPanel = new JPanel();
	private final JPanel DVDTextFieldPanel = new JPanel();
	private final JPanel bookTextFieldPanel = new JPanel();
	//private final JPanel errorPanel = new JPanel();
	private final JPanel buttonPanel = new JPanel();
	private final JPanel southPanel = new JPanel();
	
	private final JPanel CDLabelPanel = new JPanel();
	private final JPanel DVDLabelPanel = new JPanel();
	private final JPanel bookLabelPanel = new JPanel();
	
	private final ButtonGroup radioGroup = new ButtonGroup();
	private final JRadioButton CDRadioButton = new JRadioButton("CD", false);
	private final JRadioButton DVDRadioButton = new JRadioButton("DVD", false);
	private final JRadioButton bookRadioButton = new JRadioButton("Book", false);
	

	private JLabel errorLabel = new JLabel("", SwingConstants.CENTER);

	public ItemDialog(JFrame frame){
		super(frame, "Item dialog", true);

		Dimension frameSize = frame.getSize();
		int frameWidth = frameSize.width;
		int frameHeight = frameSize.height;
		setSize(frameWidth/3,frameHeight/3);

		//setLayout(new GridLayout(4, 1));
		setLayout(new BorderLayout());
		GridLayout textFieldLayout = new GridLayout(0,1);

		radioGroup.add(CDRadioButton);
		radioGroup.add(DVDRadioButton);
		radioGroup.add(bookRadioButton);
		radioButtonPanel.add(CDRadioButton);
		radioButtonPanel.add(DVDRadioButton);
		radioButtonPanel.add(bookRadioButton);
		
		CDRadioButton.addActionListener(event -> {
			addJPanel(CDMainPanel);
			media = MediaCategory.CD;
			
			CDTitleField.setText("");
			CDGenreField.setText("");
			CDArtistField.setText("");
			CDDescriptionField.setText("");
			CDQuantityField.setText("");
		});
		
		DVDRadioButton.addActionListener(event -> {
			addJPanel(DVDMainPanel);
			media = MediaCategory.DVD;
			
			DVDTitleField.setText("");
			DVDGenreField.setText("");
			DVDCastField.setText("");
			DVDDescriptionField.setText("");
			DVDQuantityField.setText("");
		});
		
		bookRadioButton.addActionListener(event -> {
			addJPanel(bookMainPanel);
			media = MediaCategory.BOOK;
			
			bookTitleField.setText("");
			bookGenreField.setText("");
			bookAuthorField.setText("");
			bookISBNField.setText("");
			bookDescriptionField.setText("");
			bookQuantityField.setText("");
		});
		
		doneButton.addActionListener(this);
		
		buttonPanel.setLayout(new FlowLayout());
		southPanel.setLayout(new BorderLayout());

		buttonPanel.add(doneButton);
		southPanel.add(errorLabel, BorderLayout.CENTER);
		southPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		CDTextFieldPanel.setLayout(textFieldLayout);
		CDLabelPanel.setLayout(textFieldLayout);
		
		DVDTextFieldPanel.setLayout(textFieldLayout);
		DVDLabelPanel.setLayout(textFieldLayout);
		
		bookTextFieldPanel.setLayout(textFieldLayout);
		bookLabelPanel.setLayout(textFieldLayout);

		CDMainPanel.add(CDLabelPanel, BorderLayout.WEST);
		CDMainPanel.add(CDTextFieldPanel, BorderLayout.CENTER);
		DVDMainPanel.add(DVDLabelPanel, BorderLayout.WEST);
		DVDMainPanel.add(DVDTextFieldPanel, BorderLayout.CENTER);
		bookMainPanel.add(bookLabelPanel, BorderLayout.WEST);
		bookMainPanel.add(bookTextFieldPanel, BorderLayout.CENTER);
		
		CDTextFieldPanel.add(CDTitleField);
		CDTextFieldPanel.add(CDArtistField);
		CDTextFieldPanel.add(CDGenreField);
		CDTextFieldPanel.add(CDDescriptionField);
		CDTextFieldPanel.add(CDQuantityField);
		
		CDLabelPanel.add(new JLabel("Title:"));
		CDLabelPanel.add(new JLabel("Artist(s):"));
		CDLabelPanel.add(new JLabel("Genre:"));
		CDLabelPanel.add(new JLabel("Description:"));
		CDLabelPanel.add(new JLabel("Quantity:"));

		DVDTextFieldPanel.add(DVDTitleField);
		DVDTextFieldPanel.add(DVDCastField);
		DVDTextFieldPanel.add(DVDGenreField);
		DVDTextFieldPanel.add(DVDDescriptionField);
		DVDTextFieldPanel.add(DVDQuantityField);
		
		DVDLabelPanel.add(new JLabel("Title:"));
		DVDLabelPanel.add(new JLabel("Cast(s):"));
		DVDLabelPanel.add(new JLabel("Genre:"));
		DVDLabelPanel.add(new JLabel("Description:"));
		DVDLabelPanel.add(new JLabel("Quantity:"));
		
		bookTextFieldPanel.add(bookTitleField);
		bookTextFieldPanel.add(bookAuthorField);
		bookTextFieldPanel.add(bookISBNField);
		bookTextFieldPanel.add(bookGenreField);
		bookTextFieldPanel.add(bookDescriptionField);
		bookTextFieldPanel.add(bookQuantityField);
		
		bookLabelPanel.add(new JLabel("Title:"));
		bookLabelPanel.add(new JLabel("Author(s):"));
		bookLabelPanel.add(new JLabel("ISBN:"));
		bookLabelPanel.add(new JLabel("Genre:"));
		bookLabelPanel.add(new JLabel("Description:"));
		bookLabelPanel.add(new JLabel("Quantity:"));

	}

	private void addJPanel(JPanel panel) {
		removePanels();
		
		add(panel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		//add(buttonPanel);

		validate();
		repaint();	
	}

	public void actionPerformed(ActionEvent e) {
		String title = "";
		String genre = "";
		String description = "";
		
		errorLabel.setForeground(Color.RED);

		if (media ==  MediaCategory.CD){
			title = CDTitleField.getText().trim();
			genre = CDGenreField.getText().trim();
			description = CDDescriptionField.getText().trim();
			String artist = CDArtistField.getText().trim();
			quantity = CDQuantityField.getText().trim().toLowerCase();

			if (!Utility.isNumeric(quantity))
				quantity = "1";
			
			if (title.length() < 1)
				errorLabel.setText("Title may be invalid.");
			else{
				item = new CD(itemID, title, description, genre, artist);
				
				resetSettings();
			}
		}
		else if (media ==  MediaCategory.DVD){
			title = DVDTitleField.getText().trim();
			genre = DVDGenreField.getText().trim();
			description = DVDDescriptionField.getText();
			String cast = DVDCastField.getText().trim();
			quantity = DVDQuantityField.getText().trim().toLowerCase();

			if (!Utility.isNumeric(quantity))
				quantity = "1";
			
			if (title.length() < 1)
				errorLabel.setText("Title may be invalid.");
			else{
				item = new DVD(itemID, title, description, genre, cast);
				
				resetSettings();
			}
		}
		else if (media ==  MediaCategory.BOOK){
			title = bookTitleField.getText().trim();
			genre = bookGenreField.getText().trim();
			description = bookDescriptionField.getText().trim();
			String author = bookAuthorField.getText().trim();
			String ISBN = bookISBNField.getText().trim();
			quantity = bookQuantityField.getText().trim().toLowerCase();

			if (!Utility.isNumeric(quantity))
				quantity = "1";
			
			if (title.length() < 1)
				errorLabel.setText("Title may be invalid.");
			else{
				item = new Book(itemID, title, description, genre, author, ISBN);
				
				resetSettings();
			}
		}
	}
	
	private void resetSettings() {
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
		removePanels();

		if (m instanceof CD){   
			add(CDMainPanel);
			media = MediaCategory.CD;
			
			CDTitleField.setText(m.getTitle());
			CDGenreField.setText(m.getGenre());
			CDArtistField.setText(((CD) m).getArtist());
			CDDescriptionField.setText(m.getDescription());
			CDQuantityField.setText(quant);
		}
		else if (m instanceof DVD){   
			add(DVDMainPanel);
			media = MediaCategory.DVD;
			
			DVDTitleField.setText(m.getTitle());
			DVDGenreField.setText(m.getGenre());
			DVDCastField.setText(((DVD) m).getCast());
			DVDDescriptionField.setText(m.getDescription());
			DVDQuantityField.setText(quant);
		}
		else if (m instanceof Book){   
			add(bookMainPanel);
			media = MediaCategory.BOOK;
			
			bookTitleField.setText(m.getTitle());
			bookGenreField.setText(m.getGenre());
			bookAuthorField.setText(((Book) m).getAuthor());
			bookISBNField.setText(((Book) m).getISBN());
			bookDescriptionField.setText(m.getDescription());
			bookQuantityField.setText(quant);
		}
		
		//add(errorPanel);
		//add(buttonPanel);
		add(southPanel, BorderLayout.SOUTH);

	}

	public void inputItemDetails(String itemID) {
		this.itemID = itemID;
		errorLabel.setText("");
		
		remove(radioButtonPanel);
		removePanels();

		add(radioButtonPanel, BorderLayout.NORTH);
		
	}

	private void removePanels() {
		remove(CDMainPanel);
		remove(DVDMainPanel);
		remove(bookMainPanel);

		//remove(errorPanel);
		remove(southPanel);
		
	}

	public void resetRadioButtonGroup() {
		radioGroup.clearSelection();
	}
}

