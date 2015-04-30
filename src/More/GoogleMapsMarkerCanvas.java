/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package More;

import com.jappit.midmaps.googlemaps.GoogleMaps;
import com.jappit.midmaps.googlemaps.GoogleMapsCoordinates;
import com.jappit.midmaps.googlemaps.GoogleMapsMarker;
import com.jappit.midmaps.googlemaps.GoogleStaticMap;
import com.jappit.midmaps.googlemaps.GoogleStaticMapHandler;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;



public class GoogleMapsMarkerCanvas extends GoogleMapsTestCanvas implements GoogleStaticMapHandler
{
	GoogleMaps gMaps = null;
	GoogleStaticMap map = null;
	Command zoomInCommand, zoomOutCommand;
	public GoogleMapsMarkerCanvas(MIDlet m, Displayable testListScreen, float lalt, float lon)
	{
		super(m, testListScreen);
                addCommand(zoomInCommand = new Command("Zoom in", Command.OK, 1));
		addCommand(zoomOutCommand = new Command("Zoom out", Command.OK, 2));
		gMaps = new GoogleMaps();
		map = gMaps.createMap(getWidth(), getHeight(), GoogleStaticMap.FORMAT_PNG);
		map.setHandler(this);
		map.setCenter(new GoogleMapsCoordinates( lalt, lon));
		GoogleMapsMarker redMarker = new GoogleMapsMarker(new GoogleMapsCoordinates( lalt, lon ));
		redMarker.setColor(GoogleStaticMap.COLOR_BLACK);
		redMarker.setSize(GoogleMapsMarker.SIZE_MID);
		redMarker.setLabel('+');
                
		map.addMarker(redMarker);
		map.setZoom(10);
		map.update();

   
	}
        
	protected void paint(Graphics g)
	{
		map.draw(g, 0, 0, Graphics.TOP | Graphics.LEFT);
	}
	public void GoogleStaticMapUpdateError(GoogleStaticMap map, int errorCode, String errorMessage)
	{
		showError("map error: " + errorCode + ", " + errorMessage);
	}
	public void GoogleStaticMapUpdated(GoogleStaticMap map)
	{
		repaint();
	}
        protected void keyPressed(int key)
	{
		int gameAction = getGameAction(key);
		if(gameAction == Canvas.UP || gameAction == Canvas.RIGHT || gameAction == Canvas.DOWN || gameAction == Canvas.LEFT)
		{
			map.move(gameAction);
		}
                
	}
	public void commandAction(Command c, Displayable d)
	{
		super.commandAction(c, d);
		if(c == zoomInCommand)
			map.zoomIn();
		else if(c == zoomOutCommand)
			map.zoomOut();
	}
}
