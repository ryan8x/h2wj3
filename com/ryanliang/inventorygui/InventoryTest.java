/**
 *
 * @author Ryan Liang
 */

package com.ryanliang.inventorygui;

public class InventoryTest {

	public static void main(String[] args) {
		
		InventoryModel model = new InventoryModel();
		InventoryController controller = new InventoryController(model);
		InventoryView view = new InventoryView(controller);
		
		view.setModel(model);
		model.setView(view);
		view.mainMenu();

	}

}
