/**
 *
 * @author Ryan Liang
 */

package com.ryanliang.inventorygui;

public class InventoryController implements Controllable {

		private final Modellable model;
		
		public InventoryController(Modellable model) {
			super();
			this.model = model;
		}

		public void addItem(Media media, String quantity) {
			model.addItem(media, quantity);
			
		}

		public void saveData() {
			model.saveData();
			
		}

		public void searchItem(String query) {
			model.searchItem(query);
			
		}
		
		public void searchItemForEditing(String itemID){
			model.searchItemForEditing(itemID);
		}

		public void loadData() {
			model.loadData();
			
		}

		@Override
		public void deleteItem(String itemID){
			model.deleteItem(itemID);
			
		}

		@Override
		public void editItem(Media media, String quantity) {
			model.editItem(media, quantity);
			
		}

		@Override
		public void generateID() {
			model.generateID();
			
		}


		

		
}
