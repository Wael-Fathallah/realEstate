package More;

import javax.microedition.io.Connector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

/**
 *
 * @author Elyes
 */
public class SMS extends TextBox implements Runnable, CommandListener {

    Display disp;
    //Screens
    Displayable last;
    //Commands
    Command send = new Command("Envoyer", Command.SCREEN, 1);
    Command back = new Command("Back", Command.BACK, 1);
    //Other
    MessageConnection clientConn;
    String num;
    String cont;
    boolean resultat;

    private SMS() {
        super("Message SMS", null, 140, TextField.ANY);
        this.addCommand(back);
        this.addCommand(send);
        this.setCommandListener(this);
    }

    public SMS(MIDlet m, String num) {
        this();
        disp = Display.getDisplay(m);
        last = disp.getCurrent();
        this.num = num;
    }

    public boolean sendSMS(String num, String cont) {
        this.resultat = true;
        if (cont.length() > 0 && cont.length() <= 160) {
            this.num = num;
            this.cont = cont;
            try {
                Thread th = new Thread(this);
                th.start();
                th.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        } else {
            return false;
        }
        return resultat;
    }

    public void run() {
        try {
            clientConn = (MessageConnection) Connector.open("sms://" + num);
        } catch (Exception e) {
            resultat = false;
        }

        try {
            TextMessage textmessage = (TextMessage) clientConn.newMessage(MessageConnection.TEXT_MESSAGE);
            textmessage.setAddress("sms://" + num);
            textmessage.setPayloadText(cont);
            clientConn.send(textmessage);
            clientConn.close();
        } catch (Exception e) {
            resultat = false;
        }

    }

    public void commandAction(Command c, Displayable d) {
        if (c == back && d == this) {
            disp.setCurrent(last);
        }
        if (c == send && d == this) {
            if (this.getString().length() > 0 && sendSMS(num, this.getString()) == true) {
                disp.setCurrent(new Alert("Info", "SMS envoyé", null, AlertType.INFO), last);
            } else {
                disp.setCurrent(new Alert("Erreur", "l'envoi a échoué", null, AlertType.ERROR), last);
            }
        }
    }
}
