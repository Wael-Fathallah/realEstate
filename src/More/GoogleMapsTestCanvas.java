/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package More;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

abstract public class GoogleMapsTestCanvas extends Canvas implements CommandListener
{
	Command back;
	
	Displayable testListScreen;
	MIDlet midlet;
	
	public GoogleMapsTestCanvas(MIDlet m, Displayable testListScreen)
	{
		this.midlet = m;
		this.testListScreen = testListScreen;
		
		addCommand(back = new Command("Back", Command.BACK, 1));
		
		setCommandListener(this);
	}
	
	public void commandAction(Command c, Displayable d)
	{
		if(c == back)
		{
			Display.getDisplay(midlet).setCurrent(testListScreen);
		}
	}
	void showError(String message)
	{
		Alert error = new Alert("Error", message, null, AlertType.ERROR);
		
		Display.getDisplay(midlet).setCurrent(error, this);
	}
}
