/**
 *
 * @author Ryan L.
 */

package com.ryanliang.inventorygui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

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
				
		organizeUI();
		addListeners();
			
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	private void addListeners() {
		saveFileMenu.addActionListener(event -> {
			controller.saveData();	
		});
		
		exitFileMenu.addActionListener(event -> {
			quitApp();
		});
	
		aboutHelpMenu.addActionListener(event -> {
			JOptionPane.showMessageDialog(null, "Media inventory system v1.0 Copyright 2017 RLTech Inc");
		});
		
		newEditMenu.addActionListener(event -> newItem());
		findEditMenu.addActionListener(event -> searchItem());
		deleteEditMenu.addActionListener(event -> deleteItem());
		editEditMenu.addActionListener(event -> editItem());
		
		newToolBarButton.addActionListener(event -> newItem());
		findToolBarButton.addActionListener(event -> searchItem());
		deleteToolBarButton.addActionListener(event -> deleteItem());
		editToolBarButton.addActionListener(event -> editItem());
		
		nextToolBarButton.addActionListener(event -> nextItem());
		previousToolBarButton.addActionListener(event -> previousItem());
		firstToolBarButton.addActionListener(event -> firstItem());
		lastToolBarButton.addActionListener(event -> lastItem());
      
		addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		    	quitApp();	
		    }
		});
	}

	private void organizeUI() {
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
		setSearchResultStatusVisible(false);

		add(itemDetails);
	}

	private void quitApp() {
    	int answer = JOptionPane.showConfirmDialog(null, "Save data?");
    	if (answer == JOptionPane.YES_OPTION){
    		controller.saveData();	
    		System.exit(0);
    	}
    	else if (answer == JOptionPane.NO_OPTION)
    		System.exit(0);		
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
		clearItemDetails();
		setSearchResultStatusVisible(false);
		
		String input = JOptionPane.showInputDialog("Enter item ID number");
		if (input != null){
			controller.searchItemForEditing(input.trim());
			
			searchResult = null;
		}
	}

	private void deleteItem() {
		clearItemDetails();
		setSearchResultStatusVisible(false);
		
		String input = JOptionPane.showInputDialog("Enter item ID number");
		if (input != null){
			controller.deleteItem(input.trim());
			
			searchResult = null;
		}
	}

	private void newItem() {
		clearItemDetails();
		setSearchResultStatusVisible(false);
		
		Media temp = null;
		//Generate item ID which will be needed in the openItemDialog()
		controller.generateID();

		openItemDialog(temp, "");
		
		if (itemDialog.getDone() == true){
			Media item = itemDialog.getItem();
			
			controller.addItem(item, itemDialog.getQuantity());
			displayResult(item);
			
			searchResult = null;
		}
		if (itemDialog != null){
			itemDialog.initUI();
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

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@Override
	public void update(UpdateType ut) {
		if (ut == UpdateType.SEARCH_RESULT){
			searchResult = model.getSearchResult();
			
			if (searchResult.length < 1){
				clearItemDetails();
				JOptionPane.showMessageDialog(null, "Item does not exist", "alert", JOptionPane.ERROR_MESSAGE); 
			}
			else {
				searchResultStatus.setText(String.valueOf((searchResult.length)));
				validate();
				//reset counter
				resultCounter = 0;
				displayResult(searchResult[0]);	
				
				setSearchResultStatusVisible(true);
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

	private void setSearchResultStatusVisible(boolean v) {
		searchResultLabel.setVisible(v);
		searchResultStatus.setVisible(v);
		validate();	
	}

	private void editResult(Media mm) {
		Media temp;
		if (mm instanceof CD){	

			openItemDialog(mm, model.getItemQuantity(mm.getID()));		
			if (itemDialog.getDone() == true){	
				CD item = (CD) itemDialog.getItem();

				String ID = item.getID();
				String title = item.getTitle();
				String quantity = itemDialog.getQuantity();
				String genre = item.getGenre();
				String description = item.getDescription();
				String artist = item.getArtist();

				temp = new CD(ID, title, description , genre, artist);
				controller.editItem(temp, quantity); 
				displayResult(temp);
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

				temp = new DVD(ID, title, description , genre, cast);
				controller.editItem(temp, quantity); 
				displayResult(temp);
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

				temp = new Book(ID, title, description , genre, author, ISBN);
				controller.editItem(temp, quantity); 
				displayResult(temp);
			}
		}
		if (itemDialog != null){
			itemDialog.initUI();
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

	private void clearItemDetails() {
		itemDetails.setText("");
	}

}