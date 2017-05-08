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
	
	//private PersonDialog addPersonDialog = null;
	
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
	
	private static final JPanel statusPanel = new JPanel();
	private static final JLabel totalContactsLabel = new JLabel("Total Contacts: ");
	private JLabel totalContactsStatus = new JLabel("");
	private int totalContact = 0;
	
	private int tableRowNum = 0;
	private boolean tableRowSelected = false; 
	
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

		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		add(statusPanel, BorderLayout.SOUTH);

		statusPanel.add(totalContactsLabel);
		statusPanel.add(totalContactsStatus);

		
		openFileMenu.addActionListener((event) -> {
			JFileChooser openDialog = new JFileChooser();
			openDialog.showOpenDialog(null);
		});
		
		exitFileMenu.addActionListener(event -> {
			controller.saveData();	
			System.exit(0);
		});
	
		newEditMenu.addActionListener(event -> newContact());
		newToolBarButton.addActionListener(event -> newContact());
		findToolBarButton.addActionListener(event -> searchItem());
		deleteToolBarButton.addActionListener(event -> deleteContact());
		editToolBarButton.addActionListener(event -> editContact());
      
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void searchItem() {
		String query = "0";
		controller.searchItem(query);
	}

	private void editContact() {


	}

	private void deleteContact() {
		

	}

	private void newContact() {
		String itemID = "0";
		String title = "test1";
		String description = "abc";
		String genre = "www";
		String artist = "me";
		String quantity = "22";
		
		controller.addItem(new CD(itemID, title, description , genre, artist), quantity);

	}
/*	
	private void dialog(Person p) {
		
		if (addPersonDialog == null)
			addPersonDialog = new PersonDialog(this);
		
		addPersonDialog.initializeTextFields(p);
		addPersonDialog.setLocationRelativeTo(this);
		addPersonDialog.setDone(false);
		addPersonDialog.setVisible(true);
		
		if (addPersonDialog.getDone() == true){			
			contacts.add(addPersonDialog.getPerson());
			
			//totalContact = contacts.getSize();

		}		
	}
*/

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
		
		displayContacts();

	}

	@Override
	public void update(UpdateType ut) {
		if (ut == UpdateType.SEARCH_RESULT){
			Media [] result = model.getSearchResult();
			
			System.out.println(result.length > 0?"":"No match found");
			System.out.println();
			for (Media mm : result){
				
				System.out.println("Quantity: " + model.getItemQuantity(mm.getID()) + "\n");
				
				JLabel IDLabel = new JLabel("Item ID:  ");
				JLabel IDLabelValue = new JLabel(mm.getID());
				JLabel titleLabel = new JLabel("Title:  ");
				JLabel titleLabelValue = new JLabel("<html>" + mm.getTitle() + " </html>");
				JLabel genreLabel = new JLabel("Genre:  ");
				JLabel genreLabelValue = new JLabel(mm.getGenre());
				JLabel descriptionLabel = new JLabel("Description:  ");
				JLabel descriptionLabelValue = new JLabel("<html>" + mm.getDescription() + " </html>");
				
				IDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				IDLabelValue.setHorizontalAlignment(SwingConstants.LEFT);
				titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				titleLabelValue.setHorizontalAlignment(SwingConstants.LEFT);
				genreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				genreLabelValue.setHorizontalAlignment(SwingConstants.LEFT);
				descriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				descriptionLabelValue.setHorizontalAlignment(SwingConstants.LEFT);

				JPanel panel1 = new JPanel();
				GridLayout layout = new GridLayout(0,2);

				
				add(panel1);
				panel1.setLayout(layout);
				panel1.add(IDLabel);
				panel1.add(IDLabelValue);
				panel1.add(titleLabel);
				panel1.add(titleLabelValue);
				panel1.add(genreLabel);
				panel1.add(genreLabelValue);
				panel1.add(descriptionLabel);
				panel1.add(descriptionLabelValue);
				
				validate();
			}
		}

	}


}