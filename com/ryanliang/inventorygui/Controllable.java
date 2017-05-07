/**
 *
 * @author Ryan L.
 */

package com.ryanliang.inventorygui;

public interface Controllable {
	public void saveData();

	public void loadData();

	public void searchItem(String query);
	public void searchItemForEditing(String itemID);

	public void addItem(Media media, String quantity);
	
	public void editItem(Media media, String quantity);
	
	public void deleteItem(String itemID);
	
	public void generateID(); 
}
