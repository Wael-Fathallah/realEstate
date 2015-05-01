/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package More;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;

/**
 *
 * @author Hamza
 */
public abstract class PlayerManager implements Runnable, CommandListener, PlayerListener {

    Form form;
    Player player;
    String locator;
    Display display;

    private Command stopCommand;
    private Command pauseCommand;
    private Command startCommand;

    public PlayerManager(Form form, String locator, Display display) {
        this.form = form;
        this.locator = locator;
        this.display = display;

        // stop, pause and restart commands
        stopCommand = new Command("Stop", Command.STOP, 1);
        pauseCommand = new Command("Pause", Command.ITEM, 1);
        startCommand = new Command("Play", Command.ITEM, 1);

        // the form acts as the interface to stop and pause the media
        form.addCommand(stopCommand);
        form.addCommand(pauseCommand);

    }

    public void run() {

        try {

		
            
            player = Manager.createPlayer(locator);

            // listener à l'ecoute des evenement de pause, play ...
            player.addPlayerListener(this);
	
            player.setLoopCount(-1); 
            player.prefetch();
            player.realize();

            player.start(); // and start
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }

    }

    public void commandAction(Command command, Displayable disp) {

        if (disp instanceof Form) {

			// if showing form, means the media is being played
            // and the user is trying to stop or pause the player
            try {

                if (command == stopCommand) { // if stopping the media play

                    player.close(); // close the player
                    form.removeCommand(startCommand); // remove the start command
                    form.removeCommand(pauseCommand); // remove the pause command
                    form.removeCommand(stopCommand);  // and the stop command

                } else if (command == pauseCommand) { // if pausing

                    player.stop(); // pauses the media, note that it is called stop
                    form.removeCommand(pauseCommand); // remove the pause command
                    form.addCommand(startCommand); // add the start (restart) command
                } else if (command == startCommand) { // if restarting

                    player.start(); // starts from where the last pause was called
                    form.removeCommand(startCommand);
                    form.addCommand(pauseCommand);
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }

    }

    // gére les evenement du player
    public void playerUpdate(Player player, String event, Object eventData) {

		// if the event is that the player has started, show the form
        // but only if the event data indicates that the event relates to newly
        // stated player, as the STARTED event is fired even if a player is
        // restarted. Note that eventData indicates the time at which the start
        // event is fired.
        if (event.equals(PlayerListener.STARTED)
                && new Long(0L).equals((Long) eventData)) {

            display.setCurrent(form);
        
        } else if (event.equals(PlayerListener.CLOSED)) {

            form.deleteAll(); // clears the form of any previous controls
        }
    }
    
}
