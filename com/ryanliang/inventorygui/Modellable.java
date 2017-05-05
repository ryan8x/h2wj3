/**
 *
 * @author Ryan Liang
 */

package com.ryanliang.inventorygui;

public interface Modellable {

	public void addItem(Media media, String quantity);

	public void saveData();
	public void loadData();

	public void editItem(Media media, String quantity);
	
	public void deleteItem(String itemID);
	
	public void searchItem(String query);
	public Media[] getSearchResult();
	
	public void searchItemForEditing(String itemID);
	
	public void generateID(); 
	public String getID(); 
	
	public String getItemQuantity(String itemID); 
	


}
