package More;

import javax.microedition.io.Connector;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

/**
 *
 * @author Elyes
 */
public class SMS implements Runnable {

    MessageConnection clientConn;
    String num;
    String cont;
    boolean resultat;

    public SMS() {
    }

    public boolean sendSms(String num, String cont) {
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
        } catch (Exception e) {
            resultat = false;
        }

    }
}
