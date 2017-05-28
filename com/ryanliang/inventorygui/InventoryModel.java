/**
 *
 * @author Ryan L.
 */

package com.ryanliang.inventorygui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class InventoryModel implements Modellable {

	private Viewable view;
	private final Properties CDList = new Properties();
	private final Properties DVDList = new Properties();
	private final Properties bookList = new Properties();
	private final Properties IDMemory = new Properties();
	private final Properties inventory = new Properties();
	private final ArrayList<Media> searchResult = new ArrayList<>();
	private final String CDFile = "CD_list.dat";
	private final String DVDFile = "DVD_list.dat";
	private final String bookFile = "Book_list.dat";
	private final String IDMemoryFile = "ID_memory.dat";
	private final String inventoryFile = "Inventory.dat";
	
	private final static String delimiter = "-1a-2b-";
	private static long IDCounter = 0;
	
	@Override
	public void addItem(Media media, String quantityA) {
		if (media != null && quantityA != null){
			String quantity = quantityA;
			String ID = media.getID();

			setData(media);
			inventory.setProperty(ID, quantity);
			IDMemory.setProperty("IDCounter", String.valueOf(++IDCounter));
		}
		else 
			System.out.println("addItem(Media media, String quantityA) reference is null.");
	}
	
	private void setData(Media media) {		
		String none = "None";
		
		String ID = media.getID();
		String title = media.getTitle();
		String description = media.getDescription();
		String genre = media.getGenre();
		
		title = title.trim().equals("")?none:title;
		description = description.trim().equals("")?none:description;
		genre = genre.trim().equals("")?none:genre;
		
		String value = title + delimiter + description + delimiter + genre + delimiter;
		
		if (media instanceof CD){
			String artist = ((CD) media).getArtist();
			
			artist = artist.trim().equals("")?none:artist;
			
			CDList.setProperty(ID, value + artist);
		}
		else if (media instanceof DVD){
			String cast = ((DVD) media).getCast();
			
			cast = cast.trim().equals("")?none:cast;
			
			DVDList.setProperty(ID, value + cast);
		}
		else if (media instanceof Book){
			String author = ((Book) media).getAuthor();
			String ISBN = ((Book) media).getISBN();
			
			author = author.trim().equals("")?none:author;
			ISBN = ISBN.trim().equals("")?none:ISBN;
			
			bookList.setProperty(ID, value + author + delimiter + ISBN);
		}	
	}

	@Override
	public void editItem(Media media, String quantity) {
		if (media != null && quantity != null){
			String ID = media.getID();
			setData(media);

			//Modify only if quantity is not empty and is a number (not consisting of alphabetic characters)
			if (!quantity.equals("") && Utility.isNumeric(quantity))
				inventory.setProperty(ID, quantity);
		}
		else 
			System.out.println("editItem(Media media, String quantity) reference is null.");
	}

	@Override
	public void saveData() {
		try {
			FileOutputStream cdOut = new FileOutputStream(CDFile, false);
			CDList.store(cdOut, "CD List");
			cdOut.close();

			FileOutputStream dvdOut = new FileOutputStream(DVDFile, false);
			DVDList.store(dvdOut, "DVD List");
			dvdOut.close();

			FileOutputStream bookOut = new FileOutputStream(bookFile, false);
			bookList.store(bookOut, "Book List");
			bookOut.close();

			FileOutputStream inventoryOut = new FileOutputStream(inventoryFile, false);
			inventory.store(inventoryOut, "Inventory");
			inventoryOut.close();

			FileOutputStream IDOut = new FileOutputStream(IDMemoryFile, false);
			IDMemory.store(IDOut, "ID memory");
			IDOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@Override
	public void loadData() {		
		File fileCD = new File(CDFile);
		File fileDVD = new File(DVDFile);
		File fileBook = new File(bookFile);
		File fileInventory = new File(inventoryFile);
		File fileID = new File(IDMemoryFile);
		
		try {
			if (fileCD.exists() && !fileCD.isDirectory()){
				FileInputStream cdIn = new FileInputStream(CDFile);
				CDList.load(cdIn);
				cdIn.close();
			}

			if (fileDVD.exists() && !fileDVD.isDirectory()){
				FileInputStream dvdIn = new FileInputStream(DVDFile);
				DVDList.load(dvdIn);
				dvdIn.close();
			}

			if (fileBook.exists() && !fileBook.isDirectory()){
				FileInputStream bookIn = new FileInputStream(bookFile);
				bookList.load(bookIn);
				bookIn.close();
			}
			
			if (fileInventory.exists() && !fileInventory.isDirectory()){
				FileInputStream inventoryIn = new FileInputStream(inventoryFile);
				inventory.load(inventoryIn);
				inventoryIn.close();
			}
			
			if (fileID.exists() && !fileID.isDirectory()){
				FileInputStream IDIn = new FileInputStream(IDMemoryFile);
				IDMemory.load(IDIn);
				String tempID = IDMemory.getProperty("IDCounter");
				
				if (tempID != null)
					IDCounter = Long.valueOf(tempID);
				
				IDIn.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void setView(Viewable view) {
		this.view = view;
	}
	
	@Override
	public void searchItem(String query) {
		if (query != null){
			searchItemHelper(query);
			view.update(UpdateType.SEARCH_RESULT);
		}
		else 
			System.out.println("searchItem(String query) reference is null.");
	}
	
	@Override
	public Media[] getSearchResult(){
		Media [] result = searchResult.toArray(new Media[searchResult.size()]);
		searchResult.clear();
		return result;
	}

	@Override
	public void deleteItem(String itemID){
		if (itemID != null){
			CDList.remove(itemID);
			DVDList.remove(itemID);
			bookList.remove(itemID);
			inventory.remove(itemID);
		}
		else 
			System.out.println("deleteItem(String itemID) reference is null.");	
	}

	@Override
	public void generateID() {
		view.update(UpdateType.ID);	
	}
	
	@Override
	public String getID() {	
		return String.valueOf(IDCounter);
	}
	
	private void searchItemHelper(String query) {	
		String temp = null;
		String value;
		
		if (!query.equals("")){
			//ID based search
			if (Utility.isNumeric(query)){
				while (true){
					temp = CDList.getProperty(query);
					if (temp != null){
						searchResultHelper(query, temp, MediaCategory.CD);
						break;
					}		

					temp = DVDList.getProperty(query);
					if (temp != null){			
						searchResultHelper(query, temp, MediaCategory.DVD);
						break;
					}	

					temp = bookList.getProperty(query);
					if (temp != null){					
						searchResultHelper(query, temp, MediaCategory.BOOK);
						break;
					}	
					break;
				}
			}
			//simple return all CDs, DVDs or Books
			else if (query.equals("return-all-cds")){
				for (String key : CDList.stringPropertyNames()){
					temp = CDList.getProperty(key);
					searchResultHelper(key, temp, MediaCategory.CD);
				}
			}
			else if (query.equals("return-all-dvds")){
				for (String key : DVDList.stringPropertyNames()){
					temp = DVDList.getProperty(key);
					searchResultHelper(key, temp, MediaCategory.DVD);
				}
			}				
			else if (query.equals("return-all-books")){
				for (String key : bookList.stringPropertyNames()){
					temp = bookList.getProperty(key);
					searchResultHelper(key, temp, MediaCategory.BOOK);
				}				
			}
			//Word phrase based search
			else{
				query = query.toLowerCase();
				for (String key : CDList.stringPropertyNames()){
					temp = CDList.getProperty(key);
					value = temp.toLowerCase();
					if (value.contains(query)){
						searchResultHelper(key, temp, MediaCategory.CD);
					}
				}
				for (String key : DVDList.stringPropertyNames()){
					temp = DVDList.getProperty(key);
					value = temp.toLowerCase();
					if (value.contains(query)){
						searchResultHelper(key, temp, MediaCategory.DVD);
					}
				}
				for (String key : bookList.stringPropertyNames()){
					temp = bookList.getProperty(key);
					value = temp.toLowerCase();
					if (value.contains(query)){
						searchResultHelper(key, temp, MediaCategory.BOOK);
					}
				}
			}
		}
	}

	private void searchResultHelper(String key, String str, MediaCategory media) {
		String[] parts = str.split(delimiter);	
		String title = parts[0]; 
		String description = parts[1]; 
		String genre = parts[2]; 
		
		if (media == MediaCategory.CD){
			String artist = parts[3]; 
			searchResult.add(new CD(key, title, description, genre, artist));
		}
		else if (media == MediaCategory.DVD){
			String cast = parts[3]; 
			searchResult.add(new DVD(key, title, description, genre, cast));
		}
		else if (media == MediaCategory.BOOK){
			String author = parts[3]; 
			String ISBN = parts[4]; 
			searchResult.add(new Book(key, title, description, genre, author, ISBN));
		}

	}

	@Override
	public void searchItemForEditing(String itemID) {		
		if (itemID != null){
			searchItemHelper(itemID);
			view.update(UpdateType.EDIT);
		}
		else 
			System.out.println("searchItemForEditing(String itemID) reference is null.");
	}

	@Override
	public String getItemQuantity(String itemID) {
		if (itemID != null)
			return inventory.getProperty(itemID);
		else{ 
			System.out.println("getItemQuantity(String itemID) reference is null.");
			return null;
		}
	}
}

