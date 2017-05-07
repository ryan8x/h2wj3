/**
 *
 * @author Ryan L.
 */

package com.ryanliang.inventorygui;

import java.awt.Dimension;
import java.awt.Toolkit;

public class InventoryTest {

	public static void main(String[] args) {
		
		InventoryModel model = new InventoryModel();
		InventoryController controller = new InventoryController(model);
		InventoryView view = new InventoryView(controller);
		
		view.setModel (model);
		model.setView(view);
		view.start();

/*		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = (int) (screenSize.width*0.8);
		int screenHeight = (int) (screenSize.height*0.8);
		view.setSize(screenWidth, screenHeight);
		//app.pack();
		view.setLocationRelativeTo(null);
		view.setVisible(true);
*/
	}


}
