/**
 *
 * @author Ryan L.
 */

package com.ryanliang.inventorygui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Toolkit;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class InventoryView extends JFrame implements Viewable{
	
	private final Controllable controller;
	private Modellable model;
	
	private Media [] searchResult;
	private int resultCounter = 0;
	
	private static String itemID = "0";
	
	private ItemDialog itemDialog = null;
	
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu fileMenu = new JMenu("File");
	private final JMenu editMenu = new JMenu("Edit");
	private final JMenu helpMenu = new JMenu("Help");
	
	private final JMenuItem saveFileMenu = new JMenuItem("Save");
	private final JMenuItem exitFileMenu = new JMenuItem("Exit");
	
	private final JMenuItem newEditMenu = new JMenuItem("New");
	private final JMenuItem editEditMenu = new JMenuItem("Edit"); 
	private final JMenuItem deleteEditMenu = new JMenuItem("Delete");
	private final JMenuItem findEditMenu = new JMenuItem("Find"); 
	
	private final JMenuItem aboutHelpMenu = new JMenuItem("About");
	
	private final JButton newToolBarButton = new JButton("New"); 
	private final JButton editToolBarButton = new JButton("Edit"); 
	private final JButton deleteToolBarButton = new JButton("Delete"); 
	private final JButton findToolBarButton = new JButton("Find"); 
	private final JButton firstToolBarButton = new JButton("First"); 
	private final JButton previousToolBarButton = new JButton("Previous"); 
	private final JButton nextToolBarButton = new JButton("Next"); 
	private final JButton lastToolBarButton = new JButton("Last"); 
	
	private final JToolBar toolBar = new JToolBar();
	
	private final JPanel westPanel = new JPanel();
	private final JPanel statusPanel = new JPanel();
	private final JLabel searchResultLabel = new JLabel("Search result: ");
	private JLabel searchResultStatus = new JLabel("");
	
	private JLabel itemDetails = new JLabel("");
	
	public InventoryView(Controllable controller) {
		super("Media inventory system");
		this.controller = controller;
					
		fileMenu.add(saveFileMenu);
		fileMenu.addSeparator();
		fileMenu.add(exitFileMenu);
		
		editMenu.add(newEditMenu);
		editMenu.add(editEditMenu);
		editMenu.add(deleteEditMenu);
		editMenu.add(findEditMenu);
		
		helpMenu.add(aboutHelpMenu);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);
		
		toolBar.add(newToolBarButton);
		toolBar.add(editToolBarButton);
		toolBar.add(deleteToolBarButton);
		toolBar.add(findToolBarButton);
		toolBar.add(firstToolBarButton);
		toolBar.add(previousToolBarButton);
		toolBar.add(nextToolBarButton);
		toolBar.add(lastToolBarButton);
		add(toolBar, BorderLayout.NORTH);
		
		add(westPanel, BorderLayout.WEST);
		westPanel.add(new JLabel("      "));
		
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		add(statusPanel, BorderLayout.SOUTH);
		statusPanel.add(searchResultLabel);
		statusPanel.add(searchResultStatus);

		add(itemDetails);
		
		saveFileMenu.addActionListener(event -> {
			controller.saveData();	
		});
		
		exitFileMenu.addActionListener(event -> {
			controller.saveData();	
			System.exit(0);
		});
	
		newEditMenu.addActionListener(event -> newItem());
		newToolBarButton.addActionListener(event -> newItem());
		findToolBarButton.addActionListener(event -> searchItem());
		deleteToolBarButton.addActionListener(event -> deleteItem());
		editToolBarButton.addActionListener(event -> editItem());
		nextToolBarButton.addActionListener(event -> nextItem());
		previousToolBarButton.addActionListener(event -> previousItem());
		firstToolBarButton.addActionListener(event -> firstItem());
		lastToolBarButton.addActionListener(event -> lastItem());
      
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void lastItem() {
		if (searchResult != null){
			if (searchResult.length > 1){
				resultCounter = searchResult.length - 1;
				displayResult(searchResult[resultCounter]);		
			}
		}
	}

	private void firstItem() {
		if (searchResult != null){
			if (searchResult.length > 1){
				resultCounter = 0;
				displayResult(searchResult[resultCounter]);		
			}	
		}
	}

	private void nextItem() {
		if (searchResult != null){
			if (searchResult.length > 1 && (resultCounter < (searchResult.length-1))){
				resultCounter++;
				displayResult(searchResult[resultCounter]);		
			}	
		}
	}

	private void previousItem() {
		if (searchResult != null){
			if (searchResult.length > 1 && (resultCounter > 0)){
				resultCounter--;
				displayResult(searchResult[resultCounter]);		
			}	
		}
	}

	private void searchItem() {
		String input = JOptionPane.showInputDialog("Enter item ID number"); 
		if (input != null)
			controller.searchItem(input.trim());
	}

	private void editItem() {

		String input = JOptionPane.showInputDialog("Enter item ID number");
		if (input != null)
			controller.searchItemForEditing(input.trim());
	}

	private void deleteItem() {
		
		String input = JOptionPane.showInputDialog("Enter item ID number");
		if (input != null)
			controller.deleteItem(input.trim());
	}

	private void newItem() {
		Media temp = null;
		controller.generateID();

		openItemDialog(temp, "");
		
		if (itemDialog.getDone() == true){
			Media item = itemDialog.getItem();
			
			//may need to check null value.  will analyze later.
			controller.addItem(item, itemDialog.getQuantity());
		}	
	}
	
	private void openItemDialog(Media m, String quantity) {
		
		if (itemDialog == null)
			itemDialog = new ItemDialog(this);
		
		if (m == null){
			itemDialog.resetRadioButtonGroup();
			itemDialog.inputItemDetails(itemID);
		}
		else{
			itemDialog.initializeTextFields(m, quantity);
		}
		itemDialog.setLocationRelativeTo(this);
		itemDialog.setDone(false);
		itemDialog.setVisible(true);
		
	}


	@Override
	public void setModel(Modellable model) {
		this.model = model;

	}

	@Override
	public void start() {
		controller.loadData();
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = (int) (screenSize.width*0.8);
		int screenHeight = (int) (screenSize.height*0.8);
		this.setSize(screenWidth, screenHeight);
		//app.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@Override
	public void update(UpdateType ut) {
		if (ut == UpdateType.SEARCH_RESULT){
			searchResult = model.getSearchResult();
			
			if (searchResult.length < 1)
				JOptionPane.showMessageDialog(null, "Item does not exist", "alert", JOptionPane.ERROR_MESSAGE); 
			else {
				searchResultStatus.setText(String.valueOf((searchResult.length)));
				validate();
				//reset counter
				resultCounter = 0;
				displayResult(searchResult[0]);		
			}
		}
		else if (ut == UpdateType.EDIT){
			Media [] result = model.getSearchResult();
			
			if (result.length < 1)
				JOptionPane.showMessageDialog(null, "Item does not exist", "alert", JOptionPane.ERROR_MESSAGE); 
			else
				editResult(result[0]);			
		}
		else if (ut == UpdateType.ID){
			itemID = model.getID();
		}

	}

	private void editResult(Media mm) {
		if (mm instanceof CD){	

			openItemDialog(mm, model.getItemQuantity(mm.getID()));		
			if (itemDialog.getDone() == true){	
				//may need to check null value.  will analyze later.
				CD item = (CD) itemDialog.getItem();

				String ID = item.getID();
				String title = item.getTitle();
				String quantity = itemDialog.getQuantity();
				String genre = item.getGenre();
				String description = item.getDescription();
				String artist = item.getArtist();

				controller.editItem(new CD(ID, title, description , genre, artist), quantity); 
			}
		}
		else if (mm instanceof DVD){	

			openItemDialog(mm, model.getItemQuantity(mm.getID()));		
			if (itemDialog.getDone() == true){	
				DVD item = (DVD) itemDialog.getItem();

				String ID = item.getID();
				String title = item.getTitle();
				String quantity = itemDialog.getQuantity();
				String genre = item.getGenre();
				String description = item.getDescription();
				String cast = item.getCast();

				controller.editItem(new DVD(ID, title, description , genre, cast), quantity); 
			}
		}
		else if (mm instanceof Book){	

			openItemDialog(mm, model.getItemQuantity(mm.getID()));		
			if (itemDialog.getDone() == true){	
				Book item = (Book) itemDialog.getItem();

				String ID = item.getID();
				String title = item.getTitle();
				String quantity = itemDialog.getQuantity();
				String genre = item.getGenre();
				String description = item.getDescription();
				String author = item.getAuthor();
				String ISBN = item.getISBN();

				controller.editItem(new Book(ID, title, description , genre, author, ISBN), quantity); 
			}
		}
	}

	private void displayResult(Media mm) {
		if (mm instanceof CD){						
			itemDetails.setText("<html>" + "<h3><font color=blue>Item ID</font></h3>" + mm.getID() + "<br>" 
					+ "<h3><font color=blue>Quantity</font></h3>" + model.getItemQuantity(mm.getID()) + "<br>"
					+ "<h3><font color=blue>Title</font></h3>" + mm.getTitle() + "<br>" 
					+ "<h3><font color=blue>Artist(s)</font></h3>" + ((CD)mm).getArtist() + "<br>"
					+ "<h3><font color=blue>Genre</font></h3>" + mm.getGenre() + "<br>" 
					+ "<h3><font color=blue>Description</font></h3>" + mm.getDescription() + "<br>" +  "</html>");
		}
		else if (mm instanceof DVD){						
			itemDetails.setText("<html>" + "<h3><font color=blue>Item ID</font></h3>" + mm.getID() + "<br>" 
					+ "<h3><font color=blue>Quantity</font></h3>" + model.getItemQuantity(mm.getID()) + "<br>"
					+ "<h3><font color=blue>Title</font></h3>" + mm.getTitle() + "<br>" 
					+ "<h3><font color=blue>Cast(s)</font></h3>" + ((DVD)mm).getCast() + "<br>"
					+ "<h3><font color=blue>Genre</font></h3>" + mm.getGenre() + "<br>" 
					+ "<h3><font color=blue>Description</font></h3>" + mm.getDescription() + "<br>" +  "</html>");
		}
		else if (mm instanceof Book){						
			itemDetails.setText("<html>" + "<h3><font color=blue>Item ID</font></h3>" + mm.getID() + "<br>" 
					+ "<h3><font color=blue>Quantity</font></h3>" + model.getItemQuantity(mm.getID()) + "<br>"
					+ "<h3><font color=blue>Title</font></h3>" + mm.getTitle() + "<br>" 
					+ "<h3><font color=blue>Author(s)</font></h3>" + ((Book)mm).getAuthor() + "<br>"
					+ "<h3><font color=blue>ISBN</font></h3>" + ((Book)mm).getISBN() + "<br>"
					+ "<h3><font color=blue>Genre</font></h3>" + mm.getGenre() + "<br>" 
					+ "<h3><font color=blue>Description</font></h3>" + mm.getDescription() + "<br>" +  "</html>");
		}
		validate();
	}

}