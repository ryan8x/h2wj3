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
	
	private static String itemID = "0";
	
	private ItemDialog itemDialog = null;
	
	private static final JMenuBar menuBar = new JMenuBar();
	private static final JMenu fileMenu = new JMenu("File");
	private static final JMenu editMenu = new JMenu("Edit");
	private static final JMenu helpMenu = new JMenu("Help");
	private static final JMenuItem openFileMenu = new JMenuItem("Open");
	private static final JMenuItem saveFileMenu = new JMenuItem("Save");
	private static final JMenuItem saveAsFileMenu = new JMenuItem("Save As");
	private static final JMenuItem printFileMenu = new JMenuItem("Print");
	private static final JMenuItem exitFileMenu = new JMenuItem("Exit");
	
	private static final JMenuItem newEditMenu = new JMenuItem("New");
	private static final JMenuItem editEditMenu = new JMenuItem("Edit"); 
	private static final JMenuItem deleteEditMenu = new JMenuItem("Delete");
	private static final JMenuItem findEditMenu = new JMenuItem("Find"); 
	private static final JMenuItem firstEditMenu = new JMenuItem("First");
	private static final JMenuItem previousEditMenu = new JMenuItem("Previous"); 
	private static final JMenuItem nextEditMenu = new JMenuItem("Next"); 
	private static final JMenuItem lastEditMenu = new JMenuItem("Last");
	
	private static final JMenuItem documentationHelpMenu = new JMenuItem("Documentation"); 
	private static final JMenuItem aboutHelpMenu = new JMenuItem("About");
	
	private static final JButton newToolBarButton = new JButton("New"); 
	private static final JButton editToolBarButton = new JButton("Edit"); 
	private static final JButton deleteToolBarButton = new JButton("Delete"); 
	private static final JButton findToolBarButton = new JButton("Find"); 
	private static final JButton firstToolBarButton = new JButton("First"); 
	private static final JButton previousToolBarButton = new JButton("Previous"); 
	private static final JButton nextToolBarButton = new JButton("Next"); 
	private static final JButton lastToolBarButton = new JButton("Last"); 
	
	private static final JToolBar toolBar = new JToolBar();
	private JTable table = null;
	private JScrollPane scrollPane = null;
	
	private static final JPanel westPanel = new JPanel();
	private static final JPanel statusPanel = new JPanel();
	private static final JLabel totalContactsLabel = new JLabel("Total Contacts: ");
	private JLabel totalContactsStatus = new JLabel("");
	private int totalContact = 0;
	
	private int tableRowNum = 0;
	private boolean tableRowSelected = false; 
	
	private JLabel itemDetails = new JLabel("");
	
	public InventoryView(Controllable controller) {
		super("Media inventory system");
		this.controller = controller;
					
		fileMenu.add(openFileMenu);
		fileMenu.addSeparator();
		fileMenu.add(saveFileMenu);
		fileMenu.add(saveAsFileMenu);
		fileMenu.addSeparator();
		fileMenu.add(printFileMenu);
		fileMenu.addSeparator();
		fileMenu.add(exitFileMenu);
		
		editMenu.add(newEditMenu);
		editMenu.add(editEditMenu);
		editMenu.add(deleteEditMenu);
		editMenu.add(findEditMenu);
		editMenu.add(firstEditMenu);
		editMenu.add(previousEditMenu);
		editMenu.add(nextEditMenu);
		editMenu.add(lastEditMenu);
		
		helpMenu.add(documentationHelpMenu);
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

		statusPanel.add(totalContactsLabel);
		statusPanel.add(totalContactsStatus);

		add(itemDetails);
		
		openFileMenu.addActionListener((event) -> {
			JFileChooser openDialog = new JFileChooser();
			openDialog.showOpenDialog(null);
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
      
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void searchItem() {
		String input = JOptionPane.showInputDialog("Enter item ID number").trim(); 
		controller.searchItem(input);
	}

	private void editItem() {

		String input = JOptionPane.showInputDialog("Enter item ID number").trim(); 
		controller.searchItemForEditing(input);
	}

	private void deleteItem() {
		
		String input = JOptionPane.showInputDialog("Enter item ID number").trim(); 
		controller.deleteItem(input);
	}

	private void newItem() {
		Media temp = null;
		controller.generateID();
		//openItemDialog(new CD(itemID, "", "", "", ""), "");
		openItemDialog(temp, "");
		
		if (itemDialog.getDone() == true){
			Media item = itemDialog.getItem();
			
			if (item instanceof CD){  
				controller.addItem(item, itemDialog.getQuantity());
			}
		}	
	}
	
	private void openItemDialog(Media m, String quantity) {
		
		if (itemDialog == null)
			itemDialog = new ItemDialog(this);
		
		if (m == null){
			itemDialog.resetRadioButtonGroup();
			itemDialog.inputItemDetails();
		}
		else{
			itemDialog.initializeTextFields(m, quantity);
		}
		itemDialog.setLocationRelativeTo(this);
		itemDialog.setDone(false);
		itemDialog.setVisible(true);
		
	}


	private void displayContacts() {



		//Refresh frame components in case table contents are changed.
		validate();
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
		
		//displayContacts();

	}

	@Override
	public void update(UpdateType ut) {
		if (ut == UpdateType.SEARCH_RESULT){
			Media [] result = model.getSearchResult();
			
			if (result.length < 1)
				JOptionPane.showMessageDialog(null, "Item does not exist", "alert", JOptionPane.ERROR_MESSAGE); 
			else{
				for (Media mm : result){
					
					//****More code is needed for multiple search results (items)!!	****
					
					itemDetails.setText("<html>" + "<h3><font color=blue>Item ID</font></h3>" + mm.getID() + "<br>" 
							+ "<h3><font color=blue>Quantity</font></h3>" + model.getItemQuantity(mm.getID()) + "<br>"
							+ "<h3><font color=blue>Title</font></h3>" + mm.getTitle() + "<br>" 
							+ "<h3><font color=blue>Genre</font></h3>" + mm.getGenre() + "<br>" 
							+ "<h3><font color=blue>Description</font></h3>" + mm.getDescription() + "<br>" +  "</html>");

					validate();
				}
			}
		}
		else if (ut == UpdateType.EDIT){
			Media [] result = model.getSearchResult();
			
			if (result.length < 1)
				JOptionPane.showMessageDialog(null, "Item does not exist", "alert", JOptionPane.ERROR_MESSAGE); 
			else{
				for (Media mm : result){

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
			}
		}
		else if (ut == UpdateType.ID){
			itemID = model.getID();
		}

	}


}