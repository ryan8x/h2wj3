/**
 *
 * @author Ryan L.
 */

package com.ryanliang.inventorygui;

public interface Viewable {
	
	public void setModel(Modellable model);
	public void start();
	public void update(UpdateType ut);
}
